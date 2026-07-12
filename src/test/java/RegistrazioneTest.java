import Boundary.FormRegistrazione;
import Boundary.MainFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RegistrazioneTest {

    // Arrange
    private FormRegistrazione formRegistrazione;

    @BeforeEach
    void setUp(){
         formRegistrazione = new FormRegistrazione();
    }

    // Act
    @Test
    void testCorretto(){
        boolean esito;
        try{
            esito = formRegistrazione.Registra("Cittadino", "Mario", "Rossi", "Mario.Rossi85@gmail.com", "1234567890", "Password-1");
        }
        catch (IllegalArgumentException ex){
            ex.getMessage();
            esito= false;
        }
        assertTrue(esito);
    }


}
