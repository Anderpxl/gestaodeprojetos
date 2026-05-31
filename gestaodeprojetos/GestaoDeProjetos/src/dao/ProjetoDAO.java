package dao;

import model.Projeto;
import model.Equipe;
import model.Tarefa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    public boolean inserir(Projeto projeto) {

        String sql = """
            INSERT INTO gestaodeprojeto.projetos
            (nome_projeto, descricao,
             data_inicio, data_final, equipes_id)

            VALUES (?, ?, ?, ?, ?)
        """;

        try(
                Connection conn = Conexao.conectar();

                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, projeto.getNomeProjeto());
            stmt.setString(2, projeto.getDescricao());
            stmt.setDate(3, java.sql.Date.valueOf(projeto.getDataInicio()));
            stmt.setDate(4, java.sql.Date.valueOf(projeto.getDataFinal()));
            stmt.setInt(5, projeto.getEquipe().getId());

            stmt.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Projeto> listar() {

        List<Projeto> lista = new ArrayList<>();

        String sql = """
        SELECT
            p.id,
            p.nome_projeto,
            p.descricao,
            p.data_inicio,
            p.data_final,

            e.id AS equipe_id,
            e.nome_equipe

        FROM gestaodeprojeto.projetos p

        LEFT JOIN gestaodeprojeto.equipes e
            ON p.equipes_id = e.id
        """;

        try(
                Connection conn = Conexao.conectar();

                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

                Projeto projeto = new Projeto();

                projeto.setId(rs.getInt("id"));

                projeto.setNomeProjeto(rs.getString("nome_projeto"));

                projeto.setDescricao(rs.getString("descricao"));

                projeto.setDataInicio(rs.getDate("data_inicio").toLocalDate());

                projeto.setDataFinal(rs.getDate("data_final").toLocalDate());

                Integer equipeId = (Integer) rs.getObject("equipe_id");

                if(equipeId != null) {

                    Equipe equipe = new Equipe();

                    equipe.setId(equipeId);
                    equipe.setNomeEquipe(rs.getString("nome_equipe"));

                    projeto.setEquipe(equipe);
                }

                TarefaDAO tarefaDAO = new TarefaDAO();

                projeto.setTarefas(tarefaDAO.listarPorProjeto(projeto.getId()));

                lista.add(projeto);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Tarefa> buscarTarefasProjeto(int projetoId) {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = """
        SELECT
            id,
            nome_tarefa,
            descricao,
            data_inicio,
            data_final,
            status_tarefa
        FROM gestaodeprojeto.tarefas
        WHERE projeto_id = ?
        """;

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, projetoId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

                Tarefa tarefa = new Tarefa();

                tarefa.setId(rs.getInt("id"));
                tarefa.setNomeTarefa(rs.getString("nome_tarefa"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                tarefa.setDataFinal(rs.getDate("data_final").toLocalDate());
                tarefa.setStatus(Tarefa.Status.valueOf(rs.getString("status_tarefa")));

                tarefas.add(tarefa);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return tarefas;
    }

    public void alterarNomeProjeto(int projetoId, String novoNome) {

        String sql = """
        UPDATE gestaodeprojeto.projetos
        SET nome_projeto = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();

                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, novoNome);

            stmt.setInt(2, projetoId);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void alterarDescricao(int projetoId, String descricao) {

        String sql = """
        UPDATE gestaodeprojeto.projetos
        SET descricao = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();

                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, descricao);

            stmt.setInt(2, projetoId);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void excluirDescricao(int projetoId) {

        String sql = """
        UPDATE gestaodeprojeto.projetos
        SET descricao = NULL
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();

                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, projetoId);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void alterarDataFinal(int projetoId, String novaData) {

        String sql = """
        UPDATE gestaodeprojeto.projetos
        SET data_final = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();

                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setDate(1, java.sql.Date.valueOf(novaData));

            stmt.setInt(2, projetoId);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void alterarEquipe(int projetoId, int equipeId) {

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

    public Projeto buscarPorId(int id) {

        String sql = """
        SELECT *
        FROM projetos
        WHERE id = ?
        """;

        try(
                Connection conn = Conexao.conectar();

                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {

                Projeto projeto = new Projeto();

                projeto.setId(rs.getInt("id"));

                projeto.setNomeProjeto(rs.getString("nome_projeto"));

                projeto.setDescricao(rs.getString("descricao"));

                projeto.setDataInicio(rs.getDate("data_inicio").toLocalDate());

                projeto.setDataFinal(rs.getDate("data_final").toLocalDate());

                return projeto;
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    public int contarTarefasProjeto(int projetoId) {

        String sql = """
        SELECT COUNT(*) AS total
        FROM gestaodeprojeto.tarefas
        WHERE projeto_id = ?
        """;

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, projetoId);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return rs.getInt("total");
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }

    public int contarTarefasPorStatus(int projetoId, String status) {

        String sql = """
        SELECT COUNT(*) AS total
        FROM gestaodeprojeto.tarefas
        WHERE projeto_id = ?
        AND status_tarefa = ?
        """;

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, projetoId);

            stmt.setString(2, status);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return rs.getInt("total");
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
