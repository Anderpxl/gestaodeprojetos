package dao;

import model.Colaborador;
import model.Equipe;
import model.Projeto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAO {
    // Método para criar equipe no banco de dados
    public int criarEquipe(String nomeEquipe, int gerenteId) {

        String sql = """
        INSERT INTO gestaodeprojeto.equipes
        (nome_equipe, gerente_id)
        VALUES (?, ?)
        """;

        int equipeId = 0;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, nomeEquipe);
            stmt.setInt(2, gerenteId);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                equipeId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipeId;
    }
    // Método para listar as equipes cadastradas no banco de dados
    public List<Equipe> listar() {

        List<Equipe> lista = new ArrayList<>();

        String sql = """
            SELECT
            e.id,
            e.nome_equipe,

            g.id AS gerente_id,
            g.nome AS gerente_nome

            FROM gestaodeprojeto.equipes e

            LEFT JOIN gestaodeprojeto.colaboradores g
            ON e.gerente_id = g.id
        """;

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

                Equipe equipe = new Equipe();

                equipe.setId(rs.getInt("id"));
                equipe.setNomeEquipe(rs.getString("nome_equipe"));

                int gerenteId = rs.getInt("gerente_id");

                if (!rs.wasNull()) {

                    Colaborador gerente = new Colaborador();
                    gerente.setId(gerenteId);

                    gerente.setNome(rs.getString("gerente_nome"));
                    equipe.setGerente(gerente);
                } else {
                    equipe.setGerente(null);
                }

                List<Colaborador> colaboradores = buscarColaboradoresEquipe(conn, equipe.getId());
                equipe.setColaboradores(colaboradores);

                lista.add(equipe);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    // Método de listar/buscar equipes relacionadas por Id com um projeto no banco de dados
    public List<Projeto> buscarProjetosEquipe(int equipeId) {

        List<Projeto> projetos = new ArrayList<>();

        String sql = """
        SELECT
            id,
            nome_projeto,
            descricao,
            data_inicio,
            data_final
        FROM gestaodeprojeto.projetos
        WHERE equipes_id = ?
        """;

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, equipeId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

                Projeto projeto = new Projeto();

                projeto.setId(rs.getInt("id"));
                projeto.setNomeProjeto(rs.getString("nome_projeto"));
                projeto.setDescricao(rs.getString("descricao"));
                projeto.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                projeto.setDataFinal(rs.getDate("data_final").toLocalDate());

                projetos.add(projeto);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return projetos;
    }
    // Método para listar/buscar colaboradores relacionados por Id com uma equipe no banco de dados
    private List<Colaborador> buscarColaboradoresEquipe(Connection conn, int equipeId) throws SQLException {

        List<Colaborador> colaboradores = new ArrayList<>();

        String sql = """
        SELECT
            c.id,
            c.nome
        FROM colaboradores c
        JOIN equipe_colaborador ec
            ON c.id = ec.colaboradores_id
        WHERE ec.equipes_id = ?
        """;

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, equipeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    Colaborador colaborador = new Colaborador();

                    colaborador.setId(rs.getInt("id"));
                    colaborador.setNome(rs.getString("nome"));

                    colaboradores.add(colaborador);
                }
            }
        }
        return colaboradores;
    }
    // Método para alterar no da equipe por Id no banco de dados
    public void alterarNomeEquipe(int equipeId, String novoNome) {

        String sql = """
        UPDATE gestaodeprojeto.equipes
        SET nome_equipe = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, novoNome);
            stmt.setInt(2, equipeId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método para adicionar um projeto relacionado ao Id da equipe no banco de dados
    public void adicionarProjeto(int equipeId, int projetoId) {

        String sql = """
        UPDATE gestaodeprojeto.projetos
        SET equipes_id = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, equipeId);
            stmt.setInt(2, projetoId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método para alterar um projeto de uma equipe a partir do Id do projeto e inserir um novo projeto à equipe no banco de dados
    public void trocarProjeto(int equipeId, int projetoAtual, int novoProjeto) {

        removerProjeto(equipeId, projetoAtual);
        adicionarProjeto(equipeId, novoProjeto);

    }
    // Método para remover um projeto de uma equipe a partir do Id do projeto no banco de dados
    public void removerProjeto(int equipeId, int projetoId) {

        String sql = """
        UPDATE gestaodeprojeto.projetos
        SET equipes_id = NULL
        WHERE id = ?
        AND equipes_id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, projetoId);
            stmt.setInt(2, equipeId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método para adicionar um colaborador a uma equipe através do Id no banco de dados
    public void adicionarColaborador(int equipeId, int colaboradorId) {

        String sql = """
        INSERT INTO gestaodeprojeto.equipe_colaborador
        (equipes_id, colaboradores_id)
        VALUES (?, ?)
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, equipeId);
            stmt.setInt(2, colaboradorId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método para alterar um colaborador de equipe através do Id no banco de dados
    public void trocarColaborador(int equipeId, int colaboradorAtual, int novoColaborador) {

        excluirColaborador(equipeId, colaboradorAtual);
        adicionarColaborador(equipeId, novoColaborador);
    }
    // Método para remover um colaborador de uma equipe através do Id no banco de dados
    public void excluirColaborador(int equipeId, int colaboradorId) {

        String sql = """
        DELETE FROM gestaodeprojeto.equipe_colaborador
        WHERE equipes_id = ?
        AND colaboradores_id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, equipeId);
            stmt.setInt(2, colaboradorId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método parar adicionar um gerente a um projeto através do Id no banco de dados
    public void adicionarGerente(int equipeId, int gerenteId) {

        String sql = """
        UPDATE gestaodeprojeto.equipes
        SET gerente_id = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, gerenteId);
            stmt.setInt(2, equipeId);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Gerente vinculado à equipe.");
            } else {
                System.out.println("Equipe não encontrada.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método para remover o gerente de uma equipe através do Id no banco de dados
    public void removerGerente(int gerenteEquipeId) {

        String sql = """
        UPDATE gestaodeprojeto.equipes
        SET gerente_id = NULL
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, gerenteEquipeId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método para excluir uma equipe através do Id no banco de dados
    public void excluirEquipe(int equipeId) {
        String sql = """
        DELETE FROM gestaodeprojeto.equipes
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, equipeId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método para verificar se a equipe existe no banco de dados através do Id
    public boolean verificaEquipe(int equipeId) {

        String sql =
                "SELECT id FROM equipes WHERE id = ?";

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, equipeId);

            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Método para buscar a equipe através do Id no banco de dados
    public Equipe buscarPorId(int equipeId) {

        String sql = """
        SELECT
            id,
            nome_equipe,
            gerente_id
        FROM gestaodeprojeto.equipes
        WHERE id = ?
        """;

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, equipeId);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Equipe equipe = new Equipe();

                equipe.setId(rs.getInt("id"));
                equipe.setNomeEquipe(rs.getString("nome_equipe"));

                return equipe;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
