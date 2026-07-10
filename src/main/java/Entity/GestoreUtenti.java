package Entity;

import Database.GestorePersistenza;

//Façade
public class GestoreUtenti {

    //Attributi
    private GestorePersistenza gestorePersistenza;

    //Costruttore


    //Metodi
    public String registraUtente(Ruolo ruolo, String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        return ;
        //TODO
    }


    public boolean cercaUtente(String email, String password) {
        return (email.contains("Ciao"));
        //TODO
    }
}