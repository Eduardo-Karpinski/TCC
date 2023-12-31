from sqlalchemy import create_engine, Column, Integer, String, Numeric, ForeignKey
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, declarative_base, relationship

engine = create_engine("mysql+pymysql://root:root@127.0.0.1/python")
base = declarative_base()

class Produto(base):
    __tablename__ = "produto"
    id = Column(Integer, primary_key=True, autoincrement=True)
    nome = Column(String(100), nullable=False)
    descricao = Column(String(100), nullable=False)
    unidade_de_medida = Column(String(100), nullable=False)
    ean = Column(String(15), unique=True, nullable=False)
    preco = Column(Numeric(precision=10, scale=2), nullable=False)
    custo = Column(Numeric(precision=10, scale=2), nullable=False)
    fornecedor_id = Column(Integer, ForeignKey('fornecedor.id'), nullable=False)
    fornecedor = relationship("Fornecedor", back_populates="produtos")
    
    def to_dict(self):
        return {
            "id": self.id,
            "nome": self.nome,
            "descricao": self.descricao,
            "unidade_de_medida": self.unidade_de_medida,
            "ean": self.ean,
            "preco": float(self.preco),
            "custo": float(self.custo),
            "fornecedor_id": self.fornecedor_id
        }

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
    produtos = relationship("Produto", back_populates="fornecedor")
    
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