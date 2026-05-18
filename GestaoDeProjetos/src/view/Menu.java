package view;

import controller.service.LoginService;
import dao.ColaboradorDAO;
import dao.EquipeDAO;
import model.Colaborador;
import model.Equipe;

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

    private void cadastrarColaborador() {
        Colaborador colaborador = new Colaborador();

        System.out.println("\n===== CADASTRO DE COLABORADOR =====");

        System.out.print("Nome: ");
        colaborador.setNome(scanner.nextLine());

        System.out.print("CPF: ");
        colaborador.setCpf(scanner.nextLine());

        System.out.print("Email: ");
        colaborador.setEmail(scanner.nextLine());

        System.out.print("Usuário: ");
        colaborador.setUsuario(scanner.nextLine());

        System.out.print("Senha: ");
        colaborador.setSenha(scanner.nextLine());

        System.out.print("Cargo (ADMINISTRADOR, GERENTE ou COLABORADOR): ");

        colaborador.setPerfil(
                Colaborador.Perfil.valueOf(
                        scanner.nextLine().toUpperCase()
                )
        );

        ColaboradorDAO dao = new ColaboradorDAO();

        boolean sucesso = dao.inserir(colaborador);

        if(sucesso) {
            System.out.println("Colaborador cadastrado.");
        } else {
            System.out.println("Erro ao cadastrar.");
        }
    }

    private void listarColaboradores() {
        ColaboradorDAO dao = new ColaboradorDAO();

        List<Colaborador> lista = dao.listar();

        System.out.println("\n===== COLABORADORES =====");

        for(Colaborador c : lista) {

            System.out.println("ID: " + c.getId());

            System.out.println("Nome: " + c.getNome());

            System.out.println("Email: " + c.getEmail());

            System.out.println("Usuário: " + c.getUsuario());

            System.out.println("------------------");
        }
    }

    private void cadastrarEquipe() {

        Equipe equipe = new Equipe();

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

    private void listarEquipes() {

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

        } else {

            System.out.println("Bem-vindo, " + usuarioLogado.getNome());
        }

        if(usuarioLogado.getPerfil() == Colaborador.Perfil.GERENTE || usuarioLogado.getPerfil() == Colaborador.Perfil.ADMINISTRADOR) {

        }

    }

    public void menuPrincipal() {

        int opcao = -1;

        while(opcao != 0) {

            System.out.println("\n===== GERENCIADOR DE PROJETOS =====");
            System.out.println("1 - Cadastrar colaborador");
            System.out.println("2 - Cadastrar equipe");
            System.out.println("3 - Cadastrar projeto");
            System.out.println("4 - Listar colaboradores");
            System.out.println("5 - Listar equipes");
            System.out.println("6 - Listar projetos");
            System.out.println("7 - Configurações");
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
                    menuConfiguracoes();
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