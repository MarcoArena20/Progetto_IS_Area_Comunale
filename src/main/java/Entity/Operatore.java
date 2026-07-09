package Entity;

public class Operatore extends UtenteAutenticato {

    //Attributi
    /*
    protected String nome;
    protected String cognome;
    protected String email;
    protected String recapitoTelefonico;
    protected String passwordHash;
    */
    private String idOperatore;

    //Costruttore
    public Operatore(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        super(nome, cognome, email, recapitoTelefonico, passwordHash);
        //TODO
        //Generare automaticamente l'idOperatore
    }

    //Getter
    public String getIdOperatore() { return idOperatore; }


}