package Boundary;

import Controller.ControllerSegnalazioni;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;
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

    /**
     *  button utilizzato per la presa in carico di una segnalazione libera
     */

    public JButton btnPrendiInCarico;

    /**
     *  button usato per l'aggiornamento dello stato della segnalazione in gestione
     */

    public JButton btnAggiornaStato;

    /**
     *  button usato per la conclusione della gestione della segnalazione @see FormConclusioneGestione
     */

    public JButton btnConcludiGestione;


    private JTable tableStati;

    private JScrollPane scrollStati;

    private JFrame dettaglioFrame;


    public FormVisualizzaDettaglioSegnalazioneRicevuta(Integer idRow) {

        txtDescrizione.setEditable(false);
        txtDescrizione.setLineWrap(true);
        txtDescrizione.setWrapStyleWord(true);

        configuraAzioni((idRow));
        caricaDettagliSegnalazione(idRow);
        caricaCronologiaStati(idRow);
    }

    public JFrame apriDettaglioFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Dettaglio Segnalazione");
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                FormVisualizzaSegnalazioniRicevute listaAggiornata = new FormVisualizzaSegnalazioniRicevute();
                listaAggiornata.apriVisualizzaFrame();
            }
        });

        frame.setVisible(true);

        // Salvataggio del frame nella variabile interna
        dettaglioFrame = frame;

        return frame;
    }


    private void configuraAzioni(Integer idRow) {
        /*
        * Visto che lo stato della segnalazione può avanzare o ritornare allo stato iniziale (senza dipendere da input dell'utente)
        * l'azione invocata al click dei button sarà una chiamata al ControllerSegnalazioni o l'apertura del formConclusioneGestione
        * (specificando la riga visualizzata al dettaglio) nel caso in cui si vuole concludere la gestione.
        */
        btnPrendiInCarico.addActionListener(e -> eseguiAzione(() -> ControllerSegnalazioni.iniziaGestioneSegnalazione(),idRow));
        btnAggiornaStato.addActionListener(e -> eseguiAzione(() -> ControllerSegnalazioni.aggiornaStatoSegnalazione(),idRow));
        btnConcludiGestione.addActionListener(e -> {
            JFrame frameConclusione = new FormConclusioneGestione(idRow).apriConclusioneFrame();

            frameConclusione.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    caricaDettagliSegnalazione(idRow);
                    caricaCronologiaStati(idRow);
                }
            });
        });
    }

    public void eseguiAzione(java.util.function.Supplier<Boolean> operazione,Integer idRow) {
        if (operazione.get()) {
            JOptionPane.showMessageDialog(dettaglioFrame, "Operazione eseguita con successo.");
            caricaDettagliSegnalazione(idRow);
            caricaCronologiaStati(idRow);
        } else {
            JOptionPane.showMessageDialog(dettaglioFrame, "Errore nell'esecuzione dell'operazione.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void caricaDettagliSegnalazione(Integer idRow) {
        Map<String, String> mappaDettagli = ControllerSegnalazioni.getDettagliSegnalazione(idRow);

        if (mappaDettagli != null) {
            lblTitolo.setText(mappaDettagli.get("titolo"));
            txtDescrizione.setText(mappaDettagli.get("descrizione"));

            String catGrezza = mappaDettagli.get("categoria");
            String catPulita = "";
            if (catGrezza != null) {
                catPulita = catGrezza.replace("_", " ").trim().toLowerCase();
                if (!catPulita.isEmpty()) {
                    catPulita = catPulita.substring(0, 1).toUpperCase() + catPulita.substring(1);
                }
            }
            lblCategoria.setText(catPulita);

            lblPosizione.setText(mappaDettagli.get("posizione"));
            lblData.setText(mappaDettagli.get("data"));
            lblCittadino.setText(mappaDettagli.get("idCittadino"));

            String url = mappaDettagli.get("urlImmagine");
            lblUrlImmagine.setText(url != null && !url.isEmpty() ? url : "Nessun allegato presente");

            String statoAttuale = mappaDettagli.get("stato");
            String statoLabelPulito = "";
            if (statoAttuale != null) {
                statoLabelPulito = statoAttuale.replaceAll("(?i)STATO:", "").replace("_", " ").trim().toLowerCase();
                if (!statoLabelPulito.isEmpty()) {
                    statoLabelPulito = statoLabelPulito.substring(0, 1).toUpperCase() + statoLabelPulito.substring(1);
                }
            }
            lblStato.setText(statoLabelPulito);
            aggiornaVisibilitaPulsanti(mappaDettagli.get("stato"));
        } else {
            if (dettaglioFrame != null) {
                dettaglioFrame.dispose();
            }
        }
    }

    private void caricaCronologiaStati(Integer idRow) {
        if (tableStati == null) return;

        String[] colonneStato = {"Data", "Stato","Titolo Nota","Descrizione Nota"};

        DefaultTableModel modelStati = new DefaultTableModel(colonneStato, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableStati.setModel(modelStati);

        List<String[]> datiCronologia = ControllerSegnalazioni.caricaStoricoStatiSegnalazione(idRow);

        if (!datiCronologia.isEmpty()) {
            for (String[] riga : datiCronologia) {

                String statoPulito = "";
                if (riga[1] != null) {
                    statoPulito = riga[1].replaceAll("(?i)STATO:", "").replace("_", " ").trim().toLowerCase();
                    if (!statoPulito.isEmpty()) {
                        statoPulito = statoPulito.substring(0, 1).toUpperCase() + statoPulito.substring(1);
                    }
                }

                String titoloNota = (riga.length > 2 && riga[2] != null) ? riga[2] : "";
                String descrizioneNota = (riga.length > 3 && riga[3] != null) ? riga[3] : "";

                modelStati.addRow(new String[]{riga[0], statoPulito, titoloNota, descrizioneNota});
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
