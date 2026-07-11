package Boundary;

import Controller.ControllerUtenti;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormRegistrazione {

    private JPanel contentPanel;
    private JButton registratiButton;
    private JComboBox ruoloRegistrazione;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField recapitoTelefonicoTextField;
    private JTextField passwordTextField;
    private JTextField emailTextField;

    public FormRegistrazione() {
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Registra();

            }
        });
    }

    private void printFormRegistrazione(){
        System.out.println("Ruolo: "+ ruoloRegistrazione.getSelectedItem());
        System.out.println("Nome: "+nomeTextField.getText());
        System.out.println("Cognome: "+cognomeTextField.getText());
        System.out.println("Recapito Telefonico: "+recapitoTelefonicoTextField.getText());
        System.out.println("Email: "+emailTextField.getText());
        System.out.println("Password: "+passwordTextField.getText());
    }

    private boolean controlloFormatoDatiRegistrazione (String ruoloStringa, String nome, String cognome, String email, String recapitoTelefonico, String password){
        boolean esitoFormatoRegistrazione=false;
        try {
            esitoFormatoRegistrazione = CheckDatiForm.checkDatiFormRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
            return  esitoFormatoRegistrazione;
        } catch (IllegalArgumentException ex) {
            // Mostra il motivo del fallimento nel terminale e restituisce false
            System.out.println("Validazione fallita: " + ex.getMessage());
            return esitoFormatoRegistrazione;
        }
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
        String ruoloStringa = (String) ruoloRegistrazione.getSelectedItem();
        String nome = nomeTextField.getText();
        String cognome = cognomeTextField.getText();
        String recapitoTelefonico =recapitoTelefonicoTextField.getText();
        String email= emailTextField.getText();
        String password = passwordTextField.getText();

        printFormRegistrazione();
        boolean esitoRegistrazione =false;
        boolean esitoFormatoRegistrazione;
        esitoFormatoRegistrazione= controlloFormatoDatiRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
        System.out.println("Esito form registrazione: "+esitoFormatoRegistrazione);
        if (esitoFormatoRegistrazione){
            esitoRegistrazione =ControllerUtenti.salvaUtente(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
            System.out.println("Esito Registrazione: "+esitoRegistrazione);
        }
        else{
            return;
            //TODO interfaccia registrazione rifiutata
        }
    }

}
