package Entity.StateMachine;

import Entity.Enum.StatoType;
import Entity.Segnalazione;

public class StatoInLavorazione extends StatoSegnalazione {

    //Costruttore
    public StatoInLavorazione() {}

    //Metodi
    @Override
    public boolean aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoInLavorazione] Invocato aggiornaStato con esito: " + esito);

        if (esito) {
            //Avanzo di stato..
            System.out.println("[StatoInLavorazione] Avanzamento di stato..");
            segnalazione.setStato(new StatoRisolta());
        } else {
            //Ritorno a StatoInviata
            System.out.println("[StatoInLavorazione] Reset stato..");
            segnalazione.setStato(new StatoInviata());
        }

        return true;
    }

    @Override
    public String getStatoToString() {
        return StatoType.IN_LAVORAZIONE.name();
    }


}
