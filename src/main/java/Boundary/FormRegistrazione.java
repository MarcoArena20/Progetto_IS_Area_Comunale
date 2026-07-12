package Boundary;

import Controller.ControllerUtenti;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormRegistrazione {
    private MainFrame mainFrame;
    private JFrame registrazioneFrame;
    private JPanel contentPanel;
    private JButton registratiButton;
    private JComboBox ruoloRegistrazione;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField recapitoTelefonicoTextField;
    private JTextField passwordTextField;
    private JTextField emailTextField;
    private JButton tornaAlMenuPrincipaleButton;

    public FormRegistrazione() {
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (registrazioneFrame != null) {
                        String ruoloStringa = (String) ruoloRegistrazione.getSelectedItem();
                        String nome = nomeTextField.getText();
                        String cognome = cognomeTextField.getText();
                        String recapitoTelefonico = recapitoTelefonicoTextField.getText();
                        String email = emailTextField.getText();
                        String password = passwordTextField.getText();

                        boolean esito = registra(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
                        if (esito) {
                            if (ruoloStringa.equals("CITTADINO")) {
                                new FormAreaPersonaleCittadino().apriAreaPersonale();
                            } else {
                                new FormVisualizzaSegnalazioniRicevute().apriVisualizzaFrame();
                            }
                            registrazioneFrame.dispose();
                        }
                    } else {
                        registrazioneFrame = apriFormRegistrazione();
                    }
                }
                catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(registrazioneFrame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        tornaAlMenuPrincipaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame = new MainFrame();
                mainFrame.apriMainFrame();
                registrazioneFrame.dispose();
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
            controlloFormatoRegistrazione = CheckDatiFormAccessoRegistrazione.checkDatiFormRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
            return  controlloFormatoRegistrazione;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public JFrame apriFormRegistrazione(){

        registrazioneFrame = new JFrame();
        registrazioneFrame.setTitle("Registrazione");
        registrazioneFrame.setContentPane(contentPanel);
        registrazioneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registrazioneFrame.setResizable(true);
        registrazioneFrame.pack();
        registrazioneFrame.setLocationRelativeTo(null);
        registrazioneFrame.setVisible(true);


        return registrazioneFrame;
    }

    public boolean registra(String ruoloStringa, String nome, String cognome, String email, String recapitoTelefonico, String password) throws IllegalArgumentException {

        printFormRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
        boolean esitoFormatoRegistrazione = false;
        boolean esitoRegistrazione = false;

        //check formato
        try {
            esitoFormatoRegistrazione = controlloFormatoDatiRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        if (esitoFormatoRegistrazione){
            try {
                esitoRegistrazione = ControllerUtenti.salvaUtente(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(ex.getMessage());
            }
        }
        return esitoRegistrazione;
    }
}
