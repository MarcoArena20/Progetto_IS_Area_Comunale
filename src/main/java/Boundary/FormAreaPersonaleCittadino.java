package Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormAreaPersonaleCittadino {

    private JFrame areaPersonale;
    private JPanel contentPanel;
    private JButton visualizzaButton;
    private JButton creaSegnalazioneButton;

    public FormAreaPersonaleCittadino(){


        visualizzaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Visualizza segnalazioni inviate");
            }
        });
        creaSegnalazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaPersonale.dispose();
                new FormCreazioneSegnalazione().apriCreazioneFrame();
            }
        });
    }

    public JFrame apriAreaPersonale(){

        JFrame frame = new JFrame();
        frame.setTitle("CreazioneFrame");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        areaPersonale = frame;

        return frame;

    }
}
