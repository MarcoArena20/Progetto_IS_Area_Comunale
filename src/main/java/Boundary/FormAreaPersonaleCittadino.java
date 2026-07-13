package Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interfaccia grafica (Boundary) per la visualizzazione dell'area personale per il Cittadino.
 *
 * @author Ciorra Alessandro
 * @version 1.0
 */
public class FormAreaPersonaleCittadino {

    private JFrame areaPersonale;
    private JPanel contentPanel;
    private JButton visualizzaButton;
    private JButton creaSegnalazioneButton;

    /**
     * Costruisce il form impostando i listener per i pulsanti di navigazione.
     */
    public FormAreaPersonaleCittadino(){

        //Listener per il pulsante visualizza
        visualizzaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaPersonale.dispose();
                FormVisualizzaSegnalazioniInviate visualizzaFrame = new FormVisualizzaSegnalazioniInviate();
                visualizzaFrame.apriFormVisualizzaSegnalazioni();
            }
        });

        //Listener per il pulsante creazione
        creaSegnalazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaPersonale.dispose();
                new FormCreazioneSegnalazione().apriCreazioneFrame();
            }
        });
    }

    /**
     * Inizializza e rende visibile la finestra principale
     *
     * @return l'istanza del JFrame configurato e visualizzato a schermo
     */
    public JFrame apriAreaPersonaleCittadino(){

        areaPersonale = new JFrame("CreazioneFrame");
        areaPersonale.setContentPane(contentPanel);

        areaPersonale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        areaPersonale.setResizable(false);
        areaPersonale.pack();
        areaPersonale.setLocationRelativeTo(null);
        areaPersonale.setVisible(true);

        return areaPersonale;

    }
}
