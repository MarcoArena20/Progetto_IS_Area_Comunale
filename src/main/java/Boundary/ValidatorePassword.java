package Boundary;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorePassword {
    // Definiamo la REGEX come costante
    // ^                      -> Inizio della stringa
    // (?=.{8,25}$)           -> Controlla che la lunghezza totale sia tra 8 e 25 caratteri
    // (?=[^A-Z]*[A-Z])       -> Lookahead: assicura la presenza di almeno una lettera maiuscola
    // (?=[^a-z]*[a-z])       -> Lookahead: assicura la presenza di almeno una lettera minuscola
    // (?=[^0-9]*[0-9])       -> Lookahead: assicura la presenza di almeno un numero
    // (?=[^!?\-@%]*[!?\-@%]) -> Lookahead: assicura la presenza di almeno uno dei caratteri speciali indicati
    // [a-zA-Z0-9!?\-@%]+     -> Consente SOLO i caratteri validi definiti per l'intera stringa
    // $                      -> Fine della stringa
    private static final String PASSWORD_REGEX =
            "^(?=.{8,25}$)(?=[^A-Z]*[A-Z])(?=[^a-z]*[a-z])(?=[^0-9]*[0-9])(?=[^!?\\-@%]*[!?\\-@%])[a-zA-Z0-9!?\\-@%]+$";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    public static boolean validaPassword(String password) {
        // Controllo protettivo contro valori nulli
        if (password == null) {
            return false;
        }

        // Associa il testo della password al motore regex compilato
        Matcher matcher = PASSWORD_PATTERN.matcher(password);

        // Verifica se l'intera stringa soddisfa tutti i vincoli espressi nella regex
        return matcher.matches();
    }
}

