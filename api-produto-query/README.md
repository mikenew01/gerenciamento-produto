
# Projeto: API Produto Query

## Objetivo do Projeto
Este projeto é uma API para consulta de produtos, integrada a um sistema de mensageria para comunicação entre microserviços. O objetivo é fornecer funcionalidades de consulta de dados de produtos, baseando-se nos eventos recebidos por meio do RabbitMQ para manter os dados atualizados.

## Tecnologias Utilizadas
- **Java** com **Spring Boot**: Framework principal para desenvolvimento da API.
- **RabbitMQ**: Usado para mensageria, permitindo comunicação entre os microserviços.
- **MongoDB**: Banco de dados NoSQL para armazenamento dos dados de consulta.
- **Docker**: Contêiner para execução da aplicação de forma isolada.

## Padrões de Projeto Utilizados
- **Listener Pattern**: Implementado para escutar mensagens do RabbitMQ e processar eventos de criação, atualização e remoção de produtos.
- **Repository Pattern**: Uso de repositórios para abstração do acesso ao banco de dados.
- **Controller-Handler-Service**: Estrutura comum para organização das camadas da aplicação, separando a lógica de negócio da manipulação de requisições HTTP.

## Estrutura de Pastas

```plaintext
api-produto-query/
├── config/                     # Configurações do sistema, como RabbitMQConfig
├── controller/                 # Controladores REST para endpoints da API
│   └── handler/                # Manipuladores de exceções globais
├── dto/                        # Classes de transferência de dados
├── entity/                     # Entidades que representam os modelos de dados no MongoDB
├── enums/                      # Enums utilizados no sistema, como NomeEvento
├── exception/                  # Classes de exceção específicas
├── listener/                   # Listeners para processar mensagens de eventos
├── repository/                 # Interfaces de repositórios para acesso aos dados
└── service/                    # Lógica de negócio para manipulação dos produtos
```

## Explicação das Pastas
- **config/**: Define as configurações da aplicação, como configurações de mensageria.
- **controller/**: Responsável por definir os endpoints da API e gerenciar as requisições HTTP.
- **dto/**: Define objetos de transferência de dados para simplificar a comunicação entre camadas.
- **entity/**: Modelos de dados que representam os documentos no MongoDB.
- **listener/**: Listeners para consumir mensagens do RabbitMQ, garantindo sincronização de dados.
- **repository/**: Abstrações para o acesso ao banco de dados, facilitando a manipulação dos dados.
- **service/**: Contém a lógica de negócios, concentrando operações de consulta e manipulação de produtos.

## Como Subir o Projeto
1. Pode executar diretamente da IDE ou buildando a imagem.
2. A aplicação estará disponível na porta especificada no arquivo `application.properties`.

## Endpoints da API

| Recurso                       | Método | Endpoint               | Descrição                   |
|-------------------------------|--------|------------------------|-----------------------------|
| Consulta de Produto           | GET    | `/v1/produtos/{traceId}` | Consulta produto por traceId |
| Consulta de todos os produtos | GET    | `/v1/produtos` | Consulta todos os produtos  |

## Links para Acessar a Aplicação

| Serviço           | Link                          |
|-------------------|-------------------------------|
| Swagger UI        | `http://localhost:8080/swagger-ui.html` |

## Exemplo de Requisição e Resposta

**Requisição**:
```http
GET /v1/produtos/782b7ebd-82a2-451f-aa81-c9f8f5e68ad6
```

**Resposta**:
```json
{
    "id": "782b7ebd-82a2-451f-aa81-c9f8f5e68ad6",
    "nome": "Tênis Esportivo",
    "descricao": "Tênis para corrida",
    "quantidade": 15,
    "preco": 199.99
}
```
