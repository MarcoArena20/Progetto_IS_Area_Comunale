package Entity;

public class StatoPresaInCarico implements StatoSegnalazione{

    //Costruttore
    public StatoPresaInCarico() {}

    //Metodi
    @Override
    public boolean aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoPresaInCarico] Invocato aggiornaStato con esito: " + esito);

        if (esito == true) {
            //Avanzo di stato..
            System.out.println("[StatoPresaInCarico] Avanzamento di stato..");
            segnalazione.setStato(new StatoInLavorazione());
        } else {
            //Ritorno a StatoInviata
            System.out.println("[StatoPresaInCarico] Reset stato..");
            segnalazione.setStato(new StatoInviata());
        }

        return true;

    }

    @Override
    public StatoPresaInCarico getStato() {
        System.out.println("[StatoPresaInCarico] " + this.toString());
        return this;
    }

    @Override
    public String toString() {
        return "Stato: PresaInCarico";
    }


}
