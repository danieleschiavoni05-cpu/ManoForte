package org.elis.manoforte.dao.jdbc;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.elis.manoforte.dao.definition.DisponibilitaDAO;
import org.elis.manoforte.exception.NessunValoreTrovatoException;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Disponibilita;
import org.elis.manoforte.model.TipoDisponibilita;

public class JdbcDisponibilitaDAO implements DisponibilitaDAO{
	private DataSource dataSource;

    public JdbcDisponibilitaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	@Override
	public List<Disponibilita> findDisponibilitaByIdProfessionista(long id) throws Exception {
		List<Disponibilita> disponibilita = new ArrayList<>();
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM disponibilita WHERE id_utente = ?");
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				disponibilita.add(new Disponibilita(
						resultSet.getLong("id"),
						resultSet.getDate("data").toLocalDate(),
						resultSet.getTime("ora_inizio").toLocalTime(),
						resultSet.getTime("ora_fine").toLocalTime(),
						resultSet.getLong("id_utente"),
						TipoDisponibilita.values()[resultSet.getInt("tipo")],
						DayOfWeek.values()[resultSet.getInt("giorno_settimana")]
				));
			}
			return disponibilita;
		}catch (SQLException e){
			e.printStackTrace();
			throw new SQLException("Errore di connessione al database.");
		}
    }

	@Override
	public List<Disponibilita> findDisponibilitaByData(LocalDate data) throws Exception {
		List<Disponibilita> disponibilita = new ArrayList<>();
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM disponibilita WHERE data = ?");
			statement.setDate(1, Date.valueOf(data));
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				disponibilita.add(new Disponibilita(
						resultSet.getLong("id"),
						resultSet.getDate("data").toLocalDate(),
						resultSet.getTime("ora_inizio").toLocalTime(),
						resultSet.getTime("ora_fine").toLocalTime(),
						resultSet.getLong("id_utente"),
						TipoDisponibilita.values()[resultSet.getInt("tipo")],
						DayOfWeek.values()[resultSet.getInt("giorno_settimana")]
				));
			}
			return disponibilita;
		}catch (SQLException e){
			e.printStackTrace();
			throw new SQLException("Errore di connessione al database.");
		}

	}

	@Override
	public List<Disponibilita> findDisponibilitaByDataOra(LocalDateTime dataora) throws Exception {
		List<Disponibilita> disponibilita = new ArrayList<>();
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM disponibilita WHERE data = ? AND ora_inizio = ?");
			statement.setDate(1, Date.valueOf(LocalDate.from(dataora)));
			statement.setTime(2, Time.valueOf(LocalTime.from(dataora)));
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				disponibilita.add(new Disponibilita(
						resultSet.getLong("id"),
						resultSet.getDate("data").toLocalDate(),
						resultSet.getTime("ora_inizio").toLocalTime(),
						resultSet.getTime("ora_fine").toLocalTime(),
						resultSet.getLong("id_utente"),
						TipoDisponibilita.values()[resultSet.getInt("tipo")],
						DayOfWeek.values()[resultSet.getInt("giorno_settimana")]
				));
			}
			return disponibilita;
		}catch (SQLException e){
			e.printStackTrace();
			throw new SQLException("Errore di connessione al database.");
		}
	}

	@Override
	public void inserisciDisponibilita(Disponibilita disponibilita) throws Exception {
		try (Connection connection = dataSource.getConnection()) {

			PreparedStatement statement = connection.prepareStatement("INSERT INTO disponibilita" +
					"(data, ora_inizio, ora_fine, id_utente, tipo, giorno_settimana) VALUES (?, ?, ?, ?, ?, ?)");

			if(disponibilita.getTipo().equals(TipoDisponibilita.RICORSIVO)) {
				statement.setNull(1, Types.DATE);
				statement.setInt(6, disponibilita.getGiorno_settimana().ordinal());
			}else{
				statement.setDate(1, Date.valueOf(disponibilita.getData()));
				statement.setNull(6, Types.INTEGER);
			}

			statement.setTime(2, Time.valueOf(disponibilita.getOra_inizio()));
			statement.setTime(3, Time.valueOf(disponibilita.getOra_fine()));
			statement.setLong(4, disponibilita.getId_utente());
			statement.setInt(5, disponibilita.getTipo().ordinal());
			statement.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new SQLException("Errore di connessione al database.");
		}
	}
	
	public List<Disponibilita> findDisponibilitaByEmailProfessionista(String email) throws Exception {
	    List<Disponibilita> lista = new ArrayList<>();

	    String query = "SELECT * FROM disponibilita WHERE id_utente = (SELECT id FROM utente WHERE email = ?)";

	    try (Connection conn = dataSource.getConnection(); // O il tuo metodo per la connessione
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        
	        ps.setString(1, email);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Disponibilita d = new Disponibilita();
	                d.setId(rs.getLong("id"));
					if(rs.getDate("data")!=null) {
						d.setData(rs.getDate("data").toLocalDate());
					}else d.setData(null);
	                d.setOra_inizio(rs.getTime("ora_inizio").toLocalTime());
	                d.setOra_fine(rs.getTime("ora_fine").toLocalTime());
	              	d.setId_utente(rs.getLong("id_utente"));
					d.setTipo(TipoDisponibilita.values()[rs.getInt("tipo")]);
					d.setGiorno_settimana(DayOfWeek.values()[rs.getInt("giorno_settimana")]);
	                
	                lista.add(d);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Errore durante il recupero delle disponibilità per email: " + email);
	    }
	    
	    return lista;
	}

	public List<Disponibilita> findDisponibilitaByEmailProfessionistaAndTipo(String email, TipoDisponibilita tipo) throws Exception {
		List<Disponibilita> lista = new ArrayList<>();

		String query = "SELECT * FROM disponibilita WHERE id_utente = (SELECT id FROM utente WHERE email = ?) AND tipo = ?";

		try (Connection conn = dataSource.getConnection(); // O il tuo metodo per la connessione
			 PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, email);
			ps.setInt(2, tipo.ordinal());

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Disponibilita d = new Disponibilita();
					d.setId(rs.getLong("id"));
					if(rs.getDate("data")!=null) {
						d.setData(rs.getDate("data").toLocalDate());
					}else d.setData(null);
					d.setOra_inizio(rs.getTime("ora_inizio").toLocalTime());
					d.setOra_fine(rs.getTime("ora_fine").toLocalTime());
					d.setId_utente(rs.getLong("id_utente"));
					d.setTipo(TipoDisponibilita.values()[rs.getInt("tipo")]);
					d.setGiorno_settimana(DayOfWeek.values()[rs.getInt("giorno_settimana")]);

					lista.add(d);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Errore durante il recupero delle disponibilità per email: " + email);
		}

		return lista;
	}

	@Override
	public void deleteDisponiblitaById(Long idDisponibilita) throws Exception {
		try (Connection connection = dataSource.getConnection()) {

			PreparedStatement statement = connection.prepareStatement("DELETE FROM disponibilita WHERE id = ?");
			statement.setLong(1, idDisponibilita);
			statement.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new SQLException("Errore di connessione al database.");
		}
	}

	@Override
	public void removeDisponibilitaByDataOraEmail(LocalDate data, LocalTime ora_inizio, String email) throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM disponibilita WHERE data =? AND ora_inizio =? AND id_utente = (SELECT id FROM utente WHERE email = ?) ");
			statement.setDate(1, Date.valueOf(data));
			statement.setTime(2, Time.valueOf(ora_inizio));
			statement.setString(3, email);
			statement.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new SQLException("Errore di connessione al database.");
		}
	}

	@Override
	public boolean checkRicorrenzaById(Long idDisponibilita) throws Exception {
		try(Connection connection = dataSource.getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM disponibilita WHERE id = ?");
			statement.setLong(1, idDisponibilita);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
                return rs.getInt("tipo")==TipoDisponibilita.RICORSIVO.ordinal();
			}
			return false;
		}catch (SQLException e){
			e.printStackTrace();
			throw new SQLException("Errore di connessione al database.");
		}
	}


}
