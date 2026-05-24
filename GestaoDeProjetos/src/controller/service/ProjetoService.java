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

}
