package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * Rappresenta un'interfaccia grafica per la visualizzazione delle segnalazioni ricevute
 * da parte di un operatore. Permette di filtrare i risultati e accedere ai dettagli.
 *
 * @author Alessandro Galasso
 * @version 1.0
 *
 */

public class FormVisualizzaSegnalazioniRicevute extends JFrame {

    /**
     * frame di visualizzazione
     */
    private JFrame visualizzaFrame;

    /**
     * panel per il contenuto del frame
     */
    private JPanel contentPanel;

    /**
     * combo box per filtrare in base allo stato della segnalazione
     */
    private JComboBox<String> comboStato;

    /**
     * combo box per filtrare in base alla categoria della segnalazione
     */
    private JComboBox<String> comboCategoria;

    /**
     * combo box per filtrare in base all'area di competenza
     */
    private JComboBox<String> comboArea;

    /**
     * button per applicare i filtri selezionati alla tabella
     */
    private JButton btnApplicaFiltri;

    /**
     * tabella per mostrare l'elenco delle segnalazioni
     */
    private JTable tableSegnalazioni;

    /**
     * button per visualizzare il dettaglio di una singola segnalazione selezionata
     */
    private JButton btnVisualizzaDettaglio;

    /**
     * Costruisci un nuovo frame popolando la tabella iniziale e creando gli action listener
     * legati all'applicazione dei filtri e all'apertura del dettaglio della segnalazione.
     */

    public FormVisualizzaSegnalazioniRicevute() {

        aggiornaTabella();

        // Listener per l'applicazione dei filtri di ricerca
        btnApplicaFiltri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaTabella();
            }
        });

        // Listener per l'apertura della finestra di dettaglio della segnalazione
        btnVisualizzaDettaglio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tableSegnalazioni.getSelectedRow();

                // Verifica preventiva della selezione di una riga
                if (rigaSelezionata == -1) {
                    JOptionPane.showMessageDialog(visualizzaFrame,
                            "Seleziona una segnalazione dalla tabella prima di procedere.",
                            "Attenzione",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Istanziazione e apertura del form di dettaglio parametrizzato con l'ID
                FormVisualizzaDettaglioSegnalazioneRicevuta formDettaglio =
                        new FormVisualizzaDettaglioSegnalazioneRicevuta((int) rigaSelezionata);
                formDettaglio.apriDettaglioFrame();
                visualizzaFrame.dispose();
            }
        });
    }

    /**
     *
     * Imposta le proprietà del frame di visualizzazione e lo rende visibile all'utente.
     *
     * @return il frame appena configurato e mostrato
     */

    public JFrame apriVisualizzaFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Pannello Operatore - Visualizza Segnalazioni Ricevute");
        frame.setContentPane(contentPanel);

        // Essendo la schermata principale, EXIT_ON_CLOSE va bene
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        visualizzaFrame = frame;

        return frame;
    }

    /**
     * Legge i valori dei filtri attualmente selezionati e interroga il
     * ControllerSegnalazioni per aggiornare i dati mostrati nella tabella.
     */
    private void aggiornaTabella() {
        String stato = comboStato.getSelectedItem() != null ? comboStato.getSelectedItem().toString() : "Tutti";
        String categoria = comboCategoria.getSelectedItem() != null ? comboCategoria.getSelectedItem().toString() : "Tutte";
        String area = comboArea.getSelectedItem() != null ? comboArea.getSelectedItem().toString() : "Tutte";

        // Invocazione del metodo di controllo
        List<String[]> righe = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore(stato, categoria, area);

        String[] colonne = {"ID", "Utente", "Data", "Descrizione", "Stato", "Categoria", "Area"};

        DefaultTableModel model = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (String[] riga : righe) {
            if (riga[4] != null) {
                String rigaPulita = riga[4].replaceAll("(?i)STATO:", "").replace("_", " ").trim().toLowerCase();
                if (!rigaPulita.isEmpty()) {
                    riga[4] = rigaPulita.substring(0, 1).toUpperCase() + rigaPulita.substring(1);
                }
            }

            if (riga[5] != null) {
                String categoriaPulita = riga[5].replace("_", " ").trim().toLowerCase();
                if (!categoriaPulita.isEmpty()) {
                    riga[5] = categoriaPulita.substring(0, 1).toUpperCase() + categoriaPulita.substring(1);
                }
            }

            model.addRow(riga);
        }
        tableSegnalazioni.setModel(model);
    }

    public static void main(String[] args){
        FormVisualizzaSegnalazioniRicevute form = new FormVisualizzaSegnalazioniRicevute();
        form.apriVisualizzaFrame();
    }


}

