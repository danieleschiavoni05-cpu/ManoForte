package org.elis.manoforte.dao.definition;

import org.elis.manoforte.utility.DataSourceConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private static Connection getConnection() throws Exception {
        return DataSourceConfig.getDataSource().getConnection();
    }

    public static void aggiungiCitta(String nome) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO citta (nome) VALUES (?)");
            ps.setString(1, nome);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void aggiungiProfessione(String nome) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO professione (nome) VALUES (?)");
            ps.setString(1, nome);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<Object[]> getCitta() {
        List<Object[]> lista = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT id, nome FROM citta ORDER BY nome");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Object[]{rs.getInt("id"), rs.getString("nome")});
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public static List<Object[]> getProfessioni() {
        List<Object[]> lista = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT id, nome FROM professione ORDER BY nome");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Object[]{rs.getInt("id"), rs.getString("nome")});
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public static void eliminaCitta(int id) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM citta WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void eliminaProfessione(int id) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM professione WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void modificaCitta(int id, String nome) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE citta SET nome=? WHERE id=?");
            ps.setString(1, nome);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void modificaProfessione(int id, String nome) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE professione SET nome=? WHERE id=?");
            ps.setString(1, nome);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
