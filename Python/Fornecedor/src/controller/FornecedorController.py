from fastapi import APIRouter
from service import FornecedorService
from dto.FornecedorDTO import FornecedorDTO;

router = APIRouter()

@router.get("/{id}")
def getById(id: int):
	return FornecedorService.getById(id)

@router.get("")
def get(page: int = 0, size: int = 10, sort: str = "id,ASC"):
	return FornecedorService.get(page, size, sort)

@router.delete("/{id}")
def delete(id: int):
	return FornecedorService.delete(id)

@router.put("/{id}")
def update(id: int, fornecedorDTO: FornecedorDTO):
	return FornecedorService.update(id, fornecedorDTO)

@router.post("")
def save(fornecedorDTO: FornecedorDTO):
	return FornecedorService.save(fornecedorDTO)