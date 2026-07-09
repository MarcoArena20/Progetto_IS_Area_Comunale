package Entity;

public abstract class UtenteAutenticato {

    //Attributi
    protected String nome;
    protected String cognome;
    protected String email;
    protected String recapitoTelefonico;
    protected String passwordHash;

    //Costruttore
    public UtenteAutenticato(String nome, String cognome, String email, String recapitoTelefonico, String passwordHash) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.recapitoTelefonico = recapitoTelefonico;
        this.passwordHash = passwordHash;
    }

    // Getter e Setter
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRecapitoTelefonico() { return recapitoTelefonico; }
    public void setRecapitoTelefonico(String recapitoTelefonico) { this.recapitoTelefonico = recapitoTelefonico; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}