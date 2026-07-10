package Entity;

import Database.GestorePersistenza;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Façade
public class GestoreUtenti {

    //Attributi
    private final GestorePersistenza gestorePersistenza;

    public GestoreUtenti(){

        this.gestorePersistenza = new GestorePersistenza();

    }

    //Metodi
    public String registraUtente(Ruolo ruolo, String nome, String cognome, String email, String recapitoTelefonico, String passwordHash){
        if (gestorePersistenza.cercaPerCampo(UtenteAutenticato.class, "email", email)==null){
            return null;
        }
        else if(ruolo == Ruolo.OPERATORE){
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
    public String accessoUtente(Ruolo ruolo, String email, String passwordHash){
        if (ruolo == Ruolo.OPERATORE){
            Operatore operatoreAccesso = (Operatore) cercaUtente(email, passwordHash);
            if (operatoreAccesso != null){
                return  operatoreAccesso.getIdOperatore().toString();
            }
            else {return null;}
        }
        else{
            Cittadino cittadinoAccesso = (Cittadino) cercaUtente(email, passwordHash);
            if (cittadinoAccesso != null){
                return  cittadinoAccesso.getIdCittadino().toString();
            }
            else {return null;}
        }
    }

    public UtenteAutenticato cercaUtente(String email, String passwordHash) {
        //creo i criteri per cercare nel DB(email e pass)
        Map<String, Object> criteriRicercaUtente = new HashMap<>();

        criteriRicercaUtente.put("email", email);
        criteriRicercaUtente.put("password", passwordHash);

        //faccio una richiesta al gestore della persistenza
        List<UtenteAutenticato> listaUtentiTrovati;
        listaUtentiTrovati = gestorePersistenza.cercaPerCampi( UtenteAutenticato.class ,criteriRicercaUtente);
        //verifico che sia stato effettivamente trovato un UtenteAutenticato con quell'email e pass
        if(listaUtentiTrovati!= null && !listaUtentiTrovati.isEmpty()){
            return  listaUtentiTrovati.get(0);
        }
        else{return null;}
    }

    public Cittadino cercaUtente(Long idCittadino) {

        return gestorePersistenza.trovaPerId(Cittadino.class, idCittadino);
    }


}