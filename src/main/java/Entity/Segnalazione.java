package Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


public class Segnalazione {

    //Attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSegnalazione;

    @ManyToOne
    @JoinColumn(name = "idCittadino")
    private Cittadino cittadino;

    private String titolo;
    private String descrizione;
    private Categoria categoria;
    private String posizione;

    @Convert(converter = ConverterStato.class)
    private StatoSegnalazione stato;
    private LocalDateTime data;
    private String urlImmagine;

    @OneToOne
    private ElencoGestioniSegnalazione elencoGestioniSegnalazione;

    //Costruttore
    public Segnalazione(String titolo, String descrizione, Categoria categoria, String posizione, LocalDateTime data, String urlImmagine, Cittadino cittadino) {
        //TODO
        //Generare automaticamente idSegnalazione

        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.posizione = posizione;
        this.data = data;

        this.urlImmagine = urlImmagine;

        this.stato = new StatoInviata(); //Stato iniziale segnalazione
        this.cittadino = cittadino;
        //Essendo inizialmente la segnalazione nello stato "Inviata",
        //sicuramente l'elencoGestioni sarà null inizialmente
        this.elencoGestioniSegnalazione = null;
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
    public String getIdCittadino() { return cittadino.getIdCittadino(); }
    public StatoSegnalazione getStato() { return stato; }
    public void setStato(StatoSegnalazione stato) {this.stato = stato;}
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public String getUrlImmagine() { return urlImmagine; }
    public void setUrlImmagine(String urlImmagine) { this.urlImmagine = urlImmagine; }

    // Metodi
    public void aggiungiNota(String titoloNota, String descrizioneNota) {
        elencoGestioniSegnalazione.salvaNota(titoloNota, descrizioneNota);
    }

    public synchronized boolean iniziaGestione(String idOperatore) {//metodo synchronized in modo da garantire la mutua esclusione

        //0. Check se è possibile prenderla in carico
        if (!this.stato.getStatoToString().equals(StatoType.INVIATA.toString())) {
            System.err.println("[Segnalazione] Impossibile prendere in carico una richiesta che è già in gestione!");
            return false;
        }

        //1. Controllo se è la prima gestione
        if (elencoGestioniSegnalazione == null) {
            elencoGestioniSegnalazione = new ElencoGestioniSegnalazione(this);//TODO verificare se è necessario il riferimento o se basta l'id
        }

        //2. Salvo operatore che inizia la gestione
        elencoGestioniSegnalazione.salvaOperatore(idOperatore);

        //3. Aggiorno lo stato
        boolean esito = this.stato.aggiornaStato(this, true);
        if (esito == false) {
            System.err.println("[Segnalazione] Operazione di aggiornaStato non consentito!");
            return false;
        }

        //4. Salvo il cambiamento di stato
        elencoGestioniSegnalazione.salvaCambiamentoStato(this.stato);

        //TODO NOTIFICA

        System.out.println("[Segnalazione "+this.idSegnalazione+"] Iniziata la gestione..\n" +
                this.toString()
        );


        return true;
    }

    //Per l'aggiornamento stato e per la conclusione della gestione sicuramente sarà in mutua esclusione per i controlli effettuati in precedenza
    public boolean aggiornaStato() {

        //0. Check preliminare ammissibilità operazione
        if (elencoGestioniSegnalazione == null) {
            System.err.println("[Segnalazione] Riferimento a elencoGestioniSegnalazione non esistente!");
            return false;
        }


        //1. Aggiorno lo stato
        boolean esito = this.stato.aggiornaStato(this,true);
        if (esito == false) {
            System.err.println("[Segnalazione] Operazione di aggiornaStato non consentito!");
            return false;
        }

        //2. Salvo il cambiamento di stato
        elencoGestioniSegnalazione.salvaCambiamentoStato(this.stato);

        System.out.println("[Segnalazione "+this.idSegnalazione+"] Aggiornato stato..\n" +
                this.toString()
        );

        return true;
    }


    public boolean concludiGestione(boolean gestioneRisolutiva) {

        //0. Check preliminare ammissibilità operazione
        if (elencoGestioniSegnalazione == null) {
            System.err.println("[Segnalazione] Riferimento a elencoGestioniSegnalazione non esistente!");
            return false;
        }

        //1. Distinguo le due terminazioni
        if (!gestioneRisolutiva) {
            //La gestione non è stata risolutiva

            //1.1 aggiorno stato con esito false
            this.stato.aggiornaStato(this, false);

            //1.2 Salvo il cambiamento di stato
            elencoGestioniSegnalazione.salvaCambiamentoStato(this.stato);

            return true;//Modifica andata a buon fine
        } else {
            //La gestione è stata risolutiva

            //2.1 controllo se è ammissibile l'operazione
            if (!( this.stato instanceof StatoInLavorazione )){
                //Operazione non consentita
                System.err.println("[Segnalazione] Impossibile concludere la gestione della segnalazione!");

                return false;

            } else{

                //2.2 aggiorno stato con esito false
                this.stato.aggiornaStato(this, true);

                //2.3 Salvo il cambiamento di stato
                elencoGestioniSegnalazione.salvaCambiamentoStato(this.stato);


                //TODO NOTIFICA

                System.out.println("[Segnalazione "+this.idSegnalazione+"] Conclusa gestione..\n" +
                        this.toString()
                );

                return true;
            }
        }

    }

    @Override
    public String toString() {
        return "Segnalazione{\n" +
                "idSegnalazione=" + idSegnalazione + '\n' +
                //", titolo=" + titolo + '\n' +
                //", descrizione=" + descrizione + '\n' +
                //", categoria=" + categoria + '\n\ +
                //", posizione=" + posizione + '\n' +
                ", stato=" + stato.getStatoToString() + '\n' +
                //", data=" + data.toString() + '\n' +
                //", urlImmagine=" + urlImmagine + '\n' +
                '}';
    }



    public static void main(String[] args) {
        System.out.println("[Segnalazione] MainTest avviato..");

        Segnalazione s = new Segnalazione("Discarica","È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                                           Categoria.RIFIUTI_ABBANDONATI, "Viale delle mimose", null, null, null);
        /*
        Titolo: Discarica
        Descrizione: È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.
        Categoria: Rifiuti abbandonati
        Posizione: Viale delle mimose
        */


        System.out.println(s.toString());

        s.iniziaGestione("Operatore1");

        //s.concludiGestione(false);

        //s.iniziaGestione("Operatore1");
        //s.concludiGestione(true);

        s.aggiornaStato();

        s.concludiGestione(true);

    }



}