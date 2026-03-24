package org.elis.manoforte.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Check(constraints = "((tipo = 0) and (data is not null) and (giorno_settimana is null)) or" +
                     "((tipo = 1) and (data is null) and (giorno_settimana is not null)) or " +
                     "((tipo = 2) and (data is not null))")

public class Disponibilita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private LocalDate data;
    @Column(nullable=false)
    private LocalTime ora_inizio;
    @Column(nullable=false)
    private LocalTime ora_fine;
    @Column(columnDefinition = "tinyint not null unsigned check(tipo in (0,1,2) check((tip")
    private TipoDisponibilita tipo;
    @Column(nullable=false)
    private DayOfWeek giorno_settimana;
    @ManyToOne
    private Utente utente;

    public Disponibilita(Long id, LocalDate data, LocalTime ora_inizio, LocalTime ora_fine, Utente utente,  TipoDisponibilita tipo, DayOfWeek giorno_settimana) {
        this.id = id;
        this.data = data;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.utente = utente;
        this.tipo = tipo;
        this.giorno_settimana = giorno_settimana;
    }

    public Disponibilita() { }

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

    public Utente getId_utente() {
        return utente;
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

    public void setId_utente(Utente utente) {
        this.utente = utente;
    }

    public void setTipo(TipoDisponibilita tipo) {
        this.tipo = tipo;
    }

    public void setGiorno_settimana(DayOfWeek giorno_settimana) {
        this.giorno_settimana = giorno_settimana;
    }

}
