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
                String ruoloStringa = (String) ruoloRegistrazione.getSelectedItem();
                String nome = nomeTextField.getText();
                String cognome = cognomeTextField.getText();
                String recapitoTelefonico = recapitoTelefonicoTextField.getText();
                String email = emailTextField.getText();
                String password = passwordTextField.getText();

                Registra(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);

            }
        });
    }

    private void printFormRegistrazione(String ruoloStringa, String nome, String cognome, String email, String recapitoTelefonico, String password){
        System.out.println("Ruolo: "+ ruoloStringa);
        System.out.println("Nome: "+nome);
        System.out.println("Cognome: "+cognome);
        System.out.println("Recapito Telefonico: "+recapitoTelefonico);
        System.out.println("Email: "+email);
        System.out.println("Password: "+password);
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

    public boolean Registra(String ruoloStringa, String nome, String cognome, String email, String recapitoTelefonico, String password) {

        printFormRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
        boolean esitoRegistrazione = false;

        //check formato
        try {
            controlloFormatoDatiRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(registrazioneFrame, ex.getMessage());
        }

        try {
            esitoRegistrazione = ControllerUtenti.salvaUtente(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
            if (esitoRegistrazione) {
                registrazioneFrame.dispose();
                if (ruoloStringa.equals("CITTADINO")) {
                    new FormAreaPersonaleCittadino().apriAreaPersonale();
                } else {
                    new FormVisualizzaSegnalazioni().apriFormVisualizzaSegnalazioni();
                }
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(registrazioneFrame, ex.getMessage());
        }
        return  esitoRegistrazione;
    }
}
