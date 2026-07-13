package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Interfaccia grafica (Boundary) per la visualizzazione dettagliata di una singola segnalazione inviata.
 *
 * La classe si occupa di mostrare l'estratto completo di una segnalazione, suddiviso in:
 * Dati generali (organizzati in una tabella a riga singola invertita)
 * Cronologia degli stati attraversati dalla segnalazione
 * Descrizione testuale e percorso dell'immagine allegata
 *
 * @author Ciorra Alessandro
 * @version 1.0
 */

public class FormVisualizzaDettaglioSegnalazioneInviata {
    private JFrame frame;
    private JPanel contentPanel;
    private JScrollPane scrollStati;
    private JScrollPane scrollDettagli;
    private JScrollPane scrollImage;
    private JTable tableDettagli;
    private JTable tableStati;
    private JTextArea textAreaDescrizione;
    private JTextArea textAreaImage;
    private JButton INDIETROButton;
    private DefaultTableModel modelInvertito;
    private DefaultTableModel modelStati;

    /**
     * Costruisce il form inizializzando i componenti grafici, configurando i modelli delle tabelle
     * e impostando i listener per i pulsanti di navigazione.
     */
    public FormVisualizzaDettaglioSegnalazioneInviata(){
        //Mettiamo in sicurezza tutte le JTextArea (Sola Lettura e testo a capo automatico)
        configuraTextArea(textAreaDescrizione);
        configuraTextArea(textAreaImage);

        //Definiamo le colonne fisse: la prima per il nome del campo, la seconda per il valore
        String[] colonne = {"Proprietà", "Valore"};

        this.modelInvertito = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rende la tabella non modificabile
            }
        };

        tableDettagli.setModel(modelInvertito);

        // Nascondiamo la riga in alto con "Proprietà" e "Valore"
        tableDettagli.setTableHeader(null);

        //Definiamo le 2 colonne della cronologia
        String[] colonneStato = {"Data", "Stato", "Titolo"};

        modelStati = new DefaultTableModel(colonneStato, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Non modificabile
            }
        };

        tableStati.setModel(modelStati);

        // Nascondiamo l'header
        tableStati.setTableHeader(null);

        //gestione del click sul pulsante INDIETRO
        INDIETROButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                FormVisualizzaSegnalazioniInviate visualizzaFrame = new FormVisualizzaSegnalazioniInviate();
                visualizzaFrame.apriFormVisualizzaSegnalazioni();
            }
        });
    }

    /**
     * Configura le proprietà standard di una JTextArea per garantirne la sola lettura
     * e il wrapping corretto del testo.
     *
     * @param textArea l'istanza della JTextArea da configurare
     */
    private void configuraTextArea(JTextArea textArea) {
        if (textArea != null) {
            textArea.setEditable(false);       // Blocca la tastiera
            textArea.setLineWrap(true);        // Va a capo quando tocca il bordo
            textArea.setWrapStyleWord(true);   // Non taglia le parole a metà
            textArea.setBackground(Color.WHITE); // Sfondo pulito
        }
    }

    /**
     * Inizializza, popola e rende visibile la finestra principale del dettaglio della segnalazione.
     *
     * @param riga l'indice della riga selezionata nel form precedente, corrispondente alla segnalazione
     * @return l'istanza del JFrame configurato e mostrato a schermo
     */
    public JFrame apriFormVisualizzaDettaglioSegnalazioni(int riga){

        frame = new JFrame("Visualizza dettaglio segnalazione");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setSize(900, 600);
        frame.setMinimumSize(new java.awt.Dimension(850, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Map<String, List<String[]>> mappDettagli = visualizzaDettaglioSegnalazione(riga);
        aggiornaTabella(mappDettagli);
        return frame;
    }

    /**
     * Interroga il livello controller per caricare i dati della segnalazione e li impacchetta in una mappa.
     *
     * @param riga l'indice della riga della segnalazione da caricare
     * @return una Map contenente i dati strutturati
     */
    private Map<String, List<String[]>> visualizzaDettaglioSegnalazione(int riga){
        //Chiediamo i dati al controller
        List<String[]> datiGenerali = ControllerSegnalazioni.caricaDettaglioSegnalazione(riga);

        //Chiediamo i dati al controller
        List<String[]> datiCronologia = ControllerSegnalazioni.caricaStoricoStatiSegnalazione(riga);

        //Chiediamo la descrizione al controller
        String[] DescrizioneEImmagine = ControllerSegnalazioni.caricaDescrizioneEImmagineSegnalazione(riga);

        List<String[]> datiDescrizioneImmagine = new ArrayList<>();
        datiDescrizioneImmagine.add(DescrizioneEImmagine);

        // Creiamo e ritorniamo la Map con i dati del dettaglio
        return new HashMap<>(Map.of(
                "datiGenerali", datiGenerali,
                "datiCronologia", datiCronologia,
                "datiDescrizioneImmagine", datiDescrizioneImmagine
        ));
    }

    /**
     * Estrae le informazioni dalla mappa dei dettagli e popola i rispettivi elementi dell'interfaccia grafica
     * (modelli delle tabelle e aree di testo).
     *
     * @param mappaDettagli la mappa contenente le liste di array di stringhe prelevate dal controller
     */
    private void aggiornaTabella(Map<String, List<String[]>> mappaDettagli){

        //Inseriamo le righe verticali nel modello della tabella
        for (String[] riga1 : mappaDettagli.get("datiGenerali")) {
            modelInvertito.addRow(riga1);
        }

        //Inseriamo i dati nella la tabella
        for (String[] riga2 : mappaDettagli.get("datiCronologia")) {
            modelStati.addRow(riga2);
        }

        //Inseriamo la descrizione nella TextArea
        textAreaDescrizione.setText(mappaDettagli.get("datiDescrizioneImmagine").get(0)[0]);
        textAreaImage.setText(mappaDettagli.get("datiDescrizioneImmagine").get(0)[1]);

    }

}
