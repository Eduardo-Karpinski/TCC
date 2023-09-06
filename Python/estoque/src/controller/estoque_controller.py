from fastapi import APIRouter
from service import estoque_service
from dto.estoque_DTO import EstoqueDTO;
from decimal import Decimal

router = APIRouter()

@router.get("/estoqueBaixo")
def find_all_estoque_baixo():
	return estoque_service.find_all_estoque_baixo()

@router.put("/{produto_id}/{quantidade}")
def atualiza_estoque(produto_id: int, quantidade: Decimal):
	return estoque_service.atualiza_estoque(produto_id, quantidade)

@router.get("/{id}")
def get_by_id(id: int):
	return estoque_service.get_by_id(id)

@router.get("")
def get(page: int = 0, size: int = 10, sort: str = "id,ASC"):
	return estoque_service.get(page, size, sort)

@router.put("/{id}")
def update(id: int, estoqueDTO: EstoqueDTO):
	return estoque_service.update(id, estoqueDTO)

@router.post("")
def save(estoqueDTO: EstoqueDTO):
	return estoque_service.save(estoqueDTO)