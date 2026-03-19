package org.elis.manoforte.dao.jdbc;

import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.model.*;
import org.elis.manoforte.utility.SqlQuery;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAOJDBC implements RecensioneDAO {

    DataSource dataSource;

    public RecensioneDAOJDBC(DataSource dataSource) {this.dataSource = dataSource;}

    @Override
    public List<Recensione> findAll() {
        List<Recensione> lista = new ArrayList<>();
        String sql = "SELECT r.*, " +
                     "u1.nome AS nome_cliente, u1.cognome AS cogn_cliente, " +
                     "u2.nome AS nome_pro, u2.cognome AS cogn_pro " +
                     "FROM recensione r " +
                     "JOIN utente u1 ON r.id_cliente = u1.id " +
                     "JOIN utente u2 ON r.id_professionista = u2.id " +
                     "ORDER BY r.data DESC";

        try (Connection conn = dataSource.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapResultSetToRecensione(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    @Override
    public List<Recensione> findAllwithConditions() {
        List<Recensione> lista = new ArrayList<>();
        String sql = "SELECT * FROM (" +
                "  SELECT r.*, " +
                "  u1.nome AS nome_cliente, u1.cognome AS cogn_cliente, " +
                "  u2.nome AS nome_pro, u2.cognome AS cogn_pro, " +
                "  ROW_NUMBER() OVER (PARTITION BY r.id_professionista ORDER BY r.data DESC) as rn " +
                "  FROM recensione r " +
                "  JOIN utente u1 ON r.id_cliente = u1.id " +
                "  JOIN utente u2 ON r.id_professionista = u2.id " +
                ") AS t " +
                "WHERE rn = 1 " +
                "ORDER BY data DESC " +
                "LIMIT 5";

        try (Connection conn = dataSource.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapResultSetToRecensione(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void inserisciRecensione(Recensione r) {
        String sql = "INSERT INTO recensione (descrizione, voto, data, id_cliente, id_professionista) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, r.getDescrizione());
            ps.setInt(2, r.getVoto());
            ps.setDate(3, r.getData() != null ? Date.valueOf(r.getData()) : new Date(System.currentTimeMillis()));
            ps.setLong(4, r.getId_cliente());
            ps.setLong(5, r.getId_professionista());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Recensione> findByIdProfessionista(long idPro) {
        List<Recensione> lista = new ArrayList<>();
        String sql = "SELECT r.*, u1.nome AS nome_cliente, u1.cognome AS cogn_cliente, " +
                     "u2.nome AS nome_pro, u2.cognome AS cogn_pro " +
                     "FROM recensione r " +
                     "JOIN utente u1 ON r.id_cliente = u1.id " +
                     "JOIN utente u2 ON r.id_professionista = u2.id " +
                     "WHERE r.id_professionista = ? ORDER BY r.data DESC";

        try (Connection conn = dataSource.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setLong(1, idPro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToRecensione(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<CardRecensione> getRecensioneByEmailProfessionistaLimit(String email, int i) throws SQLException{
        List<CardRecensione> richieste = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()){
            PreparedStatement statement = conn.prepareStatement(SqlQuery.elencoRecensioniByEmailProfessionistaLimit);
            statement.setString(1, email);
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CardRecensione card = new CardRecensione();
                card.setId(rs.getLong("id_recensione"));
                card.setData(rs.getDate("data_recensione").toLocalDate());
                card.setCliente(
                        new Utente(rs.getString("nome"),
                                rs.getString("cognome"),
                                rs.getString("email")));
                richieste.add(card);
            }
        }
        return richieste;
    }

    public void delete(long id) {
        String sql = "DELETE FROM recensione WHERE id = ?";
        try (Connection conn = dataSource.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private Recensione mapResultSetToRecensione(ResultSet rs) throws SQLException {
        Recensione r = new Recensione(
                rs.getLong("id"),
                rs.getString("descrizione"),
                rs.getInt("voto"),
                rs.getDate("data").toLocalDate(),
                rs.getLong("id_cliente"),
                rs.getLong("id_professionista")
        );

        return r;
    }
}