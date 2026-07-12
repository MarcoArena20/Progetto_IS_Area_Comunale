import Controller.ControllerSegnalazioni;
import Controller.ControllerUtenti;
import Entity.Enum.Ruolo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VisualizzaDettaglioSegnalazioneRicevutaTest {

    @BeforeEach
    void setUp() {
        //Pre-Condizione: L'operatore ha effettuato l'accesso
        ControllerUtenti.setIdUtenteCorrente(1L, Ruolo.OPERATORE.name());

        //Pre-Condizione: L'operatore si trova nell'elenco
        ControllerSegnalazioni.visualizzaSegnalazioniPerOperatore("Tutti","Tutte","Tutte");
    }

    @Test
    void testTC1_DettaglioCompleto(){
        //Input: Selezione ID segnalazione valida
        Integer idRowSelezione = 0;

        Map <String,String> dettagli = ControllerSegnalazioni.getDettagliSegnalazione(idRowSelezione);

        //Output attesi
        assertNotNull(dettagli,"Il sistema deve restituire i dettagli della segnalazione");
        assertTrue(dettagli.containsKey("titolo") && dettagli.containsKey("idCittadino"),"Devono essere presenti dati testuali e del cittadino");

        String urlImmagine = dettagli.get("urlImmagine");
        assertNotNull(urlImmagine);
        assertFalse(urlImmagine.isEmpty(),"L'immagine deve essere presente e caricata");

        String note = dettagli.get("note");
        assertNotNull(note);
        assertFalse(note.isEmpty(), "L'elenco delle note deve essere presente");
    }

    @Test
    void testTC2_ImmagineSenzaNote(){
        //Input: Selezione ID segnalazione valida
        Integer idRowSelezione = 1;

        Map <String,String> dettagli = ControllerSegnalazioni.getDettagliSegnalazione(idRowSelezione);

        //Output attesi
        assertNotNull(dettagli,"Il sistema deve restituire i dettagli della segnalazione");

        String urlImmagine = dettagli.get("urlImmagine");
        assertNotNull(urlImmagine);
        assertFalse(urlImmagine.isEmpty(),"L'immagine deve essere presente e caricata");

        String note = dettagli.get("note");
        assertTrue(note == null || note.isEmpty(), "Non ci devono essere note collegate alla segnalazione");
    }

    @Test
    void testTC3_SenzaImmagineConNote(){
        //Input: Selezione ID segnalazione valida
        Integer idRowSelezione =2;

        Map <String,String> dettagli = ControllerSegnalazioni.getDettagliSegnalazione(idRowSelezione);

        //Output attesi
        assertNotNull(dettagli,"Il sistema deve restituire i dettagli della segnalazione");

        //Verifica Immagine assente
        String urlImmagine = dettagli.get("urlImmagine");
        assertTrue(urlImmagine == null || urlImmagine.isEmpty(), "Nessun allegato presente");

        String note = dettagli.get("note");
        assertNotNull(note);
        assertFalse(note.isEmpty(), "L'elenco delle note deve essere presente");
    }

    @Test
    void testTC4_SenzaImmagineSenzaNote(){
        //Input: Selezione ID segnalazione valida
        Integer idRowSelezione =3;

        Map <String,String> dettagli = ControllerSegnalazioni.getDettagliSegnalazione(idRowSelezione);

        //Output attesi
        assertNotNull(dettagli,"Il sistema deve restituire i dettagli della segnalazione");

        //Verifica Immagine assente
        String urlImmagine = dettagli.get("urlImmagine");
        assertTrue(urlImmagine == null || urlImmagine.isEmpty(), "Nessun allegato presente");

        String note = dettagli.get("note");
        assertTrue(note == null || note.isEmpty(), "Non ci devono essere note collegate alla segnalazione");
    }

    @Test
    void testTC5_IdInesistente() {
        Integer idRowNonValido = 999; // Simula un indice non mappato o fuori limite

        // Output Atteso: Il sistema gestisce l'ID inesistente.
        assertThrows(Exception.class, () -> {
            ControllerSegnalazioni.getDettagliSegnalazione(idRowNonValido);
        }, "Il sistema deve restituire un errore o impedire l'accesso al dettaglio per un ID non valido");
    }

}