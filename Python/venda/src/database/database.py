from sqlalchemy import create_engine, Column, Integer, String, Numeric, ForeignKey, Boolean, DateTime, Table
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, declarative_base, relationship
from datetime import datetime

engine = create_engine("mysql+pymysql://root:root@127.0.0.1/python")
base = declarative_base()

venda_produtos = Table(
    'venda_produtos',
    base.metadata,
    Column('venda_id', Integer, ForeignKey('venda.id')),
    Column('produtos_id', Integer, ForeignKey('venda_produto.id'))
)

class Venda(base):
    __tablename__ = "venda"
    id = Column(Integer, primary_key=True, autoincrement=True)
    data = Column(DateTime, nullable=False)
    is_finalizada = Column(Boolean, nullable=False, default=False)
    produtos = relationship("VendaProduto", secondary=venda_produtos, back_populates="vendas")

    def __init__(self):
        self.data = datetime.now()
        self.is_finalizada = False
        
    def to_dict(self):
        return {
            "id": self.id,
            "data": self.data.isoformat(),
            "is_finalizada": self.is_finalizada,
            "produtos": [produto.to_dict() for produto in self.produtos]
        }

class VendaProduto(base):
    __tablename__ = "venda_produto"
    id = Column(Integer, primary_key=True, autoincrement=True)
    produto_id = Column(Integer, ForeignKey("produto.id"), nullable=False)
    quantidade = Column(Numeric(precision=10, scale=2), nullable=False)
    preco = Column(Numeric(precision=10, scale=2), nullable=False)
    vendas = relationship("Venda", secondary=venda_produtos, back_populates="produtos")
    produto = relationship("Produto", back_populates="venda_produtos")
    
    def to_dict(self):
        return {
            "id": self.id,
            "produto": self.produto.to_dict(),
            "quantidade": float(self.quantidade),
            "preco": float(self.preco)
        }

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
    venda_produtos = relationship('VendaProduto', back_populates='produto')
    
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