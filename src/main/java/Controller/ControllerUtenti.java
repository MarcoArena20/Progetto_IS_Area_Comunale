package Controller;

import Entity.Enum.Ruolo;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import Entity.Gestori.GestoreUtenti;

/**
 * Fornisce un punto di accesso per le operazioni
 * relative alla gestione degli utenti dell'applicazione.
 * <p>
 * La classe permette alle classi del livello Boundary di interagire
 * con il sottosistema di gestione degli utenti senza conoscere i
 * dettagli implementativi.
 *
 *
 * @version 1.0
 */
public class ControllerUtenti {

    /**
     * Uniforma il formato del ruolo convertendolo nella
     * classe specifica dei ruoli, in modo da fornire all'
     * entity formato prestabilito.
     * @param ruoloStringa ruolo espresso come stringa
     * @return il valore corrispondente dell'enum {@code Ruolo},
     *         oppure {@code null} se il ruolo non è valido
     */
    private static Ruolo stringaToRuolo(String ruoloStringa){
        try {
            if (ruoloStringa == null) {
                throw new IllegalArgumentException("Ruolo non specificato.");
            } else if (ruoloStringa.trim().equalsIgnoreCase("Cittadino")) {
                return Ruolo.CITTADINO;
            } else if (ruoloStringa.trim().equalsIgnoreCase("Operatore")) {
                return Ruolo.OPERATORE;
            } else {
                throw new IllegalArgumentException("Ruolo non specificato.");
            }
        }catch (IllegalArgumentException e){
            e.getMessage();
            return null;
        }
    }

    /**
     * Calcola l'hash SHA-256 della password fornita.
     * <p>
     * La password viene convertita in una sequenza di byte utilizzando
     * la codifica UTF-8 e successivamente trasformata in una stringa
     * esadecimale rappresentante l'hash generato in modo da non far viaggiare in chiaro la suddetta.
     *
     * @param password password da cifrare tramite algoritmo di hashing
     * @return stringa contenente l'hash SHA-256 della password
     * @throws NoSuchAlgorithmException se l'algoritmo SHA-256 non è disponibile
     */
    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

            return sb.toString();
        }
    /**
     * Registra un nuovo utente nel sistema.
     * <p>
     * Il metodo converte il ruolo ricevuto e delega
     * al gestore degli utenti il salvataggio dei dati.
     *
     * @param ruoloStringa ruolo dell'utente espresso come stringa
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param email indirizzo email dell'utente
     * @param recapitoTelefonico numero telefonico dell'utente
     * @param password password dell'utente
     * @return {@code true} se la registrazione è completata correttamente,
     *         {@code false} altrimenti
     * @throws IllegalArgumentException se l'utente risulta già registrato
     *         oppure se i dati forniti non sono validi
     */
        public static boolean salvaUtente(String ruoloStringa, String nome, String cognome, String email, String recapitoTelefonico ,String password) throws IllegalArgumentException{
            GestoreUtenti gestoreUtenti = new GestoreUtenti();
            boolean esitoRegistrazione = false;
            try {
                Ruolo ruolo = stringaToRuolo(ruoloStringa);
                String passwordHash = hashPassword(password);
                String idUtente = gestoreUtenti.registraUtente(ruolo, nome, cognome, email, recapitoTelefonico, passwordHash);
                if (idUtente != null) {
                    esitoRegistrazione = true;
                    setIdUtenteCorrente(Long.parseLong(idUtente), ruoloStringa);
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Utente Già registrato!");
            }
            catch (NoSuchAlgorithmException e){
                System.err.println("Errore critico!");
            }
            return esitoRegistrazione;
        }

    /**
     * Effettua l'autenticazione di un utente tramite le credenziali fornite.
     *
     * @param ruoloStringa ruolo dell'utente espresso come stringa
     * @param email indirizzo email dell'utente
     * @param password password dell'utente
     * @return {@code true} se l'autenticazione ha esito positivo,
     *         {@code false} altrimenti
     * @throws IllegalArgumentException se le credenziali fornite
     *         non risultano corrette
     */
        public static boolean accessoUtente(String ruoloStringa, String email, String password){
            GestoreUtenti gestoreUtenti = new GestoreUtenti();
            boolean esitoAccesso=false;
            try{
                Ruolo ruolo = stringaToRuolo(ruoloStringa);
                String passwordHash =hashPassword(password.trim());
                String idUtente = gestoreUtenti.accessoUtente(ruolo, email.trim(), passwordHash);
                if (idUtente!=null){
                    esitoAccesso=true;
                    setIdUtenteCorrente(Long.parseLong(idUtente), ruoloStringa);
                }
            }
            catch (IllegalArgumentException ex){
                throw  new IllegalArgumentException("Email o password sbagliati!");
            }
            catch (NoSuchAlgorithmException e){
                System.err.println("Errore critico! riprovare.");
            }
            return esitoAccesso;
        }

    /**
     * Permette di ottenere l'id dell'utente correttamente autenticato
     * @return l'id dell'utente corrente
     */
    public static Long getIdUtenteCorrente(){
        Path path = Path.of("configuration/config.txt");
        try {
            if (!Files.exists(path)) {

                return null;

            }else{
                List<String> lines = Files.readAllLines(path);
                return Long.parseLong(lines.get(0).split(":")[1]);
            }
        }catch(IOException e){

            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo utilizzato per leggere da file il ruolo dell'utente corrente e restituirlo
     *
     * @return stringa contenente ruolo dell'utente corrente
     */
    public static String getRuoloUtenteCorrente(){
        Path path = Path.of("configuration/config.txt");
        try {
            if (!Files.exists(path)) {
                return null;
            }else{
                List<String> lines = Files.readAllLines(path);
                return lines.get(1).split(":")[1];
            }
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo utilizzato per verificare che l'utente corrente abbia il ruolo specificato in ingresso
     *
     * @param ruolo ruolo specificato in ingresso
     * @return true se i ruoli corrispondono, false altrimenti
     */

    public static boolean verificaRuoloUtenteCorrente(Ruolo ruolo) {

        String ruoloUtente = ControllerUtenti.getRuoloUtenteCorrente();
        if (!ruoloUtente.equals(ruolo.name())) {
            System.err.println("[ControllerSegnalazioni] Non si hanno i permessi per effettuare questa azione!");
            return false;
        } else {
            return true;
        }

    }

    /**
     * Aggiorna le informazioni relative all'utente corrente
     * nella configurazione locale dell'applicazione.
     *<p>
     * Il metodo salva l'identificativo dell'utente e il relativo ruolo
     * nel file di configurazione utilizzato dall'applicazione.
     *
     * @param idUtenteCorrente identificativo dell'utente corrente
     * @param ruolo ruolo associato all'utente corrente
     */

    public static void setIdUtenteCorrente(Long idUtenteCorrente, String ruolo){

        Path path = Path.of("configuration/config.txt");

        try {
            Files.createDirectories(Path.of("configuration"));

            if (!Files.exists(path)) {

                Files.createFile(path);
                Files.writeString(path, "idUtente:" + idUtenteCorrente + "\nruolo:" + ruolo + "\nidSegnalazione:\n", StandardOpenOption.APPEND);

            } else {

                List<String> lines = Files.readAllLines(path);
                lines.set(0, "idUtente:" + idUtenteCorrente);
                lines.set(1, "ruolo:" + ruolo);

                Files.write(path, lines);

            }


        } catch (IOException e) {
            System.err.println("Errore critico, la creazione della sessione locale è fallita!");
            e.printStackTrace();
        }
    }
}

