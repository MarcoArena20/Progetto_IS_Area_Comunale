package Boundary;
import Controller.ControllerUtenti;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FormAccesso {
    private MainFrame mainFrame;
    private JFrame accessoFrame;
    private JPanel contentPanel;
    private JTextField emailField;
    private JTextField passwordField;
    private JComboBox ruoloAccesso;
    private JButton accediButton;
    private JButton tornaAlMenuPrincipaleButton;

    public FormAccesso() {
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (accessoFrame != null) {
                        String ruoloStringa = (String) ruoloAccesso.getSelectedItem();
                        String email = emailField.getText();
                        String password = passwordField.getText();

                        boolean esito = accedi(ruoloStringa, email, password);
                        if (esito) {
                            if (ruoloStringa.equals("CITTADINO")) {
                                new FormAreaPersonaleCittadino().apriAreaPersonale();
                            }
                            else{
                                new FormVisualizzaSegnalazioniRicevute().apriVisualizzaFrame();
                            }
                            accessoFrame.dispose();
                        }
                    } else {
                        accessoFrame = apriFormAccesso();
                    }
                }
                catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(accessoFrame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        tornaAlMenuPrincipaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame = new MainFrame();
                mainFrame.apriMainFrame();
                accessoFrame.dispose();
            }
        });
    }

    private void printFormAccesso(String ruoloStringa, String email, String password){
        System.out.println("Ruolo: "+ruoloStringa);
        System.out.println("Mail: "+email);
        System.out.println("Password: "+password);
    }

    private boolean controlloFormatoDatiAccesso (String ruoloStringa, String email, String password) throws IllegalArgumentException {
        boolean controlloFormatoAccesso = false;
        try {
            controlloFormatoAccesso = CheckDatiFormAccessoRegistrazione.checkDatiFormAccesso(ruoloStringa, email, password);
            return controlloFormatoAccesso;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }


    public JFrame apriFormAccesso(){

        accessoFrame = new JFrame();
        accessoFrame.setTitle("Accesso");
        accessoFrame.setContentPane(contentPanel);

        accessoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accessoFrame.setResizable(true);
        accessoFrame.pack();
        accessoFrame.setLocationRelativeTo(null);
        accessoFrame.setVisible(true);

        return accessoFrame;
    }

    public boolean accedi(String ruoloStringa, String email, String password) throws IllegalArgumentException{

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
            }
            catch (IllegalArgumentException ex){
                throw new IllegalArgumentException(ex.getMessage());
            }
        }
        return esitoAccesso;
    }

}
