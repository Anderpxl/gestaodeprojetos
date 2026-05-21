package dao;

import model.Projeto;
import model.Equipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    public boolean inserir(Projeto projeto) {

        String sql = """
            INSERT INTO gestaodeprojeto.projetos
            (nome_projeto, descricao,
             data_inicio, data_final, equipe_id)

            VALUES (?, ?, ?, ?, ?)
        """;

        Equipe equipe = new Equipe();

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

        JOIN gestaodeprojeto.equipes e
            ON p.equipe_id = e.id
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

                Equipe equipe = new Equipe();

                equipe.setId(rs.getInt("equipe_id"));

                equipe.setNomeEquipe(rs.getString("nome_equipe"));

                projeto.setEquipe(equipe);

                lista.add(projeto);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
