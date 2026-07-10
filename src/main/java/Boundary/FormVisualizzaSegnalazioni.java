package Boundary;

//import controller.GestoreSegnalazioni;
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

        //Collega il modello alla tabella
        tabellaSegnalazioni.setModel(tableModel);

        //Rende la tabella selezionabile una riga alla volta
        tabellaSegnalazioni.setRowSelectionAllowed(true);
        tabellaSegnalazioni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Carica i dati nella tabella tramite il controller
        //aggiornaTabella();

        // 3. Carica i dati finti di appoggio (Metodo aggiornato sotto)
        caricaDatiDiProva();

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

                //mostro a schermo qualcosa prima di collegare il controller
                JOptionPane.showMessageDialog(
                        contentPanel,
                        "Hai selezionato correttamente:\n" +
                                "Titolo: " + segnalazioneSelezionata.getTitolo() + "\n" +
                                "Descrizione: " + segnalazioneSelezionata.getDescrizione(),
                        "Test Selezione",
                        JOptionPane.INFORMATION_MESSAGE
                );

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
    // NUOVO METODO: Crea la lista di appoggio direttamente qui dentro
    private void caricaDatiDiProva() {
        listaSegnalazioniDati = new ArrayList<>();

        // Creiamo 3 segnalazioni finte usando il costruttore della tua classe Entity
        // (Adatta i parametri in base al costruttore esatto della tua classe Segnalazione)
        Cittadino cittadino = new Cittadino("aldo", "Arena", "aaaa@a", "3271715524", "aaaaa");
        Segnalazione s1 = new Segnalazione(cittadino, "Lampione spento", "Il lampione davanti al civico 12 non funziona da giorni.", Categoria.ILLUMINAZIONE_GUASTA, "Via Roma 12");
        Segnalazione s2 = new Segnalazione(cittadino, "Buca profonda", "C'è una voragine pericolosa per i motocicli al centro della carreggiata.", Categoria.STRADA_DISSESTATA, "Corso Italia");
        Segnalazione s3 = new Segnalazione(cittadino, "Rifiuti in strada", "Abbandono di materassi sul marciapiede vicino al cassonetto.", Categoria.RIFIUTI_ABBANDONATI, "Via Milano");

        // Riempiamo la lista di appoggio
        listaSegnalazioniDati.add(s1);
        listaSegnalazioniDati.add(s2);
        listaSegnalazioniDati.add(s3);

        // Puliamo e spingiamo i dati graficamente nella JTable
        tableModel.setRowCount(0);
        for (Segnalazione s : listaSegnalazioniDati) {
            Object[] riga = {
                    s.getTitolo(),
                    s.getStato(), // Sarà "INVIATA" di default dal costruttore
                    s.getData()
            };
            tableModel.addRow(riga);
        }
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
