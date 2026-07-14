package Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Punto di accesso del programma.
 * Contiene i metodi per creare i form di accesso e registrazione
 *
 * @author Marco Arena
 * @version 1.0
 */

public class MainFrame {

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton accediButton;
    private JButton registratiButton;

    /**
     * Construttore che inizializza gli actionListener per i pulsanti di accesso e registrazione
     */
    public MainFrame(){

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mainFrame.dispose();
                SchermataAccesso();

            }
        });
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mainFrame.dispose();
                SchermataRegistrazione();

            }
        });
    }

    /**
     * Inizializzazione del main frame
     */

    public void apriMainFrame(){

        JFrame frame = new JFrame();
        frame.setTitle("MainFrame");
        frame.setContentPane(mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mainFrame = frame;

    }

    /**
     * Metodo per la visualizzazione del form di registrazione
     */

    private void SchermataAccesso(){

        JFrame accediFrame = new FormAccesso().apriFormAccesso();
        accediFrame.toFront();
        accediFrame.requestFocus();

    }

    /**
     * Metodo per la visualizzazione del form di registrazione
     */

    private void SchermataRegistrazione(){

        JFrame registraFrame = new FormRegistrazione().apriFormRegistrazione();
        registraFrame.toFront();
        registraFrame.requestFocus();

    }

    /**
     * Main di avvio per il software
     * @param args argomenti da riga di comando
     */

    public static void main(String[] args){

        System.out.println("Avvio dell'applicazione e creazione dell'oggetto MainFrame");
        new MainFrame().apriMainFrame();

    }
    
}
