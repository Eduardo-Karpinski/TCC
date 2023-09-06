from pydantic import BaseModel
from datetime import datetime
from typing import List
from dto.venda_produto_DTO import VendaProduto

class Venda(BaseModel):
    id: int
    produtos: List["VendaProduto"]
    data: datetime
    is_finalizada: bool