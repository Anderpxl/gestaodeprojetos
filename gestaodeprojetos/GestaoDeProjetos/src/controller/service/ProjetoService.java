package controller.service;

import dao.EquipeDAO;
import dao.ProjetoDAO;
import dao.TarefaDAO;
import model.Equipe;
import model.Projeto;
import model.Tarefa;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProjetoService {
    // Método para criar um novo projeto no sistema/banco de dados relacionando equipes
    public void cadastrarProjeto() {

        Projeto projeto = new Projeto();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n====== CADASTRO DE PROJETO ======");

        System.out.print("Nome do projeto: ");
        projeto.setNomeProjeto(scanner.nextLine());
        System.out.print("Descrição: ");
        projeto.setDescricao(scanner.nextLine());
        System.out.print("Data início (AAAA-MM-DD): ");
        projeto.setDataInicio(LocalDate.parse(scanner.nextLine()));
        System.out.print("Data final (AAAA-MM-DD): ");
        projeto.setDataFinal(LocalDate.parse(scanner.nextLine()));

        System.out.print("ID da equipe: ");

        Equipe equipe = new Equipe();
        equipe.setId(scanner.nextInt());
        scanner.nextLine();

        projeto.setEquipe(equipe);

        ProjetoDAO dao = new ProjetoDAO();

        boolean sucesso = dao.inserir(projeto);

        if(sucesso) {
            System.out.println("Projeto cadastrado.");
        } else {
            System.out.println("Erro ao cadastrar.");
        }
    }
    // Método para listar projetos cadastrados no sistema/banco de dados retornando o projeto, equipe responsável, gerente responsável, data início/fim e tarefas
    public void listarProjetos(){

        ProjetoDAO dao = new ProjetoDAO();

        List<Projeto> lista = dao.listar();

        System.out.println("\n====== PROJETOS ======");

        for(Projeto p : lista) {
            System.out.println("\nProjeto ID: " + p.getId());
            System.out.println("Nome do projeto: " + p.getNomeProjeto());

            if(p.getEquipe() != null) {
                System.out.println("Equipe: " + p.getEquipe().getNomeEquipe());
            } else {
                System.out.println("Equipe: Sem equipe.");
            }

            System.out.println("Descrição: " + p.getDescricao());
            System.out.println("Data Inicial: " + p.getDataInicio());
            System.out.println("Data Final: " + p.getDataFinal());
            System.out.println("\n======= TAREFAS =======");

            System.out.println("Quantidade de tarefas: " + (p.getTarefas() == null ? "NULL" : p.getTarefas().size()));

            if(p.getTarefas() != null && !p.getTarefas().isEmpty()) {

                for(Tarefa t : p.getTarefas()) {
                    System.out.println("\nTarefa ID: " + t.getId());
                    System.out.println("Tarefa: " + t.getNomeTarefa());
                    System.out.println("Status: " + t.getStatus());
                    System.out.println("Descrição: " + t.getDescricao());
                    System.out.println("Início: " + t.getDataInicio());
                    System.out.println("Final: " + t.getDataFinal());

                    if(t.getStatus() != Tarefa.Status.CONCLUIDA && LocalDate.now().isAfter(t.getDataFinal())) {
                        t.setStatus(Tarefa.Status.ATRASADA);
                    }
                }
            } else {
                System.out.println("Nenhuma tarefa cadastrada.");
            }
            System.out.println("=========================");
        }
    }
    // Método para alterar projetos cadastrados no sistema baseado nas opções estabelecidas no switch/case
    public void editarProjeto() {

        Scanner scanner = new Scanner(System.in);
        TarefaService tarefaService = new TarefaService();
        ProjetoDAO projetoDAO = new ProjetoDAO();

        System.out.println("\n====== EDITAR PROJETO ======");

        System.out.print("Digite o ID do projeto: ");
        int projetoId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n1 - Alterar nome");
        System.out.println("2 - Alterar descrição");
        System.out.println("3 - Excluir descrição");
        System.out.println("4 - Alterar data final");
        System.out.println("5 - Alterar equipe");
        System.out.println("6 - Inserir tarefa");
        System.out.println("7 - Alterar tarefa");
        System.out.println("0 - Voltar");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                System.out.println("\n====== ALTERAR NOME DO PROJETO ======");

                System.out.print("Novo nome do projeto: ");
                String novoNome = scanner.nextLine();

                projetoDAO.alterarNomeProjeto(projetoId, novoNome);

                System.out.println("Nome alterado!");
                break;

            case 2:
                System.out.println("\n====== ALTERAR DESCRIÇÃO DO PROJETO ======");

                System.out.print("Nova descrição: ");
                String descricao = scanner.nextLine();

                projetoDAO.alterarDescricao(projetoId, descricao);

                System.out.println("Descrição alterada!");
                break;

            case 3:
                projetoDAO.excluirDescricao(projetoId);

                System.out.println("Descrição removida!");
                break;

            case 4:
                System.out.println("\n====== ALTERAR DATA FINAL DO PROJETO ======");

                System.out.print("Nova data final (AAAA-MM-DD): ");
                String data = scanner.nextLine();

                projetoDAO.alterarDataFinal(projetoId, data);

                System.out.println("Data final alterada!");
                break;

            case 5:
                EquipeDAO equipeDAO = new EquipeDAO();

                System.out.println("\n====== ALTERAR EQUIPE DO PROJETO ======");

                System.out.print("ID da equipe: ");
                int equipeId = scanner.nextInt();

                projetoDAO.alterarEquipe(projetoId, equipeId);

                if(!equipeDAO.verificaEquipe(equipeId)) {
                    System.out.println("Equipe não encontrada.");
                    return;
                }

                System.out.println("Equipe alterada!");
                break;
            // Opção para chamar método de inserir tarefa à um projeto
            case 6:
                tarefaService.inserirTarefa(projetoId);
                break;
            // Opção para chamar método de alterar tarefa de um projeto
            case 7:
                tarefaService.editarTarefa();
                break;

            case 0:
                return;

            default:
                System.out.println("Opção inválida.");
        }
    }
}
