package Boundary;

import Entity.Enum.Categoria;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class CheckFormSegnalazione {

    public static boolean checkTitolo(String titolo){

        if (titolo.length() < 5 || titolo.length() > 15 || !titolo.matches("^[\\p{L} ]+$")){

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

    public static boolean checkCategoria(String posizione){

        if (posizione.length() < 10 || posizione.length() > 20 || !posizione.matches("^[\\p{L}\\p{N} ,]+$")){

            System.out.println("Errore nella posizione");
            return false;

        }

        return true;

    }

    public static boolean checkPosizione(String categoria){

        Set<String> valori = Set.of(
                "ILLUMINAZIONE_GUASTA",
                "STRADA_DISSESTATA",
                "RIFIUTI_ABBANDONATI",
                "PERICOLO_GENERICO",
                "ARREDO_URBANO_DANNEGGIATO"
        );

        return valori.contains(categoria);

    }

    public static boolean checkUrlImmagine(String urlImmagine){

        if (urlImmagine.length() < 10 || urlImmagine.length() > 50){

            System.out.println("Errore nell'url");
            return false;

        }

        return true;

    }

    public static boolean checkData(String data){


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try{
                LocalDateTime.parse(data, formatter);

        }catch(DateTimeException e){

            System.out.println("Errore nella data");
            return false;

        }

        return true;

    }

}
