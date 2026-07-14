package Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interfaccia grafica (Boundary) per la visualizzazione dell'area personale per l'operatore.
 *
 * @author Ciorra Alessandro
 * @version 1.0
 */
public class FormAreaPersonaleOperatore {
    private JFrame frame;
    private JButton visualizzaSegnalazioniRicevuteButton;
    private JPanel contentPanel;
    private JLabel BENVENUTOLabel;

    /**
     * Costruisce il form impostando i listener per i pulsanti di navigazione.
     */
    public FormAreaPersonaleOperatore(){

        // Listener per il pulsante Visualizza Segnalazioni Ricevute
        visualizzaSegnalazioniRicevuteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                FormVisualizzaSegnalazioniRicevute visualizzaFrame = new FormVisualizzaSegnalazioniRicevute();
                visualizzaFrame.apriVisualizzaFrame();
            }
        });
    }

    /**
     * Inizializza e rende visibile la finestra principale
     */
    public void apriAreaPersonaleOperatore(){

        frame = new JFrame("Area Personale Operatore");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
