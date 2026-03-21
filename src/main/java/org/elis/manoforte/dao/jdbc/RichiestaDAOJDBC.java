package org.elis.manoforte.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.model.CardRichiesta;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.StatoRichiesta;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.SqlQuery;

public class RichiestaDAOJDBC implements RichiestaDAO{
	DataSource dataSource;

	public RichiestaDAOJDBC(DataSource dataSource) {this.dataSource = dataSource;}

	@Override
	public List<Richiesta> findRichiestaByIdCliente(long id) throws Exception {
		List<Richiesta> richieste = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM richiesta WHERE id_cliente=?")) {

			ps.setLong(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					richieste.add(mapRowToRichiesta(rs));
				}
			}
		}

		return richieste;
	}

	@Override
	public List<Richiesta> findRichiestaByIdProfessionista(long id) throws Exception {
		List<Richiesta> richieste = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM richiesta WHERE id_professionista=?")) {

			ps.setLong(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					richieste.add(mapRowToRichiesta(rs));
				}
			}
		}

		return richieste;
	}

	@Override
	public void inserisciRichiesta(Richiesta richiesta) throws Exception {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement("INSERT INTO richiesta (data, ora_inizio, ora_fine, indirizzo, stato, id_cliente, id_professionista, descrizione) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

			ps.setDate(1, Date.valueOf(richiesta.getData()));
			ps.setTime(2, Time.valueOf(richiesta.getOra_inizio()));
			ps.setTime(3, Time.valueOf(richiesta.getOra_fine()));
			ps.setString(4, richiesta.getIndirizzo());
			ps.setInt(5, 0);
			ps.setLong(6, richiesta.getId_cliente());
			ps.setLong(7, richiesta.getId_professionista());
			ps.setString(8, richiesta.getDescrizione());

			ps.executeUpdate();
		}		// TODO Auto-generated method stub

	}

	@Override
	public void updateRichiesta(Richiesta richiesta) throws Exception {
		String sql = "UPDATE richiesta SET data=?, ora_inizio=?, ora_fine=?, indirizzo=?, stato=?, id_cliente=?, id_professionista=? WHERE id=?";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setDate(1, java.sql.Date.valueOf(richiesta.getData()));
			ps.setTime(2, java.sql.Time.valueOf(richiesta.getOra_inizio()));
			ps.setTime(3, java.sql.Time.valueOf(richiesta.getOra_fine()));
			ps.setString(4, richiesta.getIndirizzo());
			ps.setString(5, StatoRichiesta.IN_ATTESA_DI_CONFERMA.name()); 
			ps.setLong(6, richiesta.getId_cliente());
			ps.setLong(7, richiesta.getId_professionista());
			ps.setLong(8, richiesta.getId());

			ps.executeUpdate();
		}
	}

	@Override
	public Richiesta getRichiestaById(long id) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM richiesta WHERE id=?")) {

			ps.setLong(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToRichiesta(rs);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Richiesta mapRowToRichiesta(ResultSet rs) throws SQLException {
	    Richiesta r = new Richiesta();
	    
	    r.setId(rs.getLong("id"));
	    r.setData(rs.getDate("data").toLocalDate());
	    r.setOra_inizio(rs.getTime("ora_inizio").toLocalTime());
	    r.setOra_fine(rs.getTime("ora_fine").toLocalTime());
	    r.setIndirizzo(rs.getString("indirizzo"));
	    r.setDescrizione(rs.getString("descrizione")); // Non dimenticare la descrizione!
	    
	    // Recupero l'intero dal DB e lo trasformo in Enum tramite l'indice (ordinal)
	    int statoInt = rs.getInt("stato");
	    r.setStatoRichiesta(StatoRichiesta.values()[statoInt]);
	    
	    r.setId_cliente(rs.getLong("id_cliente"));

	    long idProf = rs.getLong("id_professionista");
	    if (!rs.wasNull()) {
	        r.setId_professionista(idProf);
	    } else {
	        r.setId_professionista(null); 
	    }
	    
	    return r;
	}

	@Override
	public void updateStatoRichiesta(long id, StatoRichiesta stato) throws Exception{
		String sql = "UPDATE richiesta SET stato=? WHERE id=?";

		try(Connection conn = dataSource.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, stato.ordinal());
			ps.setLong(2, id);
			ps.executeUpdate();
		}

	}

	@Override
	public List<CardRichiesta> getRichiesteByEmailProfessionistaAndStato(String email, StatoRichiesta stato) throws SQLException {
		List<CardRichiesta> richieste = new ArrayList<>();
		try (Connection conn = dataSource.getConnection()){
			PreparedStatement statement = conn.prepareStatement(SqlQuery.elencoRichiesteByIdProfessionistaAndStato);
			statement.setString(1, email);
			statement.setInt(2, stato.ordinal());
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				CardRichiesta card = new CardRichiesta();
				card.setId(rs.getLong("id_richiesta"));
				card.setData(rs.getDate("data_richiesta").toLocalDate());
				card.setStatoRichiesta(StatoRichiesta.values()[rs.getInt("stato")]);
				card.setCliente(
						new Utente(rs.getString("nome"),
								rs.getString("cognome"),
								rs.getString("email"),
								rs.getLong("id_citta")));
				richieste.add(card);
			}
		}
		return richieste;
	}

	@Override
	public List<Richiesta> getRichiesteByEmailProfessionistaAndTipo(String email, StatoRichiesta statoRichiesta) throws SQLException {
		List<Richiesta> richieste = new ArrayList<>();
		try (Connection conn = dataSource.getConnection()){
			PreparedStatement statement = conn.prepareStatement(SqlQuery.elencoRichiesteByIdProfessionistaAndStato);
			statement.setString(1, email);
			statement.setInt(2, statoRichiesta.ordinal());
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Richiesta richiesta = new Richiesta();
				richiesta.setId(rs.getLong("id_richiesta"));
				richiesta.setData(rs.getDate("data_richiesta").toLocalDate());
				richiesta.setStatoRichiesta(StatoRichiesta.values()[rs.getInt("stato")]);
				richiesta.setOra_inizio(LocalTime.parse(rs.getString("ora_inizio")));
				richiesta.setOra_fine(LocalTime.parse(rs.getString("ora_fine")));
				richieste.add(richiesta);
			}
		}
		return richieste;
	}


}
