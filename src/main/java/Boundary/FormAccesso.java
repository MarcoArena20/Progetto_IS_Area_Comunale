package Boundary;
import Controller.ControllerUtenti;

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
                Accedi();
            }
        });
    }
    private boolean controlloFormatoDatiAccesso (String ruoloStringa, String email, String password){
        boolean esitoFormAccesso=false;
        try{
            esitoFormAccesso = CheckDatiForm.checkDatiFormAccesso(ruoloStringa, email, password);
        } catch (IllegalArgumentException ex) {
            // Mostra il motivo del fallimento nel terminale e restituisce false
            System.out.println("Validazione fallita: " + ex.getMessage());
            return esitoFormAccesso;
        }
        return esitoFormAccesso;
    }
    private void printFormAccesso(){
        System.out.println("Ruolo: "+ruoloAccesso.getSelectedItem());
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

    private void Accedi(){
        String ruoloStringa = (String) ruoloAccesso.getSelectedItem();
        String email= emailField.getText();
        String password = passwordField.getText();

        printFormAccesso();
        boolean esitoAccesso=false;
        boolean esitoFormatoAccesso=false;

        esitoFormatoAccesso = controlloFormatoDatiAccesso(ruoloStringa, email, password);
        if (esitoFormatoAccesso){
            esitoAccesso = ControllerUtenti.accessoUtente(ruoloStringa, email, password);
        }
        else{
            return;
            //TODO interfaccia accesso rifiutato
        }


        System.out.println("Esito formato accesso: "+esitoFormatoAccesso);
        System.out.println("Esito accesso: "+esitoAccesso);

    }

}
