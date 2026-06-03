package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // Esse código é para efetuar a conexão do Intellij com o Workbench MySql
    private static final String URL = "jdbc:mysql://localhost:3306/gestaodeprojeto?useTimezone=true&serverTimezone=UTC";

    private static final String USUARIO = "root";

    private static final String SENHA = "password";

    public static Connection conectar() {

        try {
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conectado ao banco!");

            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar:");
            e.printStackTrace();

            return null;
        }
    }
}