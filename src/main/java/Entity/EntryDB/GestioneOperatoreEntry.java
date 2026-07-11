package Entity.EntryDB;

import jakarta.persistence.*;
import Entity.*;

@Entity
@Table(name = "GestioneOperatore")
public class GestioneOperatoreEntry {

    //Attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGestione;

    @ManyToOne
    @JoinColumn(name = "idOperatore")
    private Operatore operatore;

    @ManyToOne
    @JoinColumn(name = "idSegnalazione")
    private Segnalazione segnalazione;

    private boolean attiva;
    private String titolo;
    private String descrizione;

    //Costruttore
    public GestioneOperatoreEntry() {

    }

    public GestioneOperatoreEntry(Operatore operatore, Segnalazione segnalazione, boolean attiva, String titolo, String descrizione) {
        this.idGestione = idGestione;
        this.operatore = operatore;
        this.segnalazione = segnalazione;
        this.attiva = attiva;
        this.titolo = titolo;
        this.descrizione = descrizione;
    }

    //Getter e Setter
    public Long getIdGestione() {
        return idGestione;
    }

    public void setIdGestione(Long idGestione) {
        this.idGestione = idGestione;
    }

    public Operatore getOperatore() {
        return operatore;
    }

    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }

    public Segnalazione getSegnalazione() {
        return segnalazione;
    }

    public void setSegnalazione(Segnalazione segnalazione) {
        this.segnalazione = segnalazione;
    }

    public boolean isAttiva() {
        return attiva;
    }

    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}