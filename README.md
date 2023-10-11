# vehiclepedia

Vehiclepedia é um API desenvolvida em Spring Boot para obter iformações sobre veículos com base na API Tabela Fipe.

### Como obter as informações dos veículos?

As informações são obtidas através dos endpoints da aplicação, que podem ser acionados pelo Postman.
A collection **vehiclepedia.json** está dentro do diretório **collection_test_postman**. Ao importá-la 
em seu workspace do Postman, haverá as seguintes pastas:

#### carros:
Contém requisições **GET** para acessar as informações sobre carros.

#### motos:
Contém requisições **GET** para acessar as informações sobre motos.

#### caminhoes:
Contém requisições **GET** para acessar as informações sobre caminhões.

#### actuator:
Contém requisições **GET** para acessar as informações sobre o microsserviço, como informações gerais e health check.

**Para que as requisições sejam enviadas com sucesso ao microsserviço, é necessário que ele tenha sido iniciado na porta indicada na url.***


## Informações adicionais:


### Packages:

#### - controller: 
Contém as classes de controller, onde estão os endpoints para acesso as informações.

#### - model.entity: 
Contém as entidades, que são os modelos de dados do microsserviço.

#### - model.service: 
Contém as classes de serviço, onde está a lógica para obtenção dos dados.

#### - handler: 
Contém o classe de tratamento global de exceções.

#### - config: 
Contém as classes de configuração.

#### - utils: 
Contém uma classe com as constantes utilizadas no microsserviços.


### Testes unitários: 
Desenvolvidos com Mockito e JUnit 5.


### Mecanismo de cache: 
Desenvolvido via AWS S3 (para usar é necessário ter as access keys e o endpoint do bucket AWS S3.

