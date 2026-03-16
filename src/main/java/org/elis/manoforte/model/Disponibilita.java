package org.elis.manoforte.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilita {
    private Long id;
	private LocalDate data;
    private LocalTime ora_inizio;
    private LocalTime ora_fine;
    private Long id_utente;

    public Disponibilita(Long id, LocalDate data, LocalTime ora_inizio, LocalTime ora_fine, Long id_utente) {
        this.id = id;
        this.data = data;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.id_utente = id_utente;
    }

    public Disponibilita() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra_inizio() {
        return ora_inizio;
    }

    public LocalTime getOra_fine() {
        return ora_fine;
    }

    public Long getId_utente() {
        return id_utente;
    }
    public void setId(Long id) {
		this.id = id;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public void setOra_inizio(LocalTime ora_inizio) {
		this.ora_inizio = ora_inizio;
	}

	public void setOra_fine(LocalTime ora_fine) {
		this.ora_fine = ora_fine;
	}

}
