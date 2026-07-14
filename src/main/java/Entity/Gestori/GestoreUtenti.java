package Entity.Gestori;

import Database.GestorePersistenza;
import Entity.Cittadino;
import Entity.Operatore;
import Entity.Enum.Ruolo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * E' una façade utilizzata per gestire gli Utenti
 */

public class GestoreUtenti {

    /**
     * attributo che si riferisce al gestorePersistenza in modo da poter salvare le segnalazioni create, modificate o aggiornate
     * su database
     */
    private final GestorePersistenza gestorePersistenza;

    /**
     * Costruttore per creare ed inizializzare il gestore
     */
    public GestoreUtenti() {

        this.gestorePersistenza = new GestorePersistenza();

    }

    /**
     * Verifica se esiste già un utente registrato con il ruolo
     * e l'indirizzo email specificati.
     *
     * @param ruolo ruolo dell'utente da verificare
     * @param email indirizzo email associato all'utente
     * @return {@code true} se esiste già un utente con il ruolo
     *         e l'email specificati, {@code false} altrimenti
     */
    private boolean verificaUtenteGiaRegistrato(Ruolo ruolo, String email)  {
        if(ruolo == Ruolo.CITTADINO) {
            return !gestorePersistenza.cercaPerCampo(Cittadino.class, "email", email).isEmpty();
        } else {
            return !gestorePersistenza.cercaPerCampo(Operatore.class, "email", email).isEmpty();
        }
    }

    /**
     * Registra un nuovo utente nel sistema.
     * Prima della registrazione viene verificato che non esista già
     * un utente dello stesso ruolo associato all'indirizzo email fornito.
     * In base al ruolo specificato viene creata un'istanza di
     * {@link Cittadino} oppure di {@link Operatore}.
     *
     * @param ruolo ruolo del nuovo utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param email indirizzo email dell'utente
     * @param recapitoTelefonico numero telefonico dell'utente
     * @param passwordHash hash della password dell'utente
     * @return identificativo dell'utente registrato
     * @throws IllegalArgumentException se esiste già un utente
     *         registrato con gli stessi criteri oppure se la registrazione
     *         non può essere completata
     */
    public String registraUtente(Ruolo ruolo, String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        //verifico che non ci sia un Utente(Cittadino/Operatore) che si sta registrando con un email gia registrata per il ruolo scelto
        try {
            if (verificaUtenteGiaRegistrato(ruolo, email)) {
                throw new IllegalArgumentException();
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
        catch (IllegalArgumentException ex){
            throw new IllegalArgumentException();
        }
    }
    /**
     * Effettua la ricerca di un utente e verifica le credenziali
     * fornite per consentire l'accesso al sistema.
     * <p>
     * La ricerca viene effettuata in base al ruolo selezionato,
     * distinguendo tra utenti di tipo {@link Cittadino} e {@link Operatore}.
     *
     * @param ruolo ruolo dell'utente che tenta l'accesso
     * @param email indirizzo email dell'utente
     * @param passwordHash hash della password dell'utente
     * @return identificativo dell'utente autenticato
     * @throws IllegalArgumentException se non viene trovato
     *         un utente corrispondente alle credenziali fornite
     */
    public String accessoUtente(Ruolo ruolo, String email, String passwordHash) throws  IllegalArgumentException{
        if (ruolo == Ruolo.OPERATORE) {
            Operatore operatoreAccesso = cercaUtenteOperatore(email, passwordHash);
            if (operatoreAccesso != null) {
                return operatoreAccesso.getIdOperatore().toString();
            } else {
                throw new IllegalArgumentException("Accesso negato!");
            }
        } else {
            Cittadino cittadinoAccesso = cercaUtenteCittadino(email, passwordHash);
            if (cittadinoAccesso != null) {
                return cittadinoAccesso.getIdCittadino().toString();
            } else {
                throw new IllegalArgumentException("Accesso negato!");
            }
        }
    }

    /**
     * Cerca un cittadino sulla base dell'indirizzo email
     * e dell'hash della password forniti.
     *
     * @param email indirizzo email del cittadino
     * @param passwordHash hash della password del cittadino
     * @return istanza del {@link Cittadino} trovato,
     *         oppure {@code null} se nessun utente corrisponde
     *         ai criteri di ricerca
     */
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

    /**
     * Cerca un operatore sulla base dell'indirizzo email
     * e dell'hash della password forniti.
     *
     * @param email indirizzo email dell'operatore
     * @param passwordHash hash della password dell'operatore
     * @return istanza dell'{@link Operatore} trovato,
     *         oppure {@code null} se nessun utente corrisponde
     *         ai criteri di ricerca
     */
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

    /**
     * metodo per ricavare il riferimento all'oggetto cittadino, fornendo in ingresso l'id
     *
     * @param idCittadino id del cittadino ricercato
     * @return riferimento al cittadino
     */
    public Cittadino cercaCittadino(Long idCittadino) {

        return gestorePersistenza.trovaPerId(Cittadino.class, idCittadino);
    }
}

