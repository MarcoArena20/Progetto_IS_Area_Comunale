package Boundary;

//import controller.GestoreSegnalazioni;
import Controller.ControllerSegnalazioni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 * Interfaccia grafica (Boundary) che mostra l'elenco di tutte le segnalazioni inviate dal cittadino.
 *
 * Permette di visualizzare le segnalazioni all'interno di una tabella non modificabile e fornisce
 * le funzionalità per selezionare una riga e visualizzarne il dettaglio, modificarla (se lo stato lo consente)
 * o tornare alla schermata dell'area personale.
 *
 * @author Ciorra Alessandro
 * @version 1.0
 */

public class FormVisualizzaSegnalazioniInviate {

    private JFrame visualizzaFrame;
    private JPanel contentPanel;
    private JScrollPane scroll;
    private JButton visualizzaDettaglioButton;
    private JTable tabellaSegnalazioni;
    private JButton modificaSegnalazioneButton;
    private JButton INDIETROButton;
    private DefaultTableModel tableModel;

    /**
     * Costruisce il form configurando la tabella, agganciando il modello dei dati,
     *  e configurando i listener per i vari pulsanti d'azione.
     */
    public FormVisualizzaSegnalazioniInviate() {

        //definizione delle colonne della tabella
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

        //Collega il modello alla tabella
        tabellaSegnalazioni.setModel(tableModel);

        // Caricamento e inserimento delle righe nella tabella
        List<String[]> righe = visualizzaSegnalazioni();
        for(String[] riga : righe){
            tableModel.addRow(riga);
        }

        // Listener per il pulsante VISUALIZZA DETTAGLIO
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
                visualizzaFrame.dispose();
                // Apriamo il form di dettaglio passando il numero della riga selezionata
                FormVisualizzaDettaglioSegnalazioneInviata dettaglioFrame = new FormVisualizzaDettaglioSegnalazioneInviata();
                dettaglioFrame.apriFormVisualizzaDettaglioSegnalazioni(rigaSelezionata);

            }
        });

        // Listener per il pulsante MODIFICA SEGNALAZIONE
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

                // Una volta ottenuta la conferma, otteniamo le informazioni relative alla segnalazione
                // e apriamo il form di modifica

                boolean modificabile = ControllerSegnalazioni.verificaModificabilita(rigaSelezionata);
                if(!modificabile) {
                    JOptionPane.showMessageDialog(
                            contentPanel,
                            "Segnalazione non modificabile.",
                            "Attenzione",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                else{
                    Map<String, String> parametri = ControllerSegnalazioni.ottieniParametriModificabili(rigaSelezionata);
                    //Apriamo il form di dettaglio passando il numero della riga selezionata
                    FormModificaSegnalazione modificaFrame = new FormModificaSegnalazione(rigaSelezionata);
                    modificaFrame.apriModificaForm(parametri.get("titolo"),
                            parametri.get("descrizione"),
                            parametri.get("categoria"),
                            parametri.get("posizione"),
                            parametri.get("data"),
                            parametri.get("immagine"));

                    visualizzaFrame.dispose();

                }
            }
        });

        // Listener per il pulsante INDIETRO
        INDIETROButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizzaFrame.dispose();
                FormAreaPersonaleCittadino areaPersonaleFrame = new FormAreaPersonaleCittadino();
                areaPersonaleFrame.apriAreaPersonale();
            }
        });

    }

    /**
     * Inizializza, imposta le dimensioni minime e rende visibile la finestra principale
     * contenente l'elenco delle segnalazioni.
     *
     * @return l'istanza del JFrame configurato e visualizzato a schermo
     */
    public JFrame apriFormVisualizzaSegnalazioni(){

        visualizzaFrame = new JFrame("Visualizza segnalazione");
        visualizzaFrame.setContentPane(contentPanel);

        visualizzaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        visualizzaFrame.pack();
        visualizzaFrame.setMinimumSize(new java.awt.Dimension(600, 500));
        visualizzaFrame.setLocationRelativeTo(null);
        visualizzaFrame.setVisible(true);

        return visualizzaFrame;
    }

    /**
     * Interroga il controller per richiedere la lista di tutte le segnalazioni caricate.
     *
     * @return una List di array di stringhe, dove ogni array rappresenta i dati di una singola segnalazione
     */
    private List<String[]> visualizzaSegnalazioni(){
        List<String[]> righe = ControllerSegnalazioni.caricaSegnalazioni();

        return righe;
    }

}
