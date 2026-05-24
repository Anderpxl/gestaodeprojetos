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

        // Nome da equipe
        System.out.print("Nome da equipe: ");

        String nomeEquipe = scanner.nextLine();

        // Gerente
        System.out.print("ID do gerente: ");

        int gerenteId = scanner.nextInt();

        scanner.nextLine();

        // Criar equipe
        int equipeId = equipeDAO.criarEquipe(nomeEquipe, gerenteId);

        // Adicionar colaboradores
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

        // Adicionar projeto opcional
        System.out.print(
                "Deseja adicionar projeto? (S/N): "
        );

        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("S")) {

            System.out.print("ID do projeto: ");

            int projetoId = scanner.nextInt();

            equipeDAO.adicionarProjeto(
                    equipeId,
                    projetoId
            );
        }

        System.out.println(
                "\nEquipe cadastrada com sucesso!"
        );
    }

    public void listarEquipes() {

        EquipeDAO dao = new EquipeDAO();

        List<Equipe> lista = dao.listar();

        System.out.println("\n===== EQUIPES =====");

        for(Equipe e : lista) {

            System.out.println("ID: " + e.getId());
            System.out.println("Equipe: " + e.getNomeEquipe());
            System.out.println("Gerente: " + e.getGerente().getNome());

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
        System.out.println("4 - Excluir projeto");
        System.out.println("5 - Adicionar colaborador");
        System.out.println("6 - Alterar colaborador");
        System.out.println("7 - Remover colaborador");
        System.out.println("8 - Adicionar gerente");
        System.out.println("9 - Trocar gerente");
        System.out.println("10 - Remover gerente");

        int opcao = scanner.nextInt();

        scanner.nextLine();

        switch (opcao) {

            case 1:

                System.out.print("Novo nome da equipe: ");

                String novoNome = scanner.nextLine();

                equipeDAO.alterarNomeEquipe(equipeId, novoNome);

                System.out.println("Nome alterado com sucesso!");

                break;

            case 2:

                System.out.print("ID do projeto: ");

                int projetoId = scanner.nextInt();

                equipeDAO.adicionarProjeto(equipeId, projetoId);

                System.out.println("Projeto adicionado!");

                break;

            case 3:

                System.out.print("ID do projeto atual: ");

                int projetoAtual = scanner.nextInt();

                System.out.print("Novo ID do projeto: ");

                int novoProjeto = scanner.nextInt();

                equipeDAO.trocarProjeto(equipeId, projetoAtual, novoProjeto);

                System.out.println("Projeto trocado!");

                break;

            case 4:

                System.out.print("ID do projeto: ");

                int removerProjeto = scanner.nextInt();

                equipeDAO.excluirProjeto(equipeId, removerProjeto);

                System.out.println("Projeto removido!");

                break;

            case 5:

                System.out.print("ID do colaborador: ");

                int colaboradorId = scanner.nextInt();

                equipeDAO.adicionarColaborador(equipeId, colaboradorId);

                System.out.println("Colaborador adicionado!");

                break;

            case 6:

                System.out.print("ID colaborador atual: ");

                int colaboradorAtual = scanner.nextInt();

                System.out.print("Novo colaborador: ");

                int novoColaborador = scanner.nextInt();

                equipeDAO.trocarColaborador(equipeId, colaboradorAtual, novoColaborador);

                System.out.println("Colaborador alterado!");

                break;

            case 7:

                System.out.print("ID do colaborador: ");

                int removerColaborador = scanner.nextInt();

                equipeDAO.excluirColaborador(equipeId, removerColaborador);

                System.out.println("Colaborador removido do projeto!");

                break;

            case 8:

            case 9:

                System.out.print("ID do gerente: ");

                int gerenteId = scanner.nextInt();

                equipeDAO.alterarGerente(equipeId, gerenteId);

                System.out.println("Gerente atualizado!");

                break;

            case 10:

                equipeDAO.removerGerente(equipeId);

                System.out.println("Gerente removido!");

                break;

            default:

                System.out.println("Opção inválida.");
        }
    }

}
