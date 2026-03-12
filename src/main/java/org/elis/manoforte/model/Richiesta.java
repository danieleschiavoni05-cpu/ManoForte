package org.elis.manoforte.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Richiesta {
    private Long id;
	private LocalDate data;
    private LocalTime ora_inizio;
    private LocalTime ora_fine;
    private String indirizzo;
    private StatoRichiesta statoRichiesta;
    private Long id_cliente;
    private Long id_professionista;
	private String descrizione;

    public Richiesta(Long id, LocalDate data, LocalTime ora_inizio, LocalTime ora_fine,
                     String indirizzo, StatoRichiesta statoRichiesta, String descrizione, Long id_cliente, Long id_professionista) {
        this.id = id;
        this.data = data;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.indirizzo = indirizzo;
		this.descrizione = descrizione;
        this.statoRichiesta = statoRichiesta;
        this.id_cliente = id_cliente;
        this.id_professionista = id_professionista;
    }

    public Richiesta() {
	}

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getOra_inizio() {
		return ora_inizio;
	}

	public void setOra_inizio(LocalTime ora_inizio) {
		this.ora_inizio = ora_inizio;
	}

	public LocalTime getOra_fine() {
		return ora_fine;
	}

	public void setOra_fine(LocalTime ora_fine) {
		this.ora_fine = ora_fine;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public StatoRichiesta getStatoRichiesta() {
		return statoRichiesta;
	}

	public void setStatoRichiesta(StatoRichiesta statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}

	public Long getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Long id_cliente) {
		this.id_cliente = id_cliente;
	}

	public Long getId_professionista() {
		return id_professionista;
	}

	public void setId_professionista(Long id_professionista) {
		this.id_professionista = id_professionista;
	}
}
