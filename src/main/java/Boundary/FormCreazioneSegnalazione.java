package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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
    private JComboBox posizioneComboBox;

    public FormCreazioneSegnalazione() {
        creaSegnalazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Map<String, String> dati = CheckFormSegnalazione.recuperaDatiSegnalazione(titoloField, descrizioneField, categoriaBox, posizioneComboBox, dataField, urlImmagineField);

                boolean creata = creaSegnalazione(dati.get("titolo"),
                                                dati.get("descrizione"),
                                                dati.get("categoria"),
                                                dati.get("posizione"),
                                                dati.get("data"),
                                                dati.get("urlImmagine"));

                if(creata) {
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

    public boolean creaSegnalazione(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        CheckFormSegnalazione.checkDatiSegnalazione(titolo, descrizione, categoria, posizione, data, urlImmagine);

        if (urlImmagine.equalsIgnoreCase(""))
            urlImmagine = null;

        if(data.equalsIgnoreCase(""))
            data = null;

        return ControllerSegnalazioni.creaSegnalazione(titolo, descrizione, categoria, posizione, data, urlImmagine);
    }

}
