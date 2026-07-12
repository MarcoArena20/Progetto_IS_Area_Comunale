package Boundary;

import Controller.ControllerSegnalazioni;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FormVisualizzaDettaglioSegnalazioneRicevuta extends JFrame {

    private JPanel contentPanel;
    private JLabel lblTitolo;
    private JLabel lblId;
    private JLabel lblPosizione;
    private JLabel lblStato;
    private JLabel lblData;
    private JLabel lblCittadino;
    private JLabel lblCategoria;
    private JLabel lblUrlImmagine;

    private JTextArea txtDescrizione;
    private JScrollPane scrollPaneDescrizione;

    public JButton btnPrendiInCarico;
    public JButton btnAggiornaStato;
    public JButton btnConcludiGestione;

    private JFrame dettaglioFrame;


    public FormVisualizzaDettaglioSegnalazioneRicevuta(Integer idRow) {

        txtDescrizione.setEditable(false);
        txtDescrizione.setLineWrap(true);

        // Inizializzazione Listener
        configuraAzioni((idRow));
        caricaDettagliSegnalazione(idRow);
    }

    public JFrame apriDettaglioFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Dettaglio Segnalazione");
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Salvataggio del frame nella variabile interna
        dettaglioFrame = frame;

        return frame;
    }


    private void configuraAzioni(Integer idRow) {
        btnPrendiInCarico.addActionListener(e -> eseguiAzione(() -> ControllerSegnalazioni.iniziaGestioneSegnalazione(),idRow));
        btnAggiornaStato.addActionListener(e -> eseguiAzione(() -> ControllerSegnalazioni.aggiornaStatoSegnalazione(),idRow));
        btnConcludiGestione.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormConclusioneGestione(idRow).apriConclusioneFrame();
                dettaglioFrame.dispose();
            }
        });

    }

    // Metodo helper per ridurre la duplicazione del codice
    public void eseguiAzione(java.util.function.Supplier<Boolean> operazione,Integer idRow) {
        if (operazione.get()) {
            JOptionPane.showMessageDialog(dettaglioFrame, "Operazione eseguita con successo.");
            caricaDettagliSegnalazione(idRow);
        } else {
            JOptionPane.showMessageDialog(dettaglioFrame, "Errore nell'esecuzione dell'operazione.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void caricaDettagliSegnalazione(Integer idRow) {
        Map<String, String> mappaDettagli = ControllerSegnalazioni.getDettagliSegnalazione(idRow);

        if (mappaDettagli != null) {
            lblTitolo.setText(mappaDettagli.get("titolo"));
            txtDescrizione.setText(mappaDettagli.get("descrizione"));
            lblCategoria.setText(mappaDettagli.get("categoria"));
            lblPosizione.setText(mappaDettagli.get("posizione"));
            lblData.setText(mappaDettagli.get("data"));
            lblCittadino.setText(mappaDettagli.get("idCittadino"));

            String url = mappaDettagli.get("urlImmagine");
            lblUrlImmagine.setText(url != null && !url.isEmpty() ? url : "Nessun allegato presente");

            String statoAttuale = mappaDettagli.get("stato");
            lblStato.setText(statoAttuale.toUpperCase());
            aggiornaVisibilitaPulsanti(mappaDettagli.get("stato"));
        } else {
            if (dettaglioFrame != null) {
                dettaglioFrame.dispose();
            }
        }
    }

    private void aggiornaVisibilitaPulsanti(String stato) {
        // Reset visibilità
        btnPrendiInCarico.setVisible(false);
        btnAggiornaStato.setVisible(false);
        btnConcludiGestione.setVisible(false);

        // Logica a stati
        switch (stato.toUpperCase()) {
            case "INVIATA":
                btnPrendiInCarico.setVisible(true);
                break;
            case "PRESA_IN_CARICO":
                btnAggiornaStato.setVisible(true);
                btnConcludiGestione.setVisible(true);
                break;
            case "IN_LAVORAZIONE":
                btnConcludiGestione.setVisible(true);
                break;
        }
    }
}
