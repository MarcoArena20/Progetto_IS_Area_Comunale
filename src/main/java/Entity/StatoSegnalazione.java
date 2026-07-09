package Entity;

public interface StatoSegnalazione {

    //Metodi
    public abstract void aggiornaStato(Segnalazione segnalazione, boolean esito);
    public abstract StatoSegnalazione getStato();
}
