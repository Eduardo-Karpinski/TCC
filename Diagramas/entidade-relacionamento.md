```mermaid
erDiagram
    PRODUTO }|..|{ FORNECEDOR : "Tem"
    ESTOQUE }|..|{ PRODUTO : "Tem"
    VENDA }|..|{ VENDAPRODUTO : "Tem"
    VENDAPRODUTO }|..|{ PRODUTO : "Tem"

    PRODUTO {
        Long id
        String nome
        String descricao
        String unidadeDeMedida
        String ean
        BigDecimal preco
        BigDecimal custo
        FORNECEDOR fornecedor
    }
    FORNECEDOR {
        Long id
        String nome
        String cnpj
        String telefone
        String cep
        String endereco
        String complemento
        String bairro
        String municipio
        String estado
    }
    ESTOQUE {
        Long id
        PRODUTO produto
        BigDecimal quantidade
        BigDecimal quantidadeMinima
    }
    VENDA {
        Long id
        List~VENDAPRODUTO~ produtos
        LocalDateTime data
        Boolean isFinalizada
    }
    VENDAPRODUTO {
        Long id
        PRODUTO produto
        -BigDecimal quantidade
        -BigDecimal preco
    }
```