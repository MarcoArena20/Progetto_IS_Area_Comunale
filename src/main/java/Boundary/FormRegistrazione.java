package Boundary;

import Controller.ControllerUtenti;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormRegistrazione {

    private JPanel contentPanel;
    private JButton registratiButton;
    private JComboBox ruoloBox;
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
        System.out.println("Ruolo: "+ruoloBox.getSelectedItem());
        System.out.println("Nome: "+nomeTextField.getText());
        System.out.println("Cognome: "+cognomeTextField.getText());
        System.out.println("Recapito Telefonico: "+recapitoTelefonicoTextField.getText());
        System.out.println("Email: "+emailTextField.getText());
        System.out.println("Password: "+passwordTextField.getText());
    }

    private boolean controlloFormatoDatiRegistrazione (String ruolo, String nome, String cognome, String recapitoTelefonico, String email, String password){
        try {
            // Controllo del ruolo selezionato: Cittadino || Operatore Comunale
            if (!ruolo.equals("CITTADINO") && !ruolo.equals("OPERATORE")) {
                throw new IllegalArgumentException("Il ruolo selezionato non è valido. Scegliere 'Cittadino' o 'Operatore Comunale'.");
            }

            // Controllo Nome: Lettere, Lunghezza: 3-20
            if (nome == null || nome.length() < 3 || nome.length() > 20) {
                throw new IllegalArgumentException("La lunghezza del nome deve essere compresa tra 3 e 20 caratteri.");
            }
            for (char c : nome.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ') {
                    throw new IllegalArgumentException("Il nome contiene numeri o caratteri speciali non consentiti.");
                }
            }

            // Controllo Cognome: Lettere, Lunghezza: 3-20
            if (cognome == null || cognome.length() < 3 || cognome.length() > 20) {
                throw new IllegalArgumentException("La lunghezza del cognome deve essere compresa tra 3 e 20 caratteri.");
            }
            for (char c : cognome.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ') {
                    throw new IllegalArgumentException("Il cognome contiene numeri o caratteri speciali non consentiti.");
                }
            }

            // Controllo recapito telefonico: Solo numeri, Lunghezza = 10
            if (recapitoTelefonico == null || recapitoTelefonico.length() != 10) {
                throw new IllegalArgumentException("Il recapito telefonico deve essere lungo esattamente 10 cifre.");
            }
            for (char c : recapitoTelefonico.toCharArray()) {
                if (!Character.isDigit(c)) {
                    throw new IllegalArgumentException("Il recapito telefonico contiene lettere o caratteri non numerici.");
                }
            }

            // Controllo mail: Lunghezza 7-50, una sola '@', almeno un '.'
            if (email == null || email.length() < 7 || email.length() > 50) {
                throw new IllegalArgumentException("La lunghezza dell'email deve essere compresa tra 7 e 50 caratteri.");
            }
            int chiocciole = 0;
            for (char c : email.toCharArray()) {
                if (c == '@') chiocciole++;
            }
            if (chiocciole != 1 || !email.contains(".")) {
                throw new IllegalArgumentException("L'email non contiene una chiocciola '@' o manca del punto identificativo del dominio.");
            }

            // Controllo password: Lunghezza 8-25, almeno 1 Maiuscola, 1 Minuscola, 1 Numero, 1 Speciale [!?-@%]
            if (password == null || password.length() < 8 || password.length() > 25) {
                throw new IllegalArgumentException("La lunghezza della password deve essere compresa tra 8 e 25 caratteri.");
            }

            // Controllo dei criteri di sicurezza della password (maiuscole, minuscole, numeri e speciali)
            boolean haMaiuscola = false;
            boolean haMinuscola = false;
            boolean haNumero = false;
            boolean haSpeciale = false;
            String specialiConsentiti = "!?-@%";

            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    haMaiuscola = true;
                } else if (Character.isLowerCase(c)) {
                    haMinuscola = true;
                } else if (Character.isDigit(c)) {
                    haNumero = true;
                } else if (specialiConsentiti.indexOf(c) != -1) {
                    haSpeciale = true;
                } else {
                    throw new IllegalArgumentException("La password contiene caratteri speciali non ammessi. Usa solo: !, ?, -, @, %");
                }
            }

            if (!haMaiuscola || !haMinuscola || !haNumero || !haSpeciale) {
                throw new IllegalArgumentException("La password non soddisfa i requisiti minimi: deve contenere almeno una maiuscola, una minuscola, un numero e un carattere speciale.");
            }

            // Se tutti i controlli passano senza lanciare eccezioni
            return true;

        } catch (IllegalArgumentException ex) {
            // Mostra il motivo del fallimento nel terminale e restituisce false
            System.out.println("Validazione fallita: " + ex.getMessage());
            return false;
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
        String ruoloStringa = (String) ruoloBox.getSelectedItem();
        String cognome = cognomeTextField.getText();
        String nome = nomeTextField.getText();
        String recapitoTelefonico =recapitoTelefonicoTextField.getText();
        String email= emailTextField.getText();
        String password = passwordTextField.getText();

        printFormRegistrazione();
        boolean esitoRegistrazione=false;
        boolean esitoFormatoRegistrazione = controlloFormatoDatiRegistrazione((String) ruoloBox.getSelectedItem(),
                                                                                nomeTextField.getText(),
                                                                                cognomeTextField.getText(),
                                                                                recapitoTelefonicoTextField.getText(),
                                                                                emailTextField.getText(),
                                                                                passwordTextField.getText());

        esitoRegistrazione = ControllerUtenti.salvaUtente(ruoloStringa, nome,cognome,recapitoTelefonico,email,password);
        System.out.println("Esito form registrazione: "+esitoFormatoRegistrazione);
        System.out.println("Esito Registrazione: "+esitoRegistrazione);

    }

}
