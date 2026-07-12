package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormVisualizzaSegnalazioniRicevute extends JFrame {

    private JFrame visualizzaFrame;
    private JPanel contentPanel;
    private JComboBox<String> comboStato;
    private JComboBox<String> comboCategoria;
    private JComboBox<String> comboArea;
    private JButton btnApplicaFiltri;
    private JTable tableSegnalazioni;
    private JButton btnVisualizzaDettaglio;

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

        if (righe != null) {
            for (String[] riga : righe) {
                model.addRow(riga);
            }
        }

        tableSegnalazioni.setModel(model);
    }
    /*
    public static void main(String[] args){
        FormVisualizzaSegnalazioniRicevute form = new FormVisualizzaSegnalazioniRicevute();
        form.apriVisualizzaFrame();
    }

     */
}

