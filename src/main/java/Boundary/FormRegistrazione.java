package Boundary;

import Controller.ControllerUtenti;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormRegistrazione {

    private JFrame registrazioneFrame;
    private JPanel contentPanel;
    private JButton registratiButton;
    private JComboBox ruoloRegistrazione;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField recapitoTelefonicoTextField;
    private JTextField passwordTextField;
    private JTextField emailTextField;

    private JFrame FormVisualizzaSegnalazioniRicevute;

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

    private boolean controlloFormatoDatiRegistrazione (String ruoloStringa, String nome, String cognome, String email, String recapitoTelefonico, String password)throws IllegalArgumentException{
        boolean controlloFormatoRegistrazione=false;
        try {
            controlloFormatoRegistrazione = CheckDatiForm.checkDatiFormRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
            return  controlloFormatoRegistrazione;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public JFrame apriFormRegistrazione(){

        registrazioneFrame = new JFrame();
        registrazioneFrame.setTitle("RegistraFrame");
        registrazioneFrame.setContentPane(contentPanel);
        registrazioneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registrazioneFrame.setResizable(true);
        registrazioneFrame.pack();
        registrazioneFrame.setLocationRelativeTo(null);
        registrazioneFrame.setVisible(true);


        return registrazioneFrame;
    }

    private void Registra() {
        String ruoloStringa = (String) ruoloRegistrazione.getSelectedItem();
        String nome = nomeTextField.getText();
        String cognome = cognomeTextField.getText();
        String recapitoTelefonico = recapitoTelefonicoTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        printFormRegistrazione();
        boolean esitoFormatoRegistrazione = false;
        boolean esitoRegistrazione= false;

        try{
            esitoFormatoRegistrazione = controlloFormatoDatiRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
            if (esitoFormatoRegistrazione) {
                esitoRegistrazione = ControllerUtenti.salvaUtente(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
                registrazioneFrame.dispose();
                System.out.println("Esito Registrazione: " + esitoRegistrazione);
            } else {
                JOptionPane.showMessageDialog(registrazioneFrame, "Impossibile registrarsi con le suddette credenziali!");
            }
        }
        catch (IllegalArgumentException ex){
            JOptionPane.showMessageDialog(registrazioneFrame, ex.getMessage());
        }


        if (esitoRegistrazione) {

            registrazioneFrame.dispose();
            if (ruoloStringa.equals("CITTADINO")) {

                new FormAreaPersonaleCittadino().apriAreaPersonale();

            } else if (ruoloStringa.equals("OPERATORE")){
                new FormVisualizzaSegnalazioniRicevute().apriVisualizzaFrame();
            }
        }
    }

}
