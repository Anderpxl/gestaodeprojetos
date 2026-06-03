package controller.service;

import dao.ColaboradorDAO;
import model.Colaborador;
// Classe para autenticar o usuario e senha buscando informação no banco de dados referento ao colaborador
public class LoginService {

    private ColaboradorDAO dao = new ColaboradorDAO();

    public Colaborador autenticar(String usuario, String senha) {

        return dao.fazerLogin(usuario, senha);

    }

}
