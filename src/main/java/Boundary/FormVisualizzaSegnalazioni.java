package Boundary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormVisualizzaSegnalazioni {

    private JPanel contentPanel;
    private JScrollPane scroll;
    private JButton visualizzaDettaglioButton;

    // 1. Definiamo i nomi delle colonne iniziali
    String[] colonneIniziali = {"Titolo", "Stato", "Data Inserimento"};

    // 2. Creiamo il modello con queste colonne (e 0 righe iniziali)
    DefaultTableModel tableModel = new DefaultTableModel(colonneIniziali, 0);

    // 4. Colleghiamo il modello alla JTable
    private JTable tabellaSegnalazioni = new JTable(tableModel);

    public FormVisualizzaSegnalazioni() {
        visualizzaDettaglioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

}
