package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormVisualizzaSegnalazioni extends JFrame {

    // Componenti grafici che devi legare nel file .form (tramite la proprietà "field name")
    private JPanel contentPanel;
    private JComboBox<String> comboStato;
    private JComboBox<String> comboCategoria;
    private JComboBox<String> comboArea;
    private JButton btnApplicaFiltri;
    private JTable tableSegnalazioni;
    private JButton btnVisualizzaDettaglio;
    private JButton btnPrendiInCarico;
    private JButton btnChiudi;

    public FormVisualizzaSegnalazioni() {
        // Configurazione base della finestra
        setTitle("Pannello Operatore - Visualizza Segnalazioni");
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        btnApplicaFiltri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String stato = comboStato.getSelectedItem() != null ? comboStato.getSelectedItem().toString() : "Tutti";
                String categoria = comboCategoria.getSelectedItem() != null ? comboCategoria.getSelectedItem().toString() : "Tutte";
                String area = comboArea.getSelectedItem() != null ? comboArea.getSelectedItem().toString() : "Tutte";

                List<String[]> righe = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore(stato, categoria, area);

                String[] colonne = {
                        "ID",
                        "Utente",
                        "Data",
                        "Descrizione",
                        "Stato",
                        "Categoria",
                        "Area"
                };

                DefaultTableModel model = new DefaultTableModel(colonne, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                if(righe != null) {
                    for(String[] riga : righe){
                        model.addRow(riga);
                    }
                }

                tableSegnalazioni.setModel(model);
            }
        });


        // TODO: Aggiungere in futuro i listener per btnVisualizzaDettaglio e btnPrendiInCarico
    }
}