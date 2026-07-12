package Boundary;

import Controller.ControllerSegnalazioni;
import Entity.Enum.Categoria;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private JComboBox comboBox1;

    public FormCreazioneSegnalazione() {
        creaSegnalazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Map<String, String> dati = recuperaDatiSegnalazione();

                boolean creata = creaSegnalazione(dati.get("titolo"),
                                                dati.get("descrizione"),
                                                dati.get("categoria"),
                                                dati.get("posizzione"),
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

    private Map<String, String> recuperaDatiSegnalazione(){

        // Caratteristiche obbligatorie che ogni segnalazione deve avere
        String titolo = titoloField.getText();
        String descrizione = descrizioneField.getText();
        String categoria = (String) categoriaBox.getSelectedItem();
        String posizione = posizioneField.getText();

        // Caratteristiche opzionali che una segnalazione può avere
        String urlImmagine = urlImmagineField.getText();
        String data = dataField.getText();

        Map<String, String> dati = new HashMap<>();
        dati.put("titolo", titolo);
        dati.put("descrizione", descrizione);
        dati.put("categoria", categoria);
        dati.put("posizione", posizione);
        dati.put("data", data);
        dati.put("urlImmagine", urlImmagine);

        return dati;

    }

    public boolean creaSegnalazione(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        if(!CheckFormSegnalazione.checkTitolo(titolo))
            return false;

        if(!CheckFormSegnalazione.checkDescrizione(descrizione))
            return false;

        if(!CheckFormSegnalazione.checkCategoria(categoria))
            return false;

        if(!CheckFormSegnalazione.checkPosizione(posizione))
            return false;

        if(!CheckFormSegnalazione.checkData(data))
            return false;

        if(!CheckFormSegnalazione.checkUrlImmagine(urlImmagine))
            return false;

        if (urlImmagine.equalsIgnoreCase(""))
            urlImmagine = null;

        if(data.equalsIgnoreCase(""))
            data = null;

        return ControllerSegnalazioni.creaSegnalazione(titolo, descrizione, categoria, posizione, data, urlImmagine);
    }

}
