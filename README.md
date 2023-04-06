# BiblioTECH
### Projeto de gerenciamento de uma Biblioteca.
### Cargo pretendido : Desenvolvedor Pleno/C
####  linguagens e tecnologias escolhidas:
- Java 11
- Spring Boot 2.4.0
- Spring Data JPA 2.4.0
- Swagger2  2.9.2
- Spring Security 5.4.1
- Lombok 1.18.16
- Flyway 7.1.1
- PostgreSQL 
- Maven
- Docker 
- Angular
- Typescript
- Angular Material

<p>A escolha do Framework **Spring Boot** para o desenvolvimento da API se da pela minha experiencia com essa tecnologia ques traz beneficios e facilidades tais como : Facilidade de configuração, modularidade  que ajuda a manter a aplicação leve e rápida, ser com uma ampla gama de outras tecnologias e frameworks, o que permite integrá-lo facilmente com outras partes da arquitetura da aplicação e seu ecossistema que possui uma grande comunidade de desenvolvedores, além de uma ampla gama de ferramentas e bibliotecas de terceiros, o que pode facilitar o desenvolvimento e melhorar a qualidade da aplicação.</p>
<p>Já a escolha do **Angular** para o frontend da aplicação se deu por ser um  baseado em componentes, essa abordagem facilita a manutenção e escalabilidade da aplicação. Além de ser escrito em TypeScript, que é uma linguagem de programação que adiciona recursos importantes ao JavaScript, como tipagem estática e suporte a classes e interfaces. Isso torna o código mais fácil de entender e manter, tambem possui muitas ferramentas, bibliotecas e recursos disponíveis para ajudar no desenvolvimento e na resolução de problemasa lém de ter sido  projetado com a testabilidade em mente, o que torna fácil escrever testes unitários e de integração para a aplicação.</p> 
 
### Requisitos do Projeto
#### Perfis de Usuários
A aplicação possui três perfis de usuários: administrador, cliente e bibliotecário.
- **Administrador**: capaz de cadastrar, editar e excluir todos os livros (nome, autor e data de cadastro).
- **Cliente**: poderá realizar solicitações de empréstimos, também pode cadastrar livros, mas só pode editar e excluir os livros que cadastrou, e visualizar os demais cadastrados no sistema.
- **Bibliotecário**: responsável por gerenciar os empréstimos dos livros. Este perfil é responsável por aceitar as solicitações de empréstimo, informar quando uma devolução for realizada e ver os empréstimos que estão em andamento.

#### Log in e Sign up
- O cadastro será sempre de usuário do perfil cliente, já o perfil administrador deve ser persistido diretamente no banco de dados, assim que a aplicação subir.

#### Empréstimos
- Um usuário comum do sistema deverá solicitar o empréstimo selecionando o livro desejado em uma área de “solicitações de empréstimo” no sistema.
- Ao solicitar o empréstimo, o sistema deverá validar se o livro solicitado está em estoque e disponível para empréstimo.
- Ao realizar um empréstimo, deverá ser especificado de quantos dias será o empréstimo.
- Executar penalidade em situações de atraso de entrega e perda/danos referentes aos livros.

#### Penalidades
- Em caso de atraso inferior a 10 dias, o usuário fica impedido de realizar novos empréstimos por 2 dias.
- A partir de 10 dias de atraso, o usuário fica impedido de realizar novos empréstimos por 1 semana, incrementando mais 1 semana a cada 10 dias de atraso (Exemplo: de 10 a 19 dias de atraso, 1 semana de penalidade; De 20 a 29 dias de atraso, 2 semanas de penalidade), com no máximo 4 semanas de penalidade.
- Em caso de perda ou dano de livros, o usuário terá penalidade de 1 mês sem poder realizar novos empréstimos, assim como terá seu acesso bloqueado por 1 semana

