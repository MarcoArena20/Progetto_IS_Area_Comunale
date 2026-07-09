package Entity;

public interface StatoSegnalazione {

    //Metodi
    public abstract boolean aggiornaStato(Segnalazione segnalazione, boolean esito);
    public abstract StatoSegnalazione getStato();
}
