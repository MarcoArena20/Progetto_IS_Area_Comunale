package Entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ElencoGestioniSegnalazione {

    //Attributi
    private Segnalazione segnalazione;
    private Map<LocalDateTime, NotaInterna> storicoNote;
    private Map<LocalDateTime, StatoSegnalazione> storicoStato;
    private Map<LocalDateTime, String> storicoOperatore;

    //Costruttore
    public ElencoGestioniSegnalazione(Segnalazione segnalazione) {
        this.segnalazione = segnalazione;
        this.storicoNote = new HashMap<>();
        this.storicoStato = new HashMap<>();
        this.storicoOperatore = new HashMap<>();
    }

    //Metodi
    public void salvaOperatore(String idOperatore) {
        this.storicoOperatore.put(LocalDateTime.now(), idOperatore);
        System.out.println("[ElencoGestioniSegnalazione] Aggiunto operatore: "+idOperatore);
    }

    public void salvaCambiamentoStato(StatoSegnalazione stato) {
        this.storicoStato.put(LocalDateTime.now(), stato);
        System.out.println("[ElencoGestioniSegnalazione] Aggiunto cambiamento stato in: "+stato.toString());
    }

    public void salvaNota(String titolo, String descrizione) {
        NotaInterna nota = new NotaInterna(titolo, descrizione);

        this.storicoNote.put(LocalDateTime.now(), nota);
        System.out.println("[ElencoGestioniSegnalazione] Aggiunto stato: "+nota.toString());

    }



}

