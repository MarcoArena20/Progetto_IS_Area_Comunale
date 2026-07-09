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
        //TODO
        return true;
    }

    public List<Segnalazione> cercaSegnalazioni(String idCittadino) {
        //TODO

        return null;
    }

    public List<Segnalazione> cercaSegnalazioni(Stato stato, Categoria categoria, String posizione) {
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