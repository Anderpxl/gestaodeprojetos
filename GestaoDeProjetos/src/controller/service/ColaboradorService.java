package controller.service;

import dao.ColaboradorDAO;
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

    public void listarColaboradores() {
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

}
