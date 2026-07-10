package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Operatore extends UtenteAutenticato {

    //Attributi
    /*
    protected String nome;
    protected String cognome;
    protected String email;
    protected String recapitoTelefonico;
    protected String passwordHash;
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperatore;

    //Costruttore
    public Operatore() {}

    public Operatore(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        super(nome, cognome, email, recapitoTelefonico, passwordHash);
    }

    //Getter
    public Long getIdOperatore() { return idOperatore; }

    //Metodi
    @Override
    public String getRuolo() {
        //TODO
        return "";
    }
}