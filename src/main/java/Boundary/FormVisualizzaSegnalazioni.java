package Boundary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormVisualizzaSegnalazioni {

    private JPanel contentPanel;
    private JScrollPane scroll;
    private JButton visualizzaDettaglioButton;
    private JTable tabellaSegnalazioni;
    private DefaultTableModel tableModel;

    public FormVisualizzaSegnalazioni() {
        visualizzaDettaglioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() {
        String[] colonneIniziali = {"Titolo", "Stato", "Data Inserimento"};
        DefaultTableModel tableModel = new DefaultTableModel(colonneIniziali, 0);

        tabellaSegnalazioni = new JTable(tableModel);
    }
}
