
# API Produto Consumer

## Objetivo do Projeto

Este projeto tem como objetivo consumir mensagens de uma fila RabbitMQ e realizar operações de criação, atualização e remoção de produtos em um banco de dados. A aplicação é um microsserviço que atua como um consumidor, processando as mensagens recebidas e atualizando o estado dos produtos no banco de dados conforme especificado nas mensagens.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal do projeto.
- **Spring Boot**: Framework para facilitar a configuração e desenvolvimento da aplicação.
- **Spring Data JPA**: Utilizado para interagir com o banco de dados de forma simplificada.
- **RabbitMQ**: Broker de mensagens utilizado para o processamento assíncrono de eventos.
- **PostgreSQL**: Banco de dados para persistência das informações dos produtos.
- **Flyway**: Ferramenta de versionamento e migração de banco de dados.

## Padrões de Projeto Utilizados

- **Listener Pattern**: Para consumir as mensagens de eventos do RabbitMQ e realizar as operações correspondentes no banco de dados.
- **Producer-Consumer**: Estrutura onde a aplicação age como um consumidor de mensagens e processa conforme a lógica de negócio.
- **Repository Pattern**: Para abstrair o acesso aos dados e operações do banco de dados.
- **DTO (Data Transfer Object)**: Para definir a estrutura de dados trocada entre as camadas e com a fila.

## Estrutura de Pastas

```plaintext
api-produto-consumer/
├── src/main/java/com/github/maikoncanuto/consumer
│   ├── config/                 # Configurações da aplicação, incluindo RabbitMQ
│   ├── domain/
│   │   ├── dto/                # Objetos de Transferência de Dados (DTOs)
│   │   ├── entity/             # Entidades que representam tabelas do banco de dados
│   │   ├── exception/          # Exceções personalizadas
│   ├── listener/               # Classes que escutam e processam eventos da fila
│   ├── producer/               # Produtores de mensagens para outras filas ou tópicos
│   ├── repository/             # Repositórios para manipulação de dados no banco
│   ├── ApiProdutoConsumerApplication.java # Classe principal da aplicação
├── src/main/resources/
│   ├── application.properties  # Configurações da aplicação (e.g., banco de dados, RabbitMQ)
│   └── db/migration/           # Scripts de migração do banco de dados (Flyway)
├── Dockerfile                  # Dockerfile para construção da imagem da aplicação
├── pom.xml                     # Arquivo de configuração do Maven com as dependências
└── README.md                   # Documentação do projeto
```

### Explicação das Pastas

- **config/**: Contém configurações específicas do projeto, como o setup do RabbitMQ.
- **domain/dto/**: Contém os DTOs que representam a estrutura das mensagens trocadas com a fila.
- **domain/entity/**: Contém as entidades JPA que representam as tabelas do banco de dados.
- **domain/exception/**: Contém as exceções personalizadas para tratamento de erros específicos.
- **listener/**: Contém as classes que escutam as mensagens da fila e processam conforme a lógica de negócio.
- **producer/**: Contém classes que produzem mensagens para outras filas, caso seja necessário enviar resultados.
- **repository/**: Contém os repositórios responsáveis pela persistência de dados.

## Como Subir o Projeto

1. Certifique-se de ter o Docker instalado e configurado em sua máquina.
2. No diretório raiz do projeto, execute o seguinte comando para construir e subir a aplicação:
   ```bash
   docker-compose up --build
   ```
3. A aplicação estará disponível na porta especificada no `application.properties` ou no `docker-compose.yml`.

## Endpoints e Links

| Descrição              | URL                                  | Porta | Exemplo de Request e Response |
|------------------------|--------------------------------------|-------|--------------------------------|
| Swagger UI             | http://localhost:8081/swagger-ui.html | 8081  | -                              |
| RabbitMQ Management    | http://localhost:15672              | 15672 | -                              |

### Exemplo de Request e Response

#### Criação de Produto (Exemplo)

**Request**:
```json
{
    "id": 1,
    "traceId": "12345-abcde-67890",
    "nomeProduto": "Produto Teste",
    "quantidadeProduto": 100,
    "descricaoProduto": "Produto de teste para consumo",
    "precoProduto": 49.99
}
```

**Response**:
```json
{
    "data": {
        "id": 1,
        "traceId": "12345-abcde-67890",
        "nomeProduto": "Produto Teste",
        "quantidadeProduto": 100,
        "descricaoProduto": "Produto de teste para consumo",
        "precoProduto": 49.99
    },
    "errors": null
}
```

#### Atualização de Produto (Exemplo)

**Request**:
```json
{
    "traceId": "12345-abcde-67890",
    "nomeProduto": "Produto Teste Atualizado",
    "quantidadeProduto": 150,
    "descricaoProduto": "Produto de teste atualizado",
    "precoProduto": 59.99
}
```

**Response**:
```json
{
    "data": {
        "id": 1,
        "traceId": "12345-abcde-67890",
        "nomeProduto": "Produto Teste Atualizado",
        "quantidadeProduto": 150,
        "descricaoProduto": "Produto de teste atualizado",
        "precoProduto": 59.99
    },
    "errors": null
}
```

#### Remoção de Produto (Exemplo)

**Request**: `DELETE /v1/produtos/{traceId}`

**Response**:
```json
{
    "data": {
        "message": "Produto com traceId 12345-abcde-67890 removido com sucesso"
    },
    "errors": null
}
```

---

## Observações

Este projeto está configurado para usar Flyway para versionamento do banco de dados. Certifique-se de que a configuração do banco de dados está correta no arquivo `application.properties` para evitar erros de migração.
