package controller.service;

import dao.EquipeDAO;
import model.Colaborador;
import model.Equipe;

import java.util.List;
import java.util.Scanner;

public class EquipeService {

    public void cadastrarEquipe() {

        Scanner scanner = new Scanner(System.in);

        EquipeDAO equipeDAO = new EquipeDAO();

        System.out.println("===== CADASTRAR EQUIPE =====");

        System.out.print("Nome da equipe: ");

        String nomeEquipe = scanner.nextLine();

        System.out.print("ID do gerente: ");

        int gerenteId = scanner.nextInt();

        scanner.nextLine();

        int equipeId = equipeDAO.criarEquipe(nomeEquipe, gerenteId);

        System.out.print(
                "Quantos colaboradores deseja adicionar? "
        );

        int quantidade = scanner.nextInt();

        scanner.nextLine();

        for (int i = 0; i < quantidade; i++) {

            System.out.print(
                    "Digite o ID do colaborador: "
            );

            int colaboradorId = scanner.nextInt();

            scanner.nextLine();

            equipeDAO.adicionarColaborador(equipeId, colaboradorId);
        }

        System.out.print("Deseja adicionar projeto? (S/N): ");

        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("S")) {

            System.out.print("ID do projeto: ");

            int projetoId = scanner.nextInt();

            equipeDAO.adicionarProjeto(equipeId, projetoId);
        }

        System.out.println("\nEquipe cadastrada com sucesso!");
    }

    public void listarEquipes() {

        EquipeDAO dao = new EquipeDAO();

        List<Equipe> lista = dao.listar();

        System.out.println("\n===== EQUIPES =====");

        for(Equipe e : lista) {

            System.out.println("ID: " + e.getId());
            System.out.println("Equipe: " + e.getNomeEquipe());

            if (e.getGerente() != null) {

                System.out.println("Gerente: " + e.getGerente().getNome());

            } else {

                System.out.println(
                        "Gerente: nenhum gerente atribuido à equipe"
                );
            }

            for(Colaborador c : e.getColaboradores()) {
                System.out.println("- "+ c.getNome());
            }

            System.out.println("------------------");
        }
    }

    public void editarEquipe() {

        Scanner scanner = new Scanner(System.in);

        EquipeDAO equipeDAO = new EquipeDAO();

        System.out.println("===== EDITAR EQUIPE =====");

        System.out.print("Digite o ID da equipe: ");

        int equipeId = scanner.nextInt();

        scanner.nextLine();

        System.out.println("\n1 - Alterar nome da equipe");
        System.out.println("2 - Adicionar projeto");
        System.out.println("3 - Alterar projeto");
        System.out.println("4 - Remover projeto");
        System.out.println("5 - Adicionar colaborador");
        System.out.println("6 - Alterar colaborador");
        System.out.println("7 - Remover colaborador");
        System.out.println("8 - Adicionar gerente");
        System.out.println("9 - Remover gerente");
        System.out.print("10 - Excluir equipe");
        System.out.println("\n0 - Sair");

        int opcao = scanner.nextInt();

        scanner.nextLine();

        switch (opcao) {

            case 1:

                System.out.println("\n===== ALTERAR NOME DA EQUIPE =====");

                System.out.print("Novo nome da equipe: ");

                String novoNome = scanner.nextLine();

                equipeDAO.alterarNomeEquipe(equipeId, novoNome);

                System.out.println("Nome alterado com sucesso!");

                break;

            case 2:

                System.out.println("\n===== ADICIONAR PROJETO =====");

                System.out.print("ID do projeto: ");

                int projetoId = scanner.nextInt();

                equipeDAO.adicionarProjeto(equipeId, projetoId);

                System.out.println("Projeto adicionado!");

                break;

            case 3:

                System.out.println("\n===== ALTERAR PROJETO =====");

                System.out.print("ID do projeto atual: ");

                int projetoAtual = scanner.nextInt();

                System.out.print("Novo ID do projeto: ");

                int novoProjeto = scanner.nextInt();

                equipeDAO.trocarProjeto(equipeId, projetoAtual, novoProjeto);

                System.out.println("Projeto alterado!");

                break;

            case 4:

                System.out.println("\n===== REMOVER PROJETO =====");

                System.out.print("ID do projeto: ");

                int rmvProjeto = scanner.nextInt();

                equipeDAO.removerProjeto(equipeId, rmvProjeto);

                System.out.println("Projeto removido!");

                break;

            case 5:

                System.out.println("\n===== ADICIONAR COLABORADOR =====");

                System.out.print("ID do colaborador: ");

                int colaboradorId = scanner.nextInt();

                equipeDAO.adicionarColaborador(equipeId, colaboradorId);

                System.out.println("Colaborador adicionado!");

                break;

            case 6:

                System.out.println("\n===== ALTERAR COLABORADOR =====");

                System.out.print("ID colaborador atual: ");

                int colaboradorAtual = scanner.nextInt();

                System.out.print("ID colaborador novo: ");

                int novoColaborador = scanner.nextInt();

                equipeDAO.trocarColaborador(equipeId, colaboradorAtual, novoColaborador);

                System.out.println("Colaborador alterado!");

                break;

            case 7:

                System.out.println("\n===== REMOVER COLABORADOR =====");

                System.out.print("ID do colaborador: ");

                int removerColaborador = scanner.nextInt();

                equipeDAO.excluirColaborador(equipeId, removerColaborador);

                System.out.println("Colaborador removido da equipe!");

                break;

            case 8:

                System.out.println("\n===== ADICIONAR GERENTE =====");

                System.out.print("Digite o ID do gerente: ");

                int gerenteId = scanner.nextInt();

                equipeDAO.adicionarGerente(equipeId, gerenteId);

                System.out.println("Gerente adicionado com sucesso!");

                break;

            case 9:

                System.out.println("\n===== REMOVER GERENTE =====");

                System.out.println("ID do gerente: ");

                int gerenteEquipeId = scanner.nextInt();

                equipeDAO.removerGerente(gerenteEquipeId);

                System.out.println("Gerente removido!");

                break;

            case 10:

                System.out.println("\n===== EXCLUIR EQUIPE =====");

                System.out.println("Você deseja remover equipe? (S/N)");

                String resposta = scanner.nextLine();

                if(resposta.equalsIgnoreCase("S")) {
                    equipeDAO.excluirEquipe(equipeId);
                }

                break;

            case 0:

                System.out.println("Voltando...");
                break;

            default:

                System.out.println("Opção inválida.");
        }
    }

}
