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

        if(usuarioLogado != null) {
            menuPrincipal();
        }
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

    public void menuPrincipal() {

        ColaboradorService colaboradorService = new ColaboradorService();
        EquipeService equipeService = new EquipeService();
        ProjetoService projetoService = new ProjetoService();


        int opcao = -1;

        while(opcao != 0) {

            System.out.println("\n===== GERENCIADOR DE PROJETOS =====");
            System.out.println("1 - Cadastrar colaborador");
            System.out.println("2 - Cadastrar equipe");
            System.out.println("3 - Cadastrar projeto");
            System.out.println("4 - Listar colaboradores");
            System.out.println("5 - Listar equipes");
            System.out.println("6 - Listar projetos");

            if(usuarioLogado.getPerfil() == Colaborador.Perfil.ADMINISTRADOR || usuarioLogado.getPerfil() == Colaborador.Perfil.GERENTE) {

                System.out.println("7 - Configurações");

            }

            System.out.println("0 - Sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {

                case 1:
                    cadastrarColaborador();
                    break;

                case 2:
                    cadastrarEquipe();
                    break;

                case 3:
                    cadastrarProjeto();
                    break;

                case 4:
                    listarColaboradores();
                    break;

                case 5:
                    listarEquipes();
                    break;

                case 6:
                    listarProjetos();
                    break;

                case 7:
                    if(usuarioLogado.getPerfil() == Colaborador.Perfil.ADMINISTRADOR || usuarioLogado.getPerfil() == Colaborador.Perfil.GERENTE) {

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

        System.out.println("\n===== CONFIGURAÇÕES =====");

        System.out.println("1 - Editar colaborador");
        System.out.println("2 - Excluir colaborador");
        System.out.println("3 - Editar equipe");
        System.out.println("4 - Excluir equipe");
        System.out.println("5 - Editar projeto");
        System.out.println("6 - Excluir projeto");

    }

}