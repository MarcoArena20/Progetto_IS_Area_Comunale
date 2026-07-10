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

        return gestorePersistenza.salva(segnalazione);
    }

    public List<Segnalazione> cercaSegnalazioni(String idCittadino) {
        //TODO

        return null;
    }

    public List<Segnalazione> cercaSegnalazioni(StatoSegnalazione stato, Categoria categoria, String posizione) {
        //TODO
        return null;
    }

    public Segnalazione cercaSegnalazione(String idSegnalazione) {
        //TODO

        return null;
    }

    public boolean aggiungiNota(String idSegnalazione, String titolo, String descrizioneNota) {
        //TODO
        return true;
    }

    public boolean iniziaGestioneSegnalazione(String idSegnalazione, String idOperatore) {
        //TODO
        return true;
    }

    public boolean concludiGestioneSegnalazione(String idSegnalazione, boolean esito) {
        //TODO
        return true;
    }
}