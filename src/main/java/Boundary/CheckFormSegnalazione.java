package Boundary;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * Rappresenta una classe di utilità per la gestione dei campi di una segnalazione
 *
 */

public class CheckFormSegnalazione {

    /**
     * Recupera i dati della segnalazione da un form di inserimento/modifica
     * @param titoloField text field per inserire il titolo (obbligatorio)
     * @param descrizioneField text field per inserire la descrizione (obbligatorio)
     * @param categoriaBox combo box per inserire la categoria (obbligatorio)
     * @param posizioneBox combo box per inserire la posizione (obbligatorio)
     * @param dataField text field per inserire la data (opzionale)
     * @param urlImmagineField text field per inserire l'url dell'immagine (opzionale)
     * @return una map con i campi della segnalazione e i relativi valori
     */

    public static Map<String, String> recuperaDatiSegnalazione(JTextField titoloField, JTextField descrizioneField, JComboBox categoriaBox, JComboBox posizioneBox,JTextField dataField, JTextField urlImmagineField){

        // Raccogliamo gli attributi obbligatori
        String titolo = titoloField.getText();
        String descrizione = descrizioneField.getText();
        String categoria = (String) categoriaBox.getSelectedItem();
        String posizione = (String) posizioneBox.getSelectedItem();

        // Raccogliamo gli attributi opzionali
        String data = dataField.getText();
        String urlImmagine = urlImmagineField.getText();

        // Inseriamo gli attributi in una map
        Map<String, String> dati = new HashMap<>();
        dati.put("titolo", titolo);
        dati.put("descrizione", descrizione);
        dati.put("categoria", categoria);
        dati.put("posizione", posizione);
        dati.put("data", data);
        dati.put("urlImmagine", urlImmagine);

        return dati;

    }

    /**
     * Verifica che il titolo abbia lunghezza compresa tra 5 e 30 e sia formata solo da caratteri Unicode e spazi
     * @param titolo titolo della segnalazione
     * @return true se il titolo è conforme, false altrimenti
     */

    public static boolean checkTitolo(String titolo){

        // Il titolo deve avere lunghezza compresa tra 5 e 30 e può contenere solo caratteri

        if (titolo.length() < 5 || titolo.length() > 30 || !titolo.matches("^[\\p{L} ]+$")){

            return false;

        }

        return true;

    }

    /**
     * Verifica che la descrizione abbia lunghezza compresa tra 50 e 200 e sia formata solo da caratteri Unicode,
     * spazi, virgole, punti, punti e virgola, due punti, apostrofi
     * @param descrizione descrizione della segnalazione
     * @return true se la descrizione è conforme, false altrimenti
     */

    public static boolean checkDescrizione(String descrizione){

        if (descrizione.length() < 50 || descrizione.length() > 200 || !descrizione.matches("^[\\p{L}\\p{N} .,;:']+$")) {

            return false;

        }

        return true;

    }

    /**
     * Verifica che la categoria appartenga all'insieme specificato
     * @param categoria categoria della segnalazione
     * @return true se la categoria è conforme, false altrimenti
     */

    public static boolean checkCategoria(String categoria){

        Set<String> valori = Set.of(
                "ILLUMINAZIONE_GUASTA",
                "STRADA_DISSESTATA",
                "RIFIUTI_ABBANDONATI",
                "PERICOLO_GENERICO",
                "ARREDO_URBANO_DANNEGGIATO"
        );

        return valori.contains(categoria);

    }

    /**
     * Verifica che la posizone appartenga all'insieme specificato
     * @param posizione posizione della segnalazione
     * @return true se la posizione è conforme, false altrimenti
     */

    public static boolean checkPosizione(String posizione){

        Set<String> posizioni = Set.of(

                // Centro Storico
                "Centro Storico: Via dei Tribunali 120",
                "Centro Storico: Spaccanapoli 35",
                "Centro Storico: Via San Gregorio Armeno 18",
                "Centro Storico: Via Benedetto Croce 42",
                "Centro Storico: Piazza Bellini 6",

                // Chiaia
                "Chiaia: Via dei Mille 40",
                "Chiaia: Via Chiaia 75",
                "Chiaia: Via Carlo Poerio 21",
                "Chiaia: Riviera di Chiaia 180",
                "Chiaia: Via Cavallerizza a Chiaia 52",

                // Vomero
                "Vomero: Via Luca Giordano 85",
                "Vomero: Via Scarlatti 110",
                "Vomero: Piazza Vanvitelli 15",
                "Vomero: Via Cimarosa 44",
                "Vomero: Via Aniello Falcone 210",

                // Fuorigrotta
                "Fuorigrotta: Viale Augusto 110",
                "Fuorigrotta: Via Giulio Cesare 145",
                "Fuorigrotta: Via Leopardi 95",
                "Fuorigrotta: Via Consalvo 78",
                "Fuorigrotta: Piazzale Tecchio 50",

                // Bagnoli
                "Bagnoli: Via Coroglio 57",
                "Bagnoli: Via Nuova Bagnoli 65",
                "Bagnoli: Via Diocleziano 320",
                "Bagnoli: Via Eurialo 40",
                "Bagnoli: Via Miseno 12"
        );

       return posizioni.contains(posizione);

    }

