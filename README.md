# Jogo de RPG - Projeto 

O projeto consiste em um jogo de RPG desenvolvido em Java para a disciplina Arquitetura de Software e Programação Orientada a Objetos. O programa simula a criação de personagens e a composição de partidas com combates contra inimigos, incluindo o cadastro de jogadores e a persistencia dos dados em um banco de dados PostgresSQL.

# Principais Funcionalidades

O programa foi organizado seguindo a arquitetura MVC (Model, View e Controller), separando as responsabilidade, separando as responsabilidades de interface, controle do jogo e gerenciamento de dados.

1. Gestão de Jogadores (Controller Cadastro, ControllerLogin e JogadorDAO)
    - Cadastro: permite criar uma conta de jogador
    - Login: realiza a auetnticação do jogador cadastrado

2. Gestão de Personagens (ControllerNvJogo e PersonagemDAO)
    - Criação: permite iniciar um novo jogo com um personagem
    - Classes: disponibiliza as classes Guerreiro, Arqueiro e Mago
    - Atributos: gerencia informacoes como vida, atauqe, defesa e nível

3. Partidas e Combates (ControllerPartida)
    - Combate: contorla as ações realizadas durante os confrontos com os inimigos 
    - Progressão: gerencia  o avanço das fases e o desenvolvimento do personagem
    - Itens: permite o gerenciamento dos itens associados ao personagem

4. Persistência de Dados (PostgresSQL)
    - Os dados do jogo são armazenados em um banco de dados PostgresSQL, utilizando classes DAO para realizar as operações de acesso e manipulação
    - jogador: armazena os dados de cadastro dos jogadores 
    - personagem: armazena os personagens criados e seus atributos 
    - item: armazena os itens vinculados aos personagens e às partidas
    - save_jogo: armazena o progresso das partidas, incluindo fase, vida atual, lista de inimigos derrotados e vida atual do inimigo e do protagonista 

5. Gerenciamento de Partidas Salvas (ControllerSaves e SaveDAO)
    - Salvamento: registra as informações necessárias para manter o progresso do jogo
    - Carregamento: recupera os dados de uma partida salva

# Para execução do Programa

Para rodar o proojeto em seu ambiente local siga os passos abaixo:

 - Passo 1: Instale o JDK 26, o Apache NetBeans e o PostgresSQL, necessários para a compilação, execução e persistência dos dados do projeto
 - Passo 2: Clone o repositório utilizando o git e abra a pasta do ProjetoRPG no Apache NetBeans.
 - Passo 3: No PostgresSQL, crie o banco de dados chamado feirpg. Em seguida, execute o arquivo schema.sql, localizado na pasta raiz do projeto para criar as tabelas utilizadas no projeto.
 - Passo 4: Crie o arquivo .env na na pasta raiz do projeto. O arquivo deve ter 3 variaveis: DB_URL que deve conter o seguinte formato jdbc:postgresql://<servidor>:<porta>/<nome_servidor>, DB_USER deve conter o nome do usuario do banco de dados que no Postgres geralmente é "postgres", e a senha de acesso ao banco de dado DB_PASSWORD.
 - Passo 5: Certifique-se de que o PostgresSQL esteja em execução e que o banco de dados foi criado corretamente. No NetBeans, construa o projeto e verifique se não há nenhum erro.
 - Passo 6: Execute a classe main ProjetoRPG.java pelo Netbeans. Após a inicializaçaão, o sistema estará disponível para utilização.

# Estrutura do Projeto

O projeto segue a arquitetura MVC onde cada pacote é responsavel por uma parte especifica do sistema:

- ProjetoRPG.java: Inicializa a aplicação e estabelece a comunicação inicial entre as camadas Model, View e Controller.
- controller: Gerencia o cadastro, login, menu, criação de personagens, partidas e saves.
- model: Contém as classes e regras do jogo, incluindo Jogador, Personagem, Protagonista, Inimigo, Guerreiro, Arqueiro, Mago, Itens e GameUtils
- model/dao: Contém a conexao com o PostgresSQL e as classes responsáveis pelo acesso aos dados dos jogadores, personagens, itens e partidas salvas
- view: Contém as interfaces gráficas desenvolvidas com Java Swing, como as telas de cadatsro, login, menu, novo jogo, partida e saves
- schema.sql: Contém os comandos SQL utilizados para criar as tabelas do banco de dados
- pom.xml: Define as configurações e dependências do projeto Maven

# Autor 

Este projeto foi desenvolvido por: 
Vitoria Carolyne Martins Souza - RA 74.225.003-8
