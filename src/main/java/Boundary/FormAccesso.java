package Boundary;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FormAccesso {

    private JPanel contentPanel;
    private JButton accessButton;
    private JTextField emailField;
    private JTextField passwordField;
    private JComboBox ruoloAccesso;

    public FormAccesso() {
        accessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Acccedi();
            }
        });
    }
    private void printFormAccesso(){
        System.out.println("Mail: "+ruoloAccesso.getSelectedItem());
        System.out.println("Mail: "+emailField.getText());
        System.out.println("Password: "+passwordField.getText());
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

    /*private void Accedi(){
        printFormRegistrazione();
        boolean esitoFormatoRegistrazione = controlloFormatoDatiAccesso((String) ruoloBox.getSelectedItem(),
                                                                                emailTextField.getText(),
                                                                                passwordTextField.getText());
        System.out.println("Esito form registrazione: "+esitoFormatoRegistrazione);
        System.out.println("TODO Implementation access");

    }*/

}
