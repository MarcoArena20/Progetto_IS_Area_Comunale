package Boundary;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FormAccesso {

    private JPanel contentPanel;
    private JButton accessButton;
    private JTextField emailField;
    private JTextField passwordField;

    public FormAccesso() {
        accessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Accedi();
            }
        });
    }

    public JFrame apriFormAccesso(){

        JFrame frame = new JFrame();
        frame.setTitle("AccediFrame");
        frame.setContentPane(contentPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

    private void Accedi(){

        System.out.println("TODO Implementation access");

    }

}
