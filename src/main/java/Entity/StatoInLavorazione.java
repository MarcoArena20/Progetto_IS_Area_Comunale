package Entity;

public class StatoInLavorazione implements StatoSegnalazione {

    //Costruttore
    public StatoInLavorazione() {}

    //Metodi
    @Override
    public void aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoInLavorazione] Invocato aggiornaStato con esito: " + esito);

        if (esito == true) {
            //Avanzo di stato..
            System.out.println("[StatoInLavorazione] Avanzamento di stato..");
            segnalazione.setStato(new StatoRisolta());
        } else {
            //Ritorno a StatoInviata
            System.out.println("[StatoInLavorazione] Reset stato..");
            segnalazione.setStato(new StatoInviata());
        }

    }

    @Override
    public StatoInLavorazione getStato() {
        System.out.println("[StatoInLavorazione] " + this.toString());
        return this;
    }

    @Override
    public String toString() {
        return "Stato: InLavorazione";
    }
}
