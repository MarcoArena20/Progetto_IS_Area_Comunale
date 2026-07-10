package Entity;

public class NotaInterna {

    //Attributi
    private String titolo;
    private String descrizione;

    //Costruttore
    public NotaInterna(String titolo, String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
    }

    //Getter
    public String getTitolo() { return titolo; }
    public String getDescrizione() { return descrizione; }

    //Metodi
    @Override
    public String toString() {
        return "Titolo:\t"+this.titolo +"\nDescrizione:\t" + this.descrizione;
    }
}