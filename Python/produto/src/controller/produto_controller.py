from fastapi import APIRouter
from service import produto_service
from dto.produto_DTO import ProdutoDTO;

router = APIRouter()

@router.get("/valorBaixo")
def get_all_by_valor_baixo():
    return produto_service.get_all_by_valor_baixo()

@router.get("/{id}")
def get_by_id(id: int):
	return produto_service.get_by_id(id)

@router.get("")
def get(page: int = 0, size: int = 10, sort: str = "id,ASC"):
	return produto_service.get(page, size, sort)

@router.delete("/{id}")
def delete(id: int):
	return produto_service.delete(id)

@router.put("/{id}")
def update(id: int, produtoDTO: ProdutoDTO):
	return produto_service.update(id, produtoDTO)

@router.post("")
def save(produtoDTO: ProdutoDTO):
	return produto_service.save(produtoDTO)