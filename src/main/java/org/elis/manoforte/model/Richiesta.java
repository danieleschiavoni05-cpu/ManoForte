package org.elis.manoforte.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Richiesta {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate data;
    private LocalTime ora_inizio;
    private LocalTime ora_fine;
    private String indirizzo;
	@Column(columnDefinition = "check(statoRichiesta in (0,1,2))")
    private StatoRichiesta statoRichiesta;
	@ManyToOne
    private Utente cliente;
	@ManyToOne
    private Utente professionista;
	private String descrizione;

    public Richiesta(Long id, LocalDate data, LocalTime ora_inizio, LocalTime ora_fine,
					 String indirizzo, StatoRichiesta statoRichiesta, String descrizione, Utente cliente, Utente professionista) {
        this.id = id;
        this.data = data;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.indirizzo = indirizzo;
		this.descrizione = descrizione;
        this.statoRichiesta = statoRichiesta;
        this.cliente = cliente;
        this.professionista = professionista;
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

	public Utente getCliente() {
		return cliente;
	}

	public void setCliente(Utente cliente) {
		this.cliente = cliente;
	}

	public Utente getProfessionista() {
		return professionista;
	}

	public void setProfessionista(Utente professionista) {
		this.professionista = professionista;
	}
}
