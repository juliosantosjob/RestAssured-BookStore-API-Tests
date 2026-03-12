# Testes da API BookStore (ToolsQA)
---

Este projeto contém testes automatizados para a API que validam a aplicação [BookStore](https://bookstore.toolsqa.com/).  
Os testes foram desenvolvidos utilizando **Java**, **RestAssured** e **JUnit 5**, cobrindo cenários de **criação de conta, autenticação, gerenciamento de livros e deleção de usuário**, incluindo cenários de sucesso e erro.

Documentação da API:  
https://bookstore.toolsqa.com/swagger/

---

## 📋 Suíte de Testes API - BookStore

### Feature: Criação de Conta
- Criar uma conta com sucesso
- Criar uma conta com username em branco
- Criar uma conta com password em branco
- Criar uma conta com username e password em branco
- Criar uma conta com password sem caracteres especiais
- Criar uma conta com os mesmos dados de uma conta já existente

### Feature: Login
- Login com sucesso
- Login com username inválido
- Login com password inválido
- Login com username e password inválidos
- Login inserindo a senha inválida várias vezes
- Login com password em branco

### Feature: Gerenciamento de Livros
- Acessar a lista de livros disponíveis
- Adicionar e remover um livro à lista de favoritos
- Adicionar um livro inexistente
- Deletar um livro inexistente

### Feature: Deleção de Conta
- Criar uma conta e deletar a mesma
- Deletar uma conta com ID inválido
- Deletar uma conta e tentar realizar login com a mesma

---

## Tecnologias e Ferramentas

- [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [JUnit 5](https://junit.org/junit5/)
- [RestAssured](https://rest-assured.io/)
- [Faker](https://github.com/DiUS/java-faker) (gerador de dados)
- [Allure](https://docs.qameta.io/allure/) (relatórios de testes)

---

## 💻 Instalação

Com as dependências listadas acima instaladas, siga os passos abaixo para rodar o projeto localmente:

1. **Clone o repositório:**

```bash
   git clone https://github.com/juliosantosjob/RestAssured-BookStore-API-Tests.git
```

2. **Navegue até o diretório do projeto:**

```bash
    cd RestAssured-BookStore-API-Tests
```

3. **Instale as dependências do projeto usando o Maven:**
```bash
    mvn clean install
```

---

## Execução dos Testes

Tendo executado os passos anteriores, o projeto esta pronto para ser executado localmente. Você pode rodar os testes utilizando Maven rodando o seguinte comando:
```bash
    mvn test
```

ou executar a classe `TestRunner` que se encontra no caminho `/src/test/java/com/toolsqa/bookstore/runners`.
desta maneira você pode executar todos os testes ou apenas uma suíte específica dependendo da tag que você informar.

### 📊 Reportes de Testes

O projeto gera relatórios com Allure Reports.

1. Executando Localmente: ao executar os testes é criada a pasta allure-results.
   Para visualizar o resultado dos testes, rode o comando:

```bash
    allure serve
```

2. Via Pipeline: os relatórios são gerados automaticamente na pipeline do Github actions.
   para visualizar a ultima execução, basta acessar clicar [aqui.](https://juliosantosjob.github.io/RestAssured-BookStore-API-Tests)

## 🌐 Redes:

[![Email](https://img.shields.io/badge/Email-%23D14836.svg?logo=gmail&logoColor=white)](mailto:julio958214@gmail.com)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-%230077B5.svg?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/julio-santos-43428019b)
[![Facebook](https://img.shields.io/badge/Facebook-%231877F2.svg?logo=Facebook&logoColor=white)](https://www.facebook.com/profile.php?id=100003793058455)
[![Instagram](https://img.shields.io/badge/Instagram-%23E4405F.svg?logo=Instagram&logoColor=white)](https://www.instagram.com/oficial_juliosantos/)
[![Discord](https://img.shields.io/badge/Discord-%237289DA.svg?logo=discord&logoColor=white)](https://discord.gg/julio.saantos199)