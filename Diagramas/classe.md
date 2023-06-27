```mermaid
classDiagram
    direction RL
    
    Produto "1" --> "1" Fornecedor
    Estoque "1" --> "1" Produto

    %% Produto tera um fornecedor e não varios, pois o preço pode varias por fornecedor
    %% isso pode/vai ocasionar em produtos repitidos
    class Produto{
        -Long id
        -String nome
        -String descricao
        -String unidadeDeMedida
        -String ean
        -BigDecimal preco
        -BigDecimal custo
        -Fornecedor fornecedor
    }

    class Fornecedor{
        -Long id
        -String nome
        -String cnpj
        -String telefone
        -String cep
        -String endereco
        -String complemento
        -String bairro
        -String municipio
        -String estado
    }

    class Estoque{
        -Long id
        -Produto produto
        -BigDecimal Quantidade
        -BigDecimal QuantidadeMinima
    }

    class Relatorio{
        +gerarRelatorio()
    }
```