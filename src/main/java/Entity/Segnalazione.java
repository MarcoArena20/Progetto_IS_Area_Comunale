package Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Segnalazione {

    private String titolo;
    private String descrizione;
    private Categoria categoria;
    private String posizione;
    private String idCittadino;
    private Stato stato;
    private LocalDateTime data;
    private String urlImmagine;
    private List<NotaInterna> elencoNote;

    // Costruttore con parametri essenziali come da diagramma (Stato predefinito su INVIATA)
    public Segnalazione(String titolo, String descrizione, Categoria categoria, String posizione, String idCittadino) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.posizione = posizione;
        this.idCittadino = idCittadino;
        this.stato = Stato.INVIATA; // Default
        this.data = LocalDateTime.now();
        this.elencoNote = new ArrayList<>();
    }

    // Metodi di business della classe
    public void aggiungiNota(String titolo, String descrizione) {
        this.elencoNote.add(new NotaInterna(titolo, descrizione));
    }

    public void cambiaStato(Operatore operatore) {
        // Logica per cambiare stato (es. da INVIATA a PRESA_IN_CARICO)
    }

    // Getter e Setter
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public String getPosizione() { return posizione; }
    public void setPosizione(String posizione) { this.posizione = posizione; }
    public String getIdCittadino() { return idCittadino; }
    public void setIdCittadino(String idCittadino) { this.idCittadino = idCittadino; }
    public Stato getStato() { return stato; }
    public void setStato(Stato stato) { this.stato = stato; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public String getUrlImmagine() { return urlImmagine; }
    public void setUrlImmagine(String urlImmagine) { this.urlImmagine = urlImmagine; }
    public List<NotaInterna> getElencoNote() { return elencoNote; }
}