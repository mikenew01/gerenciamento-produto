
# Projeto API Produto Producer

## Objetivo do Projeto

O objetivo deste projeto é desenvolver uma API para gerenciamento de produtos, incluindo funcionalidades como criação, atualização, exclusão e consulta de produtos. O projeto foi criado para demonstrar a integração entre serviços de mensageria e banco de dados, visando um sistema de alta resiliência e escalabilidade.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal.
- **Spring Boot 3.3**: Framework para simplificar a criação de aplicações Java, com ênfase em aplicações web e microsserviços.
- **Spring Data JPA**: Para integração com bancos de dados relacionais.
- **MongoDB**: Banco de dados NoSQL usado para armazenar dados de consulta.
- **PostgreSQL**: Banco de dados relacional usado para operações de comando.
- **RabbitMQ**: Sistema de mensageria para comunicação assíncrona entre serviços.
- **Docker** e **Docker Compose**: Para facilitar a criação e execução de contêineres, incluindo os bancos de dados e o servidor RabbitMQ.
- **Micrometer**: Para monitoramento e métricas da aplicação.
- **Prometheus** e **Grafana**: Ferramentas para monitoramento e visualização de métricas.

## Padrões de Projeto Utilizados

- **CQRS (Command Query Responsibility Segregation)**: Separação das operações de comando e consulta para maior escalabilidade e organização de código.
- **Saga Pattern**: Padrão utilizado para gerenciar transações distribuídas, garantindo consistência e compensação em caso de falhas.
- **Repository Pattern**: Para abstração da camada de dados, permitindo flexibilidade nas implementações de repositórios.
- **Factory Pattern**: Usado para encapsular a criação de objetos complexos ou de diferentes tipos de mensagens.
- **DTO (Data Transfer Object)**: Para transferir dados entre as camadas do sistema.

## Estrutura de Pastas

Abaixo está a estrutura de pastas do projeto, com uma breve explicação para cada uma:

```plaintext
api-produto-producer/
├── src/
│   ├── main/
│   │   ├── java/com/github/maikoncanuto/producer/
│   │   │   ├── config/         # Configurações gerais do projeto (ex: RabbitMQ, MongoDB, etc.)
│   │   │   ├── controller/     # Controladores REST para manipulação de produtos
│   │   │   ├── domain/
│   │   │   │   ├── dto/        # Objetos de transferência de dados (Request e Response DTOs)
│   │   │   │   ├── entity/     # Entidades que representam os modelos de dados do banco
│   │   │   │   └── service/    # Serviços para implementação das regras de negócio
│   │   │   ├── repository/     # Interfaces para interação com os bancos de dados
│   │   │   ├── service/        # Implementações dos serviços de negócio
│   │   │   ├── producer/       # Componentes responsáveis por produzir mensagens para o RabbitMQ
│   │   │   └── listener/       # Listeners para consumir mensagens do RabbitMQ
│   ├── resources/
│   │   ├── application.yml     # Arquivo de configuração principal do Spring Boot
│   │   └── scripts/            # Scripts para criação e inicialização de banco de dados
└── docker-compose.yml          # Configuração Docker Compose para iniciar o projeto com dependências
```

### Explicação das Pastas

- **config/**: Contém classes de configuração, como a configuração do RabbitMQ e MongoDB, garantindo que as dependências externas estejam configuradas corretamente.
- **controller/**: Classes que expõem as APIs REST, recebendo as requisições HTTP e retornando as respostas correspondentes.
- **domain/**:
  - **dto/**: Objetos de transferência de dados que representam as entradas e saídas da API.
  - **entity/**: Modelos de dados que mapeiam as tabelas do banco de dados e coleções do MongoDB.
  - **service/**: Lógica de negócio principal, contendo métodos que implementam as funcionalidades da aplicação.
- **repository/**: Interfaces de repositório para acesso a dados, com abstração de JPA e MongoDB.
- **producer/**: Componentes que produzem mensagens para filas do RabbitMQ, responsáveis pela comunicação assíncrona.
- **listener/**: Componentes que consomem mensagens das filas do RabbitMQ, processando e tratando as mensagens recebidas.

## Links para Acesso à Aplicação

| Serviço         | URL                                   | Porta | Descrição                                           |
|-----------------|---------------------------------------|-------|-----------------------------------------------------|
| API de Produto  | `http://localhost:8081/v1/produtos`   | 8081  | Endpoint para manipulação de dados dos produtos      |
| API de Consulta | `http://localhost:8082/v1/produtos`   | 8082  | Endpoint para consultas de produtos                 |
| RabbitMQ        | `http://localhost:15672`              | 15672 | Interface de gerenciamento do RabbitMQ              |
| Prometheus      | `http://localhost:9090`               | 9090  | Monitoramento de métricas                           |
| Grafana         | `http://localhost:3000`               | 3000  | Visualização de métricas coletadas pelo Prometheus  |
| Swagger API     | `http://localhost:8081/swagger-ui.html` | 8081  | Documentação interativa da API                      |

## Exemplo de Requisições e Respostas

### Criar Produto

**Request**
```http
POST /v1/produtos
Content-Type: application/json

{
  "nomeProduto": "Tênis",
  "descricaoProduto": "Tênis esportivo",
  "quantidadeProduto": 10,
  "precoProduto": 299.99
}
```

**Response**
```json
{
  "data": {
    "id": 1,
    "traceId": "123e4567-e89b-12d3-a456-426614174000",
    "nomeProduto": "Tênis",
    "descricaoProduto": "Tênis esportivo",
    "quantidadeProduto": 10,
    "precoProduto": 299.99
  },
  "errors": null
}
```

### Consultar Produto por ID

**Request**
```http
GET /v1/produtos/{traceId}
```

**Response**
```json
{
  "data": {
    "id": 1,
    "traceId": "123e4567-e89b-12d3-a456-426614174000",
    "nomeProduto": "Tênis",
    "descricaoProduto": "Tênis esportivo",
    "quantidadeProduto": 10,
    "precoProduto": 299.99
  },
  "errors": null
}
```

### Deletar Produto

**Request**
```http
DELETE /v1/produtos/{traceId}
```

**Response**
```json
{
  "data": "Produto com traceId 123e4567-e89b-12d3-a456-426614174000 deletado com sucesso",
  "errors": null
}
```

## Como Subir o Projeto

1. **Pré-requisitos**: Ter Docker e Docker Compose instalados.

2. **Passo a Passo**:

   - Clone o repositório para sua máquina local:
     ```bash
     git clone <URL-do-repositorio>
     cd api-produto-producer
     ```

   - Compile o projeto:
     ```bash
     ./mvnw clean install
     ```

   - Inicie os serviços com Docker Compose:
     ```bash
     docker-compose up -d
     ```

   - Após o Docker Compose subir todos os serviços (RabbitMQ, MongoDB, PostgreSQL e o próprio microsserviço), a API estará disponível para consumo.

3. **Testando a API**:

   - Acesse `http://localhost:8081/v1/produtos` para realizar operações de manipulação de dados.
   - Acesse `http://localhost:8082/v1/produtos` para operações de consulta de dados.

4. **Monitoramento**:

   - Acesse o Prometheus em `http://localhost:9090` para visualizar as métricas coletadas.
   - Acesse o Grafana em `http://localhost:3000` para uma visualização detalhada das métricas (usuário/padrão: `admin/admin`).

## Observações

- Certifique-se de que as portas configuradas no `docker-compose.yml` estão livres em seu sistema.
- Use uma ferramenta como Postman para testar os endpoints da API.
- Consulte o código de configuração em `application.yml` para ajustes adicionais.
