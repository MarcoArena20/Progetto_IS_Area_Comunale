package Entity;

public class Cittadino extends UtenteAutenticato {
    private String idCittadino;

    public Cittadino(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash, String idCittadino) {
        super(nome, cognome, email, recapitoTelefonico, passwordHash);
        this.idCittadino = idCittadino;
    }

    public String getIdCittadino() { return idCittadino; }
    public void setIdCittadino(String idCittadino) { this.idCittadino = idCittadino; }
}