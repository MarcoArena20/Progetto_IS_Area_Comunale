package Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AggiornamentoStatoEntry {

    //Attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAggiornamento;

    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "idSegnalazione")
    private Segnalazione segnalazione;

    @Convert(converter = ConverterStato.class)
    private StatoSegnalazione stato;

    @ManyToOne
    @JoinColumn(name = "idOperatore")
    private Operatore operatore;

    @ManyToOne
    @JoinColumn(name = "idNotaInterna")
    private NotaInternaEntry notaInternaEntry;


    //Costruttori
    public AggiornamentoStatoEntry() {
    }

    public AggiornamentoStatoEntry(Long idAggiornamento, LocalDateTime data, Segnalazione segnalazione, StatoSegnalazione stato, Operatore operatore, NotaInternaEntry notaInternaEntry) {
        this.idAggiornamento = idAggiornamento;
        this.data = data;
        this.segnalazione = segnalazione;
        this.stato = stato;
        this.operatore = operatore;
        this.notaInternaEntry = notaInternaEntry;
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

    public NotaInternaEntry getNotaInternaEntry() {
        return notaInternaEntry;
    }
    public void setNotaInternaEntry(NotaInternaEntry notaInternaEntry) {
        this.notaInternaEntry = notaInternaEntry;
    }

}
