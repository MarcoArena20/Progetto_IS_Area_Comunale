import Boundary.FormRegistrazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RegistrazioneTest {

    // Arrange
    private FormRegistrazione formRegistrazione;
    @BeforeEach
    void setUp() {
        formRegistrazione = new FormRegistrazione();
    }



    // Act
    @Test
    void testCorrettoCittadino() {
        boolean esito;
        esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi1@comune.it", "1234567890", "MarioRossi-03");
        assertTrue(esito);
    }

    @Test
    void testCorrettoOperatore() {
        boolean esito=false;
        esito = formRegistrazione.registra("Operatore", "Mario", "Rossi", "mario.rossi@comune.it", "1234567890", "MarioRossi-03");
        System.out.println("ESITO CORRETTO OPERATORE:"+esito);
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
    void testNomeTroppoLungo() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Marioooooooooooooooooooo", "Rossi", "mario.rossi@comune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testNomeTroppoCorto() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Ma", "Rossi", "mario.rossi@comune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testNomeConCaratteriNonAlfabetici() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "M4rio", "Rossi", "mario.rossi@comune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testCognomeTroppoLungo() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossiiiiiiiiiiiiiiiiiiii", "mario.rossi@comune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testCognomeTroppoCorto() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Ro", "mario.rossi@comune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testCognomeConCaratteriNonAlfabetici() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "R0ss1", "mario.rossi@comune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testRecapitoTelefonicoTroppoCorto() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", "12345678", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testRecapitoTelefonicoTroppoLungo() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", "1234567890111", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testRecapitoTelefonicoNonNumerici() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", "12345678ab", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testEmailTroppoLuga() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "nomeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee.cognome@comune", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testEmailTroppoCorta() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "n@a.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testEmailSenzaChioccola() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "nome.cognomecomune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testEmailContienePiuChiocciole() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "n@@me.cognomecomune.it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testEmailSoliCarattSpecSoloUnaChiocc() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "---@??.!?!-", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }
    @Test
    void testEmailNoCarttSpec() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "nomecognome@it", "1234567890", "MarioRossi-03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordTroppoCorta() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", "1234567890", "Mar-01");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordTroppoLunga() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", "1234567890", "Mariooooooooooooo-1999320132");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordNoMaiuscole() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", "1234567890", "operatorepass-01");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordNoMinuscole() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", "1234567890", "MARIOROSS-01");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordNoNumeri() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", "1234567890", "MarioRossi!");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }

    @Test
    void testPasswordNoCarattSpec() {
        boolean esito;
        try {
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", "1234567890", "MarioRossi03");
        } catch (IllegalArgumentException ex) {
            esito = false;
        }
        assertFalse(esito);
    }
    @Test
    void testPasswordCarattSpecNonAmmessi(){
        boolean esito;
        try{
            esito = formRegistrazione.registra("Cittadino", "Mario", "Rossi", "mario.rossi@comune.it", "1234567890", "MarioRossi-03[");
        }
        catch (IllegalArgumentException ex){
            esito= false;
        }
        assertFalse(esito);
    }
}
