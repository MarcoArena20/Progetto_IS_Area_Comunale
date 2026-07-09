package Boundary;

//import controller.GestoreSegnalazioni;
//import entity.Segnalazione;

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
    //private List<Segnalazione> listaSegnalazioniDati;
    //private GestoreSegnalazioni controller;

    public FormVisualizzaSegnalazioni() {

        String[] colonneIniziali = {"Categoria", "Data", "Posizione", "Stato"};
        this.tableModel = new DefaultTableModel(colonneIniziali, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rende la tabella non modificabile al doppio click
            }
        };

        //Collega il modello alla tabella
        tabellaSegnalazioni.setModel(tableModel);

        //Rende la tabella selezionabile una riga alla volta
        tabellaSegnalazioni.setRowSelectionAllowed(true);
        tabellaSegnalazioni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Carica i dati nella tabella tramite il controller
        //aggiornaTabella();

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
                //Segnalazione segnalazioneSelezionata = listaSegnalazioniDati.get(rigaSelezionata);

                // Apriamo il form di dettaglio passandogli l'oggetto
                //FormVisualizzaDettaglio dettaglioFrame = new FormVisualizzaDettaglio(segnalazioneSelezionata);
                //dettaglioFrame.setVisible(true);

            }
        });

    }

    public JFrame apriFormVisualizzaSegnalazioni(){

        JFrame frame = new JFrame();
        frame.setTitle("VisualizzaFrame");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

/*
    // Metodo per chiedere i dati al controller e spingerli dentro la JTable
    private void aggiornaTabella() {
        // Chiediamo la lista al controller (passando filtri vuoti/null come d'accordo)
        this.listaSegnalazioniDati = controller.cercaSegnalazioni(null, "", null);

        // Puliamo la tabella
        tableModel.setRowCount(0);

        // Riempiamo il modello riga per riga
        for (Segnalazione s : listaSegnalazioniDati) {
            Object[] riga = {
                    s.getTitolo(),
                    s.getStato(),
                    s.getData()
            };
            tableModel.addRow(riga);
        }
    }

    */
}
