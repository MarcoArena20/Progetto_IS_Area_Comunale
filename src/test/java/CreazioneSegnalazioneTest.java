import Boundary.FormCreazioneSegnalazione;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreazioneSegnalazioneTest {

    // Testiamo la creazione della segnalazione andando ad invocare il metodo di creaSegnalazione del controller
    @Test
    void TestCorretto(){

        boolean esito = new FormCreazioneSegnalazione().creaSegnalazione("Discarica",
                "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                "RIFIUTI_ABBANDONATI",
                "Viale delle mimose", "", "");

        assertTrue(esito);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Discarica a cielo aperto",
                            "", "Discarica104"})
    void TitoloErrato(String titolo){

        boolean esito = new FormCreazioneSegnalazione().creaSegnalazione(titolo,
                "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                "RIFIUTI_ABBANDONATI",
                "Viale delle mimose", "", "");

        assertFalse(esito);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Si segnala la presenza di ingenti rifiuti abbandonati in prossimità dell’ingresso della farmacia. Occupano parte del marciapiede e dell’area antistante, generando condizioni di degrado urbano e provocando esalazioni maleodoranti percepibili dai passanti.",
            "Ci sono rifiuti"})

    void DescrizioneErrata(String descrizione){

        boolean esito = new FormCreazioneSegnalazione().creaSegnalazione("Discarica",
                descrizione,
                "RIFIUTI_ABBANDONATI",
                "Viale delle mimose", "", "");

        assertFalse(esito);


    }

    @Test
    void categoriaErrata(){

        boolean esito = new FormCreazioneSegnalazione().creaSegnalazione("Discarica",
                "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                "Immondizia",
                "Viale delle mimose", "", "");

        assertFalse(esito);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Viale", "Viale delle mimose#!"})

    void posizioneErrata(String posizione){

        boolean esito = new FormCreazioneSegnalazione().creaSegnalazione("Discarica",
                "È stata riscontrata la presenza di ingenti rifiuti abbandonati in prossimità dell'ingresso della farmacia, con conseguenti esalazioni maleodoranti.",
                "RIFIUTI_ABBANDONATI",
                posizione, "", "");

        assertFalse(esito);


    }

}

