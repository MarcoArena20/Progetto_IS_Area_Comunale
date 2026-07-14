package Entity;

import Entity.Enum.Ruolo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * Enitity che specializza UtenteAutenticato aggiungendo l'id
 */

@Entity
public class Operatore extends UtenteAutenticato {

    /**
     * Id univoco generatore dal Database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperatore;

    /**
     * Costruttore di default utilizzato dal database
     */
    public Operatore() {}

    /**
     * Coatruttore dell'oggetto Operatore
     * @param nome nome dell'operatore
     * @param cognome cognome dell'operatore
     * @param email email dell'operatore
     * @param recapitoTelefonico recapitoTelefonico dell'operatore
     * @param passwordHash hash della password dell'operatore
     */
    public Operatore(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        super(nome, cognome, email, recapitoTelefonico, passwordHash);
    }

    /**
     * Getter dell'id
     * @return id del cittadino
     */
    public Long getIdOperatore() { return idOperatore; }

    /**
     * Getter del ruolo
     * @return stringa del ruolo
     */
    @Override
    public String getRuolo() {
        return Ruolo.OPERATORE.name();
    }

    @Override
    public String toString() {
        return"Operatore {\n" +
                    "*\tidOperatore=" + idOperatore + ",\n" +
                    '}';
    }
}