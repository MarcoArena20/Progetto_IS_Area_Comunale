package Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
public class Segnalazione extends ObserverSegnalazione { //La segnalazione è il subject che notifica i cambiamenti di stato(push model)

    //Attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSegnalazione;

    @ManyToOne
    @JoinColumn(name = "idCittadino")
    private Cittadino cittadino;

    private String titolo;
    private String descrizione;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private String posizione;

    @Convert(converter = ConverterStato.class)
    private StatoSegnalazione stato;
    private LocalDateTime data;
    private String urlImmagine;

    //Costruttori
    public Segnalazione(){

    }

    public Segnalazione(Cittadino cittadino, String titolo, String descrizione, Categoria categoria, String posizione){

        this.cittadino = cittadino;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.posizione = posizione;
        this.stato = new StatoInviata();

    }

    // Getter e Setter
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

    // Metodi
    /*
    public synchronized boolean iniziaGestione(Long idOperatore) {//metodo synchronized in modo da garantire la mutua esclusione

        //1. Aggiorno lo stato
        boolean esito = this.stato.aggiornaStato(this, true);
        if (esito == false) {
            System.err.println("[Segnalazione "+this.idSegnalazione+"] Errore nell'aggiornamento dello stato!");
            return false;
        }

        //2. Notifico observer, segnalando il nuovo stato
        esito = notifyObserver(this.idSegnalazione, this.stato);
        if (esito == false) {
            System.err.println("[Segnalazione "+this.idSegnalazione+"] Osservatori assenti!");
            return false;
        }

        //TODO NOTIFICA

        System.out.println("[Segnalazione "+this.idSegnalazione+"] Iniziata la gestione..\n" +
                this.toString()
        );


        return true;
    }
    */

    public synchronized boolean aggiornaStato(boolean avanzamento) {

        //1. Distinguo i due aggiornamenti

        if (!avanzamento) {
            //aggiornamento con esito false

            //1.1 aggiorno stato con esito false
            boolean esito = this.stato.aggiornaStato(this, false);
            if (esito == false) {
                System.err.println("[Segnalazione "+this.idSegnalazione+"] Errore nell'aggiornamento dello stato!");
                return false;
            }

            //1.2 Notifico observer, segnalando il nuovo stato
            esito = notifyObserver(this, this.stato);
            if (esito == false) {
                System.err.println("[Segnalazione "+this.idSegnalazione+"] Osservatori assenti!");
                return false;
            }

            System.out.println("[Segnalazione "+this.idSegnalazione+"] Regressione stato..\n" +
                    this.toString()
            );

            return true;//Modifica andata a buon fine


        } else {
            //avanzamento positivo

            //2.1 aggiorno stato con esito true
            boolean esito = this.stato.aggiornaStato(this, true);
            if (esito == false) {
                System.err.println("[Segnalazione "+this.idSegnalazione+"] Errore nell'aggiornamento dello stato!");
                return false;
            }

            //2.2 Notifico observer, segnalando il nuovo stato
            esito = notifyObserver(this, this.stato);
            if (esito == false) {
                System.err.println("[Segnalazione "+this.idSegnalazione+"] Osservatori assenti!");
                return false;
            }

            //TODO NOTIFICA

            System.out.println("[Segnalazione "+this.idSegnalazione+"] Avanzamento stato..\n" +
                    this.toString()
            );

            return true;
        }
    }

/*
    public boolean concludiGestione(boolean gestioneRisolutiva) {

        //1. Distinguo le due terminazioni
        if (!gestioneRisolutiva) {
            //La gestione non è stata risolutiva

            //1.1 aggiorno stato con esito false
            boolean esito = this.stato.aggiornaStato(this, false);
            if (esito == false) {
                System.err.println("[Segnalazione "+this.idSegnalazione+"] Errore nell'aggiornamento dello stato!");
                return false;
            }

            //1.2 Notifico observer, segnalando il nuovo stato
            esito = notifyObserver(this.idSegnalazione, this.stato);
            if (esito == false) {
                System.err.println("[Segnalazione "+this.idSegnalazione+"] Osservatori assenti!");
                return false;
            }

            return true;//Modifica andata a buon fine


        } else {
            //La gestione è stata risolutiva

            //2.1 aggiorno stato con esito true
            boolean esito = this.stato.aggiornaStato(this, true);
            if (esito == false) {
                System.err.println("[Segnalazione "+this.idSegnalazione+"] Errore nell'aggiornamento dello stato!");
                return false;
            }

            //2.2 Notifico observer, segnalando il nuovo stato
            esito = notifyObserver(this.idSegnalazione, this.stato);
            if (esito == false) {
                System.err.println("[Segnalazione "+this.idSegnalazione+"] Osservatori assenti!");
                return false;
            }

            //TODO NOTIFICA

            System.out.println("[Segnalazione "+this.idSegnalazione+"] Conclusa gestione..\n" +
                    this.toString()
            );

            return true;
        }

    }
    */

    //record usato per tornare in modo pulito l'anteprima
    public record InfoAnteprima(Categoria categoria, LocalDateTime data, String posizione, StatoSegnalazione stato, Long idSegnalazione) {}

    public InfoAnteprima getInfoAnteprima(){
        if(verificaPresenzaData()){
            LocalDateTime date = getData();
        }

        return new InfoAnteprima(this.categoria, this.data, this.posizione, this.stato, this.idSegnalazione);
    }

    //record usato per tornare in modo pulito i dettagli
    public record Dettaglio(InfoAnteprima anteprima, String titolo, String descrizone, String urlImmagine) {}

    public Dettaglio getDettaglioSegnalazione(){
        String url;
        if(verificaPresenzaImmagine()){
            url = getUrlImmagine();
        }
        else{
            url = new String("Immagine non presente");
        }

        return new Dettaglio(getInfoAnteprima(), getTitolo(), getDescrizione(), url);
    }

    private boolean verificaPresenzaImmagine(){
        if (this.urlImmagine == null) {
            return false;
        }
        return !(this.urlImmagine.isEmpty());
    }

    private boolean verificaPresenzaData(){
        if(this.data == null){
            return false;
        }
        return true;
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



    public static void main(String[] args) {
        System.out.println("[Segnalazione] MainTest avviato..");

        Segnalazione s = new Segnalazione(null, "Discarica","È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                                           Categoria.RIFIUTI_ABBANDONATI, "Viale delle mimose");
        /*
        Titolo: Discarica
        Descrizione: È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.
        Categoria: Rifiuti abbandonati
        Posizione: Viale delle mimose
        */

        s.attach(new ConcreteObserver("ObserverCambioStato"));

        System.out.println(s.toString());

        s.aggiornaStato(true);

        s.aggiornaStato(true);

        s.aggiornaStato(false);

    }



}