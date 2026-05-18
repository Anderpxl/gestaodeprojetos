package controller.service;

import dao.ColaboradorDAO;
import model.Colaborador;

public class LoginService {

    private ColaboradorDAO dao = new ColaboradorDAO();

    public Colaborador autenticar(String usuario, String senha) {

        return dao.fazerLogin(usuario, senha);
    }

}
