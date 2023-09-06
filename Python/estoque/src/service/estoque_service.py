from dto.estoque_DTO import EstoqueDTO;
from decimal import Decimal
from database import database
from database.database import Estoque
from fastapi.responses import JSONResponse
from fastapi import HTTPException
from sqlalchemy import asc, desc
import requests

def find_all_estoque_baixo():

    Session = database.get_session()
    with Session() as session:
    
        estoques = session.query(Estoque).filter(Estoque.quantidade <= Estoque.quantidade_minima).all()
        return JSONResponse(content=[estoque.to_dict() for estoque in estoques], status_code=200)

def atualiza_estoque(produto_id: int, quantidade: Decimal):
    
    response = requests.get(f"http://localhost:8081/produto/{produto_id}")
    
    if response.status_code == 200:
        
        Session = database.get_session()
        with Session() as session:
            
            estoque = session.query(Estoque).filter_by(produto_id=produto_id).first()

            if not estoque:
                raise HTTPException(status_code=404, detail="Estoque não encontrado")
            
            estoque.quantidade = estoque.quantidade - quantidade
            
            session.commit()
            
            return JSONResponse(content=None, status_code=200)
            
    else:
        raise HTTPException(status_code=404, detail="Produto não encontrado")

def get_by_id(id: int):
	Session = database.get_session()
	with Session() as session:
		
		estoque = session.query(Estoque).filter_by(id=id).first()
  
		if not estoque:
			raise HTTPException(status_code=404, detail="Estoque não encontrado")
     
		return JSONResponse(content=estoque.to_dict(), status_code=200)

def get(page: int, size: int, sort: str):
	Session = database.get_session()
	split = sort.split(",")

	with Session() as session:
		orderBy = asc(split[0].strip()) if split[1].strip().lower() == "asc" else desc(split[0].strip())

		estoques = session.query(Estoque) \
                              .order_by(orderBy) \
                              .offset(page * size) \
                              .limit(size) \
                              .all()

		if not estoques:
			raise HTTPException(status_code=404, detail="Nenhum estoque encontrado")

		return JSONResponse(content=[estoque.to_dict() for estoque in estoques], status_code=200)

def update(id: int, estoqueDTO: EstoqueDTO):
	Session = database.get_session()
	with Session() as session:
    
		estoque = session.query(Estoque).filter_by(id=id).first()

		if not estoque:
			raise HTTPException(status_code=404, detail="Estoque não encontrado")

		try:
			for attr, value in estoqueDTO.model_dump().items():
				setattr(estoque, attr, value)

			session.commit()

			return JSONResponse(content=None, status_code=200)
		except Exception as e:
			session.rollback()
			raise HTTPException(status_code=500, detail=str(e))

def save(estoqueDTO: EstoqueDTO):
    Session = database.get_session()
    with Session() as session:
        try:
            estoque = Estoque(**estoqueDTO.model_dump())
            session.add(estoque)
            session.commit()
            return JSONResponse(content=None, status_code=200)
        except Exception as e:
            session.rollback()
            raise HTTPException(status_code=500, detail=str(e))