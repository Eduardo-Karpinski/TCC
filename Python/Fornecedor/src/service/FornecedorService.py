from repository.FornecedorRepository import Fornecedor;
from repository import FornecedorRepository

def getById(id: int):
	Session = FornecedorRepository.getSession()
	with Session() as session:
			return session.query(Fornecedor).filter_by(id=id).first()
	
def get():
	return None

def delete():
	return None

def update():
	return None

def save():
	return None