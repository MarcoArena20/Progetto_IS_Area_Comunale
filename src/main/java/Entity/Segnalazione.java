package Entity;

import Entity.Enum.Categoria;
import Entity.Observer.ObserverSegnalazione;
import Entity.StateMachine.ConverterStato;
import Entity.StateMachine.StatoInviata;
import Entity.StateMachine.StatoSegnalazione;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity che estende il comportamento del subject del pattern Observer ed
 * implementa gli attributi della segnalazione
 */
@Entity
public class Segnalazione extends ObserverSegnalazione { //La segnalazione è il subject che notifica i cambiamenti di stato(push model)

    /**
     * Id univoco geenerato dal database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSegnalazione;

    /**
     * Riferimento al cittadino come Foreign Key di segnalazione
     */
    @ManyToOne
    @JoinColumn(name = "idCittadino")
    private Cittadino cittadino;

    /**
     * titolo della segnalazione
     */
    private String titolo;

    /**
     * descrizione della segnalazione
     */
    private String descrizione;

    /**
     * Categoria enumerativa della segnalazione
     */
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    /**
     * posizione della Segnalazione
     */
    private String posizione;

    /**
     * Stato della segnalazione convertito in StatoType
     */
    @Convert(converter = ConverterStato.class)
    private StatoSegnalazione stato;

    /**
     * Data (opzionale) della segnalazione
     */
    private LocalDateTime data;

    /**
     * Url dell'immagine (opzionale) della seganalazione
     */
    private String urlImmagine;

    /**
     * Costruttore di default per il database
     */
    public Segnalazione(){}

    /**
     * Costruttore avente i parametri obbligatori di una segnalazione
     * @param cittadino cittadino che ha creato la segnalazione
     * @param titolo titolo della segnalazione
     * @param descrizione descrizione della segnalazione
     * @param categoria categoria della segnalazione
     * @param posizione posizione della segnalazione
     */
    public Segnalazione(Cittadino cittadino, String titolo, String descrizione, Categoria categoria, String posizione){

        this.cittadino = cittadino;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.posizione = posizione;
        this.stato = new StatoInviata();

    }

    // Getter e Setter
    public Long getIdSegnalazione() {
        return idSegnalazione;
    }
    public void setIdSegnalazione(Long idSegnalazione) { this.idSegnalazione = idSegnalazione;}
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public String getPosizione() { return posizione; }
    public void setPosizione(String posizione) { this.posizione = posizione; }
    public Long getIdCittadino() { return (cittadino != null) ? cittadino.getIdCittadino(): null; }
    public StatoSegnalazione getStato() { return stato; }
    public void setStato(StatoSegnalazione stato) {this.stato = stato;}
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public String getUrlImmagine() { return urlImmagine; }
    public void setUrlImmagine(String urlImmagine) { this.urlImmagine = urlImmagine; }

    /**
     * Metodo per la gestione dell'aggiornamento dello stato della segnalazione
     * @param avanzamento avanzamento all'interno dello state diagram
     * @return true se l'aggiornamento è andato a buon fine, false altrimenti
     */
    public boolean aggiornaStato(boolean avanzamento) {

       // 1. aggiorna stato
        boolean esito = this.stato.aggiornaStato(this, avanzamento);

        if (!esito) {
            System.err.println("[Segnalazione "+this.idSegnalazione+"] Errore nell'aggiornamento dello stato!");
        }

        if (esito) {
            //2. notifico osservatore
            esito = notifyObserver(this, this.stato);

            if (!esito) {
                System.err.println("[Segnalazione " + this.idSegnalazione + "] Osservatori assenti!");
            }
        }

        System.out.println("[Segnalazione "+this.idSegnalazione+"] Avanzamento stato: "+avanzamento+"..\n" +
                this
        );

        return esito;
    }

    /**
     * Record Java di supporto utilizzato per accorpare in un unico oggetto immutabile
     * i dati essenziali di riepilogo di una segnalazione
     */
    public record InfoAnteprima(Categoria categoria, LocalDateTime data, String posizione, StatoSegnalazione stato, Long idSegnalazione) {}

    /**
     * Estrae e restituisce l'anteprima sintetica dei dati informativi della segnalazione corrente.
     *
     * @return un'istanza di InfoAnteprima
     */
    public InfoAnteprima getInfoAnteprima(){

        return new InfoAnteprima(this.categoria, this.data, this.posizione, this.stato, this.idSegnalazione);
    }

    /**
     * Record Java di supporto utilizzato per accorpare in un unico oggetto immutabile
     * i dettagli della segnalazione
     */
    public record Dettaglio(InfoAnteprima anteprima, String titolo, String descrizone, String urlImmagine) {}

    /**
     * Estrae e restituisce il dettaglio completo associato alla segnalazione.
     * Se l'immagine allegata non è presente o è vuota, valorizza il relativo campo con una stringa di default.
     *
     * @return un'istanza di Dettaglio
     */
    public Dettaglio getDettaglioSegnalazione(){
        String url;
        if(verificaPresenzaImmagine()){
            url = getUrlImmagine();
        }
        else{
            url = "Immagine non presente";
        }

        return new Dettaglio(getInfoAnteprima(), getTitolo(), getDescrizione(), url);
    }

    /**
     * Verifica la presenza effettiva di un percorso valido per l'immagine allegata.
     *
     * @return true se l'url è valorizzato e non vuoto, false altrimenti
     */
    private boolean verificaPresenzaImmagine(){
        if (this.urlImmagine == null) {
            return false;
        }
        return !(this.urlImmagine.isEmpty());
    }

    @Override
    public String toString() {
        return "Segnalazione {\n" +
                "*\tidSegnalazione=" + idSegnalazione + ",\n" +
                //"*\ttitolo=" + titolo + ",\n" +
                //"*\tdescrizione=" + descrizione + ",\n" +
                //"*\tcategoria=" + categoria + ",\n" +
                //"*\tposizione=" + posizione + ",\n" +
                "*\tstato=" + stato.getStatoToString() + ",\n" +
                //"*\tdata=" + data.toString() + ",\n" +
                //"*\turlImmagine=" + urlImmagine + ",\n" +
                '}';
    }

}