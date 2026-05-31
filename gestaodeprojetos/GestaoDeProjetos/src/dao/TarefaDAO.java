package dao;

import model.Tarefa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public boolean inserirTarefa(Tarefa tarefa, int projetoId){

        String sql = """
        INSERT INTO gestaodeprojeto.tarefas
        (
            nome_tarefa,
            descricao,
            data_inicio,
            data_final,
            status_tarefa,
            projeto_id
        )
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try(
                Connection conn = Conexao.conectar();

                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, tarefa.getNomeTarefa());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setDate(3, Date.valueOf(tarefa.getDataInicio()));
            stmt.setDate(4, Date.valueOf(tarefa.getDataFinal()));
            stmt.setString(5, tarefa.getStatus().name());
            stmt.setInt(6, projetoId);

            stmt.executeUpdate();

            return true;

        } catch(SQLException e) {

            e.printStackTrace();
            return false;
        }

    }

    public List<Tarefa> listarPorProjeto(int projetoId) {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = """
        SELECT *
        FROM gestaodeprojeto.tarefas
        WHERE projeto_id = ?
        AND data_final >= CURDATE()
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

                tarefas.add(tarefa);}

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return tarefas;
    }

    public boolean alterarNome(int tarefaId, String novoNome) {

        String sql = """
        UPDATE gestaodeprojeto.tarefas
        SET nome_tarefa = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, novoNome);
            stmt.setInt(2, tarefaId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterarDescricao(int tarefaId, String descricao) {

        String sql = """
        UPDATE gestaodeprojeto.tarefas
        SET descricao = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, descricao);
            stmt.setInt(2, tarefaId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterarDataInicio(int tarefaId, LocalDate dataInicio) {

        String sql = """
        UPDATE gestaodeprojeto.tarefas
        SET data_inicio = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setDate(1, Date.valueOf(dataInicio));
            stmt.setInt(2, tarefaId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterarDataFinal(int tarefaId, LocalDate dataFinal) {

        String sql = """
        UPDATE gestaodeprojeto.tarefas
        SET data_final = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setDate(1, Date.valueOf(dataFinal));
            stmt.setInt(2, tarefaId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Tarefa buscarPorId(int tarefaId) {

        String sql = """
        SELECT *
        FROM gestaodeprojeto.tarefas
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, tarefaId);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {

                Tarefa tarefa = new Tarefa();

                tarefa.setId(rs.getInt("id"));
                tarefa.setNomeTarefa(rs.getString("nome_tarefa"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                tarefa.setDataFinal(rs.getDate("data_final").toLocalDate());

                return tarefa;
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    public boolean alterarStatus(int tarefaId, Tarefa.Status status) {

        String sql = """
        UPDATE gestaodeprojeto.tarefas
        SET status_tarefa = ?
        WHERE id = ?
        """;

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, status.name());

            stmt.setInt(2, tarefaId);

            return stmt.executeUpdate() > 0;

        } catch(SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

}
