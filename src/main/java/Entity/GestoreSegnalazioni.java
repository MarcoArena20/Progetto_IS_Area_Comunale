package Entity;

//Librerie
import Database.GestorePersistenza;

import java.time.LocalDateTime;
import java.util.List;

//Façade
public class GestoreSegnalazioni {

    //Attributi
    private GestorePersistenza gestorePersistenza;

    public GestoreSegnalazioni(){

        gestorePersistenza = new GestorePersistenza();

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
        //TODO

        return null;
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


        synchronized (segnalazione) { //accesso in mutua esclusione alla segnalazione
            //TODO impostare limite di attesa
            StatoSegnalazione statoSegnalazione = segnalazione.getStato();

            if (!statoSegnalazione.getStatoToString().equals(StatoType.INVIATA.name())) {
                //Impossibile iniziare la gestione
                System.err.println("[GestoreSegnalazioni] Segnalazione già in gestione..");
                return false;
            } else {
                segnalazione.aggiornaStato(true);
            }
        }
        //Posso uscire dal blocco synchronized poiché è conclusa la sezione critica

        System.out.println("[GestoreSegnalazioni] Gestione iniziata correttamente");

        return true;
    }

    public boolean concludiGestioneSegnalazione(String idSegnalazione, boolean esito) {
        //TODO
        return true;
    }
}