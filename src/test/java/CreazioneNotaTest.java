import Boundary.FormConclusioneGestione;
import Boundary.FormCreazioneSegnalazione;

import Boundary.FormRegistrazione;
import Controller.ControllerSegnalazioni;
import Controller.ControllerUtenti;
import Database.GestorePersistenza;
import Entity.Enum.Ruolo;
import Entity.Operatore;
import Entity.Segnalazione;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * CASO D'USO: aggiungiNotaInterna
 * Classe di test per verificare il corretto funzionamento del caso d'uso aggiungiNotaInterna
 */

public class CreazioneNotaTest {

    /**
     * Attributo per far riferimento al form corrispondente
     */

    private FormConclusioneGestione form;

    /**
     * Attributo per indicare la riga che si sta visualizzando al dettaglio (e quindi la segnalazione corrispondente
     */

    private static Integer idRow = 0;

    //Il db si presuppone vuoto in fase di testing => si crea un operatore, un cittadino e una segnalazione (il cui id sarà 1);
    //poiché ci sarà una segnalazione, anche l'idRow sarà 1

    /**
     * Metodo utilizzato per
     * 1. inizializzare il db con un cittadino, un operatore e una segnalaione;
     * 2. ricavare id segnalazione e operatore
     * 3. settare idUtente e idSegnalazioneCorrente
     */

    @BeforeAll
    public static void setUp() {

        CreazioneSegnalazioneTest.puliziaDatabase();

        new FormRegistrazione().registra("CITTADINO", "Marco", "Arena", "marcoaren04@gmail.com", "3331430979", "Aldone04!");
        new FormRegistrazione().registra("OPERATORE", "Giuliano", "Izzo", "giuliano@gmail.com", "1112121221", "Passw123!");


        new FormCreazioneSegnalazione().creaSegnalazione("Discarica",
                "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                "RIFIUTI_ABBANDONATI",
                "Centro Storico: Via dei Tribunali 120", "", "");

        //Ricavo l'id dell'unica segnalazione inserita
        ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore(null, null, null);
        List<Segnalazione> listaS = new GestorePersistenza().cercaPerCampi(Segnalazione.class, Map.of());

        Long idSegnalazioneCorrente = listaS.get(0).getIdSegnalazione();

        List<Operatore> listaO = new GestorePersistenza().cercaPerCampi(Operatore.class, Map.of());
        Long idOperatore = listaO.get(0).getIdOperatore();


        ControllerSegnalazioni.setIdSegnalazioneCorrente(idSegnalazioneCorrente);
        ControllerUtenti.setIdUtenteCorrente(idOperatore, Ruolo.OPERATORE.name());
    }

    /**
     * Metodo utilizzato per far prendere in carico adll'operatore la segnalazione
     * Si nota che nel caso in cui venga stampato su terminale:
     *[GestoreSegnalazioni] Impossibile prendere in carico la segnalazione, stato:PRESA_IN_CARICO
     * è da considerarsi normale flusso di esecuzione visto che, in caso di errore in conclusione con aggiunta nota,
     * la conclusione della gestione non va a buon fine e quindi l'operatore starà ancora gestendo quella operazione
     */

    @BeforeEach
    public void prendiInCaricoSegnalazione() {
        //Il boundary non fa altro che chiamare questa funzione al premere del button prendi in carico;
        // -> di conseguenza il test parte dal controller
        ControllerSegnalazioni.iniziaGestioneSegnalazione();

        form = new FormConclusioneGestione(idRow);

        form.setPresenzaNota(true);

    }

    /*
     * Per i test, si presuppone che errori nell'inserimento di titolo o descrizione lancino un eccezione
     * IllegalArgumentException; di conseguenza, se non viene lanciata alcuna eccezione la conclusione è da considerarsi
     * andata a buon fine (anche se l'esito di quel metodo fosse false) visto che non sarebbero riscontrati problemi
     * nei valori inseriti come titolo e descrizione della nota
     */

    /**
     * TC: ID1
     */

    @Test
    public void tuttiInputValidi() {
        form.setTitoloNota("Risolta");
        form.setDescrizioneNota("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito;

        try {

            form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {

            esito = false;

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        assertTrue(esito);
    }

    /**
     * TC: ID2
     */

    @Test
    public void titoloLungo() {
        form.setTitoloNota("La Segnalazione è stata risolta");
        form.setDescrizioneNota("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito;

        try {

            form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {

            esito = false;

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }

    /**
     * TC: ID3
     */

    @Test
    public void titoloCorto() {
        form.setTitoloNota("Ok");
        form.setDescrizioneNota("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito;

        try {

            form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {

            esito = false;

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }

    /**
     * TC: ID4
     */

    @Test
    public void titoloSpeciale() {
        form.setTitoloNota("Ris@lta");
        form.setDescrizioneNota("È stata gestita la presenza dei rifiuti prelevandoli e smaltendoli in apposita sede");

        boolean esito;

        try {

            form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {

            esito = false;

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }

    /**
     * TC: ID5
     */

    @Test
    public void descrizioneLunga() {
        form.setTitoloNota("Check parziale");
        form.setDescrizioneNota("La segnalazione è stata ricevuta, verificata e inoltrata agli enti competenti. Sono state predisposte misure cautelari per la risoluzione del problema; saranno obbligatori altri interventi di controllo");

        boolean esito;

        try {

            form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {

            esito = false;

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }

    /**
     * TC: ID6
     */

    @Test
    public void descrizioneCorta() {
        form.setTitoloNota("Risolta");
        form.setDescrizioneNota("Ok");

        boolean esito;

        try {

            form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {

            esito = false;

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }

    /**
     * TC: ID7
     */

    @Test
    public void descrizioneSpeciale() {
        form.setTitoloNota("Risolta");
        form.setDescrizioneNota("Segnalazione gestita! correttamente#");

        boolean esito;

        try {

            form.concludiGestione();
            esito = true;

        } catch (IllegalArgumentException exception) {

            esito = false;

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertFalse(esito);
    }
}
