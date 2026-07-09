package Entity;

public class NotaInterna {

    private String titolo;
    private String descrizione;

    public NotaInterna(String titolo, String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
    }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
}