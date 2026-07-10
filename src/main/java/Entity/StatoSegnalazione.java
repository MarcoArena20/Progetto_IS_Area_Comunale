package Entity;

import jakarta.persistence.Entity;

public abstract class StatoSegnalazione {

    //Metodi
    public abstract boolean aggiornaStato(Segnalazione segnalazione, boolean esito);
    public abstract String getStatoToString();

    @Override
    public String toString() {
        return "Stato: " + this.getStatoToString();
    }
}
