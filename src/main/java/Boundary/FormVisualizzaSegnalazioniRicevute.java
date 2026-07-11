package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormVisualizzaSegnalazioniRicevute extends JFrame {

    private JPanel contentPanel;
    private JComboBox<String> comboStato;
    private JComboBox<String> comboCategoria;
    private JComboBox<String> comboArea;
    private JButton btnApplicaFiltri;
    private JScrollPane scrollPaneTable;
    private JTable tableSegnalazioni;
    private JButton btnVisualizzaDettaglio;

    public FormVisualizzaSegnalazioniRicevute() {

        setTitle("Pannello Operatore - Visualizza Segnalazioni Ricevute");
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

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
                    JOptionPane.showMessageDialog(FormVisualizzaSegnalazioniRicevute.this,
                            "Seleziona una segnalazione dalla tabella prima di procedere.",
                            "Attenzione",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Recupero dell'ID
                String idStringa = (String) tableSegnalazioni.getValueAt(rigaSelezionata, 0);

                // Conversione esplicita in Long per garantire la coerenza del tipo di dato
                Long idSegnalazioneSelezionata = Long.parseLong(idStringa);

                // Istanziazione e apertura del form di dettaglio parametrizzato con l'ID
                FormVisualizzaDettaglioSegnalazioneRicevuta formDettaglio =
                        new FormVisualizzaDettaglioSegnalazioneRicevuta(idSegnalazioneSelezionata);
                formDettaglio.setVisible(true);
            }
        });

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
    public static void main(String[] args){
        new FormVisualizzaSegnalazioniRicevute();
    }
}

