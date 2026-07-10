package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormVisualizzaSegnalazioni extends JFrame {

    private JPanel contentPanel;
    private JComboBox<String> comboStato;
    private JComboBox<String> comboCategoria;
    private JComboBox<String> comboArea;
    private JButton btnApplicaFiltri;
    private JTable tableSegnalazioni;
    private JButton btnVisualizzaDettaglio;
    private JButton btnPrendiInCarico;


    public FormVisualizzaSegnalazioni() {
        setTitle("Pannello Operatore - Visualizza Segnalazioni");
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        aggiornaTabella();

        btnApplicaFiltri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaTabella();
            }
        });

        btnVisualizzaDettaglio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tableSegnalazioni.getSelectedRow();

                if (rigaSelezionata == -1) {
                    JOptionPane.showMessageDialog(FormVisualizzaSegnalazioni.this,
                            "Seleziona una segnalazione dalla tabella prima di procedere.",
                            "Attenzione",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String idSegnalazioneSelezionata = (String) tableSegnalazioni.getValueAt(rigaSelezionata, 0);

                FormVisualizzaDettaglioSegnalazione formDettaglio = new FormVisualizzaDettaglioSegnalazione(idSegnalazioneSelezionata);
                formDettaglio.setVisible(true);
            }
        });

    }


    private void aggiornaTabella() {
        String stato = comboStato.getSelectedItem() != null ? comboStato.getSelectedItem().toString() : "Tutti";
        String categoria = comboCategoria.getSelectedItem() != null ? comboCategoria.getSelectedItem().toString() : "Tutte";
        String area = comboArea.getSelectedItem() != null ? comboArea.getSelectedItem().toString() : "Tutte";

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
}