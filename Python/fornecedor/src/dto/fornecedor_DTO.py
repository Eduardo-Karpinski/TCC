from pydantic import BaseModel

class FornecedorDTO(BaseModel):
    nome: str
    cnpj: str
    telefone: str
    cep: str
    endereco: str
    complemento: str
    bairro: str
    municipio: str
    estado: str