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
    public String registraUtente(Ruolo ruolo, String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {

        if(ruolo == Ruolo.OPERATORE){
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

    public Cittadino cercaUtente(Long idCittadino) {

        return gestorePersistenza.trovaPerId(Cittadino.class, idCittadino);
    }
}