import  Boundary.FormAccesso;
import Boundary.FormRegistrazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;


public class AccessoTest {

    private FormAccesso formAccesso;
    private FormRegistrazione formRegistrazione;

    @BeforeEach
    void setUp() {
        TestUtility.puliziaDatabase();
        formAccesso = new FormAccesso();
        formRegistrazione = new FormRegistrazione();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Operatore", "Cittadino"})
    void testRuolo(String ruolo){
        boolean esito;
        try{
            formRegistrazione.registra(ruolo, "Mario", "Rossi", "nome.cognome@comune.it", "1234567890", "MarioRossi-03");
            esito = formAccesso.accedi(ruolo, "nome.cognome@comune.it", "MarioRossi-03");
        }
        catch (IllegalArgumentException ex){
            esito = false;
        }
        assertTrue(esito);
    }

    @Test
    void testRuoloInvalido(){
        boolean esito;
        try{
            esito = formAccesso.accedi("","nome.cognome@comune.it", "MarioRossi-03");
        }
        catch (IllegalArgumentException ex){
            esito = false;
            System.out.println(ex.getMessage());
        }
        assertFalse(esito);
    }


    @ParameterizedTest
    @ValueSource(strings = {"nomeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee.cognome@comune",
                            "n@a.it",
                            "nome.cognomecomune.it",
                            "n@ome.cognome@comune.it",
                            "--@??.!?!-",
                            "nome@cognomecomuneit",
                            ""
    })
    void testEmail(String email) {
        boolean esito;
        try {
            esito = formAccesso.accedi("Cittadino", email, "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
            System.out.println(ex.getMessage());
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
            esito = formAccesso.accedi("Cittadino", "nome.cognome@comune.it",password);
        } catch (IllegalArgumentException ex) {
            esito = false;
            System.out.println(ex.getMessage());
        }
        assertFalse(esito);
    }
}