package Controller;

import Entity.Ruolo;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import Entity.GestoreUtenti;

//Façade
public class ControllerUtenti {

        private static Ruolo stringaToRuolo(String ruoloStringa) throws IllegalArgumentException{
            if (ruoloStringa == null){
                throw new IllegalArgumentException("Ruolo non specificato.");
            }
            else if (ruoloStringa.trim().equalsIgnoreCase("Cittadino")) {
                return Ruolo.CITTADINO;
            } else if (ruoloStringa.trim().equalsIgnoreCase("Operatore Comunale")) {
                return Ruolo.OPERATORE;
            }
            else{throw new IllegalArgumentException("Ruolo non specificato.");}
        }

        private static String hashPassword(String password) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        }
        public static boolean salvaUtente(String ruoloStringa, String cognome, String nome, String recapitoTelefonico, String email,String password){
            GestoreUtenti gestoreUtenti = new GestoreUtenti();
            boolean esitoregistrazione=false;
            try{
                Ruolo ruolo = stringaToRuolo(ruoloStringa);
                String passwordHash =hashPassword(password);

                if (gestoreUtenti.registraUtente(ruolo, cognome, nome, recapitoTelefonico, email, passwordHash)!=null){
                    esitoregistrazione=true;
                }
            }
            catch (NoSuchAlgorithmException e){

                //TODO
                e.printStackTrace();
            }
            return esitoregistrazione;
        }
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

    public static void setIdUtenteCorrente(Long idUtenteCorrente, String ruolo){

        // Il primo controllo da fare è verificare se il file esiste, altrimenti va creato da zero con la configurazione
        // di default, ovvero
        // idUtente:
        // ruolo:
        // idSegnalazione:

        Path path = Path.of("configuration/config.txt");

        try {
            if (!Files.exists(path)) {

                Files.createFile(path);
                Files.writeString(path, "idUtente:" + idUtenteCorrente + "\nruolo:" + ruolo + "\nidSegnalazione:\n", StandardOpenOption.APPEND);

            }else{

                List<String> lines = Files.readAllLines(path);
                lines.set(0, "idUtente:" +idUtenteCorrente );
                lines.set(1, "ruolo:" + ruolo);

                Files.write(path, lines);

            }


        }catch(IOException e){

            e.printStackTrace();

        }
    }
}
