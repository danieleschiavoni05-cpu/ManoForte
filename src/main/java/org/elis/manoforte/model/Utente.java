package org.elis.manoforte.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Utente {
	private String email;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private String password;
	private String codice_fiscale;
	private BigDecimal tariffa;
	private Ruolo ruolo;
	private Long id_citta;
	private List<Long> professioni;
	private List<Long> veicoli;

	// costruttore Admin
	public Utente(String email, String password){
		this.email = email;
		this.password = password;
		this.nome = "ADMIN";
		this.cognome = null;
		this.dataNascita = null;
		this.codice_fiscale = null;
		this.tariffa = null;
		this.ruolo = Ruolo.ADMIN;
		this.id_citta = null;
		this.professioni = null;
		this.veicoli = null;
	}

	// costruttore Cliente
	public Utente(String email, String password, String nome,
				  String cognome, LocalDate dataNascita, String codice_fiscale, Long id_citta) {
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.codice_fiscale = codice_fiscale;
		this.ruolo = Ruolo.UTENTE_BASE;
		this.id_citta = id_citta;
		this.professioni = null;
		this.tariffa = null;
		this.veicoli = null;
	}

	//costruttore Professionista
	public Utente(String email, String password, String nome,
				  String cognome, LocalDate dataNascita, String codice_fiscale, Long id_citta,
				  List<Long> professioni, BigDecimal tariffa, List<Long> veicoli) {
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.codice_fiscale = codice_fiscale;
		this.ruolo = Ruolo.PROFESSIONISTA;
		this.id_citta = id_citta;
		this.professioni = professioni;
		this.tariffa = tariffa;
		this.veicoli = veicoli;
	}


	public Ruolo getRuolo() {
		return ruolo;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getPassword() {
		return password;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public String getCodiceFiscale() {
		return codice_fiscale;
	}

	public BigDecimal getTariffa() {
		return tariffa;
	}

	public long getIdCitta() {
		return id_citta;
	}

	public List<Long> getProfessioni() {
		return professioni;
	}

	public void setProfessioni(List<Long> professioni) {
		this.professioni = professioni;
	}

	public List<Long> getVeicoli() {
		return veicoli;
	}

	public void setVeicoli(List<Long> veicoli) {
		this.veicoli = veicoli;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof Utente user)
			return email.equals(user.email) && codice_fiscale.equals(user.codice_fiscale);
		else return false;
	}

	
	public void setId_citta(Long id_citta) {
		this.id_citta = id_citta;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

}
