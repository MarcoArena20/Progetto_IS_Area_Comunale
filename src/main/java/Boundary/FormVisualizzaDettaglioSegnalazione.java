package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormVisualizzaDettaglioSegnalazione {
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

    public FormVisualizzaDettaglioSegnalazione(){
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

        INDIETROButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                FormVisualizzaSegnalazioni visualizzaFrame = new FormVisualizzaSegnalazioni();
                visualizzaFrame.apriFormVisualizzaSegnalazioni();
            }
        });
    }

    private void configuraTextArea(JTextArea textArea) {
        if (textArea != null) {
            textArea.setEditable(false);       // Blocca la tastiera
            textArea.setLineWrap(true);        // Va a capo quando tocca il bordo
            textArea.setWrapStyleWord(true);   // Non taglia le parole a metà
            textArea.setBackground(Color.WHITE); // Sfondo pulito
        }
    }

    public JFrame apriFormVisualizzaDettaglioSegnalazioni(int riga){

        frame = new JFrame("Visualizza dettaglio segnalazione");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setSize(900, 600);
        frame.setMinimumSize(new java.awt.Dimension(800, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        visualizzaDettaglioSegnalazione(riga);
        return frame;
    }

    private void visualizzaDettaglioSegnalazione(int riga){
        //Chiediamo i dati al controller
        List<String[]> datiInvertiti = ControllerSegnalazioni.caricaDettaglioSegnalazione(riga);

        //Inseriamo le righe verticali nel modello della tabella
        for (String[] riga1 : datiInvertiti) {
            modelInvertito.addRow(riga1);
        }

        //Chiediamo i dati al controller
        List<String[]> datiCronologia = ControllerSegnalazioni.caricaStoricoStatiSegnalazione(riga);

        //Inseriamo i dati nella la tabella
        for (String[] riga2 : datiCronologia) {
            modelStati.addRow(riga2);
        }

        //Chiediamo la descrizione al controller
        String[] DescrizioneEImmagine = ControllerSegnalazioni.caricaDescrizioneEImmagineSegnalazione(riga);

        //Inseriamo la descrizione nella TextArea
        textAreaDescrizione.setText(DescrizioneEImmagine[0]);
        textAreaImage.setText(DescrizioneEImmagine[1]);
    }
}
