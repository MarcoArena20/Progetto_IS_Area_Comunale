package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    public boolean presenzaNota = false;
    private final Integer idRow;

    public FormConclusioneGestione(Integer idRow) {
        this.idRow = idRow;

        confermaEConcludiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (concludiGestione()) {

                        conclusioneFrame.dispose();
                        JOptionPane.showMessageDialog(conclusioneFrame, "Conclusa la gestione della segnalazione");

                    }

                } catch (IllegalArgumentException | IllegalAccessException exception) {
                    //System.err.println(exception.getMessage());
                    JOptionPane.showMessageDialog(conclusioneFrame, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        aggiungiNotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                presenzaNota = !presenzaNota;

                visualizzaCampiNotaInterna(presenzaNota);

                if (presenzaNota) {
                    aggiungiNotaButton.setText("Rimuovi nota");
                } else {
                    aggiungiNotaButton.setText("Aggiungi nota");
                    titoloTextField.setText("");
                    descrizioneTextField.setText("");
                }
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

        visualizzaCampiNotaInterna(presenzaNota);

        statoSuccessivoEffettivoLabel.setText("Inviata");

        //Casting della stringa in formato migliore
        String statoPrecedente = ControllerSegnalazioni.getDettagliSegnalazione(this.idRow).get("stato").toLowerCase();
        statoPrecedente = statoPrecedente.substring(0, 1).toUpperCase() + statoPrecedente.substring(1);

        statoPrecedente = statoPrecedente.replace("_", " ");
        statoPrecedenteEffettivoLabel.setText(statoPrecedente);

        conclusioneFrame = frame;

        conclusioneFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new FormVisualizzaDettaglioSegnalazioneRicevuta(idRow).apriDettaglioFrame();
            }
        });

        return frame;
    }

    private void visualizzaCampiNotaInterna(boolean presenzaNota) {
        titoloNotaLabel.setVisible(presenzaNota);
        descrizioneNotaLabel.setVisible(presenzaNota);
        titoloTextField.setVisible(presenzaNota);
        descrizioneTextField.setVisible(presenzaNota);

    }

    public boolean concludiGestione() throws IllegalArgumentException, IllegalAccessException{
        boolean risolutiva = gestioneRisolutivaCheckBox.isSelected();

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

        if (!ControllerSegnalazioni.concludiGestioneSegnalazione(titolo, descrizione, risolutiva) ) {
            throw new IllegalAccessException("Non si hanno i permessi per eseguire questa azione");
        }

        return true;
    }

    private boolean verificaCampi(String titolo, String descrizione) throws IllegalArgumentException {
        //Titolo [5,15] caratteri, no caratteri speciali
        //Descrizione [5,200] caratteri, no caratteri speciali

        if (titolo.length() < 5 || titolo.length() > 15) {

            if (titolo.length() < 5) {

                System.err.println("Inserire titolo di almeno 5 caratteri");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Inserire titolo di almeno 5 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Inserire titolo di almeno 5 caratteri");

            } else {

                System.err.println("Inserire titolo di massimo 15 caratteri");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Inserire titolo di massimo 15 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Inserire titolo di massimo 15 caratteri");

            }

        }

        if (descrizione.length() < 5 || descrizione.length() > 200) {

            if (descrizione.length() < 5) {

                System.err.println("Inserire descrizione di almeno 5 caratteri");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Inserire descrizione di almeno 5 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Inserire descrizione di almeno 5 caratteri");

            } else {

                System.err.println("Inserire descrizione di massimo 200 caratteri");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Inserire descrizione di massimo 200 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Inserire descrizione di massimo 200 caratteri");

            }

        }

        //Check caratteri speciali
        for (char c : titolo.toCharArray()) {

            if (!Character.isLetter(c) && c != ' ') {

                System.err.println("Rimuovere caratteri speciali dal titolo");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Rimuovere caratteri speciali dal titolo", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Rimuovere caratteri speciali dal titolo");

            }
        }

        for (char c : descrizione.toCharArray()) {

            if (!Character.isLetter(c) && c != ' ') {

                System.err.println("Rimuovere caratteri speciali dalla descrizione");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Rimuovere caratteri speciali dalla descrizione", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Rimuovere caratteri speciali dalla descrizione");

            }
        }

        return true;
    }

    public static void main(String[] args){
        new FormConclusioneGestione(1).apriConclusioneFrame();
    }
}
