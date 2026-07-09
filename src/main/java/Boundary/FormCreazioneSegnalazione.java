package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormCreazioneSegnalazione {

    private JPanel contentPanel;
    private JButton creaSegnalazioneButton;
    private JTextField titoloField;
    private JTextField descrizioneField;
    private JComboBox categoriaBox;
    private JTextField posizioneField;
    private JTextField urlImmagineField;

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

        String titolo = titoloField.getText();
        String descrizione = descrizioneField.getText();
        String categoria = (String) categoriaBox.getSelectedItem();
        String posizione = posizioneField.getText();
        String urlImmagine = urlImmagineField.getText();

        if (!verificaDatiInseriti(titolo, descrizione, posizione))
            System.out.println("Dati inseriti non validi");
        else{

            ControllerSegnalazioni.creaSegnalazione(titolo, descrizione, categoria, posizione, "", null, urlImmagine);

        }

    }

    private boolean verificaDatiInseriti(String titolo, String descrizione, String posizione){

        if (titolo.length() < 5 || titolo.length() > 15){

            System.out.println("Errore nel titolo");
            return false;

        }

        if (descrizione.length() < 50 || descrizione.length() > 200){

            System.out.println("Errore nella descrizione");
            return false;

        }

        // La verifica della categoria è inutile dato che essa è bloccata dal comboBox

        if (posizione.length() < 10 || posizione.length() > 20){

            System.out.println("Errore nella posizione");
            return false;

        }

        return true;

    }
}
