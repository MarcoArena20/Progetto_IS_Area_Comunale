package Boundary;

import Controller.ControllerSegnalazioni;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FormVisualizzaDettaglioSegnalazione extends JFrame {

    // Componenti che devi associare nel file .form
    private JPanel contentPanel;
    private JLabel lblId;
    private JLabel lblTitolo;
    private JTextArea txtDescrizione; // Usiamo JTextArea per descrizioni lunghe
    private JLabel lblCategoria;
    private JLabel lblPosizione;
    private JLabel lblStato;
    private JLabel lblData;
    private JLabel lblCittadino;
    private JButton btnPrendiInCarico;
    private JButton btnChiudi;

    private String idSegnalazione;

    public FormVisualizzaDettaglioSegnalazione(String idSegnalazione) {
        this.idSegnalazione = idSegnalazione;

        // Configurazione della finestra
        setTitle("Dettaglio Segnalazione #" + idSegnalazione);
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 600);
        setLocationRelativeTo(null);

        // Configurazione dell'area di testo per la descrizione
        txtDescrizione.setEditable(false);
        txtDescrizione.setLineWrap(true);
        txtDescrizione.setWrapStyleWord(true);

        // Carica i dati all'apertura
        caricaDettagli();

        // Listener del pulsante Prendi In Carico
        btnPrendiInCarico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean successo = ControllerSegnalazioni.prendiInCaricoSegnalazione(idSegnalazione);

                if (successo) {
                    JOptionPane.showMessageDialog(FormVisualizzaDettaglioSegnalazione.this,
                            "Segnalazione presa in carico con successo. Lo stato è stato aggiornato.",
                            "Operazione Riuscita",
                            JOptionPane.INFORMATION_MESSAGE);

                    caricaDettagli();
                } else {
                    JOptionPane.showMessageDialog(FormVisualizzaDettaglioSegnalazione.this,
                            "Impossibile prendere in carico la segnalazione. Verificare lo stato corrente.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private void caricaDettagli() {
        Map<String, String> dettagli = ControllerSegnalazioni.getDettagliSegnalazione(idSegnalazione);

        if (dettagli != null) {
            lblId.setText(dettagli.get("id"));
            lblTitolo.setText(dettagli.get("titolo"));
            txtDescrizione.setText(dettagli.get("descrizione"));
            lblCategoria.setText(dettagli.get("categoria"));
            lblPosizione.setText(dettagli.get("posizione"));
            lblStato.setText(dettagli.get("stato"));
            lblData.setText(dettagli.get("data"));
            lblCittadino.setText(dettagli.get("idCittadino"));

            String stato = dettagli.get("stato");
            if ("Inviata".equalsIgnoreCase(stato)) {
                btnPrendiInCarico.setEnabled(true);
            } else {
                btnPrendiInCarico.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Errore critico: Impossibile recuperare i dettagli della segnalazione.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}