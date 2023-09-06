from datetime import datetime
from database import database
from database.database import Venda
from database.database import VendaProduto
from fastapi.responses import JSONResponse
from fastapi import HTTPException
from sqlalchemy import asc, desc
import requests

def findAllByIsFinalizadaTrueAndDataBetween(data1: datetime, data2: datetime):
	Session = database.get_session()
	with Session() as session:
		vendas_finalizadas = session.query(Venda).filter(
			Venda.data.between(data1, data2),
			Venda.is_finalizada == True
		).all()
		return JSONResponse(content=[venda.to_dict() for venda in vendas_finalizadas], status_code=200)
    
def finaliza(id: int):
    
	Session = database.get_session()
	with Session() as session:
		venda = session.query(Venda).filter_by(id=id).first()
  
		if not venda:
			raise HTTPException(status_code=404, detail="Venda não encontrada")
  
		venda.is_finalizada = True
		session.commit()
  
		for produto_venda in venda.produtos:
			requests.put(f"http://localhost:8082/estoque/{produto_venda.produto_id}/{produto_venda.quantidade}")
  
		return JSONResponse(content=None, status_code=200)

def removeProduto(venda_id: int, produto_id: int):
    
	Session = database.get_session()
	with Session() as session:
		venda = session.query(Venda).filter_by(id=venda_id).first()

		if not venda:
			raise HTTPException(status_code=404, detail="Venda não encontrada")

		venda_produto = session.query(VendaProduto).filter_by(id=produto_id).first()

		if not venda_produto:
			raise HTTPException(status_code=404, detail="Produto não encontrado")

		if venda_produto in venda.produtos:
			venda.produtos.remove(venda_produto)
			session.commit()
			session.delete(venda_produto)
			session.commit()
			return JSONResponse(content=None, status_code=200)

def addProduto(venda_id: int, produto_id: int, quantidade: float):

	response = requests.get(f"http://localhost:8081/produto/{produto_id}")

	if response.status_code == 200:

		Session = database.get_session()
		with Session() as session:
			venda = session.query(Venda).filter_by(id=venda_id).first()

			if not venda:
				raise HTTPException(status_code=404, detail="Venda não encontrada")

			venda_produto = VendaProduto()
			venda_produto.produto_id = produto_id
			venda_produto.quantidade = quantidade
			venda_produto.preco = response.json()['preco']
   
			session.add(venda_produto)
			session.commit()
   
			venda.produtos.append(venda_produto)

			session.add(venda)
			session.commit()

			return JSONResponse(content=None, status_code=200)
	else:
		raise HTTPException(status_code=404, detail="Produto não encontrado")
 
def get_by_id(id: int):
	Session = database.get_session()
	with Session() as session:
		
		venda = session.query(Venda).filter_by(id=id).first()

		if not venda:
			raise HTTPException(status_code=404, detail="Venda não encontrada")

		return JSONResponse(content=venda.to_dict(), status_code=200)

def get(page: int, size: int, sort: str):
	Session = database.get_session()
	split = sort.split(",")

	with Session() as session:
		orderBy = asc(split[0].strip()) if split[1].strip().lower() == "asc" else desc(split[0].strip())

		vendas = session.query(Venda) \
						.order_by(orderBy) \
						.offset(page * size) \
						.limit(size) \
						.all()

		if not vendas:
			raise HTTPException(status_code=404, detail="Nenhum venda encontrada")

		return JSONResponse(content=[venda.to_dict() for venda in vendas], status_code=200)

def delete(id: int):
	Session = database.get_session()
	with Session() as session:
		venda = session.query(Venda).filter_by(id=id).first()
  
		if not venda:
			raise HTTPException(status_code=404, detail="Venda não encontrada")

		try:
      
			for venda_produto in venda.produtos:
				session.delete(venda_produto)
      
			session.delete(venda)
			session.commit()
			return JSONResponse(content=None, status_code=200)
		except Exception as e:
			session.rollback()
			raise HTTPException(status_code=500, detail=str(e))

def save():
	Session = database.get_session()
	with Session() as session:
		try:
			venda = Venda()
			session.add(venda)
			session.commit()
			return JSONResponse(content=None, status_code=200)
		except Exception as e:
			session.rollback()
			raise HTTPException(status_code=500, detail=str(e))