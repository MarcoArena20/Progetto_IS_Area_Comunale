package Controller;

import Entity.Categoria;
import Entity.GestoreSegnalazioni;

import java.time.LocalDateTime;

//Façade
public class ControllerSegnalazioni {

    //Attributi
    private static String idSegnalazioneCorrente;



    //Metodi
    //TODO

    // Metodo per andare a creare una segnalazione
    public static final boolean creaSegnalazione(String titolo, String descrizione, String categoria, String posizione, String urlImmagine, LocalDateTime data){

        // Prima di effettuare la chiamata al Façade dello strato Entity, convertiamo il valore di categoria
        Categoria categoriaEnum = Categoria.valueOf(categoria);

        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        boolean esito = gest.inserisciSegnalazione(titolo, descrizione, categoriaEnum, posizione, data, urlImmagine, "");

        return esito;

    }
}
