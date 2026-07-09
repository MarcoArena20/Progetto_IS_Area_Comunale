package Entity;

public class Cittadino extends UtenteAutenticato {

    //Attributi
    /*
    protected String nome;
    protected String cognome;
    protected String email;
    protected String recapitoTelefonico;
    protected String passwordHash;
    */
    private String idCittadino;

    //Costruttore
    public Cittadino(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        super(nome, cognome, email, recapitoTelefonico, passwordHash);
        //TODO
        //Generare automaticamente l'idCittadino
    }

    //Getter
    public String getIdCittadino() { return idCittadino; }

    //Metodi
    @Override
    public String getRuolo() {
        //TODO
        return "";
    }

}