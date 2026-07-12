package Entity;

import Entity.Enum.Ruolo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cittadino extends UtenteAutenticato {

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
    private Long idCittadino;

    public Cittadino(){
        super();
    }

    //Costruttore
    public Cittadino(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        super(nome, cognome, email, recapitoTelefonico,  passwordHash);
    }

    //Getter
    public Long getIdCittadino() { return idCittadino; }

    //Metodi
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