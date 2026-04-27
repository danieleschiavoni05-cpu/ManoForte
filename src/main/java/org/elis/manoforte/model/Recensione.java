package org.elis.manoforte.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Check(constraints = "cliente_id!=professionista_id")
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizione;
    @Column(columnDefinition = "float unsigned check(voto>0 AND voto<6)", nullable = false)
    private BigDecimal voto;
    @Column(nullable = false)
    private LocalDate data;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente cliente;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente professionista;
    @OneToOne // Una recensione per ogni richiesta
    @JoinColumn(name = "richiesta_id", nullable = true, unique = true) 
    private Richiesta richiesta;

    public Recensione(Long id, String descrizione, BigDecimal voto, LocalDate data, Utente cliente, Utente professionista,Richiesta richiesta) {
        this.id = id;
        this.descrizione = descrizione;
        this.voto = voto;
        this.data = data;
        this.cliente = cliente;
        this.professionista = professionista;
        this.richiesta=richiesta;
    }

    public Recensione(Long id, String descrizione, BigDecimal voto, LocalDate data) {
        this.id = id;
        this.descrizione = descrizione;
        this.voto = voto;
        this.data = data;
        this.cliente = null;
        this.professionista = null;
    }

    public Recensione() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public BigDecimal getVoto() {
        return voto;
    }

    public void setVoto(BigDecimal voto){
        this.voto = voto;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Utente getCliente() {
        return cliente;
    }

    public void setCliente(Utente cliente){
        this.cliente = cliente;
    }

    public Utente getProfessionista() {
        return professionista;
    }

    public void setProfessionista(Utente professionista){
        this.professionista = professionista;
    }
    public Richiesta getRichiesta() { return richiesta; }
    public void setRichiesta(Richiesta richiesta) { this.richiesta = richiesta; }

	@Override
	public String toString() {
		return "Recensione [id=" + id + ", descrizione=" + descrizione + ", voto=" + voto + ", data=" + data
				+ ", cliente=" + cliente + ", professionista=" + professionista + "]";
	}
    
}
