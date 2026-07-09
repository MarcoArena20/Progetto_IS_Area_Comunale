package Boundary;

import Controller.ControllerSegnalazioni;
import Entity.Categoria;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormCreazioneSegnalazione {

    private JPanel contentPanel;
    private JButton creaSegnalazioneButton;
    private JTextField titoloField;
    private JTextField descrizioneField;
    private JComboBox categoriaBox;
    private JTextField posizioneField;
    private JTextField urlImmagineField;
    private JTextField dataField;

    public FormCreazioneSegnalazione() {
        creaSegnalazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                creaSegnalazione();

            }
        });
    }

    public JFrame apriCreazioneFrame(){

        JFrame frame = new JFrame();
        frame.setTitle("CreazioneFrame");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;

    }

    private void creaSegnalazione(){

        // Caratteristiche obbligatorie che ogni segnalazione deve avere
        String titolo = titoloField.getText();
        String descrizione = descrizioneField.getText();
        String categoria = (String) categoriaBox.getSelectedItem();
        String posizione = posizioneField.getText();

        // Caratteristiche opzionali che una segnalazione può avere
        String urlImmagine = urlImmagineField.getText();
        String data = dataField.getText();

        if (urlImmagine.equalsIgnoreCase(""))
            urlImmagine = null;

        if(data.equalsIgnoreCase(""))
            data = null;

        if (verificaCampiObbligatori(titolo, descrizione, posizione, categoria) && verificaCampiOpzionali(urlImmagine, data))
            System.out.println("Dati inseriti non validi");
        else
        {

            ControllerSegnalazioni.creaSegnalazione(titolo, descrizione, categoria, posizione, data, urlImmagine);

        }

    }

    private boolean verificaCampiObbligatori(String titolo, String descrizione, String posizione, String categoria){

        if (titolo.length() < 5 || titolo.length() > 15){

            System.out.println("Errore nel titolo");
            return false;

        }

        if (descrizione.length() < 50 || descrizione.length() > 200) {

            System.out.println("Errore nella descrizione");
            return false;

        }

        if (posizione.length() < 10 || posizione.length() > 20){

            System.out.println("Errore nella posizione");
            return false;

        }

        try{

            Categoria.valueOf(categoria);

        }catch (IllegalArgumentException e){

            System.out.println("Errore nella categoria");
            return false;
        }

        return true;

    }

    private boolean verificaCampiOpzionali(String urlImmagine, String data){

        if (urlImmagine != null && (urlImmagine.length() < 10 || urlImmagine.length() > 50)){

            System.out.println("Errore nell'url");
            return false;

        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try{

            if(data != null)
                LocalDateTime.parse(data, formatter);

        }catch(DateTimeException e){

            System.out.println("Errore nella data");
            return false;

        }

        return true;

    }
}
