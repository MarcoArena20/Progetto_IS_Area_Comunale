package Entity;

import Database.GestorePersistenza;

//Façade
public class GestoreUtenti {

    //Attributi
    private GestorePersistenza gestorePersistenza;

    //Costruttore
    public GestoreUtenti(){
        this.gestorePersistenza = new GestorePersistenza();
    }

    //Metodi
    public String registraUtente(Ruolo ruolo,String cognome, String nome, String email, String recapitoTelefonico, String passwordHash) {

        if(ruolo == Ruolo.OPERATORECOMUNALE){
            Operatore operatore = new Operatore(nome,cognome,email,recapitoTelefonico,passwordHash);
            gestorePersistenza.salva(operatore);
            return operatore.getIdOperatore().toString();
        }
        else{
            Cittadino cittadino = new Cittadino(nome,cognome,email,recapitoTelefonico,passwordHash);
            gestorePersistenza.salva(cittadino);
            return cittadino.getIdCittadino().toString();
        }
    }

    public boolean cercaUtente(String email, String password) {
        //TODO
        return true;
    }
}