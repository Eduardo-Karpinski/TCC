# FORNECEDOR
### http://localhost:8080/fornecedor (POST)
### http://localhost:8080/fornecedor/{id} (PUT)
### http://localhost:8080/fornecedor/{id} (DELETE)
### http://localhost:8080/fornecedor?page=0&size=10&sort=id,ASC (GET)
### http://localhost:8080/fornecedor/{id} (GET)
```
Exemplo do body
{
    "nome": "teste",
    "cnpj": "teste",
    "telefone": "teste",
    "cep": "teste",
    "endereco": "teste",
    "complemento": "teste",
    "bairro": "teste",
    "municipio": "teste",
    "estado": "teste"
}
```

# Produto
### http://localhost:8080/produto (POST)
### http://localhost:8080/produto/{id} (PUT)
### http://localhost:8080/produto/{id} (DELETE)
### http://localhost:8080/produto?page=0&size=10&sort=id,ASC (GET)
### http://localhost:8080/produto/{id} (GET)
```
Exemplo do body
{
    "nome": "teste",
    "descricao": "teste",
    "unidadeDeMedida": "teste",
    "ean": "teste",
    "preco": 8.00,
    "custo": 5.00,
    "idFornecedor": 1
}
```

# Estoque
### http://localhost:8080/estoque/{id} (PUT)
### http://localhost:8080/estoque?page=0&size=10&sort=id,ASC (GET)
### http://localhost:8080/estoque/{id} (GET)
```
Exemplo do body
{
   "idProduto": 1,
    "quantidade": 10,
    "quantidadeMinima": 5
}
```

# Venda
### http://localhost:8083/venda (POST)
### http://localhost:8083/venda/{id}/{idProduto}/{quantidade} (POST)
### http://localhost:8083/venda/finaliza/{id} (POST)
### http://localhost:8083/venda/{id} (DELETE)
### http://localhost:8083/venda/produto/{id}/{idProduto} (DELETE)
### http://localhost:8083/venda?page=0&size=10&sort=id,ASC (GET)
### http://localhost:8083/venda/{id} (GET)

# Relatorio
### http://localhost:8084/relatorio/2023-07-12T00:00:00/2023-07-12T23:59:59 (GET)