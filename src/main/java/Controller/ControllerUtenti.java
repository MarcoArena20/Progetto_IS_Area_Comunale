package Controller;

import Entity.GestoreUtenti;
import Entity.Ruolo;

//Façade
public class ControllerUtenti {

    public static boolean salvaUtente(Ruolo ruolo, String cognome, String nome, String recapitoTelefonico, String email, String passwordHash){
        GestoreUtenti gestoreUtenti = new GestoreUtenti();
        // check se posso registrare un utente con le suddette credenziali
        return gestoreUtenti.registraUtente(ruolo, cognome, nome, recapitoTelefonico, email, passwordHash) != null;

    }
}
