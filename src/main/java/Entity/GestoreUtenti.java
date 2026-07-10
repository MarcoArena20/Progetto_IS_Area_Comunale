package Entity;

import Database.GestorePersistenza;

//Façade
public class GestoreUtenti {

    //Attributi
    private GestorePersistenza gestorePersistenza;

    public GestoreUtenti(){

        this.gestorePersistenza = new GestorePersistenza();

    }

    //Metodi
    public String registraUtente(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        //TODO
        return null;
    }

    public boolean cercaUtente(String email, String password) {
        //TODO
        return true;
    }

    public Cittadino cercaUtente(Long idCittadino) {

        return gestorePersistenza.trovaPerId(Cittadino.class, idCittadino);
    }
}