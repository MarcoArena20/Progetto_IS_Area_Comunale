package Boundary;

public class CheckDatiForm {
    public static boolean checkDatiFormRegistrazione(String ruoloStringa, String nome, String cognome, String recapitoTelefonico, String email, String password) throws  IllegalArgumentException{
        // Controllo del ruolo selezionato: Cittadino || Operatore Comunale
        if (!"CITTADINO".equalsIgnoreCase(ruoloStringa) && !"OPERATORE".equals(ruoloStringa)) {
            throw new IllegalArgumentException("Il ruolo selezionato non è valido. Scegliere 'Cittadino' o 'Operatore Comunale'.");
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

        // Controllo mail: Lunghezza 7-50, una sola '@', almeno un '.'
        if (email == null || email.length() < 7 || email.length() > 50) {
            throw new IllegalArgumentException("La lunghezza dell'email deve essere compresa tra 7 e 50 caratteri.");
        }
        int chiocciole = 0;
        for (char c : email.toCharArray()) {
            if (c == '@') chiocciole++;
        }
        if (chiocciole != 1 || !email.contains(".")) {
            throw new IllegalArgumentException("L'email contiene piu di una chiocciola '@' o manca del punto identificativo del dominio.");
        }

        // Controllo password: Lunghezza 8-25, almeno 1 Maiuscola, 1 Minuscola, 1 Numero, 1 Speciale [!?-@%]
        if (password == null || password.length() < 8 || password.length() > 25) {
            throw new IllegalArgumentException("La lunghezza della password deve essere compresa tra 8 e 25 caratteri.");
        }

        // Controllo dei criteri di sicurezza della password (maiuscole, minuscole, numeri e speciali)
        boolean haMaiuscola = false;
        boolean haMinuscola = false;
        boolean haNumero = false;
        boolean haSpeciale = false;
        String specialiConsentiti = "!?-@%";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                haMaiuscola = true;
            } else if (Character.isLowerCase(c)) {
                haMinuscola = true;
            } else if (Character.isDigit(c)) {
                haNumero = true;
            } else if (specialiConsentiti.indexOf(c) != -1) {
                haSpeciale = true;
            } else {
                throw new IllegalArgumentException("La password contiene caratteri speciali non ammessi. Usa solo: !, ?, -, @, %");
            }
        }

        if (!haMaiuscola || !haMinuscola || !haNumero || !haSpeciale) {
            throw new IllegalArgumentException("La password non soddisfa i requisiti minimi: deve contenere almeno una maiuscola, una minuscola, un numero e un carattere speciale.");
        }

        // Se tutti i controlli passano senza lanciare eccezioni
        return true;
    }
    public static boolean checkDatiFormAccesso(String ruoloStringa, String email, String password) throws IllegalArgumentException{
        // Controllo del ruolo selezionato: Cittadino || Operatore Comunale
        if (!"Cittadino".equals(ruoloStringa) && !"Operatore Comunale".equals(ruoloStringa)) {
            throw new IllegalArgumentException("Il ruolo selezionato non è valido. Scegliere 'Cittadino' o 'Operatore Comunale'.");
        }
        // Controllo mail: Lunghezza 7-50, una sola '@', almeno un '.'
        if (email == null || email.length() < 7 || email.length() > 50) {
            throw new IllegalArgumentException("La lunghezza dell'email deve essere compresa tra 7 e 50 caratteri.");
        }
        int chiocciole = 0;
        for (char c : email.toCharArray()) {
            if (c == '@') chiocciole++;
        }
        if (chiocciole != 1 || !email.contains(".")) {
            throw new IllegalArgumentException("L'email contiene piu di una chiocciola '@' o manca del punto identificativo del dominio.");
        }

        // Controllo password: Lunghezza 8-25, almeno 1 Maiuscola, 1 Minuscola, 1 Numero, 1 Speciale [!?-@%]
        if (password == null || password.length() < 8 || password.length() > 25) {
            throw new IllegalArgumentException("La lunghezza della password deve essere compresa tra 8 e 25 caratteri.");
        }

        // Controllo dei criteri di sicurezza della password (maiuscole, minuscole, numeri e speciali)
        boolean haMaiuscola = false;
        boolean haMinuscola = false;
        boolean haNumero = false;
        boolean haSpeciale = false;
        String specialiConsentiti = "!?-@%";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                haMaiuscola = true;
            } else if (Character.isLowerCase(c)) {
                haMinuscola = true;
            } else if (Character.isDigit(c)) {
                haNumero = true;
            } else if (specialiConsentiti.indexOf(c) != -1) {
                haSpeciale = true;
            } else {
                throw new IllegalArgumentException("La password contiene caratteri speciali non ammessi. Usa solo: !, ?, -, @, %");
            }
        }

        if (!haMaiuscola || !haMinuscola || !haNumero || !haSpeciale) {
            throw new IllegalArgumentException("La password non soddisfa i requisiti minimi: deve contenere almeno una maiuscola, una minuscola, un numero e un carattere speciale.");
        }

        // Se tutti i controlli passano senza lanciare eccezioni
        return true;
    }
}
