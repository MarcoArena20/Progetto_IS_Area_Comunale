package Entity;
import java.util.UUID;
import java.util.Date;

public class Segnalazione {
    private UUID idSegnalazione;
    private String titolo;
    private String descrizione;
    private Categoria categoria;
    private String posizione;
    private Date data;
    private Immagine immagine;
    private Stato stato;
    private UUID idCittadino;
}
