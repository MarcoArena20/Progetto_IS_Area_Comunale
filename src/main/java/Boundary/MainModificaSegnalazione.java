package Boundary;

import Controller.ControllerSegnalazioni;

import java.util.Map;

public class MainModificaSegnalazione {

    public static void main(String[] args){

        // Simulo la pressione del tasto modifica nella schermata visualizza dettaglio segnalazione
        // Il valore della segnalazione corrente sarà presente all'interno del controller segnalazione
        // Il primo metodo che dovrà essere invocato alla pressione di tale testo è la verifica dell'effettiva
        // possibilità di modifica da parte dell'utente

        if (ControllerSegnalazioni.verificaModificabilità()){


            // Una volta ottenuta la modificabilità della segnalazione abbiamo bisogno di ritornare
            // gli attributi modificabili
            // titolo, descrizione, categoria, posizione, data, immagine

            Map<String, String> parametri = ControllerSegnalazioni.ottieniParametriModificabili();
            new FormModificaSegnalazione().apriModificaForm(parametri.get("titolo"),
                    parametri.get("descrizione"),
                    parametri.get("categoria"),
                    parametri.get("posizione"),
                    parametri.get("data"),
                    parametri.get("immagine"));

        }
        else
            System.out.println("Segnalazione NON modificabile");

    }

}
