```mermaid
sequenceDiagram
    participant Requisitor
    participant Sistema

    Requisitor -> Sistema: Solicitar criação de produto
    Sistema -> Sistema: Validação de dados
    alt Dados válidos
        Sistema -> Sistema: Criar novo produto
        Sistema -> Sistema: Persistir dados do produto
        Sistema -> Requisitor: Produto criado com sucesso
    else Dados inválidas
        Sistema -> Requisitor: Exibir mensagem de erro
    end
```