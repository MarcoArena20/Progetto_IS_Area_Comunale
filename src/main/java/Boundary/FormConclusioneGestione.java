package Boundary;

import Controller.ControllerSegnalazioni;
import Controller.ControllerUtenti;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormConclusioneGestione {
    private JFrame conclusioneFrame;
    private JPanel contentPanel;
    public JButton confermaEConcludiButton;
    public JTextField titoloTextField;
    public JTextField descrizioneTextField;
    public JButton aggiungiNotaButton;
    private JCheckBox gestioneRisolutivaCheckBox;
    private JLabel titoloNotaLabel;
    private JLabel descrizioneNotaLabel;
    private JLabel statoPrecedenteLabel;
    private JLabel statoSuccessivoLabel;
    private JLabel statoPrecedenteEffettivoLabel;
    private JLabel statoSuccessivoEffettivoLabel;

    private boolean visualizza = false;
    private final Integer idRow;

    public FormConclusioneGestione(Integer idRow) {
        this.idRow = idRow;

        confermaEConcludiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(concludiGestione()) {

                    conclusioneFrame.dispose();
                    JOptionPane.showMessageDialog(conclusioneFrame, "Conclusa la gestione della segnalazione");

                } else {

                    JOptionPane.showMessageDialog(conclusioneFrame, "Impossibile concludere la gestione della segnalazione", "Errore", JOptionPane.ERROR_MESSAGE);

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

        //Casting della stringa in formato migliore
        String statoPrecedente = ControllerSegnalazioni.getDettagliSegnalazione(this.idRow).get("stato").toLowerCase();
        statoPrecedente = statoPrecedente.substring(0, 1).toUpperCase() + statoPrecedente.substring(1);

        statoPrecedente = statoPrecedente.replace("_", " ");
        statoPrecedenteEffettivoLabel.setText(statoPrecedente);

        conclusioneFrame = frame;

        return frame;
    }

    private void visualizzaCampiNotaInterna(boolean visualizza) {
        titoloNotaLabel.setVisible(visualizza);
        descrizioneNotaLabel.setVisible(visualizza);
        titoloTextField.setVisible(visualizza);
        descrizioneTextField.setVisible(visualizza);

    }

    public boolean concludiGestione(){
        boolean risolutiva = gestioneRisolutivaCheckBox.isSelected();
        boolean presenzaNota = !visualizza;


        String titolo = titoloTextField.getText();
        String descrizione = descrizioneTextField.getText();

        if (presenzaNota) {

            if (!verificaCampi(titolo, descrizione) ) {

                return false;

            }

        } else {

            titolo = null;
            descrizione = null;

        }

        boolean esito = ControllerSegnalazioni.concludiGestioneSegnalazione(titolo, descrizione, risolutiva);

        return esito;
    }

    private boolean verificaCampi(String titolo, String descrizione) {
        //Titolo [5,15] caratteri, no caratteri speciali
        //Descrizione [5,200] caratteri, no caratteri speciali

        if (titolo.length() < 5 || titolo.length() > 15){

            System.err.println("Errore nel titolo");

            if (titolo.length()<5)
                JOptionPane.showMessageDialog(conclusioneFrame, "Inserire titolo di almeno 5 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(conclusioneFrame, "Inserire titolo di alpiù 15 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);

            return false;

        }

        if (descrizione.length() < 5 || descrizione.length() > 200) {

            System.err.println("Errore nella descrizione");

            if (descrizione.length()<5)
                JOptionPane.showMessageDialog(conclusioneFrame, "Inserire descrizione di almeno 5 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(conclusioneFrame, "Inserire titolo di alpiù 200 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);

            return false;

        }

        //Check caratteri speciali
        for (char c : titolo.toCharArray()) {

            if (!Character.isLetter(c) && c != ' ') {

                System.err.println("Caratteri speciali nel titolo");
                JOptionPane.showMessageDialog(conclusioneFrame, "Rimuovere caratteri speciali dal titolo", "Errore", JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }

        for (char c : descrizione.toCharArray()) {

            if (!Character.isLetter(c) && c != ' ') {

                System.err.println("Caratteri speciali nella descrizione");
                JOptionPane.showMessageDialog(conclusioneFrame, "Rimuovere caratteri speciali dalla descrizione", "Errore", JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }

        return true;
    }
}
