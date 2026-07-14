package Boundary;

import static Boundary.ValidatorePassword.validaPassword;

/**
 * Fornisce metodi statici per la validazione dei dati inseriti
 * nei form di registrazione e di accesso dell'applicazione.
 * <p>
 * La classe verifica che i dati rispettino i vincoli di formato
 * previsti, come il ruolo selezionato, il nome, il cognome,
 * l'indirizzo email, il recapito telefonico e la password.
 * <p>
 * In caso di dati non validi viene sollevata un'eccezione
 * IllegalArgumentException contenente un messaggio
 * descrittivo dell'errore riscontrato.
 *
 * @author Antonio Falcone
 * @version 1.0
 */
public class CheckDatiFormAccessoRegistrazione {

    /**
     * Verifica che tutti i dati inseriti nel form di registrazione
     * rispettino i vincoli previsti dall'applicazione.
     * In particolare vengono controllati:
     *
     * @param ruoloStringa il ruolo selezionato
     * @param nome il nome
     * @param cognome il cognome
     * @param email l'indirizzo email
     * @param recapitoTelefonico il recapito telefonico
     * @param password la password
     * @return {@code true} se tutti i dati rispettano i criteri di validazione
     * @throws IllegalArgumentException se almeno uno dei dati non rispetta
     *          i vincoli previsti
     */
    public static boolean checkDatiFormRegistrazione(String ruoloStringa, String nome, String cognome, String email, String recapitoTelefonico, String password) throws  IllegalArgumentException{
        // Controllo del ruolo selezionato: Cittadino || Operatore Comunale
        if (!"CITTADINO".equalsIgnoreCase(ruoloStringa) && !"OPERATORE".equalsIgnoreCase(ruoloStringa)) {
            throw new IllegalArgumentException("Il ruolo selezionato non è valido. Scegliere 'Cittadino' o 'Operatore'.");
        }

        // Controllo Nome: Lettere, Lunghezza: 3-20
        if (nome == null || nome.length() < 3 || nome.length() > 20) {
            throw new IllegalArgumentException("La lunghezza del nome deve essere compresa tra 3 e 20 caratteri.");
        }
        for (char c : nome.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                throw new IllegalArgumentException("Il nome contiene numeri o caratteri speciali non consentiti.");
            }
        }

        // Controllo Cognome: Lettere, Lunghezza: 3-20
        if (cognome == null || cognome.length() < 3 || cognome.length() > 20) {
            throw new IllegalArgumentException("La lunghezza del cognome deve essere compresa tra 3 e 20 caratteri.");
        }
        for (char c : cognome.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                throw new IllegalArgumentException("Il cognome contiene numeri o caratteri speciali non consentiti.");
            }
        }

        // Controllo recapito telefonico: Solo numeri, Lunghezza = 10
        if (recapitoTelefonico == null || recapitoTelefonico.length() != 10) {
            throw new IllegalArgumentException("Il recapito telefonico deve essere lungo esattamente 10 cifre.");
        }
        for (char c : recapitoTelefonico.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Il recapito telefonico contiene lettere o caratteri non numerici.");
            }
        }

        // Controllo formato Email
        if (!ValidatoreEmail.validaEmail(email)){
            throw  new IllegalArgumentException("La mail deve essere nel formato: alfanumerico.alfanumerico@dominio oppure alfanumerico@dominio.");
        }

        // Controllo dei criteri di sicurezza della password (maiuscole, minuscole, numeri e speciali)
        if (!validaPassword(password)) {
            throw new IllegalArgumentException("La password non soddisfa i requisiti: lunghezza compresa tra 8 e 25 caratteri, deve contenere almeno una maiuscola, una minuscola, un numero e un carattere speciale tra i seguenti: ?, !, -, @, %.");
        }

        return true;

    }

    public static boolean checkDatiFormAccesso(String ruoloStringa, String email, String password) throws IllegalArgumentException{
        // Controllo del ruolo selezionato: Cittadino || Operatore Comunale
        if (!"CITTADINO".equalsIgnoreCase(ruoloStringa) && !"OPERATORE".equalsIgnoreCase(ruoloStringa)) {
            throw new IllegalArgumentException("Il ruolo selezionato non è valido. Scegliere 'Cittadino' o 'Operatore'.");
        }
        if (!ValidatoreEmail.validaEmail(email)){
            throw  new IllegalArgumentException("La mail deve essere nel formato: alfanumerico.alfanumerico@dominio oppure alfanumerico@dominio .");
        }

        if (!ValidatorePassword.validaPassword(password)) {
            throw new IllegalArgumentException("La password non soddisfa i requisiti minimi: deve contenere almeno una maiuscola, una minuscola, un numero e un carattere speciale tra i seguenti: ?, !, -, @, %.");
        }

        return true;

    }
}
