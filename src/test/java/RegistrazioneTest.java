import Boundary.FormRegistrazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RegistrazioneTest {

    // Arrange
    private FormRegistrazione formRegistrazione;
    @BeforeEach

    void setUp() {
        CreazioneSegnalazioneTest.puliziaDatabase();
        formRegistrazione = new FormRegistrazione();
    }



    @ParameterizedTest
    @ValueSource(strings = {"Operatore", "Cittadino", ""})
    void testRuolo(String ruolo) {
        boolean esito;
        esito = formRegistrazione.registra(ruolo, "Mario", "Rossi", "mario.rossi1@comune.it", "1234567890", "MarioRossi-03");
        assertTrue(esito);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Marioooooooooooooooooooo", "Ma", "M4rio", })
    void testNome(String nome) {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", nome, "Rossi", "mario.rossi@comune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Rossiiiiiiiiiiiiiiiiiiii", "Ro", "R0ss1"})
    void testCognomeTroppoLungo(String cognome) {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", cognome, "mario.rossi@comune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678", "1234567890111", "12345678ab"})
    void testRecapitoTelefonico(String recapitoTelefonico) {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", recapitoTelefonico, "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @ParameterizedTest
    @ValueSource(strings = {"nomeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee.cognome@comune",
            "n@a.it",
            "nome.cognomecomune.it",
            "n@ome.cognome@comune.it",
            "--@??.!?!-",
            "nome@cognomecomuneit"
    })
    void testEmail(String email) {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino","Mario", "Rossi",  email,"1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }


    @ParameterizedTest
    @ValueSource(strings = {"Mar-01",
            "Mariooooooooooooo-1999320132",
            "operatorepass-01",
            "MARIOROSSI-01",
            "Mariorossi!",
            "MarioRossi03",
            "mariorossi-03["


    })
    void testPassword(String password) {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "nome.cognome@comune.it", "1234567890",password);
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }
}
