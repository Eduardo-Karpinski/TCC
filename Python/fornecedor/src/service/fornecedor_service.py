from database.database import Fornecedor;
from database import database
from sqlalchemy import asc, desc
from fastapi.responses import JSONResponse
from dto.fornecedor_DTO import FornecedorDTO;
from fastapi import HTTPException

def get_by_id(id: int):
	Session = database.get_session()
	with Session() as session:
		
		fornecedor = session.query(Fornecedor).filter_by(id=id).first()
  
		if not fornecedor:
			raise HTTPException(status_code=404, detail="Fornecedor não encontrado")
     
		return JSONResponse(content=fornecedor.to_dict(), status_code=200)
	
def get(page: int, size: int, sort: str):
	Session = database.get_session()
	split = sort.split(",")

	with Session() as session:
		orderBy = asc(split[0].strip()) if split[1].strip().lower() == "asc" else desc(split[0].strip())

		fornecedores = session.query(Fornecedor) \
                              .order_by(orderBy) \
                              .offset(page * size) \
                              .limit(size) \
                              .all()

		if not fornecedores:
			raise HTTPException(status_code=404, detail="Nenhum fornecedor encontrado")

		return JSONResponse(content=[fornecedor.to_dict() for fornecedor in fornecedores], status_code=200)
	    
def delete(id: int):
	Session = database.get_session()
	with Session() as session:
		fornecedor = session.query(Fornecedor).filter_by(id=id).first()

		if not fornecedor:
			raise HTTPException(status_code=404, detail="Fornecedor não encontrado")
     
		try:
			session.delete(fornecedor)
			session.commit()
			return JSONResponse(content=None, status_code=200)
		except Exception as e:
			session.rollback()
			raise HTTPException(status_code=500, detail=str(e))

def update(id: int, fornecedorDTO: FornecedorDTO):
    
	Session = database.get_session()
	with Session() as session:
    
		fornecedor = session.query(Fornecedor).filter_by(id=id).first()

		if not fornecedor:
			raise HTTPException(status_code=404, detail="Fornecedor não encontrado")

		try:
			for attr, value in fornecedorDTO.model_dump().items():
				setattr(fornecedor, attr, value)

			session.commit()

			return JSONResponse(content=None, status_code=200)
		except Exception as e:
			session.rollback()
			raise HTTPException(status_code=500, detail=str(e))

def save(fornecedorDTO: FornecedorDTO):

	Session = database.get_session()
	with Session() as session:
		try:
			fornecedor = Fornecedor(**fornecedorDTO.model_dump())
			session.add(fornecedor)
			session.commit()
			return JSONResponse(content=None, status_code=200)
		except Exception as e:
			session.rollback()
			raise HTTPException(status_code=500, detail=str(e))