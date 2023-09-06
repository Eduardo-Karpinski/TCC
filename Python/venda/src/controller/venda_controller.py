from fastapi import APIRouter
from datetime import datetime
from service import venda_service

router = APIRouter()

@router.get("/{data1}/{data2}")
def findAllByIsFinalizadaTrueAndDataBetween(data1: datetime, data2: datetime):
    return venda_service.findAllByIsFinalizadaTrueAndDataBetween(data1, data2)

@router.post("/finaliza/{id}")
def finaliza(id: int):
	return venda_service.finaliza(id)

@router.delete("/produto/{venda_id}/{produto_id}")
def removeProduto(venda_id: int, produto_id: int):
	 return venda_service.removeProduto(venda_id, produto_id)

@router.post("/{venda_id}/{produto_id}/{quantidade}")
def addProduto(venda_id: int, produto_id: int, quantidade: float):
    return venda_service.addProduto(venda_id, produto_id, quantidade)
 
@router.get("/{id}")
def get_by_id(id: int):
	return venda_service.get_by_id(id)

@router.get("")
def get(page: int = 0, size: int = 10, sort: str = "id,ASC"):
	return venda_service.get(page, size, sort)

@router.delete("/{id}")
def delete(id: int):
	return venda_service.delete(id)

@router.post("")
def save():
	return venda_service.save()