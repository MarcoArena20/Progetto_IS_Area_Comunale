package Boundary;

import javax.swing.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CheckFormSegnalazione {

    public static Map<String, String> recuperaDatiSegnalazione(JTextField titoloField, JTextField descrizioneField, JComboBox categoriaBox, JComboBox posizioneBox,JTextField dataField, JTextField urlImmagineField){

        // Caratteristiche obbligatorie che ogni segnalazione deve avere
        String titolo = titoloField.getText();
        String descrizione = descrizioneField.getText();
        String categoria = (String) categoriaBox.getSelectedItem();
        String posizione = (String) posizioneBox.getSelectedItem();

        // Caratteristiche opzionali che una segnalazione può avere
        String data = dataField.getText();
        String urlImmagine = urlImmagineField.getText();

        Map<String, String> dati = new HashMap<>();
        dati.put("titolo", titolo);
        dati.put("descrizione", descrizione);
        dati.put("categoria", categoria);
        dati.put("posizione", posizione);
        dati.put("data", data);
        dati.put("urlImmagine", urlImmagine);

        return dati;

    }

    public static boolean checkTitolo(String titolo){

        // Il titolo deve avere lunghezza compresa tra 5 e 30 e può contenere solo caratteri, spazi e numeri

        if (titolo.length() < 5 || titolo.length() > 30 || !titolo.matches("^[\\p{L} ]+$")){

            System.out.println("Errore nel titolo");
            return false;

        }

        return true;

    }

    public static boolean checkDescrizione(String descrizione){

        if (descrizione.length() < 50 || descrizione.length() > 200 || !descrizione.matches("^[\\p{L}\\p{N} .,']+$")) {

            System.out.println("Errore nella descrizione");
            return false;

        }

        return true;

    }

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

    public static boolean checkData(String data){

        if(data.equalsIgnoreCase(""))
            return true;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try{
            LocalDateTime.parse(data, formatter);

        }catch(DateTimeException e){

            System.out.println("Errore nella data");
            return false;

        }

        return true;

    }

    public static boolean checkUrlImmagine(String urlImmagine){

        if(urlImmagine.equalsIgnoreCase(""))
            return true;

        if (urlImmagine.length() < 10 || urlImmagine.length() > 50){

            System.out.println("Errore nell'url");
            return false;

        }

        return true;

    }

    public static boolean checkDatiSegnalazione(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        if(!CheckFormSegnalazione.checkTitolo(titolo))
            return false;

        if(!CheckFormSegnalazione.checkDescrizione(descrizione))
            return false;

        if(!CheckFormSegnalazione.checkCategoria(categoria))
            return false;

        if(!CheckFormSegnalazione.checkPosizione(posizione))
            return false;

        if(!CheckFormSegnalazione.checkData(data))
            return false;

        if(!CheckFormSegnalazione.checkUrlImmagine(urlImmagine))
            return false;

        return true;
    }

}
