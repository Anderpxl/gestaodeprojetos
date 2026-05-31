package controller.service;

import dao.ProjetoDAO;
import dao.TarefaDAO;
import model.Projeto;
import model.Tarefa;

import java.time.LocalDate;
import java.util.Scanner;

public class TarefaService {

    public void inserirTarefa(int projetoId){

        Scanner scanner = new Scanner(System.in);
        ProjetoDAO projetoDAO = new ProjetoDAO();
        Tarefa tarefa = new Tarefa();

        System.out.println("\n===== NOVA TAREFA =====");

        System.out.print("Nome da tarefa: ");
        tarefa.setNomeTarefa(scanner.nextLine());

        System.out.print("Descrição: ");
        tarefa.setDescricao(scanner.nextLine());

        Projeto projetoValidaDate = projetoDAO.buscarPorId(projetoId);

        if(projetoValidaDate == null) {
            System.out.println("Projeto não encontrado.");
            return;
        }

        System.out.print("Data início (AAAA-MM-DD): ");
        tarefa.setDataInicio(LocalDate.parse(scanner.nextLine()));

        System.out.print("Data final (AAAA-MM-DD): ");
        tarefa.setDataFinal(LocalDate.parse(scanner.nextLine()));

        if(tarefa.getDataInicio().isBefore(projetoValidaDate.getDataInicio())) {
            System.out.println("A tarefa não pode iniciar antes do projeto.");
            return;
        }

        if(tarefa.getDataFinal().isAfter(projetoValidaDate.getDataFinal())) {
            System.out.println("A tarefa não pode terminar após o projeto.");
            return;
        }

        if(tarefa.getDataFinal().isBefore(tarefa.getDataInicio())) {
            System.out.println("A data final da tarefa não pode ser anterior à data inicial.");
            return;
        }

        tarefa.setStatus(Tarefa.Status.PENDENTE);

        TarefaDAO tarefaDAO = new TarefaDAO();

        if(tarefaDAO.inserirTarefa(tarefa, projetoId)) {

            System.out.println("Tarefa cadastrada com sucesso!");

        } else {
            System.out.println("Erro ao cadastrar tarefa.");
        }

    }

    public void editarTarefa() {

        Scanner scanner = new Scanner(System.in);

        TarefaDAO tarefaDAO = new TarefaDAO();

        System.out.println("\n===== EDITAR TAREFA =====");

        System.out.print("ID da tarefa: ");

        int tarefaId = scanner.nextInt();

        scanner.nextLine();

        System.out.println("1 - Alterar nome da tarefa");
        System.out.println("2 - Alterar descrição da tarefa");
        System.out.println("3 - Alterar data início da tarefa");
        System.out.println("4 - Alterar data final da tarefa");
        System.out.println("5 - Alterar status");
        System.out.println("0 - Voltar");

        int opcao = scanner.nextInt();

        scanner.nextLine();

        switch(opcao) {

            case 1:

                System.out.print("Novo nome: ");

                String nome = scanner.nextLine();

                tarefaDAO.alterarNome(tarefaId, nome);

                System.out.println("Nome alterado.");

                break;

            case 2:

                System.out.print("Nova descrição: ");

                String descricao = scanner.nextLine();

                tarefaDAO.alterarDescricao(tarefaId, descricao);

                System.out.println("Descrição alterada.");

                break;

            case 3:

                System.out.print("Nova data inicial (AAAA-MM-DD): ");

                LocalDate dataInicio = LocalDate.parse(scanner.nextLine());

                tarefaDAO.alterarDataInicio(tarefaId, dataInicio);

                System.out.println("Data inicial alterada.");

                break;

            case 4:

                System.out.print("Nova data final (AAAA-MM-DD): ");

                LocalDate dataFinal = LocalDate.parse(scanner.nextLine());

                tarefaDAO.alterarDataFinal(tarefaId, dataFinal);

                System.out.println("Data final alterada.");

                break;

            case 5:

                System.out.println("1 - PENDENTE");
                System.out.println("2 - EM_ANDAMENTO");
                System.out.println("3 - CONCLUIDA");
                System.out.println("0 - Voltar");

                int statusOpcao = scanner.nextInt();

                Tarefa.Status novoStatus = null;

                switch(statusOpcao) {
                    case 1:
                        novoStatus = Tarefa.Status.PENDENTE;

                        break;

                    case 2:
                        novoStatus = Tarefa.Status.EM_ANDAMENTO;

                        break;

                    case 3:
                        novoStatus = Tarefa.Status.CONCLUIDA;

                        break;

                    case 0:

                        return;

                    default:
                        System.out.println("Opção invalida.");
                }

                tarefaDAO.alterarStatus(tarefaId, novoStatus);

                System.out.println("Status alterado!");

                break;

            case 0:

                return;

            default:

                System.out.println(
                        "Opção inválida."
                );

        }
    }

}
