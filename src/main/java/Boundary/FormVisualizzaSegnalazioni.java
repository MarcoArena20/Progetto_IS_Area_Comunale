package Boundary;

//import controller.GestoreSegnalazioni;
import Controller.ControllerSegnalazioni;
import Entity.Segnalazione;
import Entity.Categoria;
import Entity.Cittadino;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FormVisualizzaSegnalazioni {

    private JPanel contentPanel;
    private JScrollPane scroll;
    private JButton visualizzaDettaglioButton;
    private JTable tabellaSegnalazioni;
    private JButton modificaSegnalazioneButton;
    private DefaultTableModel tableModel;
    private JFrame frame;

    public FormVisualizzaSegnalazioni() {

        String[] colonneIniziali = {"Categoria", "Data", "Posizione", "Stato"};
        this.tableModel = new DefaultTableModel(colonneIniziali, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rende la tabella non modificabile al doppio click
            }
        };

        //Rende la tabella selezionabile una riga alla volta
        tabellaSegnalazioni.setRowSelectionAllowed(true);
        tabellaSegnalazioni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        List<String[]> righe = ControllerSegnalazioni.caricaSegnalazioni();

        for(String[] riga : righe){
            tableModel.addRow(riga);
        }

        //Collega il modello alla tabella
        tabellaSegnalazioni.setModel(tableModel);

        visualizzaDettaglioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recuperiamo l'indice della riga selezionata
                int rigaSelezionata = tabellaSegnalazioni.getSelectedRow();

                // Controlliamo se l'utente ha cliccato su una riga
                if (rigaSelezionata == -1) {
                    JOptionPane.showMessageDialog(
                            contentPanel,
                            "Seleziona una segnalazione dalla tabella prima di procedere.",
                            "Attenzione",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                frame.dispose();
                // Apriamo il form di dettaglio passando il numero della riga selezionata
                FormVisualizzaDettaglioSegnalazione dettaglioFrame = new FormVisualizzaDettaglioSegnalazione(rigaSelezionata);
                dettaglioFrame.apriFormVisualizzaDettaglioSegnalazioni(rigaSelezionata);

            }
        });

        modificaSegnalazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Aggiungere la chiamata alla schermata di modifica");
                // Recuperiamo l'indice della riga selezionata
                int rigaSelezionata = tabellaSegnalazioni.getSelectedRow();

                // Controlliamo se l'utente ha cliccato su una riga
                if (rigaSelezionata == -1) {
                    JOptionPane.showMessageDialog(
                            contentPanel,
                            "Seleziona una segnalazione dalla tabella prima di procedere.",
                            "Attenzione",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // Apriamo il form di dettaglio passando il numero della riga selezionata
                //FormModificaSegnalazione modificaFrame = new FormModificaSegnalazione(rigaSelezionata);
                //modificaFrame.apriFormModificaSegnalazioni();
            }
        });

    }

    public JFrame apriFormVisualizzaSegnalazioni(){

        frame = new JFrame("Visualizza dettaglio segnalazione");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

    public static void main(String[] args) {
        // Avviamo l'interfaccia in sicurezza nel thread grafico di Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FormVisualizzaSegnalazioni interfaccia = new FormVisualizzaSegnalazioni();
                interfaccia.apriFormVisualizzaSegnalazioni();
            }
        });
    }
}
