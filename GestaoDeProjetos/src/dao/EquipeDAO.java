package dao;

import model.Colaborador;
import model.Equipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAO {

    public boolean inserir(Equipe equipe) {

        String sql = """
            INSERT INTO equipes
            (nome_equipe, gerente_id, lider_id)
            VALUES (?, ?, ?)
        """;

        try(
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, equipe.getNomeEquipe());
            stmt.setInt(2, equipe.getGerente().getId());
            stmt.setInt(3, equipe.getLider().getId());

            stmt.executeUpdate();

            return true;

        } catch(SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public List<Equipe> listar() {

        List<Equipe> lista = new ArrayList<>();

        String sql = """
        SELECT
            e.id,
            e.nome_equipe,

            g.id AS gerente_id,
            g.nome AS gerente_nome,

            l.id AS lider_id,
            l.nome AS lider_nome

        FROM equipes e

        JOIN colaboradores g
            ON e.gerente_id = g.id

        JOIN colaboradores l
            ON e.lider_id = l.id
    """;

        try(
                Connection conn = Conexao.conectar();

                PreparedStatement stmt = conn.prepareStatement(sql)) {

                ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

                Equipe equipe = new Equipe();

                equipe.setId(rs.getInt("id"));
                equipe.setNomeEquipe(rs.getString("nome_equipe"));

                Colaborador gerente = new Colaborador();

                gerente.setId(rs.getInt("gerente_id"));
                gerente.setNome(rs.getString("gerente_nome"));

                equipe.setGerente(gerente);

                Colaborador lider = new Colaborador();

                lider.setId(rs.getInt("lider_id"));
                lider.setNome(rs.getString("lider_nome"));

                equipe.setLider(lider);

                lista.add(equipe);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
