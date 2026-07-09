package Entity;

public class StatoInviata implements StatoSegnalazione {

    //Costruttore
    public StatoInviata() {}

    //Metodi
    @Override
    public void aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoInviata] Invocato aggiornaStato con esito: " + esito);

        if (esito == true) {
            //Avanzo di stato..
            System.out.println("[StatoInviata] Avanzamento di stato..");
            segnalazione.setStato(new StatoPresaInCarico());
        } else {
            //Operazione non ha effetto
            System.out.println("[StatoInviata] Aggiornamento non ha effetto..");
        }

    }

    @Override
    public StatoInviata getStato() {
        System.out.println("[StatoInviata] " + this.toString());
        return this;
    }

    @Override
    public String toString() {
        return "Stato: Inviata";
    }

    public static void main(String[] args) {
        System.out.println("[StatoInviata] MainTest avviato..");

        StatoInviata statoTest = new StatoInviata();

        System.out.println("[StatoInviata] getStato: "+ statoTest.getStato());
        System.out.println("[StatoInviata] toString: "+ statoTest.toString());


    }

}
