package org.elis.manoforte.utility;

public class SqlQuery {
    public static final String elencoRichiesteByIdProfessionistaAndStato = """
				SELECT DISTINCT r.id as "id_richiesta", r.data as "data_richiesta", r.stato, r.ora_inizio, r.ora_fine, 
				u.nome, u.cognome, u.email, u.id_citta FROM richiesta r
				    JOIN utente u ON r.id_cliente=u.id WHERE id_professionista=(
				        SELECT p.id FROM utente p WHERE p.email=?
				    ) AND stato=?
			""";
    public static final String elencoRecensioniByEmailProfessionistaLimit = """
				SELECT DISTINCT r.id as "id_recensione", r.data as "data_recensione", u.nome, u.cognome, u.email FROM richiesta r
					JOIN utente u ON r.id_cliente=u.id WHERE id_professionista IN (
						SELECT p.id FROM utente p WHERE p.email=?)
			""";
}

