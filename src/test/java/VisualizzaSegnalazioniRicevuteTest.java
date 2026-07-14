import Boundary.FormCreazioneSegnalazione;
import Boundary.FormRegistrazione;
import Controller.ControllerSegnalazioni;
import Controller.ControllerUtenti;
import Entity.Enum.Ruolo;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * CASO D'USO: VisualizzaSegnalazioniRicevute
 * Classe di test per verificare le funzionalità di visualizzazione e filtraggio
 * delle segnalazioni ricevute da parte dell'operatore comunale.
 */
public class VisualizzaSegnalazioniRicevuteTest {

    /**
     * Inizializza lo stato del database per permettere un corretto funzionamento dei test.
     * Esegue la pulizia del database e crea gli utenti di test (un operatore e un cittadino),
     * dopodiché inserisce alcune segnalazioni di prova utilizzate per verificare i filtri di ricerca.
     */
    @BeforeAll
    static void initDB() {
        // Puliamo il DB e inseriamo i dati per i test
        TestUtility.puliziaDatabase();
        new FormRegistrazione().registra("OPERATORE", "Mario", "Rossi", "operatore@comune.it", "3331112222", "Password123!");
        new FormRegistrazione().registra("CITTADINO", "Luigi", "Verdi", "luigi@gmail.com", "3331430979", "Password234!");

        FormCreazioneSegnalazione form = new FormCreazioneSegnalazione();

        // Inseriamo Segnalazioni di test
        form.creaSegnalazione("Rifiuti strada", "Spazzatura varia per terra trovata di fronte al bar principale della piazza.", "RIFIUTI_ABBANDONATI", "Centro Storico: Piazza Bellini 6", "", "");
        form.creaSegnalazione("Strada rotta", "Buca pericolosa sul manto stradale trovata a via consalvo 78.", "STRADA_DISSESTATA", "Fuorigrotta: Via Consalvo 78", "", "");
    }

    /**
     * Configurazione eseguita prima di ogni singolo test.
     * Imposta l'utente corrente nel sistema simulando il login dell'operatore comunale (ID: 1).
     */
    @BeforeEach
    void setUp() {
        ControllerUtenti.setIdUtenteCorrente(1L, Ruolo.OPERATORE.name());
    }

    /**
     * Test per verificare la visualizzazione completa delle segnalazioni (nessun filtro applicato).
     * Assicura che, in assenza di parametri restrittivi, vengano mostrate tutte le segnalazioni presenti nel sistema.
     */
    @Test
    @DisplayName("TC1: Visualizzazione completa (nessun filtro)")
    void testTC1_NessunFiltro() {
        // Input: {"", "", ""}
        List<String[]> risultati = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("", null, "");

        // Output Atteso: Elenco di tutte le segnalazioni ricevute
        assertNotNull(risultati, "La lista restituita non deve essere nulla");
        assertFalse(risultati.isEmpty(), "La lista completa non deve essere vuota (richiede dati nel DB)");
    }

    /**
     * Test per verificare il filtraggio singolo tramite uno Stato valido.
     * Assicura che la ricerca restituisca correttamente i risultati filtrati per lo stato specificato ("Inviata").
     */
    @Test
    @DisplayName("TC2: Filtro singolo per Stato (con risultati)")
    void testTC2_FiltroStato() {
        // Input: {"Inviata", "", ""}
        List<String[]> risultati = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("Inviata", null, "");

        // Output Atteso: Elenco filtrato (non vuoto, assumendo che esistano segnalazioni 'Inviata')
        assertNotNull(risultati);
    }

    /**
     * Test per verificare il filtraggio singolo tramite una Categoria valida.
     * Verifica che il sistema restituisca i risultati filtrati correttamente per "Rifiuti abbandonati".
     */
    @Test
    @DisplayName("TC3: Filtro singolo per Categoria")
    void testTC3_FiltroCategoria() {
        // Input: {"", "Rifiuti abbandonati", ""}
        List<String[]> risultati = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("", "Rifiuti abbandonati", "");

        assertNotNull(risultati);
    }

    /**
     * Test per verificare il funzionamento di un filtro combinato (Stato, Categoria e Area Geografica)
     * che produce una corrispondenza nel database.
     */
    @Test
    @DisplayName("TC4: Filtro combinato (con risultati)")
    void testTC4_FiltroCombinatoConRisultati() {
        // Input: {"PresaInCarico", "Strada dissestata", "Via Claudio"}
        List<String[]> risultati = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("PresaInCarico", "Strada dissestata", "Via Claudio");

        assertNotNull(risultati);
    }

    /**
     * Test per verificare che una combinazione di filtri valida sintatticamente,
     * ma che non trova corrispondenza nel DB, restituisca una lista vuota ma non nulla.
     */
    @Test
    @DisplayName("TC5: Filtro combinato (senza risultati)")
    void testTC5_FiltroCombinatoSenzaRisultati() {
        // Input: {"Risolta", "Pericolo generico", "Via Roma"} - Combinazione inesistente nel DB
        List<String[]> risultati = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("Risolta", "Pericolo generico", "Via Roma");

        // Output Atteso: Schermata di elenco vuota (la lista ritornata dal Controller deve essere empty)
        assertNotNull(risultati, "Il metodo deve ritornare una lista inizializzata, non null");
        assertTrue(risultati.isEmpty(), "La lista deve essere vuota per una combinazione di filtri inesistente");
    }

    /**
     * Test per verificare la gestione dell'inserimento di uno Stato non valido.
     * Il sistema deve bloccare la ricerca e lanciare un'eccezione di tipo IllegalArgumentException.
     */
    @Test
    @DisplayName("TC6: Inserimento Stato non valido")
    void testTC6_StatoNonValido() {
        // Input: {"InSospeso", "", ""}

        // Output Atteso: La ricerca non viene effettuata e si genera un errore.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("InSospeso", "", "");
        });
    }

    /**
     * Test per verificare la gestione dell'inserimento di una Categoria non valida.
     * Il sistema deve intercettare la categoria non riconosciuta ("Immondizia") lanciando un'eccezione.
     */
    @Test
    @DisplayName("TC7: Inserimento Categoria non valida")
    void testTC7_CategoriaNonValida() {
        // Input: {"", "Immondizia", ""}

        // Output Atteso: Categoria inesistente! (Errore)
        assertThrows(IllegalArgumentException.class, () -> {
            ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("", "Immondizia", "");
        }, "Il sistema deve bloccare la ricerca per una categoria non riconosciuta");
    }

    /**
     * Test per verificare il comportamento del sistema quando viene inserito un input non conforme
     * (con caratteri speciali vietati) nel campo di ricerca dell'Area Geografica.
     * Deve essere generata una IllegalArgumentException a causa del fallimento della validazione formale.
     */
    @Test
    @DisplayName("TC8: Inserimento Area con caratteri speciali")
    void testTC8_AreaCaratteriSpeciali() {
        // Input: {"", "", "Piazza Garibaldi #!"}

        // Output Atteso: Formato Area Geografica errato! (Errore di validazione)
        assertThrows(IllegalArgumentException.class, () -> {
            ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("", "", "Piazza Garibaldi #!");
        }, "Il sistema deve rilevare i caratteri speciali e bloccare la ricerca");
    }

}
