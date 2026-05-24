package controller.service;

import dao.ProjetoDAO;
import model.Equipe;
import model.Projeto;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProjetoService {

    public void cadastrarProjeto() {

        Projeto projeto = new Projeto();

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== CADASTRO DE PROJETO =====");

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

    public void listarProjetos(){

        ProjetoDAO dao = new ProjetoDAO();

        List<Projeto> lista = dao.listar();

        System.out.println("\n===== PROJECTOS =====");

        for(Projeto p : lista) {

            System.out.println("ID: " + p.getId());
            System.out.println("Projeto: " + p.getNomeProjeto());
            System.out.println("Equipe: " + p.getEquipe().getNomeEquipe());
            System.out.println("Descrição: " + p.getDescricao());
            System.out.println("Data Inicial: " + p.getDataInicio());
            System.out.println("Data Final: " + p.getDataFinal());

            System.out.println("------------------");
        }

    }

    public void editarProjeto() {

        Scanner scanner = new Scanner(System.in);

        ProjetoDAO projetoDAO = new ProjetoDAO();

        System.out.println("===== EDITAR PROJETO =====");

        System.out.print("Digite o ID do projeto: ");

        int projetoId = scanner.nextInt();

        scanner.nextLine();

        System.out.println("\n1 - Alterar nome");
        System.out.println("2 - Alterar descrição");
        System.out.println("3 - Excluir descrição");
        System.out.println("4 - Alterar data final");
        System.out.println("5 - Adicionar equipe");
        System.out.println("6 - Trocar equipe");
        System.out.println("7 - Excluir equipe");

        System.out.print("\nEscolha: ");

        int opcao = scanner.nextInt();

        scanner.nextLine();

        switch (opcao) {

            case 1:

                System.out.print("Novo nome do projeto: ");

                String novoNome = scanner.nextLine();

                projetoDAO.alterarNomeProjeto(projetoId, novoNome);

                System.out.println("Nome alterado!");

                break;

            case 2:

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

                System.out.print("Nova data final (AAAA-MM-DD): ");

                String data = scanner.nextLine();

                projetoDAO.alterarDataFinal(projetoId, data);

                System.out.println("Data final alterada!");

                break;

            case 5:

            case 6:

                System.out.print("ID da equipe: ");

                int equipeId = scanner.nextInt();

                projetoDAO.alterarEquipe(projetoId, equipeId);

                System.out.println("Equipe atualizada!");

                break;

            case 7:

                projetoDAO.excluirEquipe(projetoId);

                System.out.println("Equipe removida!");

                break;

            default:

                System.out.println("Opção inválida.");
        }
    }

}
