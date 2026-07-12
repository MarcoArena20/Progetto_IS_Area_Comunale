package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FormModificaSegnalazione {

    private JFrame modificaFrame;
    private JPanel contentPanel;
    private JPanel labelPanel;
    private JPanel fieldPanel;
    private JTextField dataField;
    private JTextField urlImmagineField;
    private JTextField descrizioneField;
    private JTextField titoloField;
    private JButton modificaButton;
    private JComboBox categoriaBox;
    private JComboBox posizioneBox;
    private JButton annullaModificaButton;

    public FormModificaSegnalazione(int idRow){

        modificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Map<String, String> dati = CheckFormSegnalazione.recuperaDatiSegnalazione(titoloField, descrizioneField, categoriaBox, posizioneBox, dataField, urlImmagineField);

                try{

                    boolean modificata = modificaSegnalazione(idRow,
                            dati.get("titolo"),
                            dati.get("descrizione"),
                            dati.get("categoria"),
                            dati.get("posizione"),
                            dati.get("data"),
                            dati.get("urlImmagine"));

                    if(!modificata)
                        JOptionPane.showMessageDialog(contentPanel, "Errore inatteso");
                    else{

                        modificaFrame.dispose();
                        new FormAreaPersonaleCittadino().apriAreaPersonale();

                    }

                }catch (IllegalArgumentException ex){

                    JOptionPane.showMessageDialog(contentPanel, ex.getMessage(), "Errore",JOptionPane.ERROR_MESSAGE);

                }

            }
        });
        annullaModificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new FormVisualizzaSegnalazioni().apriFormVisualizzaSegnalazioni();
                modificaFrame.dispose();

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
        posizioneBox.setSelectedItem(posizione);
        dataField.setText(data);
        urlImmagineField.setText(urlImmagine);

        modificaFrame = frame;

        return frame;

    }

    public boolean modificaSegnalazione(int idRow, String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        try{

            CheckFormSegnalazione.checkDatiSegnalazione(titolo, descrizione, categoria, posizione, data, urlImmagine);

            if (urlImmagine.equalsIgnoreCase(""))
                urlImmagine = null;

            if(data.equalsIgnoreCase(""))
                data = null;

            return ControllerSegnalazioni.modificaSegnalazione(idRow, titolo, descrizione, categoria, posizione, data, urlImmagine);

        }catch(IllegalArgumentException e){

            System.err.println(e.getMessage());
            throw e;

        }

    }
}
