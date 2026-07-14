package Boundary;

import Controller.ControllerUtenti;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gestisce l'interfaccia grafica dedicata alla registrazione di un nuovo utente.
 * La classe permette all'utente di inserire i dati necessari alla registrazione,
 * verificarne il formato, inoltrare la richiesta al livello Controller
 * e, in caso di successo, aprire la schermata corrispondente al ruolo selezionato.
 *
 * @author Antonio Falcone
 * @version 1.0
 */
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

    /**
     * Inizializza la finestra di registrazione associando i listener agli elementi della GUI
     * Il pulsante registrazione prende i parametri in ingresso dalla GUI
     * Il pulsante tornaAlMenuPrincipale permette di tornare alla schermata iniziale
     */
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
                                new FormAreaPersonaleCittadino().apriAreaPersonaleCittadino();
                            } else {
                                new FormAreaPersonaleOperatore().apriAreaPersonaleOperatore();
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

    /**
     * Visualizza sulla console i dati inseriti nel form di registrazione.
     * Questo metodo è utilizzato esclusivamente a scopo di debug e verifica
     * dei dati acquisiti dall'interfaccia grafica.
     *
     * @param ruoloStringa ruolo selezionato dall'utente
     * @param nome nome inserito
     * @param cognome cognome inserito
     * @param email indirizzo email inserito
     * @param recapitoTelefonico recapito telefonico inserito
     * @param password password inserita
     */
    private void printFormRegistrazione(String ruoloStringa, String nome, String cognome, String email, String recapitoTelefonico, String password){
        System.out.println("Ruolo: "+ ruoloStringa);
        System.out.println("Nome: "+nome);
        System.out.println("Cognome: "+cognome);
        System.out.println("Recapito Telefonico: "+recapitoTelefonico);
        System.out.println("Email: "+email);
        System.out.println("Password: "+password);
    }

    /**
     * Verifica che i dati inseriti nel form di registrazione rispettino
     * il formato previsto dall'applicaione
     *
     * @param ruoloStringa ruolo selezionato dall'utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param email indirizzo email dell'utente
     * @param recapitoTelefonico recapito telefonico dell'utente
     * @param password password scelta dall'utente
     *
     * @return {@code true} se tutti i dati rispettano il formato previsto,
     *          {@code false} altriementi.
     *
     * @throws IllegalArgumentException se almeno uno dei dati inseriti
     *         non rispetta i vincoli di validazione
     *
     */
    private boolean controlloFormatoDatiRegistrazione (String ruoloStringa, String nome, String cognome, String email, String recapitoTelefonico, String password)throws IllegalArgumentException{
        boolean controlloFormatoRegistrazione;
        try {
            controlloFormatoRegistrazione = CheckDatiFormAccessoRegistrazione.checkDatiFormRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
            return  controlloFormatoRegistrazione;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    /**
     * Crea e visualizza la finestra dedicata alla registrazione di un nuovo utente.
     *
     * @return il {@link JFrame} form della registrazione
     */
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

    /**
     * Avvia la procedura di registrazione di un nuovo utente.
     * Il metodo verifica innanzitutto la validità del formato dei dati
     * inseriti; se il controllo ha esito positivo, delega al controller
     * il salvataggio del nuovo utente.
     *
     *
     * @param ruoloStringa ruolo selezionato dall'utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param email indirizzo email dell'utente
     * @param recapitoTelefonico recapito telefonico dell'utente
     * @param password password scelta dall'utente
     * @return {@code true} se la registrazione viene completata con successo,
     *         {@code false} altrimenti
     *         {@code false} altrimenti
     *
     * @throws IllegalArgumentException se i dati non rispettano il formato previsto
     *         oppure se il processo di registrazione non può essere completato
     */
    public boolean registra(String ruoloStringa, String nome, String cognome, String email, String recapitoTelefonico, String password) throws IllegalArgumentException {

        printFormRegistrazione(ruoloStringa, nome, cognome, email, recapitoTelefonico, password);
        boolean esitoFormatoRegistrazione;
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
