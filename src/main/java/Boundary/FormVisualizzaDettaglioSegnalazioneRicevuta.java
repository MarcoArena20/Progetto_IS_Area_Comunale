package Boundary;

import Controller.ControllerSegnalazioni;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Map;
import Entity.Cittadino;
import Entity.Gestori.GestoreUtenti;

/**
 *
 * Rappresenta un'interfaccia grafica per la visualizzazione dei dettagli di una specifica
 * segnalazione ricevuta dall'operatore, consentendo la gestione avanzata degli stati e delle note.
 *
 * @author Alessandro Galasso
 * @version 1.0
 *
 */

public class FormVisualizzaDettaglioSegnalazioneRicevuta extends JFrame {

    /**
     * panel per il contenuto del frame
     */
    private JPanel contentPanel;

    /**
     * label per mostrare il titolo della segnalazione
     */
    private JLabel lblTitolo;

    /**
     * label per mostrare la posizione geografica della segnalazione
     */
    private JLabel lblPosizione;

    /**
     * label per mostrare lo stato di avanzamento della segnalazione
     */
    private JLabel lblStato;

    /**
     * label per mostrare la data di invio della segnalazione
     */
    private JLabel lblData;

    /**
     * label per mostrare l'identificativo del cittadino segnalante
     */
    private JLabel lblCittadino;

    /**
     * label per mostrare la categoria della segnalazione
     */
    private JLabel lblCategoria;

    /**
     * label per mostrare l'url dell'immagine allegata
     */
    private JLabel lblUrlImmagine;

    /**
     * area di testo per visualizzare la descrizione estesa
     */
    private JTextArea txtDescrizione;

    /**
     * scroll pane per gestire lo scorrimento della descrizione
     */
    private JScrollPane scrollPaneDescrizione;

    /**
     * button per prendere in carico la segnalazione
     */
    public JButton btnPrendiInCarico;

    /**
     * button per aggiornare lo stato della segnalazione
     */
    public JButton btnAggiornaStato;

    /**
     * button per avviare la procedura di conclusione della gestione
     */
    public JButton btnConcludiGestione;

    /**
     * tabella per la visualizzazione della cronologia degli stati e delle note associate
     */
    private JTable tableStati;

    /**
     * scroll pane per la tabella della cronologia degli stati
     */
    private JScrollPane scrollStati;

    /**
     * frame di visualizzazione del dettaglio
     */
    private JFrame dettaglioFrame;

    /**
     * Costruisce un nuovo form di dettaglio configurando le impostazioni grafiche del campo di testo,
     * agganciando i listener per i pulsanti operativi e popolando i dati della segnalazione corrente.
     * * @param idRow chiave primaria della segnalazione selezionata
     */

    public FormVisualizzaDettaglioSegnalazioneRicevuta(Integer idRow) {

        txtDescrizione.setEditable(false);
        txtDescrizione.setLineWrap(true);
        txtDescrizione.setWrapStyleWord(true);

        configuraAzioni((idRow));
        caricaDettagliSegnalazione(idRow);
        caricaCronologiaStati(idRow);
    }

    /**
     * Imposta le proprietà del frame di dettaglio, associa un listener per la riapertura della
     * schermata principale alla sua chiusura e rende visibile l'interfaccia.
     *
     * @return il frame grafico di dettaglio appena configurato
     */
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

    /**
     * Configura gli action listener associati ai diversi bottoni di gestione per mappare
     * i flussi di business del ciclo di vita della segnalazione.
     *
     * @param idRow identificativo della segnalazione corrente
     */
    private void configuraAzioni(Integer idRow) {
        btnPrendiInCarico.addActionListener(e -> eseguiAzione(ControllerSegnalazioni::iniziaGestioneSegnalazione,idRow));
        btnAggiornaStato.addActionListener(e -> eseguiAzione(ControllerSegnalazioni::aggiornaStatoSegnalazione,idRow));
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

    /**
     * Esegue una specifica azione del controller e aggiorna
     * i dati della schermata in caso di esito positivo.
     *
     * @param operazione operazione che rappresenta il metodo di controllo da invocare
     * @param idRow identificativo della segnalazione interessata
     */
    public void eseguiAzione(java.util.function.Supplier<Boolean> operazione,Integer idRow) {
        if (operazione.get()) {
            JOptionPane.showMessageDialog(dettaglioFrame, "Operazione eseguita con successo.");
            caricaDettagliSegnalazione(idRow);
            caricaCronologiaStati(idRow);
        } else {
            JOptionPane.showMessageDialog(dettaglioFrame, "Errore nell'esecuzione dell'operazione.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Recupera i dati della segnalazione tramite il ControllerSegnalazioni e popola
     * tutti i relativi elementi informativi presenti all'interno dell'interfaccia.
     *
     * @param idRow identificativo della segnalazione di cui caricare le informazioni
     */
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
            lblCittadino.setText(mappaDettagli.get("nomeCittadino"));

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

    /**
     * Recupera l'elenco dei cambi di stato storici della segnalazione e costruisce
     * dinamicamente il modello per popolare la tabella degli stati.
     *
     * @param idRow identificativo della segnalazione di cui ricostruire la cronologia
     */
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

    /**
     * Gestisce la logica del diagramma degli stati nascondendo o mostrando i pulsanti di interazione
     * a seconda dello stato di elaborazione corrente della segnalazione.
     *
     * @param stato stringa che descrive lo stato attuale all'interno del ciclo di vita della segnalazione
     */
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
