package Entity.EntryDB;

import Entity.StateMachine.*;
import Entity.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;


/**
 * Classe utilizzata per memorizzare nel database gli aggiornamenti di stato di una segnalazione, in modo da poterne
 * ricostruire l'andamento temporale
 */

@Entity
@Table(name = "AggiornamentoStato")
public class AggiornamentoStatoEntry {

    //Attributi

    /**
     * Primary Key della entity
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAggiornamento;

    private LocalDateTime data;

    /**
     * Riferimento alla segnalazione corrispondente, mappato nel database come Foreign Key
     */

    @ManyToOne
    @JoinColumn(name = "idSegnalazione")
    private Segnalazione segnalazione;

    /**
     * Nuovo stato della segnalazione
     */

    @Convert(converter = ConverterStato.class)
    private StatoSegnalazione stato;

    /**
     * Riferimento all'operatore che ha aggiornato lo stato, mappato nel database come Foreign Key
     */

    @ManyToOne
    @JoinColumn(name = "idOperatore")
    private Operatore operatore;


    //Costruttori
    public AggiornamentoStatoEntry() {
    }

    public AggiornamentoStatoEntry(Segnalazione segnalazione, StatoSegnalazione stato, Operatore operatore) {
        this.data = LocalDateTime.now();
        this.segnalazione = segnalazione;
        this.stato = stato;
        this.operatore = operatore;
    }

    //Getter e Setter
    public Long getIdAggiornamento() {
        return idAggiornamento;
    }
    public void setIdAggiornamento(Long idAggiornamento) {
        this.idAggiornamento = idAggiornamento;
    }

    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Segnalazione getSegnalazione() {
        return segnalazione;
    }
    public void setSegnalazione(Segnalazione segnalazione) {
        this.segnalazione = segnalazione;
    }

    public StatoSegnalazione getStato() {
        return stato;
    }
    public void setStato(StatoSegnalazione stato) {
        this.stato = stato;
    }

    public Operatore getOperatore() {
        return operatore;
    }
    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }

    @Override
    public String toString() {
        return "AggiornamentoStato {\n" +
                "*\tidAggiornamento=" + this.idAggiornamento + ",\n" +
                "*\tdata=" + this.data.toString() + ",\n" +
                "*\tstato=" + stato.getStatoToString() + ",\n" +
                "*\tidSegnalazione=" + segnalazione.getIdSegnalazione() + ",\n" +
                "*\tidOperatore=" + operatore.getIdOperatore() + ",\n" +
                '}';
    }
}
