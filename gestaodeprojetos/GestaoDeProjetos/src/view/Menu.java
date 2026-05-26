package view;

import controller.service.ColaboradorService;
import controller.service.EquipeService;
import controller.service.LoginService;
import controller.service.ProjetoService;
import dao.ColaboradorDAO;
import dao.Conexao;
import dao.EquipeDAO;
import dao.ProjetoDAO;
import model.Colaborador;
import model.Equipe;
import model.Projeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner scanner = new Scanner(System.in);

    LoginService loginService = new LoginService();

    private Colaborador usuarioLogado;

    public void iniciar() {

        realizarLogin();

    }

    private void realizarLogin() {

        int loginOpcao = -1;

        while(loginOpcao != 0) {

            System.out.println("\n======== TELA INICIAL ========");
            System.out.println("1. Login");
            System.out.println("2. Sair");

            loginOpcao = scanner.nextInt();
            scanner.nextLine();

            switch (loginOpcao) {
                case 1:
                    fazerLogin();
                    break;

                case 2:
                    System.out.println("Sistema encerrado");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public void fazerLogin(){

        System.out.println("===== LOGIN =====");

        System.out.print("Usuário: ");
        String usuario = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        usuarioLogado = loginService.autenticar(usuario, senha);

        if(usuarioLogado == null) {

            System.out.println("Usuário ou senha inválidos.");
            return;

        }
            System.out.println("Bem-vindo, " + usuarioLogado.getNome());
            menuPrincipal();
    }

    private boolean podeGerenciar() {

        return usuarioLogado.getPerfil() == Colaborador.Perfil.ADMINISTRADOR || usuarioLogado.getPerfil() == Colaborador.Perfil.GERENTE;

    }

    public void menuPrincipal() {

        ColaboradorService colaboradorService = new ColaboradorService();
        EquipeService equipeService = new EquipeService();
        ProjetoService projetoService = new ProjetoService();

        int opcao = -1;

        while(opcao != 0) {

            System.out.println("\n===== GERENCIADOR DE PROJETOS =====");

            System.out.println("1 - Listar colaboradores");
            System.out.println("2 - Listar equipes");
            System.out.println("3 - Listar projetos");

            if(podeGerenciar()) {

                System.out.println("4 - Cadastrar colaborador");
                System.out.println("5 - Cadastrar equipe");
                System.out.println("6 - Cadastrar projeto");
                System.out.println("7 - Configurações");

            }

            System.out.println("0 - Sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {

                case 1:
                    colaboradorService.listarColaboradores();
                    break;

                case 2:
                    equipeService.listarEquipes();
                    break;

                case 3:
                    projetoService.listarProjetos();
                    break;

                case 4:
                    if(podeGerenciar()) {
                        colaboradorService.cadastrarColaborador();
                    } else {
                        System.out.println("Acesso negado.");
                    }
                    break;

                case 5:
                    if(podeGerenciar()) {
                        equipeService.cadastrarEquipe();
                    } else {
                        System.out.println("Acesso negado.");
                    }
                    break;

                case 6:
                    if(podeGerenciar()) {
                        projetoService.cadastrarProjeto();
                    } else {
                        System.out.println("Acesso negado.");
                    }
                    break;

                case 7:
                    if(podeGerenciar()) {
                        menuConfiguracoes();
                    } else {
                        System.out.println("Acesso negado.");
                    }
                    break;

                case 0:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public void menuConfiguracoes() {

        ColaboradorService colaboradorService = new ColaboradorService();
        EquipeService equipeService = new EquipeService();
        ProjetoService projetoService = new ProjetoService();

        int opcao = -1;

        while(opcao != 0) {

        System.out.println("\n===== CONFIGURAÇÕES =====");

        System.out.println("1 - Alterar colaborador");
        System.out.println("2 - Alterar equipe");
        System.out.println("3 - Alterar projeto");
        System.out.println("0 - Voltar");

        opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {

            case 1:
                colaboradorService.editarColaborador();
                break;

            case 2:
                equipeService.editarEquipe();
                break;

            case 3:
                projetoService.editarProjeto();
                break;

            case 0:
                System.out.println("Voltando...");
                break;

            default:
                System.out.println("Opção inválida");
        }
        }
    }

}