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
                try {
                    if (accessoFrame != null) {
                        String ruoloStringa = (String) ruoloAccesso.getSelectedItem();
                        String email = emailField.getText();
                        String password = passwordField.getText();

                        boolean esito = Accedi(ruoloStringa, email, password);
                        if (esito) {
                            accessoFrame.dispose();
                        }
                    } else {
                        accessoFrame = apriFormAccesso();
                    }
                }
                catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private boolean controlloFormatoDatiAccesso (String ruoloStringa, String email, String password) throws  IllegalArgumentException{
        boolean esitoFormAccesso=false;
        try{
            esitoFormAccesso = CheckDatiFormAccessoRegistrazione.checkDatiFormAccesso(ruoloStringa, email, password);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return esitoFormAccesso;
    }
    private void printFormAccesso(String ruoloStringa, String email, String password){
        System.out.println("Ruolo: "+ruoloStringa);
        System.out.println("Mail: "+email);
        System.out.println("Password: "+password);
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

    public boolean Accedi(String ruoloStringa, String email, String password) throws IllegalArgumentException{

        boolean esitoFormatoAccesso=false;
        boolean esitoAccesso = false;
        printFormAccesso(ruoloStringa, email, password);


        try{
            esitoFormatoAccesso = controlloFormatoDatiAccesso(ruoloStringa, email, password);
        }
        catch (IllegalArgumentException ex){
            throw new IllegalArgumentException(ex.getMessage());
        }

        if (esitoFormatoAccesso){
            try {
                esitoAccesso = ControllerUtenti.accessoUtente(ruoloStringa, email, password);
                if (esitoAccesso) {
                    if (ruoloStringa.equals("CITTADINO")) {
                        new FormAreaPersonaleCittadino().apriAreaPersonale();
                    }
                    else{
                        new FormVisualizzaSegnalazioniRicevute().apriVisualizzaFrame();
                    }
                }
            }
            catch (IllegalArgumentException ex){
                throw new IllegalArgumentException(ex.getMessage());
            }
        }
        return esitoAccesso;
    }

}
