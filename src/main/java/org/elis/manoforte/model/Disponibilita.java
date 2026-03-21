package org.elis.manoforte.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilita {
    private Long id;
	private LocalDate data;
    private LocalTime ora_inizio;
    private LocalTime ora_fine;
    private TipoDisponibilita tipo;
    private DayOfWeek giorno_settimana;
    private Long id_utente;

    public Disponibilita(Long id, LocalDate data, LocalTime ora_inizio, LocalTime ora_fine, Long id_utente,  TipoDisponibilita tipo, DayOfWeek giorno_settimana) {
        this.id = id;
        this.data = data;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.id_utente = id_utente;
        this.tipo = tipo;
        this.giorno_settimana = giorno_settimana;
    }

    public Disponibilita() {

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

    public TipoDisponibilita getTipo() {
        return tipo;
    }

    public DayOfWeek getGiorno_settimana() {
        return giorno_settimana;
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

    public void setId_utente(Long id_utente) {
        this.id_utente = id_utente;
    }

    public void setTipo(TipoDisponibilita tipo) {
        this.tipo = tipo;
    }

    public void setGiorno_settimana(DayOfWeek giorno_settimana) {
        this.giorno_settimana = giorno_settimana;
    }

}
