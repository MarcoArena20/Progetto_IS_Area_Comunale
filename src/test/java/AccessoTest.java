import  Boundary.FormAccesso;
import Boundary.FormRegistrazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AccessoTest {

    // Arrange
    private FormAccesso formAccesso;
    private FormRegistrazione formRegistrazione;

    @BeforeEach
    void setUp() {
        formAccesso = new FormAccesso();
        formRegistrazione = new FormRegistrazione();
    }

    // Act
    @Test
    void testCorrettoCittadino() {
        boolean esito;
        formRegistrazione.registra("Cittadino", "Mario", "Rossi", "nome.cognome@comune.it", "1234567890", "MarioRossi-03");
        esito = formAccesso.Accedi("Cittadino", "nome.cognome@comune.it", "MarioRossi-03");
        assertTrue(esito);
    }

    @Test
    void testCorrettoOperatore() {
        boolean esito;
        formRegistrazione.registra("Operatore", "Mario", "Rossi", "nome.cognome@comune.it", "1234567890", "MarioRossi-03");
        esito = formAccesso.Accedi("Operatore", "nome.cognome@comune.it", "MarioRossi-03");
        assertTrue(esito);
    }

    @Test
    void testRuoloNonValido() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("", "Mario", "Rossi", "mario.rossi@comune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testEmailTroppoLuga() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "nomeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee.cognome@comune", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testEmailTroppoCorta() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "n@a.it", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testEmailSenzaChioccola() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "nome.cognomecomune.it", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testEmailContienePiuChiocciole() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", " n@ome.cognome@comune.it", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testEmailSoliCarattSpecSoloUnaChiocc() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "--@??.!?!-", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }
    @Test
    void testEmailNoCarttSpec() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "nome@cognomecomuneit", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordTroppoCorta() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "nome.cognome@comune.it", "Mar-01");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordTroppoLunga() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "nome.cognome@comune.it", "Mariooooooooooooo-1999320132");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordNoMaiuscole() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "nome.cognome@comune.it", "operatorepass-01");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordNoMinuscole() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "nome.cognome@comune.it", "MARIOROSSI-01");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordNoNumeri() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "nome.cognome@comune.it", "Mariorossi!");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordNoCarattSpec() {
        boolean esito;
        try {
            esito = formAccesso.Accedi("Cittadino", "nome.cognome@comune.it", "MarioRossi03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }
    @Test
    void testPasswordCarattSpecNonAmmessi(){
        boolean esito;
        try{
            esito = formAccesso.Accedi("Cittadino", "nome.cognome@comune.it", "mariorossi-03[");
        }
        catch (IllegalArgumentException ex){
            esito= false;
        }
        assertFalse(esito);
    }
}