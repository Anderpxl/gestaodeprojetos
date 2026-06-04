# Sistema de Gestão de Projetos

Este projeto foi construído para centralizar a operação de gerenciamento de equipes em projetos corporativos, 
organizando tarefas, cronogramas e prazos, eliminando gargalos de comunicação e garantindo rastreabilidade contínua. A arquitetura foi projetada
para proteger dados sensíveis através de triggers automáticos no banco de dados e forçar o cumprimento de regras
de negócio essênciais baseadas em níveis de acesso.

***

## Funcionalidades do Programa

* **Gestão de Acesso:** Usuários definidos com cargo/perfil por ENUM (`ADMINISTRADOR`, `GERENTE` e `COLABORADOR`) com controle de acesso dinâmico para menus e configurações definidos baseado no perfil do usuário.
* **Cadastro de Projetos:** Inclusão de dados detalhados focados em controle de escopo e prazos (nome do projeto, descrição, data de início, data final, equipe responsável e tarefas com prazos).
* **Mecanismo de Auditoria Automatizada:** Uso de triggers de integridade no banco de dados para arquivar o estado de dados de colaboradores antes de alterações críticas ou exclusões.
* **Estrutura Organizacional de Equipes:** Criação de times com definição explícita e vinculada de um Gerente (responsável estratégico).

***

## Padrões e Métodos Utilizados

* **Linguagem:** Java (JDK 17+).
* **Banco de Dados:** Workbench MySQL 8.x persistência nativa via JDBC.
* **Arquitetura:** MVC (Model - View - Controller) com DAO (Data Access Object).
* **Paradigma:** Programação Orientada a Objetos (POO) - Encapsulamento, enumeração e classes de serviço.
* **Versionamento**: Git e GitHub

***

## Estrutura do Projeto

O código-fonte é rigorosamente organizado para separar interface, as regras de negócio e o controle de dados.


* `model/`: Classes de negócio (`Colaborador.java`,`Equipe.java`,`Projeto.java`,`Tarefa.java`) com encapsulamento de atributos e mapeamento de entidades.
* `dao/`: Gerenciamento de conexões e persistências física de dados (`Conexao.java`,`ColaboradorDAO.java`,`EquipeDAO`,`ProjetoDAO`).
* `view/`: Interfaces de interação por menu textual com o usuário e capturas via console (`Menu.java`).
* `controller/service`: Camada lógica que processa as regras de negócio e intermedia as telas e os DAO (`LoginService.java`,`ColaboradorService.java`,`EquipeService`,`ProjetoService`,`RelatorioService`,`TarefaService`).
* `Main.java`: Ponto de entrada e execução do ciclo de vida completo do sistema.

***

## Executando o Sistema

1 - Faça o download dos arquivos do projeto ou clone este repositório Git em sua máquina. \
2 - Abra a pasta raiz do projeto em sua IDE de preferência (IntelliJ IDEA, Eclipse ou NetBeans). \
3 - Certifique-se de que a instância local do MySQL está ativa e execute o script de criação do banco de dados `gestaodeprojeto.sql` conforme especificado no esquema técnico. \
4 - Execute o arquivo `Main.java` para visualizar, autenticar e interagir com a simulação do sistema diretamente no console.
