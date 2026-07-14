package Boundary;

import Controller.ControllerSegnalazioni;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 *  Rappresenta un'interfaccia grafica per la conclusione della gestione di una segnalazione, aggiungendo eventualmente
 *  una nota interna descrittiva del lavoro svolto
 *
 * @author Giuliano Izzo
 * @version 1.0
 *
 */

public class FormConclusioneGestione {

    //Attributi

    /**
     *  frame di conclusione
     */

    private JFrame conclusioneFrame;

    /**
     *  panel per il contenuto del frame
     */

    private JPanel contentPanel;

    /**
     *  button per la conferma e la conclusione della gestione
     */

    private JButton confermaEConcludiButton;

    /**
     *  text field per inserire il titolo della nota
     */

    private JTextField titoloTextField;

    /**
     *  text field per inserire la descrizione della nota
     */

    private JTextField descrizioneTextField;

    /**
     *  button per aggiungere la nota o rimuoverla
     */

    private JButton aggiungiNotaButton;

    /**
     *  checkbox per indicare se la gestione ha esito positivo (e quindi viene Risolta) o se ha esito negativo (e quindi torna in Inviata)
     */

    private JCheckBox gestioneRisolutivaCheckBox;

    /**
     *  label per indicare il text field corrispondente per inserire il titolo della nota
     */

    private JLabel titoloNotaLabel;

    /**
     *  label per indicare il text field corrispondente per inserire la descrizione della nota
     */

    private JLabel descrizioneNotaLabel;

    /**
     *  label per indicare il testo corrispondente allo stato precedente all'eventuale conclusione
     */

    private JLabel statoPrecedenteLabel;

    /**
     *  label per indicare il testo corrispondente allo stato successivo all'eventuale conclusione
     */

    private JLabel statoSuccessivoLabel;

    /**
     *  label per indicare l'effettivo stato precedente all'eventuale conclusione
     */

    private JLabel statoPrecedenteEffettivoLabel;

    /**
     *  label per indicare l'effettivo stato successivo all'eventuale conclusione
     */

    private JLabel statoSuccessivoEffettivoLabel;

    /**
     *  attributo per specificare se si vuole aggiungere la nota (effettuando i controlli su titolo e descrizione inserite) oppure no
     */

    private boolean presenzaNota = false;

    /**
     *  attributo per indicare la riga della segnalazione visualizzata al dettaglio per concluderne la gestione
     */

    private final Integer idRow;


    //Getter e Setter

    /**
     *  getter per restituire il titolo della nota
     *
     * @return stringa rappresentante il titolo
     */

    public String getTitoloNota() {return this.titoloTextField.getText();}

    /**
     *  setter per impostare il titolo della nota, utilizzato in fase di testing
     *
     * @param titolo titolo della nota
     */

    public void setTitoloNota(String titolo) { this.titoloTextField.setText(titolo);}

    /**
     *  getter per restituire la descrizione della nota
     *
     * @return stringa rappresentante la descrizione
     */

    public String getDescrizioneNota() {return this.descrizioneTextField.getText();}

    /**
     *  setter per impostare la descrizione della nota, utilizzato in fase di testing
     *
     * @param descrizione descrizione della nota
     */

    public void setDescrizioneNota(String descrizione) { this.descrizioneTextField.setText(descrizione);}

    /**
     *  getter per restituire la presenza della nota
     *
     * @return true se la nota è presente, false altrimentiß
     */

    public boolean getPresenzaNotsa() { return presenzaNota;}

    /**
     *  setter per specificare la presenza della nota
     *
     * @param presenzaNota booleano che indica la presenza della nota
     */

    public void setPresenzaNota(boolean presenzaNota) { this.presenzaNota = presenzaNota;}


    //Costruttore

    /**
     *  Costruttore per creare ed inizializzare il form, impostando la riga visualizzata al dettaglio e settando i listener ai button
     *
     * @param idRow indice di riga della segnalazione visualizzata al dettaglio, e che si vuole quindi gestire
     */

