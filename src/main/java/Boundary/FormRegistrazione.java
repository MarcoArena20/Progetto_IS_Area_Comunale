package Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormRegistrazione {

    private JPanel contentPanel;
    private JButton registratiButton;

    public FormRegistrazione() {
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Registra();

            }
        });
    }

    public JFrame apriFormRegistrazione(){

        JFrame frame = new JFrame();
        frame.setTitle("RegistraFrame");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

    private void Registra(){

        System.out.println("TODO Implementation register");

    }

}
