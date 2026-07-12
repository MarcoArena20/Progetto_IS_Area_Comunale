package Boundary;

import Controller.ControllerSegnalazioni;
import Entity.Enum.Categoria;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormCreazioneSegnalazione {

    private JFrame creazioneFrame;
    private JPanel contentPanel;
    private JButton creaSegnalazioneButton;
    private JTextField titoloField;
    private JTextField descrizioneField;
    private JComboBox categoriaBox;
    private JTextField posizioneField;
    private JTextField urlImmagineField;
    private JTextField dataField;
    private JPanel insertPanel;
    private JPanel labelPanel;
    private JLabel l;
    private JPanel titoloPanel;
    private JPanel descrizionePanel;
    private JPanel categoriaPanel;
    private JPanel posizionePanel;
    private JPanel dataPanel;
    private JPanel immaginePanel;

    public FormCreazioneSegnalazione() {
        creaSegnalazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(creaSegnalazione()) {
                    creazioneFrame.dispose();
                    new FormAreaPersonaleCittadino().apriAreaPersonale();
                }
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

        creazioneFrame = frame;

        return frame;

    }

    private boolean creaSegnalazione(){

        // Caratteristiche obbligatorie che ogni segnalazione deve avere
        String titolo = titoloField.getText();
        String descrizione = descrizioneField.getText();
        String categoria = (String) categoriaBox.getSelectedItem();
        String posizione = posizioneField.getText();

        if(!verificaCampiObbligatori(titolo, descrizione, posizione, categoria)){

            return false;

        }

        // Caratteristiche opzionali che una segnalazione può avere
        String urlImmagine = urlImmagineField.getText();
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
            boolean esito = ControllerSegnalazioni.creaSegnalazione(titolo, descrizione, categoria, posizione, data, urlImmagine);
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

    /*
    public static void main(String[] args){

        new FormCreazioneSegnalazione().apriCreazioneFrame();

    }
    */

}