    public FormConclusioneGestione(Integer idRow) {
        this.idRow = idRow;

        confermaEConcludiButton.addActionListener(new ActionListener() {
            @Override
            /*
             *  metodo eseguito quando si conferma e conclude una gestione: nel caso in cui la conclusione va a buon fine,
             *  si conclude la gestione mostrando un messaggio informativo; altrimenti, si intercettano l'eccezioni gestendole e mostrando un messaggio di errore
             *  (errore nell'inserimento della nota o errore nell'esecuzione di un'azione di cui non si hanno i permessi)
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    if (concludiGestione()) {

                        conclusioneFrame.dispose();
                        JOptionPane.showMessageDialog(conclusioneFrame, "Conclusa la gestione della segnalazione");

                    }

                } catch (IllegalArgumentException | IllegalAccessException exception) {
                    //System.err.println(exception.getMessage());
                    JOptionPane.showMessageDialog(conclusioneFrame, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        aggiungiNotaButton.addActionListener(new ActionListener() {
            @Override
            /*
             *  metodo eseguito quando si vuole aggiungere o rimuovere una nota:
             *  nel primo caso, vengono mostrati i textfield, viene cambiato il nome del button in "Rimuovi nota";
             *  nel secondo caso, vengono nascosti i textfield, viene cambiato il nome del button in "Aggiungi nota" e si "puliscono" i text field.
             */
            public void actionPerformed(ActionEvent e) {

                presenzaNota = !presenzaNota;

                visualizzaCampiNotaInterna(presenzaNota);

                if (presenzaNota) {
                    aggiungiNotaButton.setText("Rimuovi nota");
                } else {
                    aggiungiNotaButton.setText("Aggiungi nota");
                    titoloTextField.setText("");
                    descrizioneTextField.setText("");
                }
            }
        });

        gestioneRisolutivaCheckBox.addActionListener(new ActionListener() {
            /*
             *  metodo eseguito quando si vuole specificare se una gestione è stata risolutiva oppure no:
             *  nel primo caso, la label dello stato successivo viene settata a "Risolta";
             *  nel secondo caso, la label dello stato successivo viene settata a "Inviata".
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gestioneRisolutivaCheckBox.isSelected()) {
                    statoSuccessivoEffettivoLabel.setText("Risolta");
                } else {
                    statoSuccessivoEffettivoLabel.setText("Inviata");
                }
            }
        });
    }

    /**
     *  metodo eseguito quando si vuole inizializzare ed aprire il frame concludiGestione
     *
     * @return  frame di conclusione gestione
     */
    public JFrame apriConclusioneFrame(){

        JFrame frame = new JFrame();
        frame.setTitle("Concludi gestione segnalazione");

        frame.setContentPane(contentPanel);
        frame.setSize(600, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        visualizzaCampiNotaInterna(presenzaNota);

        statoSuccessivoEffettivoLabel.setText("Inviata");

        //Casting della stringa in formato migliore
        String statoPrecedente = ControllerSegnalazioni.getDettagliSegnalazione(this.idRow).get("stato").toLowerCase();
        statoPrecedente = statoPrecedente.substring(0, 1).toUpperCase() + statoPrecedente.substring(1);

        statoPrecedente = statoPrecedente.replace("_", " ");
        statoPrecedenteEffettivoLabel.setText(statoPrecedente);

        conclusioneFrame = frame;

        return frame;
    }

    /**
     *  metodo utilizzato per visualizzare i campi della nota interna
     *
     * @param presenzaNota indica la presenza della nota e quindi la visibilità dei campi
     */

    private void visualizzaCampiNotaInterna(boolean presenzaNota) {
        titoloNotaLabel.setVisible(presenzaNota);
        descrizioneNotaLabel.setVisible(presenzaNota);
        titoloTextField.setVisible(presenzaNota);
        descrizioneTextField.setVisible(presenzaNota);

    }

    /**
     * CASO D'USO: Conclusione Gestione
     * Verifica correttezza titolo e descrizione della nota se la nota è presente (presenzaNota=true) e in caso affermativo
     * contatta il ControllerSegnalazioni per effettuare la conclusione della gestione
     *
     * @return true se la conclusione è andata a buon fine, false altrimenti
     * @throws IllegalArgumentException se uno dei campi non ha rispettato i vincoli della classe @see verificaCampi
     * @throws IllegalAccessException se non si hanno i permessi per effettuare la conclusione specificata della nota visualizzata al dettaglio
     */

    public boolean concludiGestione() throws IllegalArgumentException, IllegalAccessException{
        boolean risolutiva = gestioneRisolutivaCheckBox.isSelected();

        String titolo = titoloTextField.getText();
        String descrizione = descrizioneTextField.getText();

        if (presenzaNota) {

            if (!verificaCampi(titolo, descrizione) ) {

                return false;

            }

        } else {

            titolo = null;
            descrizione = null;

        }

        if (!ControllerSegnalazioni.concludiGestioneSegnalazione(titolo, descrizione, risolutiva) ) {
            throw new IllegalAccessException("Non si hanno i permessi per eseguire questa azione");
        }

        return true;
    }


    /**
     * CASO D'USO: Conclusione Gestione
     * Verifica correttezza titolo e descrizione della nota
     *
     * @return true se la verifica è andata a buon fine
     * @throws IllegalArgumentException se uno dei campi non ha rispettato i vincoli della classe (e quindi se la verifica non è andata a buon fine)
     */

    private boolean verificaCampi(String titolo, String descrizione) throws IllegalArgumentException {
        //Titolo [5,15] caratteri, no caratteri speciali
        //Descrizione [5,200] caratteri, no caratteri speciali

        if (titolo.length() < 5 || titolo.length() > 15) {

            if (titolo.length() < 5) {

                System.err.println("Inserire titolo di almeno 5 caratteri");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Inserire titolo di almeno 5 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Inserire titolo di almeno 5 caratteri");

            } else {

                System.err.println("Inserire titolo di massimo 15 caratteri");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Inserire titolo di massimo 15 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Inserire titolo di massimo 15 caratteri");

            }

        }

        if (descrizione.length() < 5 || descrizione.length() > 200) {

            if (descrizione.length() < 5) {

                System.err.println("Inserire descrizione di almeno 5 caratteri");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Inserire descrizione di almeno 5 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Inserire descrizione di almeno 5 caratteri");

            } else {

                System.err.println("Inserire descrizione di massimo 200 caratteri");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Inserire descrizione di massimo 200 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Inserire descrizione di massimo 200 caratteri");

            }

        }

        //Check caratteri speciali
        for (char c : titolo.toCharArray()) {

            if (!Character.isLetter(c) && c != ' ') {

                System.err.println("Rimuovere caratteri speciali dal titolo");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Rimuovere caratteri speciali dal titolo", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Rimuovere caratteri speciali dal titolo");

            }
        }

        for (char c : descrizione.toCharArray()) {

            if (!Character.isLetter(c) && c != ' ') {

                System.err.println("Rimuovere caratteri speciali dalla descrizione");
                //JOptionPane.showMessageDialog(conclusioneFrame, "Rimuovere caratteri speciali dalla descrizione", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Rimuovere caratteri speciali dalla descrizione");

            }
        }

        return true;
    }

}
