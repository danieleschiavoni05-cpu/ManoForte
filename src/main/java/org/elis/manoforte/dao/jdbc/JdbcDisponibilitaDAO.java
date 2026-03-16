package org.elis.manoforte.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.elis.manoforte.dao.definition.DisponibilitaDAO;
import org.elis.manoforte.model.Disponibilita;

public class JdbcDisponibilitaDAO implements DisponibilitaDAO{
	private DataSource dataSource;

    public JdbcDisponibilitaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	@Override
	public List<Disponibilita> findDisponibilitaByIdProfessionista(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Disponibilita> findDisponibilitaByData(LocalDate data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Disponibilita> findDisponibilitaByDataOra(LocalDateTime dataora) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inserisciDisponibilita(Disponibilita disponibilita) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public List<Disponibilita> findDisponibilitaByEmailProfessionista(String email) throws Exception {
	    List<Disponibilita> lista = new ArrayList<>();
	    
	    // La query dipende dalla struttura del tuo DB. 
	    // Qui ipotizzo una JOIN tra disponibilita e utente tramite l'id_professionista
	    String query = "SELECT d.* FROM disponibilita d " +
	                   "JOIN utente u ON d.id_utente = u.id " +
	                   "WHERE u.email = ?";

	    try (Connection conn = dataSource.getConnection(); // O il tuo metodo per la connessione
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        
	        ps.setString(1, email);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Disponibilita d = new Disponibilita();
	                d.setId(rs.getLong("id"));
	                d.setData(rs.getDate("data").toLocalDate());
	                d.setOra_inizio(rs.getTime("ora_inizio").toLocalTime());
	                d.setOra_fine(rs.getTime("ora_fine").toLocalTime());
	              
	                
	                lista.add(d);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Errore durante il recupero delle disponibilità per email: " + email);
	    }
	    
	    return lista;
	}

}
