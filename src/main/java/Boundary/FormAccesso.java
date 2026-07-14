package Boundary;
import Controller.ControllerUtenti;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gestisce la GUI dedicata all'accesso degli utenti
 * all'applicazione. La classe consente all'utente di inserire le proprie credenziali,
 * verificarne il formato e inoltrare la richiesta di autenticazione
 * al livello Controller. In caso di accesso riuscito viene
 * aperta l'area dell'applicazione corrispondente al ruolo selezionato.
 *
 * @author Antonio Falcone
 * @version 1.0
 */

public class FormAccesso {
    private MainFrame mainFrame;
    private JFrame accessoFrame;
    private JPanel contentPanel;
    private JTextField emailField;
    private JTextField passwordField;
    private JComboBox ruoloAccesso;
    private JButton accediButton;
    private JButton tornaAlMenuPrincipaleButton;

    /**
     * Inizializza la finestra di accesso associando gli ascoltatori
     * agli elementi dell'interfaccia grafica.
     * <p>
     * Il pulsante di accesso acquisisce le credenziali inserite
     * dall'utente e avvia la procedura di autenticazione.
     * Il pulsante di ritorno consente invece di tornare al menu principale.
     *
     */
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
                                new FormAreaPersonaleCittadino().apriAreaPersonaleCittadino();
                            }
                            else{
                                new FormAreaPersonaleOperatore().apriAreaPersonaleOperatore();
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

    /**
     * Visualizza sulla console le credenziali inserite nel form di accesso.
     * <p>
     *  Questo metodo è utilizzato esclusivamente a scopo di debug e verifica
     *  dei dati acquisiti dall'interfaccia grafica.
     *
     * @param ruoloStringa ruolo selezionato dall'utente
     * @param email indirizzo email inserito
     * @param password password inserita
     */
    private void printFormAccesso(String ruoloStringa, String email, String password){
        System.out.println("Ruolo: "+ruoloStringa);
        System.out.println("Mail: "+email);
        System.out.println("Password: "+password);
    }

    /**
     * Verifica che le credenziali inserite nel form di accesso rispettino
     * il formato previsto dall'applicazione.
     *
     * @param ruoloStringa ruolo selezionato dall'utente
     * @param email indirizzo email dell'utente
     * @param password password dell'utente
     *
     * @return {@code true} se le credenziali rispettano il formato previsto,
     *  *       {@code false} altrimenti
     * @throws IllegalArgumentException se almeno uno dei dati inseriti
     *         non rispetta i vincoli di validazione
     */
    private boolean controlloFormatoDatiAccesso (String ruoloStringa, String email, String password) throws IllegalArgumentException {
        boolean controlloFormatoAccesso;
        try {
            controlloFormatoAccesso = CheckDatiFormAccessoRegistrazione.checkDatiFormAccesso(ruoloStringa, email, password);
            return controlloFormatoAccesso;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    /**
     * Crea e visualizza la finestra dedicata all'accesso degli utenti.
     *
     * @return il {@link JFrame} contenente il form di accesso
     */
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

    /**
     * Avvia la procedura di autenticazione dell'utente.
     * <p>
     * Il metodo verifica innanzitutto la validità del formato delle
     * credenziali inserite; se il controllo ha esito positivo,
     * delega al controller la verifica dell'accesso.
     *
     * @param ruoloStringa ruolo selezionato dall'utente
     * @param email indirizzo email dell'utente
     * @param password password dell'utente
     *
     * @return {@code true} se l'accesso viene completato con successo,
     *         {@code false} altrimenti
     *
     * @throws IllegalArgumentException se le credenziali non rispettano
     *           il formato previsto oppure se l'autenticazione non può
     *           essere terminata
     *
     */
    public boolean accedi(String ruoloStringa, String email, String password) throws IllegalArgumentException{

        boolean esitoFormatoAccesso;
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
