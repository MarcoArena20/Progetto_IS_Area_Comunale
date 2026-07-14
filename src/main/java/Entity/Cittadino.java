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
public class Cittadino extends UtenteAutenticato {
    /**
     * Id univoco generatore dal Database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCittadino;

    /**
     * Costruttore di default utilizzato dal database
     */

    public Cittadino(){
        super();
    }

    /**
     * Costruttore dell'oggetto Cittadino
     * @param nome nome del cittadino
     * @param cognome cognome del cittadino
     * @param email email del cittadino
     * @param recapitoTelefonico recapitoTelefonico del cittadino
     * @param passwordHash hash della password del cittadino
     */
    public Cittadino(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        super(nome, cognome, email, recapitoTelefonico,  passwordHash);
    }

    /**
     * Getter dell'id
     * @return id del cittadino
     */
    public Long getIdCittadino() { return idCittadino; }

    /**
     * Getter del ruolo
     * @return stringa del ruolo
     */
    @Override
    public String getRuolo() {
        return Ruolo.CITTADINO.name();
    }

    @Override
    public String toString() {
        return"Operatore {\n" +
                "*\tidCittadino=" + idCittadino + ",\n" +
                '}';
    }

}