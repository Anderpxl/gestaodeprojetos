package controller.service;

import dao.ColaboradorDAO;
import dao.EquipeDAO;
import dao.ProjetoDAO;
import model.Colaborador;

import java.util.List;
import java.util.Scanner;

public class ColaboradorService {

    public void cadastrarColaborador() {

        Colaborador colaborador = new Colaborador();

        Scanner scanner = new Scanner(System.in);

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

        colaborador.setPerfil(Colaborador.Perfil.valueOf(scanner.nextLine().toUpperCase()));

        ColaboradorDAO dao = new ColaboradorDAO();

        boolean sucesso = dao.inserir(colaborador);

        if(sucesso) {
            System.out.println("Colaborador cadastrado.");
        } else {
            System.out.println("Erro ao cadastrar.");
        }
    }

    public void listarColaboradores() {
        ColaboradorDAO dao = new ColaboradorDAO();

        List<Colaborador> lista = dao.listar();

        System.out.println("\n===== COLABORADORES =====");

        for(Colaborador c : lista) {

            System.out.println("ID: " + c.getId());
            System.out.println("Nome: " + c.getNome());
            System.out.println("Email: " + c.getEmail());
            System.out.println("Perfil: " + c.getPerfil());

            System.out.println("------------------");
        }
    }

    public void editarColaborador() {

        Scanner scanner = new Scanner(System.in);

        Colaborador  colaborador = new Colaborador();

        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

        System.out.println("===== EDITAR COLABORADOR =====");

        System.out.print("Digite o ID do colaborador: ");
        int colaboradorId = scanner.nextInt();

        scanner.nextLine();

        System.out.println("\nO que deseja alterar?");
        System.out.println("1 - Alterar Nome");
        System.out.println("2 - Alterar E-mail");
        System.out.println("3 - Alterar Senha");
        System.out.println("4 - Alterar Perfil");
        System.out.println("5 - Excluir Colaborador");


        int opcao = scanner.nextInt();

        scanner.nextLine();

        switch (opcao) {

            case 1:

                System.out.print("Novo nome: ");
                String novoNome = scanner.nextLine();

                colaboradorDAO.trocarNome(colaboradorId, novoNome);

                System.out.println("Nome alterado com sucesso!");

                break;

            case 2:

                System.out.print("Novo e-mail: ");
                String novoEmail = scanner.nextLine();

                if(novoEmail.equals(colaborador.getEmail())){
                    System.out.print("Você já utiliza esse e-mail.");
                }
                if(colaboradorDAO.emailJaExiste(novoEmail)){
                    System.out.println("Outro usuário já utiliza esse e-mail.");
                }

                boolean sucesso = colaboradorDAO.trocarEmail(colaboradorId, novoEmail);

                if(sucesso) {
                    System.out.println("E-mail alterado com sucesso!");
                }else{
                    System.out.println("Erro ao alterar e-mail.");
                }

                colaboradorDAO.trocarEmail(colaboradorId, novoEmail);

                break;

            case 3:

                System.out.print("Nova senha: ");
                String novaSenha = scanner.nextLine();

                colaboradorDAO.trocarSenha(colaboradorId, novaSenha);

                System.out.println("Senha alterada com sucesso!");

                break;

            case 4:

                System.out.println("Perfis:");
                System.out.println("ADMINISTRADOR");
                System.out.println("GERENTE");
                System.out.println("COLABORADOR");

                System.out.print("Novo perfil: ");

                String novoPerfil = scanner.nextLine();

                colaboradorDAO.trocarPerfil(colaboradorId, novoPerfil);

                if (novoPerfil != "ADMINISTRADOR" || novoPerfil != "GERENTE" || novoPerfil != "COLABORADOR") {

                    System.out.println("Perfil inválido.");
                    return;
                }

                System.out.println("Perfil alterado com sucesso!");

                break;

            case 5:

                while(true) {

                    System.out.println("Você deseja excluir este colaborador? (S/N): ");

                    String resposta = scanner.nextLine().toUpperCase();

                    if(!resposta.equals("S") && !resposta.equals("N")) {

                        System.out.println("Opção inválida.");
                        continue;
                    }

                    if(resposta.equals("S")) {

                        boolean sucessoExcluir = colaboradorDAO.excluirUsuario(colaboradorId);

                        if(sucessoExcluir) {

                            System.out.println("Colaborador excluído com sucesso!");

                        } else {

                            System.out.println("Erro ao excluir colaborador.");
                        }
                    }
                    break;
                }

                break;

            default:
                System.out.println("Opção inválida.");
        }
    }

}
