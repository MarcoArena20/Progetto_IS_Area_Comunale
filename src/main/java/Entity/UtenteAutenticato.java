package Entity;

import jakarta.persistence.MappedSuperclass;

/**
 * Classe astratta per la generalizzazione delle entità Cittadino e Operatore
 */
@MappedSuperclass
public abstract class UtenteAutenticato {

    /**
     * nome dell'utente
     */
    protected String nome;
    /**
     * cognome dell'utente
     */
    protected String cognome;
    /**
     * email dell'utente
     */
    protected String email;
    /**
     * recapitoTelefonico dell'utente
     */
    protected String recapitoTelefonico;
    /**
     * hash della password dell'utente
     */
    protected String passwordHash;

    /**
     * Costruttore di default utilizzato dal database
     */
    public UtenteAutenticato(){

    }

    /**
     * Costruttore dell'utenteAutenticato
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param email email dell'utente
     * @param recapitoTelefonico recapitoTelefonico dell'utente
     * @param passwordHash hash della password dell'utente
     */
    public UtenteAutenticato(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.recapitoTelefonico = recapitoTelefonico;
        this.passwordHash = passwordHash;
    }

    /**
     * Getter del nome
     * @return nome dell'utente
     */
    public String getNome() { return nome; }

    /**
     * Getter del cognome
     * @return cognome dell'utente
     */
    public String getCognome() { return cognome; }

    /**
     * Getter dell'email
     * @return email dell'utente
     */
    public String getEmail() { return email; }

    /**
     * Getter del recapito telefonico
     * @return recapito telefonico dell'utente
     */
    public String getRecapitoTelefonico() { return recapitoTelefonico; }

    /**
     * Getter del ruolo da implementare nelle specializzazioni
     * @return il ruolo dell'utente
     */
    public abstract String getRuolo();
}