import Boundary.FormConclusioneGestione;
import Boundary.FormCreazioneSegnalazione;
import Boundary.FormVisualizzaDettaglioSegnalazioneRicevuta;

import Boundary.FormVisualizzaSegnalazioniRicevute;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreazioneNotaTest {

    private FormConclusioneGestione form;

    @BeforeAll
    public static void setUp() {

        //TODO inizializzazione database con segnalazione, cittadino e operatore

        new FormCreazioneSegnalazione().creaSegnalazione("Discarica",
                "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                "RIFIUTI_ABBANDONATI",
                "Viale delle mimose", "", "");
        new FormVisualizzaSegnalazioniRicevute().apriVisualizzaFrame();

    }

    @BeforeEach
    public void prendiInCaricoSegnalazione() {

        FormVisualizzaDettaglioSegnalazioneRicevuta formVisualizzaDettaglioSegnalazioneRicevuta = new FormVisualizzaDettaglioSegnalazioneRicevuta(1);

        formVisualizzaDettaglioSegnalazioneRicevuta.btnPrendiInCarico.doClick();

        form = new FormConclusioneGestione(1);
        form.apriConclusioneFrame();

        form.aggiungiNotaButton.doClick();

    }

    @Test
    public void tuttiInputValidi() {
        form.titoloTextField.setText("Risolta");
        form.descrizioneTextField.setText("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito = form.concludiGestione();

        assertTrue(esito);
    }

    @Test
    public void titoloLungo() {
        form.titoloTextField.setText("La Segnalazione è stata risolta");
        form.descrizioneTextField.setText("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito = form.concludiGestione();

        assertFalse(esito);
    }

    @Test
    public void titoloCorto() {
        form.titoloTextField.setText("Ok");
        form.descrizioneTextField.setText("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito = form.concludiGestione();

        assertFalse(esito);
    }

    @Test
    public void titoloSpeciale() {
        form.titoloTextField.setText("Ris@lta");
        form.descrizioneTextField.setText("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito = form.concludiGestione();

        assertFalse(esito);
    }

    @Test
    public void descrizioneLunga() {
        form.titoloTextField.setText("Check parziale");
        form.descrizioneTextField.setText("La segnalazione è stata ricevuta, verificata e inoltrata agli enti competenti. Sono state predisposte misure cautelari per la risoluzione del problema; saranno obbligatori altri interventi di controllo");

        boolean esito = form.concludiGestione();

        assertFalse(esito);
    }

    @Test
    public void descrizioneCorta() {
        form.titoloTextField.setText("Risolta");
        form.descrizioneTextField.setText("Ok");

        boolean esito = form.concludiGestione();

        assertFalse(esito);
    }

    @Test
    public void descrizioneSpeciale() {
        form.titoloTextField.setText("Risolta");
        form.descrizioneTextField.setText("Segnalazione gestita! correttamente#");

        boolean esito = form.concludiGestione();

        assertFalse(esito);
    }
}
