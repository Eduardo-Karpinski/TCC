from pydantic import BaseModel
from decimal import Decimal

class EstoqueDTO(BaseModel):
    quantidade: Decimal
    quantidade_minima: Decimal
    produto_id: int