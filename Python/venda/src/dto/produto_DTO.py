from pydantic import BaseModel
from decimal import Decimal

class ProdutoDTO(BaseModel):
    nome: str
    descricao: str
    unidade_de_medida: str
    ean: str
    preco: Decimal
    custo: Decimal
    fornecedor_id: int