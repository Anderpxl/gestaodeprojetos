package dao;

import model.Colaborador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {

    // Método para conferir as informações de usuário e senha no banco de dados para efetuar o login
    public Colaborador fazerLogin(String usuario, String senha) {

        String sql = """
                SELECT *
                FROM gestaodeprojeto.colaboradores
                WHERE usuario = ?
                AND senha = ?
                """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Colaborador c = new Colaborador();

                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setUsuario(rs.getString("usuario"));
                c.setEmail(rs.getString("email"));
                c.setPerfil(Colaborador.Perfil.valueOf(rs.getString("perfil")));

                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Método para inserir informações de um novo colaborador no banco de dados
    public boolean inserir(Colaborador colaborador) {

        String sql = """
            INSERT INTO gestaodeprojeto.colaboradores
            (cpf, usuario, senha, nome, email, perfil)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, colaborador.getCpf());
            stmt.setString(2, colaborador.getUsuario());
            stmt.setString(3, colaborador.getSenha());
            stmt.setString(4, colaborador.getNome());
            stmt.setString(5, colaborador.getEmail());
            stmt.setString(6, colaborador.getPerfil().name());

            stmt.executeUpdate();

            return true;

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // Método rodar o código no banco de dados para listar os colaboradores do banco de dados
    public List<Colaborador> listar() {

        List<Colaborador> lista = new ArrayList<>();

        String sql = "SELECT * FROM gestaodeprojeto.colaboradores";

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Colaborador c = new Colaborador();

                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setUsuario(rs.getString("usuario"));
                c.setPerfil(Colaborador.Perfil.valueOf(rs.getString("perfil")));

                lista.add(c);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    // Método para alterar o perfil do colabarador baseado no Id no banco de dados
    public void trocarPerfil(int colaboradorId, String novoPerfil) {

        String sql = """
        UPDATE gestaodeprojeto.colaboradores
        SET perfil = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, novoPerfil);
            stmt.setInt(2, colaboradorId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método para alterar nome do colaborador baseado no Id no banco de dados
    public void trocarNome(int colaboradorId, String novoNome) {

        String sql = """
        UPDATE gestaodeprojeto.colaboradores
        SET nome = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, novoNome);
            stmt.setInt(2, colaboradorId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método para alterar senha do colaborador baseado no Id no banco de dados
    public void trocarSenha(int colaboradorId, String novaSenha) {

        String sql = """
        UPDATE gestaodeprojeto.colaboradores
        SET senha = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, novaSenha);
            stmt.setInt(2, colaboradorId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método para alterar e-mail do colaborador baseado no Id no banco de dados
    public boolean trocarEmail(int colaboradorId, String novoEmail) {

        String sql = """
        UPDATE gestaodeprojeto.colaboradores
        SET email = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, novoEmail);
            stmt.setInt(2, colaboradorId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Método para verificar se já existe o e-mail cadastrado no banco de dados e evitar duplicidade
    public boolean emailJaExiste(String email) {

        String sql = """
        SELECT COUNT(*)
        FROM gestaodeprojeto.colaboradores
        WHERE email = ?
        """;

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // Método para excluir um usuário do banco de dados baseado no Id (obs: existe uma tabela para salvar as informações cadastradas)
    public boolean excluirUsuario(int id) {

        String sql = """
        DELETE FROM gestaodeprojeto.colaboradores
        WHERE id = ?
        """;

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

            return true;

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}