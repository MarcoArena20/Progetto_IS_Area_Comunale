package Entity;

public class StatoRisolta implements StatoSegnalazione {

    //Costruttore
    public StatoRisolta() {}

    //Metodi
    @Override
    public void aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoRisolta] Impossibile modificare lo stato\n" + this.toString());
    }

    @Override
    public StatoRisolta getStato() {
        System.out.println("[StatoRisolta] " + this.toString());
        return this;
    }

    @Override
    public String toString() {
        return "Stato: Risolta";
    }

}
