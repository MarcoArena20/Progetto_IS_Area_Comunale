package Entity;

//Librerie
import Database.GestorePersistenza;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Façade
public class GestoreSegnalazioni {

    //Attributi
    private GestorePersistenza gestorePersistenza;
    private Observer observerSegnalazione;

    public GestoreSegnalazioni(){

        this.gestorePersistenza = new GestorePersistenza();

        this.observerSegnalazione = new ConcreteObserver("observerSegnalazione");

    }

    //Metodi
    public boolean inserisciSegnalazione(Long idCittadino, String titolo, String descrizione, Categoria categoria, String posizione, LocalDateTime data, String urlImmagine) {

        // Bisogna trovare il cittadino corrispondente
        Cittadino cittadino = new GestoreUtenti().cercaUtente(idCittadino);

        //if(cittadino == null)
            //return false;


        Segnalazione segnalazione = new Segnalazione(cittadino, titolo, descrizione, categoria, posizione);

        if(data != null)
            segnalazione.setData(data);

        if (urlImmagine != null)
           segnalazione.setUrlImmagine(urlImmagine);

        System.out.println("[GestoreSegnalazioni] Inserita nuova segnalazione:\n"+segnalazione.toString());

        return gestorePersistenza.salva(segnalazione);
    }

    public List<Segnalazione> cercaSegnalazioni(Long idCittadino) {

        return gestorePersistenza.cercaPerCampo(
                Segnalazione.class,
                "cittadino.idCittadino",
                idCittadino
        );
    }

    public List<Segnalazione> cercaSegnalazioni(StatoSegnalazione stato, Categoria categoria, String posizione) {
        //TODO
        return null;
    }

    public Segnalazione cercaSegnalazione(Long idSegnalazione) {
        Segnalazione segnalazione = gestorePersistenza.trovaPerId(Segnalazione.class, idSegnalazione);

        return segnalazione;
    }

    public boolean aggiungiNota(Long idSegnalazione, String titolo, String descrizioneNota) {
        //TODO
        return true;
    }

    public boolean iniziaGestioneSegnalazione(Long idSegnalazione, Long idOperatore) {
        //Bisogna controllare che: 0. la segnalazione esiste, 1. accesso in mutua esclusione, 2. la segnalazione ha stato inviata
        Segnalazione segnalazione = cercaSegnalazione(idSegnalazione);
        if (segnalazione == null) {
            System.err.println("[GestoreSegnalazioni] Nessuna segnalazione trovata..");
            return false;
        }

        System.out.println("[GestoreSegnalazioni] Trovata segnalazione:\n"+segnalazione.toString());


        StatoSegnalazione statoSegnalazione = segnalazione.getStato();

        if (!statoSegnalazione.getStatoToString().equals(StatoType.INVIATA.name())) {
            //Impossibile iniziare la gestione
            System.err.println("[GestoreSegnalazioni] Segnalazione già in gestione..");
            return false;
        } else {

            segnalazione.attach(this.observerSegnalazione);

            segnalazione.aggiornaStato(true);
        }

        //Posso uscire dal blocco synchronized poiché è conclusa la sezione critica

        System.out.println("[GestoreSegnalazioni] Gestione iniziata correttamente");

        return true;
    }

    public boolean concludiGestioneSegnalazione(String idSegnalazione, boolean esito) {
        //TODO
        return true;
    }

    public record dettaglioCompleto(Segnalazione.Dettaglio dettaglio, List<AggiornamentoStatoEntry> aggiornamentiStato) {}

    public dettaglioCompleto visualizzaDettaglioSegnalazione(Long idSegnalazione){
        Segnalazione segnalazione = cercaSegnalazione(idSegnalazione);

        Segnalazione.Dettaglio dettaglio = segnalazione.getDettaglioSegnalazione();
        List<AggiornamentoStatoEntry> aggiornamentiStato = gestorePersistenza.cercaPerCampo(
                                                                    AggiornamentoStatoEntry.class,
                                                                    "segnalazione.idSegnalazione",
                                                                    idSegnalazione
                                                                    );

        return new dettaglioCompleto(dettaglio, aggiornamentiStato);
    }

    public List<Segnalazione.InfoAnteprima> visualizzaSegnalazioniPerCittadino(Long idCittadino) {
        List<Segnalazione> segnalazioni = cercaSegnalazioni(idCittadino);
        List<Segnalazione.InfoAnteprima> anteprime = new ArrayList<>();

        for (Segnalazione segnalazione : segnalazioni) {
            Segnalazione.InfoAnteprima anteprima = segnalazione.getInfoAnteprima();
            anteprime.add(anteprima);
        }

        return anteprime;
    }
}