# WhatsApp Web - Backend

Backend da aplicação responsável pelo gerenciamento da sessão do WhatsApp Web, automação do navegador através do Selenium e comunicação com o frontend.

## Tecnologias

* Java
* Spring Boot
* Maven
* Selenium WebDriver
* Docker
* Docker Compose
* Server-Sent Events (SSE)

## Funcionalidades

* Conexão com o WhatsApp Web
* Geração e disponibilização do QR Code
* Gerenciamento da sessão do WhatsApp Web
* Desconexão da sessão
* Envio de mensagens
* Comunicação com o frontend através de API REST
* Notificações em tempo real através de Server-Sent Events (SSE)
* Execução do Selenium em container Docker

## Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

* Java
* Maven
* Docker
* Docker Compose

Verifique as instalações:

```bash
java --version
mvn --version
docker --version
docker compose version
```

## Integração com o Frontend

O backend disponibiliza uma API REST utilizada pelo frontend Angular para realizar as operações relacionadas ao WhatsApp Web.

O frontend se comunica com o backend através do endereço:

```text
http://localhost:8080
```

Entre as operações disponibilizadas estão:

* Conectar ao WhatsApp Web
* Consultar o QR Code
* Enviar mensagens
* Desconectar a sessão
* Receber notificações sobre o estado das operações

Além das requisições HTTP tradicionais, o backend utiliza **Server-Sent Events (SSE)** para enviar atualizações em tempo real para o frontend. Dessa forma, a interface consegue apresentar ao usuário eventos relacionados à conexão, envio de mensagens, sucesso, falha e encerramento da sessão.

## Executando o projeto

Como o `docker-compose.yml` utiliza uma imagem Docker do backend, primeiro é necessário gerar a imagem da aplicação.

### 1. Gerar o projeto

Na raiz do projeto, execute:

```bash
mvn clean package
```

Esse comando compila o projeto e gera o arquivo `.jar` utilizado pela imagem Docker.

### 2. Criar a imagem Docker do Backend

Execute:

```bash
docker build -t access_project_back .
```

A imagem será criada com o nome:

```text
access_project_back
```

### 3. Subir os containers

Depois de criar a imagem, execute:

```bash
docker compose up
```

Ou, caso queira reconstruir os serviços:

```bash
docker compose up --build
```

O Docker Compose será responsável por iniciar o backend e o container utilizado pelo Selenium.

O backend ficará disponível em:

```text
http://localhost:8080
```

## Encerrando a aplicação

Para parar os containers:

```bash
docker compose down
```

## Principais Endpoints

### Conectar ao WhatsApp Web

```http
GET /v2/message/connect
```

Inicia o processo de conexão com o WhatsApp Web através do Selenium.

### Visualizar QR Code

```http
GET /v2/message/qr
```

Retorna o QR Code utilizado para autenticar a sessão do WhatsApp Web.

### Enviar mensagem

```http
POST /v2/message/send
```

Realiza o envio de uma mensagem para o número informado.

### Desconectar

```http
GET /v2/message/disconnect
```

Encerra a sessão do WhatsApp Web.

### Receber notificações

```http
GET /notifications/subscribe
```

Estabelece uma conexão SSE com o frontend para receber notificações em tempo real sobre as operações realizadas.

## Arquitetura

O backend foi organizado de forma a separar as responsabilidades entre as diferentes camadas da aplicação:

```text
Frontend Angular
       │
       │ HTTP / SSE
       ▼
Spring Boot API
       │
       ▼
Message Service
       │
       ▼
Selenium Manager
       │
       ▼
Selenium WebDriver
       │
       ▼
WhatsApp Web
```

O frontend é responsável pela interação com o usuário, enquanto o backend concentra as regras da aplicação e o gerenciamento do Selenium e da sessão do WhatsApp Web.

O Docker é utilizado para fornecer um ambiente padronizado para execução da aplicação e do navegador utilizado pelo Selenium.
