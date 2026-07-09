package Entity;

import Database.GestorePersistenza;

//Façade
public class GestoreUtenti {

    //Attributi
    private GestorePersistenza gestorePersistenza;

    //Costruttore
    //TODO

    //Metodi
    public String registraUtente(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        //TODO
        return null;
    }

    public boolean cercaUtente(String email, String password) {
        //TODO
        return true;
    }

    public Cittadino cercaUtente(String idCittadino) {

        //return gestorePersistenza.cercaPerCampo(Cittadino.class, "idCittadino", idCittadino);
        return null;
    }
}