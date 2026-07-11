package Entity.Gestori;

import Database.GestorePersistenza;
import Entity.Cittadino;
import Entity.Operatore;
import Entity.Enum.Ruolo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Façade
public class GestoreUtenti {

    //Attributi
    private final GestorePersistenza gestorePersistenza;

    public GestoreUtenti() {

        this.gestorePersistenza = new GestorePersistenza();

    }

    private boolean verificaUtenteGiaRegistrato(Ruolo ruolo, String email) {
        if(ruolo == Ruolo.CITTADINO) {
            return !gestorePersistenza.cercaPerCampo(Cittadino.class, "email", email).isEmpty();
        } else {
            return !gestorePersistenza.cercaPerCampo(Operatore.class, "email", email).isEmpty();
        }
    }

    //Metodi
    public String registraUtente(Ruolo ruolo, String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        //verifico che non ci sia un Utente(Cittadino/Operatore) che si sta registrando con un email gia registrata per il ruolo scelto
        if (verificaUtenteGiaRegistrato(ruolo, email)) {
            return null;
        } else {
            //Assicurati che non esista un utente con email e ruolo specificati gia registrati
            //procedo alla registrazione e salvataggio dell'utente
            if (ruolo == Ruolo.CITTADINO) {
                Cittadino cittadino = new Cittadino(nome, cognome, email, recapitoTelefonico, passwordHash);
                gestorePersistenza.salva(cittadino);
                return cittadino.getIdCittadino().toString();
            } else {
                Operatore operatore = new Operatore(nome, cognome, email, recapitoTelefonico, passwordHash);
                gestorePersistenza.salva(operatore);
                return operatore.getIdOperatore().toString();
            }
        }
    }

    public String accessoUtente(Ruolo ruolo, String email, String passwordHash) {
        if (ruolo == Ruolo.OPERATORE) {
            Operatore operatoreAccesso = cercaUtenteOperatore(email, passwordHash);
            if (operatoreAccesso != null) {
                return operatoreAccesso.getIdOperatore().toString();
            } else {
                return null;
            }
        } else {
            Cittadino cittadinoAccesso = cercaUtenteCittadino(email, passwordHash);
            if (cittadinoAccesso != null) {
                return cittadinoAccesso.getIdCittadino().toString();
            } else {
                return null;
            }
        }
    }

    public Cittadino cercaUtenteCittadino(String email, String passwordHash) {
        //creo i criteri per cercare nel DB(email e pass)
        Map<String, Object> criteriRicercaUtenteCittadino = new HashMap<>();

        criteriRicercaUtenteCittadino.put("email", email);
        criteriRicercaUtenteCittadino.put("passwordHash", passwordHash);

        //faccio una richiesta al gestore della persistenza
        List<Cittadino> listaUtentiTrovati;
        listaUtentiTrovati = gestorePersistenza.cercaPerCampi(Cittadino.class, criteriRicercaUtenteCittadino);
        //verifico che sia stato effettivamente trovato un Cittadino con quell'email e pass
        if (listaUtentiTrovati != null && !listaUtentiTrovati.isEmpty()) {
            return listaUtentiTrovati.get(0);
        } else {
            return null;
        }
    }

    public Operatore cercaUtenteOperatore(String email, String passwordHash) {
        //creo i criteri per cercare nel DB(email e pass)
        Map<String, Object> criteriRicercaUtenteOperatore = new HashMap<>();

        criteriRicercaUtenteOperatore.put("email", email);
        criteriRicercaUtenteOperatore.put("passwordHash", passwordHash);

        //faccio una richiesta al gestore della persistenza
        List<Operatore> listaUtentiTrovati;
        listaUtentiTrovati = gestorePersistenza.cercaPerCampi(Operatore.class, criteriRicercaUtenteOperatore);
        //verifico che sia stato effettivamente trovato un Cittadino con quell'email e pass
        if (listaUtentiTrovati != null && !listaUtentiTrovati.isEmpty()) {
            return listaUtentiTrovati.get(0);
        } else {
            return null;
        }
    }

    public Cittadino cercaUtente(Long idCittadino) {

        return gestorePersistenza.trovaPerId(Cittadino.class, idCittadino);
    }
}

