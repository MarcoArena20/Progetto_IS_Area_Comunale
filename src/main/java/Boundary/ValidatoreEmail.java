package Boundary;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *  Fornisce un metodo statico per la validazione del formato
 *  di un indirizzo email.
 *
 *   @author Antonio Falcone
 *   @version 1.0
 */
public class ValidatoreEmail {
    // Definiamo la REGEX come costante
    // Spiegazione della Regex:
    // ^                 -> Inizio della stringa
    // (?=.{7,50}$)      -> Lookahead positivo: controlla che la lunghezza totale sia tra 7 e 50 caratteri
    // [a-zA-Z0-9]+      -> Prima della '@': una stringa alfanumerica di almeno 1 carattere
    // @                 -> Il carattere '@' (presente una sola volta grazie alla struttura rigida)
    // [a-zA-Z0-9]+      -> Dopo della '@': il dominio, composto da caratteri alfanumerici (almeno 1)
    // $                 -> Fine della stringa

    /**
     * Espressione regolare utilizzata per verificare il formato
     * degli indirizzi email accettati dall'applicazione.
     */
    private static final String EMAIL_REGEX = "^(?=.{7,50}$)[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)?@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)?$";

    // Compiliamo il Pattern staticamente all'avvio della classe
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Valida una stringa email in base alle specifiche suddette.
     *
     * @param email La stringa dell'email da validare
     * @return true se l'email è valida, false altrimenti
     */
    public static boolean validaEmail(String email) {
        // Gestione preventiva del valore nullo per evitare NullPointerException
        if (email == null) {
            return false;
        }

        // Colleghiamo la stringa in input al nostro motore di ricerca (Matcher)
        Matcher matcher = EMAIL_PATTERN.matcher(email);

        // Il metodo matches() controlla se l'INTERA stringa rispetta la espressione regolare
        return matcher.matches();
    }
}
