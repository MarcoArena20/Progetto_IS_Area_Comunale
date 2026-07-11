package Boundary;
import Controller.ControllerUtenti;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FormAccesso {

    private JFrame accessoFrame;
    private JPanel contentPanel;
    private JButton accessButton;
    private JTextField emailField;
    private JTextField passwordField;
    private JComboBox ruoloAccesso;

    public FormAccesso() {
        accessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Accedi();
            }
        });
    }
    private boolean controlloFormatoDatiAccesso (String ruoloStringa, String email, String password) throws  IllegalArgumentException{
        boolean esitoFormAccesso=false;
        try{
            esitoFormAccesso = CheckDatiForm.checkDatiFormAccesso(ruoloStringa, email, password);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return esitoFormAccesso;
    }
    private void printFormAccesso(){
        System.out.println("Ruolo: "+ruoloAccesso.getSelectedItem());
        System.out.println("Mail: "+emailField.getText());
        System.out.println("Password: "+passwordField.getText());
    }

    public JFrame apriFormAccesso(){

        accessoFrame = new JFrame();
        accessoFrame.setTitle("AccediFrame");
        accessoFrame.setContentPane(contentPanel);

        accessoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accessoFrame.setResizable(true);
        accessoFrame.pack();
        accessoFrame.setLocationRelativeTo(null);
        accessoFrame.setVisible(true);



        return accessoFrame;
    }

    private void Accedi() {
        String ruoloStringa = (String) ruoloAccesso.getSelectedItem();
        String email = emailField.getText();
        String password = passwordField.getText();

        printFormAccesso();
        boolean esitoAccesso = false;
        boolean esitoFormatoAccesso = false;

        try {
            esitoFormatoAccesso = controlloFormatoDatiAccesso(ruoloStringa, email, password);
            if (esitoFormatoAccesso) {
                esitoAccesso = ControllerUtenti.accessoUtente(ruoloStringa, email, password);

                if(ruoloStringa.equals("CITTADINO"))
                    new FormAreaPersonaleCittadino().apriAreaPersonale();
                else if(ruoloStringa.equals("OPERATORE"))
                    new FormVisualizzaSegnalazioniRicevute().apriVisualizzaFrame();

                accessoFrame.dispose();
                System.out.println("Esito Registrazione: " + esitoAccesso);
            } else {
                JOptionPane.showMessageDialog(null, "Email o password errati.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
