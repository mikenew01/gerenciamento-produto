
# API Gateway Project

## Objetivo do Projeto
O `API Gateway` é o ponto de entrada centralizado para gerenciar e rotear requisições entre os diversos microsserviços. Ele atua como uma camada intermediária que simplifica o acesso aos serviços, aplicando autenticação, controle de segurança e balanceamento de carga.

## Tecnologias Usadas
- **Java 17**: Linguagem de programação principal.
- **Spring Boot**: Framework para simplificar a criação de serviços RESTful.
- **Spring Cloud Gateway**: Solução de roteamento e gerenciamento de tráfego para microsserviços.
- **Docker**: Contêiner para empacotamento e implantação.
- **Maven**: Gerenciamento de dependências e build do projeto.

## Padrões de Projetos Utilizados
- **API Gateway Pattern**: Centraliza o acesso aos microsserviços, oferecendo uma interface única para o cliente.
- **Circuit Breaker**: Protege os serviços de falhas em cascata, sendo útil para lidar com falhas temporárias.
- **Load Balancer**: Distribui as requisições entre os microsserviços para balancear a carga.

## Estrutura de Pastas
- `src/main/java`: Contém o código fonte principal.
  - `controller`: Contém os controladores da API.
  - `config`: Arquivos de configuração do Spring e do Gateway.
  - `filter`: Implementação de filtros personalizados.
- `src/main/resources`: Arquivos de configuração, como `application.yml`.
- `Dockerfile`: Define o ambiente Docker para empacotar e rodar a aplicação.
- `pom.xml`: Configurações do Maven para gerenciamento de dependências.

## Como Subir o Projeto
1. Certifique-se de ter o Docker instalado.
2. Compile e empacote o projeto:
   ```bash
   mvn clean package -DskipTests
   ```
3. Construa a imagem Docker:
   ```bash
   docker build -t api-gateway .
   ```
4. Rode o contêiner:
   ```bash
   docker run -p 8080:8080 api-gateway
   ```

## Endpoints e Links
| Endpoint                   | Porta | Descrição                      |
|----------------------------|-------|--------------------------------|
| `/swagger-ui/index.html`   | 8080  | Documentação Swagger           |
| `/v1/produtos`             | 8080  | Endpoint de produtos           |

## Exemplo de Request e Response
### Exemplo de Request
**GET** `/v1/produtos/1`

#### Request
```json
{
    "id": 1
}
```

#### Response
```json
{
    "data": {
        "id": 1,
        "nome": "Produto Teste",
        "descricao": "Descrição do produto",
        "preco": 199.99
    },
    "errors": null
}
```

---

