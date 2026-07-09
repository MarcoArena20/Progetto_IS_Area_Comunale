package Entity;

//Librerie
import Database.GestorePersistenza;

import java.time.LocalDateTime;
import java.util.List;

//Façade
public class GestoreSegnalazioni {

    //Attributi
    private GestorePersistenza gestorePersistenza;

    //Costruttore
    //TODO

    //Metodi
    public boolean inserisciSegnalazione(String titolo, String descrizione, Categoria categoria, String posizione, String urlImmagine, LocalDateTime data, String idCittadino) {
        Segnalazione s = new Segnalazione(titolo, descrizione, categoria, posizione, idCittadino);
        if (urlImmagine != null) s.setUrlImmagine(urlImmagine);
        // Persisti la segnalazione

        //TODO

        return true;
    }

    public List<Segnalazione> cercaSegnalazioni(String idCittadino) {
        //TODO

        return null;
    }

    public List<Segnalazione> cercaSegnalazioni(Categoria categoria, String posizione, String stato) {
        //TODO
        return null;
    }

    public boolean aggiungiNotaDSegnalazione(String idSegnalazione, String titolo, String descrizioneNota) {
        //TODO
        return true;
    }
}