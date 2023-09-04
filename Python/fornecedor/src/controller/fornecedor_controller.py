from fastapi import APIRouter
from service import fornecedor_service
from dto.fornecedor_DTO import FornecedorDTO;

router = APIRouter()

@router.get("/{id}")
def get_by_id(id: int):
	return fornecedor_service.get_by_id(id)

@router.get("")
def get(page: int = 0, size: int = 10, sort: str = "id,ASC"):
	return fornecedor_service.get(page, size, sort)

@router.delete("/{id}")
def delete(id: int):
	return fornecedor_service.delete(id)

@router.put("/{id}")
def update(id: int, fornecedorDTO: FornecedorDTO):
	return fornecedor_service.update(id, fornecedorDTO)

@router.post("")
def save(fornecedorDTO: FornecedorDTO):
	return fornecedor_service.save(fornecedorDTO)