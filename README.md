# customer-service

Microsserviço de Cadastro de Clientes em Spring Boot 3 / Java 21, estruturado
100% de acordo com "The Clean Architecture" (Robert C. Martin) — os quatro
círculos como pacotes de topo: `entities`, `usecases`, `interfaceadapters`,
`frameworksdrivers`.

## Casos de uso incluídos

- **RegisterCustomer** — `POST /api/customers` (nome, email, CPF; recusa email duplicado)
- **GetCustomer** — `GET /api/customers/{id}` (404 tratado via `presentNotFound`)

## Rodar localmente

```
mvn spring-boot:run
```

Requer Java 21 e um Postgres em `localhost:5432/customer_db` (ver
`src/main/resources/application.yml`). O schema é criado via Flyway
(`src/main/resources/db/migration/V1__create_customers_table.sql`).

## Testes

```
mvn test
```

- `entities/*Test.java` — Entities puras (Customer, Email, Document/CPF)
- `usecases/**/Interactor*Test.java` — Interactors com fakes das boundaries (given-when-then)
- `interfaceadapters/controllers/CustomerControllerIT.java` — HTTP real, ponta a ponta, com H2 em memória (profile `test`)

Todos os testes seguem o formato **given-when-then** e usam JUnit 5 + AssertJ
(vindos de `spring-boot-starter-test`).

## Nota sobre este ambiente

Este projeto foi gerado e verificado por análise estática (grep da Regra de
Dependência — ver abaixo), mas não foi compilado aqui: o sandbox só tem Java
11 e não tem Maven/rede para baixar as dependências do Spring Boot 3
(que exige Java 17+). Rode `mvn clean test` na sua máquina para compilar e
validar a suíte de testes.

## Verificação da Regra de Dependência

Checado por grep antes da entrega:

- `entities` e `usecases` não importam `org.springframework`, `jakarta.*`,
  `com.fasterxml` nem `io.swagger`.
- Nenhuma `Entity` aparece na assinatura de Controller/Presenter/ViewModel.
- `interfaceadapters` não importa `frameworksdrivers`.
- `usecases` não importa `interfaceadapters` nem `frameworksdrivers`.
- Todo `package` declarado bate com o diretório do arquivo.
