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


    public Richiesta(Long id, LocalDate data, LocalTime ora_inizio, LocalTime ora_fine,
                     String indirizzo, StatoRichiesta statoRichiesta, Long id_cliente, Long id_professionista) {
        this.id = id;
        this.data = data;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.indirizzo = indirizzo;
        this.statoRichiesta = statoRichiesta;
        this.id_cliente = id_cliente;
        this.id_professionista = id_professionista;
    }

    

    public Richiesta() {
		// TODO Auto-generated constructor stub
	}



	public void nextStep(){
        if(statoRichiesta.equals(StatoRichiesta.IN_ATTESA_DI_CONFERMA)) statoRichiesta = StatoRichiesta.IN_CORSO;
        else statoRichiesta = StatoRichiesta.COMPLETA;
    }
    
    public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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
