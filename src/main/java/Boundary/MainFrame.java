package Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {

    private JPanel mainPanel;
    private JButton accediButton;
    private JButton registratiButton;
    private JFrame accediFrame;
    private JFrame registraFrame;

    public MainFrame(){

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SchermataAccesso();

            }
        });
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SchermataRegistrazione();

            }
        });
    }

    // Metodo di costruzione della JFrame
    public JFrame apriMainFrame(){

        JFrame frame = new JFrame();
        frame.setTitle("MainFrame");
        frame.setContentPane(mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;

    }

    private void SchermataAccesso(){

        // Metodo per la visualizzazione del form di accesso
        accediFrame = new FormAccesso().apriFormAccesso();
        accediFrame.toFront();
        accediFrame.requestFocus();

    }

    private void SchermataRegistrazione(){

        registraFrame = new FormRegistrazione().apriFormRegistrazione();
        registraFrame.toFront();
        registraFrame.requestFocus();

    }

    public static void main(String[] args){

        System.out.println("Avvio dell'applicazione e creazione dell'oggetto MainFrame");

        JFrame frame = new MainFrame().apriMainFrame();

    }
}
