from fastapi import APIRouter
from service import FornecedorService

router = APIRouter()
path = "/fornecedor/"

@router.get(path+"{id}")
def getById(id: int):
	return FornecedorService.getById(id)

def get():
	return None

def delete():
	return None

def update():
	return None

def save():
	return None