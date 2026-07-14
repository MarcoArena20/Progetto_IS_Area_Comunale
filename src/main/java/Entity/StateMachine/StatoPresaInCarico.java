package Entity.StateMachine;

import Entity.Enum.StatoType;
import Entity.Segnalazione;

public class StatoPresaInCarico extends StatoSegnalazione{

    //Costruttore
    public StatoPresaInCarico() {}

    //Metodi
    @Override
    public boolean aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoPresaInCarico] Invocato aggiornaStato con esito: " + esito);

        if (esito) {
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
    public String getStatoToString() {
        return StatoType.PRESA_IN_CARICO.name();
    }

}
