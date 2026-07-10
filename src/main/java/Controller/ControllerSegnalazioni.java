package Controller;

import Entity.Categoria;
import Entity.GestoreSegnalazioni;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Façade
public class ControllerSegnalazioni {

    private String getIdSegnalazioneCorrente(){

        // Metodo di lettura su file per ottenere la segnalazione corrente

        return null;
    }

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

        // Otteniamo l'id del cittadino per poter verificare la sua esistenza
        Long idCittadino = ControllerUtenti.getIdUtenteCoorrente();

        GestoreSegnalazioni gest = new GestoreSegnalazioni();
        boolean esito = gest.inserisciSegnalazione(idCittadino, titolo, descrizione, categoriaEnum, posizione, localData, urlImmagine);

        return esito;

    }
}
