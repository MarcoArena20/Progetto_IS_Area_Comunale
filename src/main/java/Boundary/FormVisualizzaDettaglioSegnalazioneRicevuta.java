package Boundary;

import Controller.ControllerSegnalazioni;
import javax.swing.*;
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

    private JButton btnPrendiInCarico;
    private JButton btnAggiornaStato;
    private JButton btnConcludiGestione;
    private JButton btnChiudi;

    private final Long idSegnalazione;

    public FormVisualizzaDettaglioSegnalazioneRicevuta(Long idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
        ControllerSegnalazioni.setIdSegnalazioneCorrente(idSegnalazione);

        setTitle("Dettaglio Segnalazione #" + idSegnalazione);
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        txtDescrizione.setEditable(false);
        txtDescrizione.setLineWrap(true);

        // Inizializzazione Listener
        configuraAzioni();
        caricaDettagliSegnalazione();
    }

    private void configuraAzioni() {
        btnPrendiInCarico.addActionListener(e -> eseguiAzione(() -> ControllerSegnalazioni.iniziaGestioneSegnalazione()));
        //btnAggiornaStato.addActionListener(e -> eseguiAzione(() -> ControllerSegnalazioni.(idSegnalazione)));
        //btnConcludiGestione.addActionListener(e -> eseguiAzione(() -> ControllerSegnalazioni.concludiPositivo(idSegnalazione)));
        btnChiudi.addActionListener(e -> dispose());
    }

    // Metodo helper per ridurre la duplicazione del codice
    private void eseguiAzione(java.util.function.Supplier<Boolean> operazione) {
        if (operazione.get()) {
            JOptionPane.showMessageDialog(this, "Operazione eseguita con successo.");
            caricaDettagliSegnalazione();
        } else {
            JOptionPane.showMessageDialog(this, "Errore nell'esecuzione dell'operazione.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void caricaDettagliSegnalazione() {
        Map<String, String> mappaDettagli = ControllerSegnalazioni.getDettagliSegnalazione(idSegnalazione);

        if (mappaDettagli != null) {
            lblId.setText(mappaDettagli.get("id"));
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
            dispose();
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

                break;
            case "IN_LAVORAZIONE":
                btnConcludiGestione.setVisible(true);
                break;
        }
    }
}