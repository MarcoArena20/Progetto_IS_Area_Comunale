package Entity;

import jakarta.persistence.*;

@Entity
public class NotaInternaEntry {

    //Attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotaInterna;
    private String titolo;
    private String descrizione;

    //Costruttore
    public NotaInternaEntry() {

    }

    public NotaInternaEntry(String titolo, String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
    }

    //Getter e Setter
    public Long getIdNotaInterna() {
        return idNotaInterna;
    }
    public void setIdNotaInterna(Long idNotaInterna) {
        this.idNotaInterna = idNotaInterna;
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}