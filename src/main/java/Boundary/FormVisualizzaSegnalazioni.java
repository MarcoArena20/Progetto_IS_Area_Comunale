package Boundary;

//import controller.GestoreSegnalazioni;
import Controller.ControllerSegnalazioni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class FormVisualizzaSegnalazioni {

    private JFrame visualizzaFrame;
    private JPanel contentPanel;
    private JScrollPane scroll;
    private JButton visualizzaDettaglioButton;
    private JTable tabellaSegnalazioni;
    private JButton modificaSegnalazioneButton;
    private JButton INDIETROButton;
    private DefaultTableModel tableModel;

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
                visualizzaFrame.dispose();
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

                // Una volta ottenuta la conferma, otteniamo le informazioni relative alla segnalazione
                // e apriamo il form di modifica

                boolean modificabile = ControllerSegnalazioni.verificaModificabilita(rigaSelezionata);
                if(!modificabile)
                    System.out.println("Segnalazione non modificabile");
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

        INDIETROButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizzaFrame.dispose();
                FormAreaPersonaleCittadino areaPersonaleFrame = new FormAreaPersonaleCittadino();
                areaPersonaleFrame.apriAreaPersonale();
            }
        });

    }

    public JFrame apriFormVisualizzaSegnalazioni(){

        visualizzaFrame = new JFrame("Visualizza segnalazione");
        visualizzaFrame.setContentPane(contentPanel);

        visualizzaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        visualizzaFrame.pack();
        visualizzaFrame.setLocationRelativeTo(null);
        visualizzaFrame.setVisible(true);

        return visualizzaFrame;
    }

    public static void main(String[] args){

        System.out.println("Avvio dell'applicazione e creazione dell'oggetto MainFrame");

        new FormVisualizzaSegnalazioni().apriFormVisualizzaSegnalazioni();

    }
}
