package Entity.EntryDB;

import jakarta.persistence.*;
import Entity.*;

/**
 * Classe utilizzata per memorizzare nel database le gestioni delle segnalazioni effettuate da parte degli operatori,
 * in modo da poter riconoscere gli operatori attualmente in gestione e le scorse gestioni con eventuale nota associata
 */

@Entity
@Table(name = "GestioneOperatore")
public class GestioneOperatoreEntry {

    //Attributi

    /**
     * Primary Key della entity
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGestione;

    /**
     * Riferimento all'operatore, mappato nel database come Foreign Key
     */

    @ManyToOne
    @JoinColumn(name = "idOperatore")
    private Operatore operatore;

    /**
     * Riferimento alla segnalazione, mappato nel database come Foreign Key
     */

    @ManyToOne
    @JoinColumn(name = "idSegnalazione")
    private Segnalazione segnalazione;

    /**
     * Flag booleano che indica se la gestione è attiva o si è conclusa; utilizzato per effettuare controlli di permessi
     * negli aggiornamenti di stato delle segnalazioni
     */

    private boolean attiva;

    /**
     * Titolo dell'eventuale nota aggiunta a conclusione della gestione; ha valore null nel caso in cui la gestione è
     * ancora attiva o si è conclusa senza aggiungere alcuna nota
     */

    private String titolo;

    /**
     * Descrizione dell'eventuale nota aggiunta a conclusione della gestione; ha valore null nel caso in cui la gestione è
     * ancora attiva o si è conclusa senza aggiungere alcuna nota
     */

    private String descrizione;

    //Costruttore
    public GestioneOperatoreEntry() {

    }

    public GestioneOperatoreEntry(Operatore operatore, Segnalazione segnalazione, boolean attiva, String titolo, String descrizione) {
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


    @Override
    public String toString() {
        return "Gestione Operatore {\n" +
                "*\tidGestione=" + this.idGestione + ",\n" +
                "*\ttitolo=" + this.titolo + ",\n" +
                "*\tdescrizione=" + this.descrizione + ",\n" +
                "*\tidSegnalazione=" + segnalazione.getIdSegnalazione() + ",\n" +
                "*\tidOperatore=" + operatore.getIdOperatore() + ",\n" +
                '}';
    }
}