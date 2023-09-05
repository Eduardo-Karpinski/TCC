from dto.produto_DTO import ProdutoDTO;
from database import database
from database.database import Produto
from fastapi.responses import JSONResponse
from fastapi import HTTPException
from sqlalchemy import asc, desc, func
import requests

def get_all_by_valor_baixo():
	Session = database.get_session()
	with Session() as session:
		produtos = session.query(Produto).filter(func.round((Produto.custo + Produto.custo/5), 2) >= Produto.preco).all()
		return JSONResponse(content=[produto.to_dict() for produto in produtos], status_code=200)

def get_by_id(id: int):
	Session = database.get_session()
	with Session() as session:
		
		produto = session.query(Produto).filter_by(id=id).first()
  
		if not produto:
			raise HTTPException(status_code=404, detail="Produto não encontrado")
     
		return JSONResponse(content=produto.to_dict(), status_code=200)

def get(page: int, size: int, sort: str):
	Session = database.get_session()
	split = sort.split(",")

	with Session() as session:
		orderBy = asc(split[0].strip()) if split[1].strip().lower() == "asc" else desc(split[0].strip())

		produtos = session.query(Produto) \
                              .order_by(orderBy) \
                              .offset(page * size) \
                              .limit(size) \
                              .all()

		if not produtos:
			raise HTTPException(status_code=404, detail="Nenhum produto encontrado")

		return JSONResponse(content=[produto.to_dict() for produto in produtos], status_code=200)

def delete(id: int):
	Session = database.get_session()
	with Session() as session:
		produto = session.query(Produto).filter_by(id=id).first()

		if not produto:
			raise HTTPException(status_code=404, detail="Produto não encontrado")
     
		try:
			session.delete(produto)
			session.commit()
			return JSONResponse(content=None, status_code=200)
		except Exception as e:
			session.rollback()
			raise HTTPException(status_code=500, detail=str(e))

def update(id: int, produtoDTO: ProdutoDTO):
	Session = database.get_session()
	with Session() as session:
    
		produto = session.query(Produto).filter_by(id=id).first()

		if not produto:
			raise HTTPException(status_code=404, detail="Produto não encontrado")

		try:
			for attr, value in produtoDTO.model_dump().items():
				setattr(produto, attr, value)

			session.commit()

			return JSONResponse(content=None, status_code=200)
		except Exception as e:
			session.rollback()
			raise HTTPException(status_code=500, detail=str(e))

def save(produtoDTO: ProdutoDTO):
	response = requests.get(f"http://localhost:8080/fornecedor/{produtoDTO.fornecedor_id}")
    
	if response.status_code == 200:
		Session = database.get_session()
		with Session() as session:
			try:
				produto = Produto(**produtoDTO.model_dump())
				session.add(produto)
				session.commit()

				estoque = {
        			'produto_id': produto.id,
					'quantidade': 0,
					'quantidade_minima': 0
				}

				print("teste: ", estoque)

				requests.post("http://localhost:8082/estoque", json=estoque)
	
				return JSONResponse(content=None, status_code=200)
			except Exception as e:
				session.rollback()
				raise HTTPException(status_code=500, detail=str(e))
	else:
		raise HTTPException(status_code=404, detail="Fornecedor não encontrado")