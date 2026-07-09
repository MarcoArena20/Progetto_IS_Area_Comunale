package Entity;

public class Operatore extends UtenteAutenticato {
    private String idOperatore;

    public Operatore(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash, String idOperatore) {
        super(nome, cognome, email, recapitoTelefonico, passwordHash);
        this.idOperatore = idOperatore;
    }

    public String getIdOperatore() { return idOperatore; }
    public void setIdOperatore(String idOperatore) { this.idOperatore = idOperatore; }
}