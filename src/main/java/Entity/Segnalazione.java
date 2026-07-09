package Entity;

import java.time.LocalDateTime;

public class Segnalazione {

    //Attributi
    private String idSegnalazione;
    private String titolo;
    private String descrizione;
    private Categoria categoria;
    private String posizione;
    private String idCittadino;
    private Stato stato;
    private LocalDateTime data;
    private String urlImmagine;

    //Costruttore
    public Segnalazione(String titolo, String descrizione, Categoria categoria, String posizione, LocalDateTime data, String urlImmagine) {
        //TODO
        //Generare automaticamente idSegnalazione

        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.posizione = posizione;
        this.data = data;
        this.urlImmagine = urlImmagine;
        this.stato = Stato.INVIATA; // Default
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
    public void setIdCittadino(String idCittadino){ this.idCittadino = idCittadino; }
    public Stato getStato() { return stato; }
    public void setStato(Stato stato) { this.stato = stato; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public String getUrlImmagine() { return urlImmagine; }
    public void setUrlImmagine(String urlImmagine) { this.urlImmagine = urlImmagine; }

    // Metodi
    public boolean aggiungiNota(String titoloNota, String descrizioneNota) {
        //TODO
        return true;
    }

    public boolean iniziaGestione(String idOperatore) {
        //TODO
        return true;
    }

    public boolean concludiGestione() {
        //TODO
        return true;
    }



}