#### Front-end
- Deve conter uma tela inicial de login.
- Na tela de login deverão existir links de cadastre-se e esqueci minha senha para que novos usuários possam ser cadastrados e antigos usuários possam recuperar o acesso.
- O usuário de perfil administrador terá uma tela de gerência de livros, na qual ele poderá cadastrar, editar, excluir, listar e detalhar os livros.
- O usuário de perfil cliente terá uma tela que poderá consultar a lista completa de livros, podendo cadastrar novos livros, no entanto, editar e excluir somente aqueles cadastrados por ele.

### Banco de Dados
##### FlyWay
O Flyway é utilizado nesta aplicação para versionamento do banco de dados. As migrações do banco de dados são encontradas no diretório src/main/resources/db/migration. Ao iniciar a aplicação, o Flyway irá executar automaticamente todas as migrações pendentes, garantindo que o banco de dados esteja sempre atualizado com a versão da aplicação.

Para mais informações sobre o Flyway, consulte a documentação oficial: https://flywaydb.org/documentation/migrations.
##### PostgreSQL
O banco de dados utilizado nesta aplicação é o PostgreSQL, e a configuração de conexão pode ser encontrada no arquivo application.properties no diretório src/main/resources. Para executar o banco de dados localmente, é possível utilizar o Docker Compose, como descrito abaixo:

    `docker-compose up`


Docker Compose
Para executar o banco de dados PostgreSQL em um container Docker utilizando o Docker Compose, execute o seguinte comando:



### Autenticação e Autorização
Foi utilizado io.jsonwebtoken, uma biblioteca utilizada para a criação e manipulação de tokens JWT (JSON Web Tokens) para garantir a autenticação e autorização na API.

- O token é gerado usando uma biblioteca específica do Spring Security.
- O token é necessário para acessar as rotas protegidas da API.
- O token é enviado no cabeçalho de cada solicitação do cliente.
- O token é validado no lado do servidor antes de permitir o acesso à rota protegida.
- Caso o token seja inválido ou tenha expirado, o acesso à rota protegida é negado.

### Swagger
A API foi documentada utilizando o Swagger. Para acessar a documentação, siga os seguintes passos:

1. Certifique-se de que a aplicação esteja em execução.
2. Abra um navegador web e acesse a URL http://localhost:9091/swagger-ui.html.
3. A documentação interativa da API será exibida na página do Swagger.

O Swagger foi utilizado para gerar a documentação da API automaticamente a partir do código-fonte, tornando o processo de documentação mais fácil e rápido. Através dele, é possível visualizar todos os endpoints da API, seus parâmetros, códigos de resposta e exemplos de chamadas, entre outras informações úteis para a utilização da API.

### Como rodar o projeto
#### Pré-requisitos
- Docker instalado na máquina (https://docs.docker.com/engine/install/)
- Docker Compose instalado na máquina (https://docs.docker.com/compose/install/)
- Node.js instalado na máquina (https://nodejs.org/)
- Angular CLI instalado na máquina (https://cli.angular.io/)
- PostgreSQL instalado na máquina (https://www.postgresql.org/download/)

#### Backend
Faça o download ou clone o repositório do projeto para a sua máquina.
Acesse a pasta raiz do projeto através do terminal ou prompt de comando.
Execute o comando docker-compose up para iniciar o banco de dados PostgreSQL.
Com o banco de dados rodando, execute o comando` ./mvnw spring-boot:run` para rodar o servidor da API.
Acesse o endereço http://localhost:9091/swagger-ui.html no seu navegador para acessar a documentação da API utilizando o Swagger.

#### Frontend
Acesse a pasta raiz do projeto através do terminal ou prompt de comando.
Execute o comando **npm install** para instalar as dependências do projeto.
Execute o comando **ng serve** para rodar a aplicação Angular.
Acesse o endereço http://localhost:4200 no seu navegador para acessar a aplicação.
Pronto! Agora você já pode usar a aplicação. Lembre-se de criar um usuário administrador através do banco de dados para ter acesso ao perfil de administrador da aplicação.
