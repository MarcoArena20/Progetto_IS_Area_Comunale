package Boundary;

import Controller.ControllerSegnalazioni;
import Entity.Categoria;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormModificaSegnalazione {

    private JFrame modificaFrame;
    private JPanel contentPanel;
    private JPanel labelPanel;
    private JPanel fieldPanel;
    private JTextField dataField;
    private JTextField immagineField;
    private JTextField posizioneField;
    private JTextField descrizioneField;
    private JTextField titoloField;
    private JButton modificaButton;
    private JComboBox categoriaBox;

    public FormModificaSegnalazione(){

        modificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificaSegnalazione();
            }
        });
    }

    public JFrame apriModificaForm(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        JFrame frame = new JFrame();
        frame.setTitle("ModificaFrame");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        titoloField.setText(titolo);
        descrizioneField.setText(descrizione);
        categoriaBox.setSelectedItem(categoria);
        posizioneField.setText(posizione);
        dataField.setText(data);
        immagineField.setText(urlImmagine);

        modificaFrame = frame;

        return frame;

    }

    private boolean modificaSegnalazione(){

        // Caratteristiche obbligatorie che ogni segnalazione deve avere
        String titolo = titoloField.getText();
        String descrizione = descrizioneField.getText();
        String categoria = (String) categoriaBox.getSelectedItem();
        String posizione = posizioneField.getText();

        if(!verificaCampiObbligatori(titolo, descrizione, posizione, categoria)){

            return false;

        }

        // Caratteristiche opzionali che una segnalazione può avere
        String urlImmagine = immagineField.getText();
        String data = dataField.getText();

        if (urlImmagine.equalsIgnoreCase(""))
            urlImmagine = null;

        if(data.equalsIgnoreCase(""))
            data = null;

        if (!verificaCampiOpzionali(urlImmagine, data)){
            return false;
        }
        else
        {
            boolean esito = ControllerSegnalazioni.modificaSegnalazione(titolo, descrizione, categoria, posizione, data, urlImmagine);
            return esito;
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
