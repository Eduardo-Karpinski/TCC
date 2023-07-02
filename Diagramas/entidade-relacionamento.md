```mermaid
erDiagram
    PRODUTO }|..|{ FORNECEDOR : "Tem"
    ESTOQUE }|..|{ PRODUTO : "Tem"
    VENDA }|..|{ PRODUTO : "Tem"

    PRODUTO {
        Long id
        String nome
        String descricao
        UnidadeDeMedida unidadeDeMedida
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
        Estado estado
    }
    ESTOQUE {
        Long id
        PRODUTO produto
        BigDecimal quantidade
        BigDecimal quantidadeMinima
    }
    VENDA {
        Long id
        List~PRODUTO~ produtos
        LocalDateTime data
    }
```