    /**
     * Verifica che la data venga salvata nel formato dd/MM/yyyy HH:mm
     * @param data data della segnalazione
     * @return true se la data è conforme, false altrimenti
     */

    public static boolean checkData(String data){

        if(data.equalsIgnoreCase(""))
            return true;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try{
            LocalDateTime.parse(data, formatter);

        }catch(DateTimeException e){

            return false;

        }

        return true;

    }

    /**
     * Verifica che l'url dell'immagine abbia formato http[s]://authority/path[?query][#fragment]
     * @param urlImmagine url dell'immagine della segnalazione
     * @return true se l'url è conforme, false altrimenti
     */

    public static boolean checkUrlImmagine(String urlImmagine){

        if(urlImmagine.equalsIgnoreCase(""))
            return true;

        try {
            URI uri = new URI(urlImmagine);

            return uri.getScheme() != null && (uri.getScheme().equals("http") || uri.getScheme().equals("https")) && uri.getHost() != null;

        } catch (URISyntaxException e) {
            return false;
        }

    }

    /**
     * Verifica che tutti i campi di una segnalazione siano conformi
     * @param titolo titolo della segnalazione
     * @param descrizione descrizione della segnalazione
     * @param categoria categoria della segnalazione
     * @param posizione posizione della segnalazione
     * @param data data della segnalazione
     * @param urlImmagine url dell'immagine della segnalazione
     * @throws IllegalArgumentException con la descrizione del parametro non conforme
     */

    public static void checkDatiSegnalazione(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine) throws IllegalArgumentException{

        if(!CheckFormSegnalazione.checkTitolo(titolo))
            throw new IllegalArgumentException("La lunghezza del titolo deve essere compresa tra 5 e 30 caratteri e può contenere solo caratteri Unicode");

        if(!CheckFormSegnalazione.checkDescrizione(descrizione))
            throw new IllegalArgumentException("La lunghezza della descrizione deve essere compresa tra 50 e 200 caratteri e può contenere solo caratteri Unicode compresi {.,;:'}");

        if(!CheckFormSegnalazione.checkCategoria(categoria))
            throw new IllegalArgumentException("La categoria deve appartenere all'insieme {ILLUMINAZIONE_GUASTA," +
                                                                                            " STRADA_DISSESTATA," +
                                                                                            " RIFIUTI_ABBANDONATI," +
                                                                                            " PERICOLO_GENERICO," +
                                                                                            " ARREDO_URBANO_DANNEGGIATO}");

        if(!CheckFormSegnalazione.checkPosizione(posizione))
            throw new IllegalArgumentException("La posizione deve appartenere all'insieme {\"Centro Storico: Via dei Tribunali 120\",\n" +
                    "                \"Centro Storico: Spaccanapoli 35\",\n" +
                    "                \"Centro Storico: Via San Gregorio Armeno 18\",\n" +
                    "                \"Centro Storico: Via Benedetto Croce 42\",\n" +
                    "                \"Centro Storico: Piazza Bellini 6\",\n" +
                    "\n" +
                    "                \"Chiaia: Via dei Mille 40\",\n" +
                    "                \"Chiaia: Via Chiaia 75\",\n" +
                    "                \"Chiaia: Via Carlo Poerio 21\",\n" +
                    "                \"Chiaia: Riviera di Chiaia 180\",\n" +
                    "                \"Chiaia: Via Cavallerizza a Chiaia 52\",\n" +
                    "\n" +
                    "                \"Vomero: Via Luca Giordano 85\",\n" +
                    "                \"Vomero: Via Scarlatti 110\",\n" +
                    "                \"Vomero: Piazza Vanvitelli 15\",\n" +
                    "                \"Vomero: Via Cimarosa 44\",\n" +
                    "                \"Vomero: Via Aniello Falcone 210\",\n" +
                    "\n" +
                    "                \"Fuorigrotta: Viale Augusto 110\",\n" +
                    "                \"Fuorigrotta: Via Giulio Cesare 145\",\n" +
                    "                \"Fuorigrotta: Via Leopardi 95\",\n" +
                    "                \"Fuorigrotta: Via Consalvo 78\",\n" +
                    "                \"Fuorigrotta: Piazzale Tecchio 50\",\n" +
                    "\n" +
                    "                \"Bagnoli: Via Coroglio 57\",\n" +
                    "                \"Bagnoli: Via Nuova Bagnoli 65\",\n" +
                    "                \"Bagnoli: Via Diocleziano 320\",\n" +
                    "                \"Bagnoli: Via Eurialo 40\",\n" +
                    "                \"Bagnoli: Via Miseno 12\"}");

        if(!CheckFormSegnalazione.checkData(data))
            throw new IllegalArgumentException("La data deve avere formattazione dd/mm/yyyy HH:MM");

        if(!CheckFormSegnalazione.checkUrlImmagine(urlImmagine))
            throw new IllegalArgumentException("L'url deve rispettare il formato htttp[s]://authority/path[?query][#fragment]");

    }

}
