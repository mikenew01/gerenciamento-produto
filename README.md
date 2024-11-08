
# Projeto API Produto - Docker Compose

Este projeto é composto por múltiplos microsserviços e configurações de banco de dados e mensageria, orquestrados pelo Docker Compose.

## Objetivo

O objetivo deste projeto é disponibilizar uma estrutura de microsserviços para gerenciar produtos, utilizando tecnologias modernas e altamente escaláveis.

## Estrutura de Pastas

- `api-produto-consumer`: Microsserviço consumidor de mensagens relacionadas a produtos.
- `api-produto-producer`: Microsserviço produtor que publica eventos de produtos.
- `api-produto-query`: Microsserviço responsável por consultas de dados de produtos.
- `api-gateway`: API Gateway para rotear as requisições aos serviços apropriados.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para facilitar a criação dos microsserviços.
- **RabbitMQ**: Mensageria para comunicação entre serviços.
- **PostgreSQL**: Banco de dados para persistência dos microsserviços producer e consumer.
- **MongoDB**: Banco de dados para o serviço de consulta (query).
- **Docker Compose**: Orquestração dos serviços.

## Serviços e Configuração no Docker Compose

O projeto possui os seguintes serviços configurados no `docker-compose.yml`:

| Serviço                 | Porta Local | Descrição                                       |
|-------------------------|-------------|-------------------------------------------------|
| **db-produto-consumer** | 5432        | Banco de dados PostgreSQL para o serviço consumer |
| **db-produto-query**    | 27017       | Banco de dados MongoDB para o serviço query      |
| **db-produto-producer** | 5433        | Banco de dados PostgreSQL para o serviço producer |
| **rabbitmq**            | 5672, 15672 | Serviço de mensageria RabbitMQ                   |
| **api-produto-query**   | 8083        | Microsserviço para consultas de produtos         |
| **api-produto-producer**| 8081        | Microsserviço produtor de eventos de produtos    |
| **api-produto-consumer**| 8082        | Microsserviço consumidor de eventos de produtos  |
| **api-gateway**         | 8080        | API Gateway para rotear requisições              |

## Exemplos de Endpoints

### Criar Produto
**POST** `http://localhost:8081/v1/produtos`
```json
{
    "nome": "Produto Exemplo",
    "descricao": "Descrição do produto exemplo",
    "quantidade": 100,
    "preco": 29.99
}
```
**Resposta**:
```json
{
    "data": { ... },
    "errors": null
}
```

### Atualizar Produto
**PUT** `http://localhost:8081/v1/produtos/{traceId}`
```json
{
    "nome": "Produto Exemplo Atualizado",
    "descricao": "Nova descrição",
    "quantidade": 150,
    "preco": 39.99
}
```
**Resposta**:
```json
{
    "data": { ... },
    "errors": null
}
```

### Deletar Produto
**DELETE** `http://localhost:8081/v1/produtos/{traceId}`
**Resposta**:
```json
{
    "data": { ... },
    "errors": null
}
```

## Executando o Projeto

Para iniciar o projeto, utilize o comando:

```bash
docker-compose up --build
```

Esse comando irá baixar as imagens necessárias, construir as imagens dos microsserviços e iniciar todos os containers.

## Parando o Projeto

Para parar e remover os containers, utilize o comando:

```bash
docker-compose down
```
