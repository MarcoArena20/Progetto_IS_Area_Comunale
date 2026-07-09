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

    private void createUIComponents() {
        // TODO: place custom component creation code here
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

        if (titolo.length() < 5 || titolo.length() > 15)
            return false;

        if (descrizione.length() < 50 || descrizione.length() > 200)
            return false;

        // La verifica della categoria è inutile dato che essa è bloccata dal comboBox

        if (posizione.length() < 10 || posizione.length() > 20)
            return false;

        return true;

    }
}
