import Controller.ControllerSegnalazioni;
import Controller.ControllerUtenti;
import Entity.Enum.Ruolo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class VisualizzaSegnalazioniRicevuteTest {

    @BeforeEach
    void setUp() {
        ControllerUtenti.setIdUtenteCorrente(1L, Ruolo.OPERATORE.name());
    }

    @Test
    @DisplayName("TC1: Visualizzazione completa (nessun filtro)")
    void testTC1_NessunFiltro() {
        // Input: {"", "", ""}
        List<String[]> risultati = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("", null, "");

        // Output Atteso: Elenco di tutte le segnalazioni ricevute
        assertNotNull(risultati, "La lista restituita non deve essere nulla");
        assertFalse(risultati.isEmpty(), "La lista completa non deve essere vuota (richiede dati nel DB)");
    }

    @Test
    @DisplayName("TC2: Filtro singolo per Stato (con risultati)")
    void testTC2_FiltroStato() {
        // Input: {"Inviata", "", ""}
        List<String[]> risultati = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("Inviata", null, "");

        // Output Atteso: Elenco filtrato (non vuoto, assumendo che esistano segnalazioni 'Inviata')
        assertNotNull(risultati);
    }

    @Test
    @DisplayName("TC3: Filtro singolo per Categoria")
    void testTC3_FiltroCategoria() {
        // Input: {"", "Rifiuti abbandonati", ""}
        List<String[]> risultati = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("", "Rifiuti abbandonati", "");

        assertNotNull(risultati);
    }

    @Test
    @DisplayName("TC4: Filtro combinato (con risultati)")
    void testTC4_FiltroCombinatoConRisultati() {
        // Input: {"PresaInCarico", "Strada dissestata", "Via Claudio"}
        List<String[]> risultati = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("PresaInCarico", "Strada dissestata", "Via Claudio");

        assertNotNull(risultati);
    }

    @Test
    @DisplayName("TC5: Filtro combinato (senza risultati)")
    void testTC5_FiltroCombinatoSenzaRisultati() {
        // Input: {"Risolta", "Pericolo generico", "Via Roma"} - Combinazione inesistente nel DB
        List<String[]> risultati = ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("Risolta", "Pericolo generico", "Via Roma");

        // Output Atteso: Schermata di elenco vuota (la lista ritornata dal Controller deve essere empty)
        assertNotNull(risultati, "Il metodo deve ritornare una lista inizializzata, non null");
        assertTrue(risultati.isEmpty(), "La lista deve essere vuota per una combinazione di filtri inesistente");
    }

    @Test
    @DisplayName("TC6: Inserimento Stato non valido")
    void testTC6_StatoNonValido() {
        // Input: {"InSospeso", "", ""}

        // Output Atteso: La ricerca non viene effettuata e si genera un errore.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("InSospeso", "", "");
        });
    }

    @Test
    @DisplayName("TC7: Inserimento Categoria non valida")
    void testTC7_CategoriaNonValida() {
        // Input: {"", "Immondizia", ""}

        // Output Atteso: Categoria inesistente! (Errore)
        assertThrows(IllegalArgumentException.class, () -> {
            ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("", "Immondizia", "");
        }, "Il sistema deve bloccare la ricerca per una categoria non riconosciuta");
    }

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
