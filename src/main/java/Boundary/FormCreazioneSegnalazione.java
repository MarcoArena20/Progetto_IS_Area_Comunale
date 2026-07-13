package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 *
 *  Rappresenta un'interfaccia grafica per la creazione di una segnalazione
 *
 * @author Marco Arena
 * @version 1.0
 *
 */

public class FormCreazioneSegnalazione {

    /**
     *  frame di creazione
     */

    private JFrame creazioneFrame;

    /**
     *  panel per il contenuto del frame
     */

    private JPanel contentPanel;

    /**
     *  panel per i label
     */
    private JPanel labelPanel;

    /**
     *  panel per i campi d'inserimento
     */

    private JPanel insertPanel;

    /**
     *  text field per inserire il titolo
     */

    private JTextField titoloField;

    /**
     *  text field per inserire la descrizione
     */

    private JTextField descrizioneField;

    /**
     *  combo box per inserire la categoria
     */

    private JComboBox categoriaBox;

    /**
     * combo box per inserire la posizione
     */

    private JComboBox posizioneBox;

    /**
     *  text field per inserire la posizione
     */

    private JTextField dataField;

    /**
     *  text field per inserire l'url dell'immagine
     */

    private JTextField urlImmagineField;

    /**
     *  button per creare la segnalazione
     */

    private JButton creaSegnalazioneButton;

    /**
     * Costruisce un nuovo frame creando un action listener legato al
     * button per la creazione della segnalazione
     */

    public FormCreazioneSegnalazione() {
        creaSegnalazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                creaSegnalazioneButton();

            }
        });
    }

    /**
     *  Imposta le proprietà del frame di creazione e lo rende visibile all'utente
     */

    public void apriCreazioneFrame(){

        JFrame frame = new JFrame();
        frame.setTitle("Frame di creazione");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        creazioneFrame = frame;

    }

    /**
     * CASO D'USO: CreazioneSegnalazione
     * Verifica che i dati d'ingresso siano validi e in caso affermativo contatta il
     * ControllerSegnalazioni per creare una segnalazione
     *
     * @param titolo titolo della segnalazione (campo obbligatorio)
     * @param descrizione descrizione della segnalazione (campo obbligatorio)
     * @param categoria categoria della segnalazione (campo obbligatorio)
     * @param posizione posizione della segnalazione (campo obbligatorio)
     * @param data data della segnalazione (campo opzionale)
     * @param urlImmagine url dell'allegato della segnalazione (campo opzionale)
     * @return true se la creazione è andata a buon fine, false altrimenti
     * @throws IllegalArgumentException se uno dei campi non ha rispettato i vincoli della classe @see CheckFormSegnalazione
     */

    public boolean creaSegnalazione(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine) throws IllegalArgumentException{

        try{

            // Controlliamo i parametri d'ingresso
            CheckFormSegnalazione.checkDatiSegnalazione(titolo, descrizione, categoria, posizione, data, urlImmagine);

            // Verifichiamo la presenza della data
            if(data.equalsIgnoreCase(""))
                data = null;

            // Verifichiamo la presenza dell'url dell'immagine
            if (urlImmagine.equalsIgnoreCase(""))
                urlImmagine = null;

            // Invochiamo il ControllerSegnalazioni
            return ControllerSegnalazioni.creaSegnalazione(titolo, descrizione, categoria, posizione, data, urlImmagine);

        }catch(IllegalArgumentException e){

            System.err.println(e.getMessage());
            throw e;

        }

    }

    /**
     * Legge i valori passati dall'utente e avvia il Caso d'uso CreazioneSegnalazione
     */

    private void creaSegnalazioneButton(){

        // Leggiamo i valori di input passati dall'utente
        Map<String, String> dati = CheckFormSegnalazione.recuperaDatiSegnalazione(titoloField, descrizioneField, categoriaBox, posizioneBox, dataField, urlImmagineField);

        try{

            // Creiamo la segnalazione
            boolean creata = creaSegnalazione(dati.get("titolo"),
                    dati.get("descrizione"),
                    dati.get("categoria"),
                    dati.get("posizione"),
                    dati.get("data"),
                    dati.get("urlImmagine"));

            if(!creata){

                // In caso di errore non legato ai parametri d'ingresso mostriamo un errore inatteso.
                // Rientrano in questa casistica tutti i problemi che si possono presentare dal package Controller
                // fino al package Database

                JOptionPane.showMessageDialog(contentPanel, "Errore inatteso");

            }
            else{

                // Se la creazione è andata a buon fine il cittadino accede alla propria area personale
                creazioneFrame.dispose();
                new FormAreaPersonaleCittadino().apriAreaPersonale();

            }

        }catch(IllegalArgumentException ex){

            // In caso di errore legato ai parametri d'ingresso mostriamo la descrizione dei vincoli su tale campo
            JOptionPane.showMessageDialog(contentPanel, ex.getMessage(), "Errore",JOptionPane.ERROR_MESSAGE);

        }

    }

}
