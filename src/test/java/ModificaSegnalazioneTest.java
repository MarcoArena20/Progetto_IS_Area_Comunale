import Boundary.FormCreazioneSegnalazione;
import Boundary.FormModificaSegnalazione;
import Boundary.FormRegistrazione;
import Controller.ControllerSegnalazioni;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * CASO D'USO: CreazioneSegnalazione
 * Classe di test per verificare il metodo di creaSegnalazione
 */

public class ModificaSegnalazioneTest {

    @BeforeAll
    static void setUp(){

        TestUtility.puliziaDatabase();

        // La prima cosa da fare è creare un utente ed una segnalazione

        // Creaiamo un utente
        new FormRegistrazione().registra("CITTADINO", "Marco", "Arena", "marcoaren04@gmail.com", "3331430979", "Aldone04!");

        boolean esito = new FormCreazioneSegnalazione().creaSegnalazione("Discarica",
                "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                "RIFIUTI_ABBANDONATI",
                "Fuorigrotta: Piazzale Tecchio 50", "", "");

        ControllerSegnalazioni.caricaSegnalazioni(); // Viene effettuato il mapping in memoria

    }

    /**
     * Test corretto con i soli campi obbligatori
     */

    @Test
    void testCorrettoObbligatori(){

        boolean esito = false;

        try{

            esito = new FormModificaSegnalazione(0).modificaSegnalazione(0, "Discarica",
                    "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                    "RIFIUTI_ABBANDONATI",
                    "Fuorigrotta: Piazzale Tecchio 50", "", "");

        }catch(IllegalArgumentException e){

            esito = false;

        }

        assertTrue(esito);

    }

    /**
     * Test corretto con campi obbligatori e opzionali
     */

    @Test
    void testCorrettoOpzionali(){

        boolean esito = false;

        try{

            esito = new FormModificaSegnalazione(0).modificaSegnalazione(0, "Discarica",
                    "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                    "RIFIUTI_ABBANDONATI",
                    "Fuorigrotta: Piazzale Tecchio 50", "20/05/2004 22:50", "https://www.testCaseProgetto.it");

        }catch(IllegalArgumentException e){

            esito = false;

        }

        assertTrue(esito);

    }

    /**
     * Test parametrico al variare del titolo nelle classi di equivalenza non valide
     * @param titolo titolo della descrizione
     */

    @ParameterizedTest
    @ValueSource(strings = {"", // Titolo vuoto
            "Ok", // Titolo < 5
            "Abbandono di rifiuti con cattivi odori davanti alla farmacia"}) // Titolo > 30

    void titoloErrato(String titolo){

        boolean esito = true;

        try{

            esito = new FormModificaSegnalazione(0).modificaSegnalazione(0, titolo,
                    "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                    "RIFIUTI_ABBANDONATI",
                    "Fuorigrotta: Piazzale Tecchio 50", "", "");

        }catch(IllegalArgumentException e){

            esito = false;

        }


        assertFalse(esito);

    }

    /**
     * Test parametrico al variare della descrizione nelle classi di equivalenza non valide
     * @param descrizione descrizione della segnalazione
     */

    @ParameterizedTest
    @ValueSource(strings = {"", // Descrizione vuota
            "Discarica a cielo aperto", // Descrizione < 50
            "È stata segnalata una situazione di forte degrado ambientale " + // Descrizione > 200
                    "causata dall'abbandono di numerosi rifiuti vicino all'ingresso della farmacia. " +
                    "Le persone che transitano nella zona hanno evidenziato la presenza di esalazioni maleodoranti " +
                    "e un evidente disagio per residenti, lavoratori e clienti. Si richiede un controllo immediato dell'area, " +
                    "la rimozione dei rifiuti e un intervento volto a prevenire nuovi episodi simili.",})

    void descrizioneErrata(String descrizione){

        boolean esito = true;

        try{

            esito = new FormModificaSegnalazione(0).modificaSegnalazione(0, "Discarica",
                    descrizione,
                    "RIFIUTI_ABBANDONATI",
                    "Viale delle mimose", "", "");

        }catch(IllegalArgumentException e){

            esito = false;

        }

        assertFalse(esito);


    }

    /**
     * Test sulla categoria della segnalazione non valida
     */

    @Test
    void categoriaErrata(){

        boolean esito = true;

        try{

            esito = new FormModificaSegnalazione(0).modificaSegnalazione(0,"Discarica",
                    "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                    "Immondizia",
                    "Fuorigrotta: Piazzale Tecchio 50", "", "");

        }catch(IllegalArgumentException e){

            esito = false;

        }

        assertFalse(esito);

    }

    /**
     * Test sulla posizione della segnalazione non valida
     */


    @Test
    void posizioneErrata(){

        boolean esito = true;

        try{

            esito = new FormModificaSegnalazione(0).modificaSegnalazione(0, "Discarica",
                    "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                    "RIFIUTI_ABBANDONATI",
                    "Viale delle resede 8", "", "");

        }catch(IllegalArgumentException e){

            esito = false;

        }

        assertFalse(esito);

    }

    /**
     * Test sul formato della data non valido
     */

    @Test
    void dataErrata(){

        boolean esito = true;

        try{

            esito = new FormModificaSegnalazione(0).modificaSegnalazione(0, "Discarica",
                    "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                    "RIFIUTI_ABBANDONATI",
                    "Fuorigrotta: Piazzale Tecchio 50", "20 Maggio 2004 22:50", "");

        }catch(IllegalArgumentException e){

            esito = false;

        }

        assertFalse(esito);

    }

    /**
     * Test sul formato dell'url dell'immagine non valido
     */

    @Test
    void urlImmagineErrato(){

        boolean esito = true;

        try{

            esito = new FormModificaSegnalazione(0).modificaSegnalazione(0, "Discarica",
                    "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                    "RIFIUTI_ABBANDONATI",
                    "Fuorigrotta: Piazzale Tecchio 50", "", "ftp://www.testCaseProgetto.it");

        }catch(IllegalArgumentException e){

            esito = false;

        }

        assertFalse(esito);

    }
}
