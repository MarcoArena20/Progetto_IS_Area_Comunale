package Controller;
import Entity.Ruolo;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import Entity.GestoreUtenti;

//Façade
public class ControllerUtenti {
    private final GestoreUtenti gestoreUtenti = new GestoreUtenti();

    private Ruolo stringaToRuolo(String ruoloStringa) throws IllegalArgumentException{
        if (ruoloStringa == null){
            throw new IllegalArgumentException("Ruolo non specificato.");
        }
        else if (ruoloStringa.trim().equalsIgnoreCase("Cittadino")) {
            return Ruolo.CITTADINO;
        } else if (ruoloStringa.trim().equalsIgnoreCase("Operatore Comunale")) {
            return Ruolo.OPERATORECOMUNALE;
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
        try{
            boolean esitoregistrazione=false;
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
    }

}
