from pydantic import BaseModel

class VendaProduto(BaseModel):
    id: int
    quantidade: float
    preco: float
    produto_id: int