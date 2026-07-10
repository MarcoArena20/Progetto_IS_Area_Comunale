package Entity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UtenteAutenticato {

    //Attributi
    protected String nome;
    protected String cognome;
    protected String email;
    protected String recapitoTelefonico;
    protected String passwordHash;

    public UtenteAutenticato(){

    }

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
    public String getCognome() { return cognome; }
    public String getEmail() { return email; }
    public String getRecapitoTelefonico() { return recapitoTelefonico; }
    //Per privacy si toglie il get della password hash

    //Metodi
    public abstract String getRuolo();
}