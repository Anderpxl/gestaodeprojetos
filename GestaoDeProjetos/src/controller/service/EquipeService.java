package controller.service;

import dao.EquipeDAO;
import model.Colaborador;
import model.Equipe;

import java.util.List;
import java.util.Scanner;

public class EquipeService {

    public void cadastrarEquipe() {

        Equipe equipe = new Equipe();

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== CADASTRO DE EQUIPE =====");

        System.out.print("Nome da equipe: ");
        equipe.setNomeEquipe(scanner.nextLine());

        System.out.print("ID do gerente: ");

        Colaborador gerente = new Colaborador();

        gerente.setId(scanner.nextInt());

        scanner.nextLine();

        equipe.setGerente(gerente);

        System.out.print("ID do líder: ");

        Colaborador lider = new Colaborador();

        lider.setId(scanner.nextInt());

        scanner.nextLine();

        equipe.setLider(lider);

        EquipeDAO dao = new EquipeDAO();

        boolean sucesso = dao.inserir(equipe);

        if(sucesso) {
            System.out.println("Equipe cadastrada.");
        } else {
            System.out.println("Erro ao cadastrar equipe.");
        }
    }

    public void listarEquipes() {

        EquipeDAO dao = new EquipeDAO();

        List<Equipe> lista = dao.listar();

        System.out.println("\n===== EQUIPES =====");

        for(Equipe e : lista) {

            System.out.println("ID: " + e.getId());
            System.out.println("Equipe: " + e.getNomeEquipe());
            System.out.println("Gerente: " + e.getGerente().getNome());
            System.out.println("Líder: " + e.getLider().getNome());

            System.out.println("------------------");
        }
    }

}
