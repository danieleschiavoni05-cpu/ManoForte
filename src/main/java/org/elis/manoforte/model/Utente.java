package org.elis.manoforte.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Utente {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	@Column(nullable = false, length = 16)
	private String password;
	private String codice_fiscale;
	@Column(precision = 5, scale = 2)
	private BigDecimal tariffa;
	@Column(nullable = false)
	private Ruolo ruolo;

	@ManyToOne
	private Citta citta;
	@OneToMany(mappedBy = "utente")
	private List<Disponibilita> disponibilita;
	@ManyToMany
	private List<Professione> professione;
	@ManyToMany
	private List<Veicolo> veicolo;

	@OneToMany(mappedBy = "cliente")
	private List<Richiesta> richiesteEffettuate;
	@OneToMany(mappedBy = "professionista")
	private List<Richiesta> richiesteRicevute;
	@OneToMany(mappedBy = "cliente")
	private List<Recensione> recensioneInviate;
	@OneToMany(mappedBy = "professionista")
	private List<Recensione> recensioneRicevute;
	@OneToMany(mappedBy = "utente")
	private List<Immagine> immagine;



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
		this.citta = null;
		this.professione = null;
		this.veicolo = null;
	}

	// costruttore Cliente
	public Utente(String email, String password, String nome,
				  String cognome, LocalDate dataNascita, String codice_fiscale) {
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.codice_fiscale = codice_fiscale;
		this.ruolo = Ruolo.UTENTE_BASE;
		this.citta = null;
		this.professione = null;
		this.tariffa = null;
		this.veicolo = null;
	}

	// costruttore Cliente - Card Richiesta
	public Utente(String nome, String cognome, String email, Citta citta){
		this.email = email;
		this.password = null;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = null;
		this.codice_fiscale = null;
		this.ruolo = Ruolo.UTENTE_BASE;
		this.citta = citta;
		this.professione = null;
		this.tariffa = null;
		this.veicolo = null;
	}

	// costruttore Cliente - Card Recensione
	public Utente(String nome, String cognome, String email){
		this.email = email;
		this.password = null;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = null;
		this.codice_fiscale = null;
		this.ruolo = Ruolo.UTENTE_BASE;
		this.citta = null;
		this.professione = null;
		this.tariffa = null;
		this.veicolo = null;
	}

	//costruttore Professionista
	public Utente(String email, String password, String nome,
				  String cognome, LocalDate dataNascita, String codice_fiscale,
				  BigDecimal tariffa) {
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.codice_fiscale = codice_fiscale;
		this.ruolo = Ruolo.PROFESSIONISTA;
		this.citta = null;
		this.professione = null;
		this.tariffa = tariffa;
		this.veicolo = null;
	}

	public Utente(String email, String password, String nome,
	              String cognome, LocalDate dataNascita, String codice_fiscale,
	              Citta citta) {
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.codice_fiscale = codice_fiscale;
		this.ruolo = Ruolo.UTENTE_BASE;
		this.citta = citta;
		this.professione = null;
		this.tariffa = null;
		this.veicolo = null;
	}


	public Utente() {
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
	
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCodiceFiscale() {
		return codice_fiscale;
	}

	public BigDecimal getTariffa() {
		return tariffa;
	}

	public Citta getIdCitta() {
		return citta;
	}

	public List<Professione> getProfessione() {
		return professione;
	}

	public void setProfessione(List<Professione> professione) {
		this.professione = professione;
	}

	public List<Veicolo> getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(List<Veicolo> veicolo) {
		this.veicolo = veicolo;
	}

	public void setId_citta(Citta citta) {
		this.citta = citta;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome=cognome;
	}

	public void setCodiceFiscale(String codice_fiscale) {
		this.codice_fiscale=codice_fiscale;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public void setTariffa(BigDecimal tariffa) {
		this.tariffa = tariffa;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public Citta getCitta() {
		return citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
	}

	public List<Disponibilita> getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(List<Disponibilita> disponibilita) {
		this.disponibilita = disponibilita;
	}

	public List<Immagine> getImmagine() {
		return immagine;
	}

	public void setImmagine(List<Immagine> immagine) {
		this.immagine = immagine;
	}


	public List<Richiesta> getRichiesteEffettuate() {
		return richiesteEffettuate;
	}

	public void setRichiesteEffettuate(List<Richiesta> richiesteEffettuate) {
		this.richiesteEffettuate = richiesteEffettuate;
	}

	public List<Richiesta> getRichiesteRicevute() {
		return richiesteRicevute;
	}

	public void setRichiesteRicevute(List<Richiesta> richiesteRicevute) {
		this.richiesteRicevute = richiesteRicevute;
	}

	public List<Recensione> getRecensioneInviate() {
		return recensioneInviate;
	}

	public void setRecensioneInviate(List<Recensione> recensioneInviate) {
		this.recensioneInviate = recensioneInviate;
	}

	public List<Recensione> getRecensioneRicevute() {
		return recensioneRicevute;
	}

	public void setRecensioneRicevute(List<Recensione> recensioneRicevute) {
		this.recensioneRicevute = recensioneRicevute;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof Utente user)
			return email.equals(user.email) && codice_fiscale.equals(user.codice_fiscale);
		else return false;
	}
}
