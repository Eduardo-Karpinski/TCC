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
    "idFornecedor": 4
}
```