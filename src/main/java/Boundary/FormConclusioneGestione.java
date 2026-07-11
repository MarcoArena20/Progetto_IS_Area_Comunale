package Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormConclusioneGestione {
    private JFrame conclusioneFrame;
    private JPanel contentPanel;
    private JButton confermaEConcludiButton;
    private JTextField titoloTextField;
    private JTextField descrizioneTextField;
    private JButton aggiungiNotaButton;
    private JCheckBox gestioneRisolutivaCheckBox;
    private JLabel titoloNotaLabel;
    private JLabel descrizioneNotaLabel;
    private JLabel statoPrecedenteLabel;
    private JLabel statoSuccessivoLabel;
    private JLabel statoPrecedenteEffettivoLabel;
    private JLabel statoSuccessivoEffettivoLabel;

    private boolean visualizza = false;


    public FormConclusioneGestione() {


        confermaEConcludiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(concludiGestione()) {
                    conclusioneFrame.dispose();
                    //new FormAreaPersonaleCittadino().apriAreaPersonale();
                }
            }
        });

        aggiungiNotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizzaCampiNotaInterna(visualizza);
                if (visualizza) {
                    aggiungiNotaButton.setText("Rimuovi nota");
                } else {
                    aggiungiNotaButton.setText("Aggiungi nota");
                    titoloTextField.setText("");
                    descrizioneTextField.setText("");
                }
                visualizza = !visualizza;
            }
        });

        gestioneRisolutivaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gestioneRisolutivaCheckBox.isSelected()) {
                    statoSuccessivoEffettivoLabel.setText("Risolta");
                } else {
                    statoSuccessivoEffettivoLabel.setText("Inviata");
                }
            }
        });
    }

    public JFrame apriConclusioneFrame(){

        JFrame frame = new JFrame();
        frame.setTitle("Concludi gestione segnalazione");

        frame.setContentPane(contentPanel);
        frame.setSize(600, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        visualizzaCampiNotaInterna(visualizza);
        visualizza=true;

        statoSuccessivoEffettivoLabel.setText("Inviata");

        conclusioneFrame = frame;

        return frame;
    }

    private void visualizzaCampiNotaInterna(boolean visualizza) {
        titoloNotaLabel.setVisible(visualizza);
        descrizioneNotaLabel.setVisible(visualizza);
        titoloTextField.setVisible(visualizza);
        descrizioneTextField.setVisible(visualizza);

    }

    private boolean concludiGestione(){
        boolean risolutiva = gestioneRisolutivaCheckBox.isSelected();
        boolean presenzaNota = !risolutiva;

        if (presenzaNota) {
            String titolo = titoloTextField.getText();
            String descrizione = descrizioneTextField.getText();

            if (verificaCampi(titolo, descrizione) ) {
                JOptionPane.showMessageDialog(conclusioneFrame, "Errore nell'inserimento della nota.", "Errore", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        //TODO verifica ammissibilità operazione


        return true;
    }

    private boolean verificaCampi(String titolo, String descrizione) {
        //TODO
        return titolo.isEmpty() && descrizione.isEmpty();
    }
}
