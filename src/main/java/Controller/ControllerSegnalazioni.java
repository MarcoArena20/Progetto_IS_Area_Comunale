package Controller;

import Entity.Categoria;
import Entity.GestoreSegnalazioni;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Façade
public class ControllerSegnalazioni {

    //Attributi
    private static String idSegnalazioneCorrente;

    // Metodo per creare una segnalazione
    public static final boolean creaSegnalazione(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        // Prima di effettuare la chiamata al Façade dello strato Entity, convertiamo il valore di categoria
        Categoria categoriaEnum = Categoria.valueOf(categoria);
        LocalDateTime localData;

        if(data != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            localData = LocalDateTime.parse(data, formatter);

        }else{

            localData = null;

        }

        String idCittadino = ControllerUtenti.getIdUtenteCoorrente();

        GestoreSegnalazioni gest = new GestoreSegnalazioni();
        boolean esito = gest.inserisciSegnalazione(titolo, descrizione, categoriaEnum, posizione, idCittadino, localData, urlImmagine);

        return esito;

    }
}
