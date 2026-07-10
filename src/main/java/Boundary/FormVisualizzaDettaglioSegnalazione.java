package Boundary;

import Entity.Segnalazione;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class FormVisualizzaDettaglioSegnalazione {
    private JPanel contentPanel;
    private JLabel imageLabel;
    private JScrollPane scrollDettagli;
    private JScrollPane scrollDescrizione;
    private JScrollPane scrollStato;
    private JTextArea texAreaStato;
    private JTextArea textAreaDescrizione;
    private JTextArea textAreaDettagli;


    public FormVisualizzaDettaglioSegnalazione(Segnalazione segnalazione){
        // 1. Mettiamo in sicurezza tutte le JTextArea (Sola Lettura e testo a capo automatico)
        configuraTextArea(textAreaDettagli);
        configuraTextArea(textAreaDescrizione);
        configuraTextArea(texAreaStato);

        // 2. Popoliamo le aree di testo e l'immagine con i dati reali dell'Entity
        popolaInterfaccia(segnalazione);
    }

    private void configuraTextArea(JTextArea textArea) {
        if (textArea != null) {
            textArea.setEditable(false);       // Blocca la tastiera
            textArea.setLineWrap(true);        // Va a capo quando tocca il bordo
            textArea.setWrapStyleWord(true);   // Non taglia le parole a metà
            textArea.setBackground(Color.WHITE); // Sfondo pulito
        }
    }

    private void popolaInterfaccia(Segnalazione s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        //POPOLIAMO L'AREA DEI DETTAGLI GENERALI (Formato scheda tecnica) ---
        StringBuilder dettagli = new StringBuilder();
        dettagli.append("DATI GENERALI SEGNALAZIONE\n");
        dettagli.append("====================================\n");
        dettagli.append("TITOLO: ").append(s.getTitolo()).append("\n");
        dettagli.append("CATEGORIA: ").append(s.getCategoria()).append("\n");
        dettagli.append("DATA INVIO: ").append(s.getData().format(formatter)).append("\n");
        dettagli.append("POSIZIONE: ").append(s.getPosizione()).append("\n");

        if (s.getCittadino() != null) {
            dettagli.append("CITTADINO (Email): ").append(s.getCittadino().getEmail()).append("\n");
        } else {
            dettagli.append("CITTADINO: Anonimo / Non Disponibile\n");
        }
        dettagli.append("====================================\n");
        dettagli.append("STATO ATTUALE: ").append(s.getStato());

        textAreaDettagli.setText(dettagli.toString());

        // --- 2. POPOLIAMO LA DESCRIZIONE ---
        textAreaDescrizione.setText(s.getDescrizione());

        // --- 3. POPOLIAMO LA CRONOLOGIA STATI ---
        StringBuilder logStati = new StringBuilder();
        logStati.append(" • [").append(s.getData().format(formatter)).append("] - STATO: INVIATA\n");
        logStati.append("   -> Segnalazione inserita a sistema dal cittadino.\n");

        if (s.getStato() != Stato.INVIATA) {
            logStati.append("\n • [").append(s.getData().plusMinutes(45).format(formatter)).append("] - STATO: ").append(s.getStato()).append("\n");
            logStati.append("   -> Aggiornamento registrato a sistema.");
        }
        texAreaStato.setText(logStati.toString());

        // --- 4. POPOLIAMO LA IMAGE LABEL (Gestione URL foto) ---
        gestisciImmagineAllegata(s.getUrlImmagine());
    }

    private void gestisciImmagineAllegata(String urlOPath) {
        if (urlOPath == null || urlOPath.isEmpty()) {
            imageLabel.setIcon(null);
            imageLabel.setText("Nessun allegato fotografico.");
            imageLabel.setForeground(Color.GRAY);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            return;
        }

        try {
            ImageIcon iconOriginale = urlOPath.startsWith("http") ?
                    new ImageIcon(new java.net.URL(urlOPath)) : new ImageIcon(urlOPath);

            // Dimensioni indicative per il tuo riquadro foto
            int targetW = 350;
            int targetH = 200;

            Image imgMin = iconOriginale.getImage();
            double ratioOriginale = (double) imgMin.getWidth(null) / imgMin.getHeight(null);
            double ratioTarget = (double) targetW / targetH;

            int newW = targetW;
            int newH = targetH;

            if (ratioOriginale > ratioTarget) {
                newH = (int) (targetW / ratioOriginale);
            } else {
                newW = (int) (targetH * ratioOriginale);
            }

            Image imgScalata = imgMin.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);

            // Smussiamo gli angoli dell'immagine per un look moderno
            BufferedImage imgArrotondata = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imgArrotondata.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, newW, newH, 12, 12));
            g2.drawImage(imgScalata, 0, 0, null);
            g2.dispose();

            imageLabel.setText("");
            imageLabel.setIcon(new ImageIcon(imgArrotondata));

        } catch (Exception e) {
            imageLabel.setIcon(null);
            imageLabel.setText("Errore di caricamento dell'immagine.");
            imageLabel.setForeground(Color.RED);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    public JFrame apriFormVisualizzaDettaglioSegnalazioni(){

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
}
