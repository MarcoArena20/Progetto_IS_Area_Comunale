import Boundary.FormConclusioneGestione;
import Boundary.FormCreazioneSegnalazione;
import Boundary.FormVisualizzaDettaglioSegnalazioneRicevuta;

import Boundary.FormVisualizzaSegnalazioniRicevute;
import Controller.ControllerSegnalazioni;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreazioneNotaTest {

    private FormConclusioneGestione form;
    private static Integer idRow;

    //Il db si presuppone vuoto in fase di testing => si crea un operatore, un cittadino e una segnalazione (il cui id sarà 1);
    //poiché ci sarà una segnalazione, anche l'idRow sarà 1

    @BeforeAll
    public static void setUp() {

        //TODO inizializzazione database con segnalazione, cittadino e operatore

        new FormCreazioneSegnalazione().creaSegnalazione("Discarica",
                "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                "RIFIUTI_ABBANDONATI",
                "Centro Storico: Via dei Tribunali 120", "", "");
        //new FormVisualizzaSegnalazioniRicevute().apriVisualizzaFrame();

        ControllerSegnalazioni.caricaSegnalazioni();
        ControllerSegnalazioni.setIdSegnalazioneCorrente(1L);

        idRow = Integer.parseInt(ControllerSegnalazioni.getIdSegnalazioneCorrente().toString());

    }

    @BeforeEach
    public void prendiInCaricoSegnalazione() {
        /*Metodo necessario poiché la precondizione per terminare una gestione e averla in carico;
        * Si nota che nel caso in cui venga stampato su terminale:
        *
        *[GestoreSegnalazioni] Impossibile prendere in carico la segnalazione, stato:PRESA_IN_CARICO
        *
        * è da considerarsi normale flusso di esecuzione visto che, in caso di errore in conclusione con aggiunta nota,
        * la conclusione della gestione non va a buon fine e quindi l'operatore starà ancora gestendo quella operazione
        */

        //Il boundary non fa altro che chiamare questa funzione al premere del button prendi in carico;
        // -> di conseguenza il test parte dal controller
        ControllerSegnalazioni.iniziaGestioneSegnalazione();

        form = new FormConclusioneGestione(1);

        form.presenzaNota = true;

    }

    @Test
    public void tuttiInputValidi() {
        form.titoloTextField.setText("Risolta");
        form.descrizioneTextField.setText("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito;

        try {
            esito = form.concludiGestione();
        } catch (IllegalArgumentException exception) {
            esito = false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        assertTrue(esito);
    }

    @Test
    public void titoloLungo() {
        form.titoloTextField.setText("La Segnalazione è stata risolta");
        form.descrizioneTextField.setText("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito;

        try {
            esito = form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {

            esito = false;

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }

    @Test
    public void titoloCorto() {
        form.titoloTextField.setText("Ok");
        form.descrizioneTextField.setText("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito;

        try {
            esito = form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {
            esito = false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }

    @Test
    public void titoloSpeciale() {
        form.titoloTextField.setText("Ris@lta");
        form.descrizioneTextField.setText("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito;

        try {
            esito = form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {
            esito = false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }

    @Test
    public void descrizioneLunga() {
        form.titoloTextField.setText("Check parziale");
        form.descrizioneTextField.setText("La segnalazione è stata ricevuta, verificata e inoltrata agli enti competenti. Sono state predisposte misure cautelari per la risoluzione del problema; saranno obbligatori altri interventi di controllo");

        boolean esito;

        try {
            esito = form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {
            esito = false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }

    @Test
    public void descrizioneCorta() {
        form.titoloTextField.setText("Risolta");
        form.descrizioneTextField.setText("Ok");

        boolean esito;

        try {
            esito = form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {
            esito = false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }

    @Test
    public void descrizioneSpeciale() {
        form.titoloTextField.setText("Risolta");
        form.descrizioneTextField.setText("Segnalazione gestita! correttamente#");

        boolean esito;

        try {
            esito = form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {
            esito = false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }
}
