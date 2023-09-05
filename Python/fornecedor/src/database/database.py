from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, declarative_base

engine = create_engine("mysql+pymysql://root:root@127.0.0.1/python")
base = declarative_base()

class Fornecedor(base):
    __tablename__ = "fornecedor"
    id = Column(Integer, primary_key=True, autoincrement=True)
    nome = Column(String(100), nullable=False)
    cnpj = Column(String(100), unique=True, nullable=False)
    telefone = Column(String(100), nullable=False)
    cep = Column(String(100), nullable=False)
    endereco = Column(String(100), nullable=False)
    complemento = Column(String(100), nullable=False)
    bairro = Column(String(100), nullable=False)
    municipio = Column(String(100), nullable=False)
    estado = Column(String(100), nullable=False)
    
    def to_dict(self):
        return {
            "id": self.id,
            "nome": self.nome,
            "cnpj": self.cnpj,
            "telefone": self.telefone,
            "cep": self.cep,
            "endereco": self.endereco,
            "complemento": self.complemento,
            "bairro": self.bairro,
            "municipio": self.municipio,
            "estado": self.estado
        }

def create_data_base():
    base.metadata.create_all(engine)

def get_session():
    return sessionmaker(bind=engine)