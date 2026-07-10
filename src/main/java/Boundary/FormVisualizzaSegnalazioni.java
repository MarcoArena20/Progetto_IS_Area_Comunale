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
    private DefaultTableModel tableModel;
    private List<Segnalazione> listaSegnalazioniDati;
    //private GestoreSegnalazioni controller;

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
                // Recuperiamo la riga selezionata
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

                // Recuperiamo l'oggetto reale corrispondente alla riga cliccata
                Segnalazione segnalazioneSelezionata = listaSegnalazioniDati.get(rigaSelezionata);

                // Apriamo il form di dettaglio passandogli l'oggetto
                FormVisualizzaDettaglioSegnalazione dettaglioFrame = new FormVisualizzaDettaglioSegnalazione();
                dettaglioFrame.apriFormVisualizzaDettaglioSegnalazioni();

            }
        });

    }

    public JFrame apriFormVisualizzaSegnalazioni(){

        JFrame frame = new JFrame("Visualizza dettaglio segnalazione");
        FormVisualizzaSegnalazioni form = new FormVisualizzaSegnalazioni();
        frame.setContentPane(form.contentPanel);

        /*
         * DISPOSE_ON_CLOSE chiude solo questa finestra,
         * senza terminare tutta l'applicazione.
         */
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

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
