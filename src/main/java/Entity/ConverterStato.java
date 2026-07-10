package Entity;

import jakarta.persistence.AttributeConverter;

public class ConverterStato implements AttributeConverter<StatoSegnalazione, String> {

    @Override
    public String convertToDatabaseColumn(StatoSegnalazione stato){

        if (stato instanceof StatoInviata) return StatoType.INVIATA.name();
        if (stato instanceof StatoPresaInCarico) return StatoType.PRESA_IN_CARICO.name();
        if (stato instanceof StatoInLavorazione) return StatoType.IN_LAVORAZIONE.name();
        if (stato instanceof StatoRisolta) return StatoType.RISOLTA.name();

        throw new IllegalArgumentException("Stato non supportato");

    }


    @Override
    public StatoSegnalazione convertToEntityAttribute(String value){

        StatoType type = StatoType.valueOf(value);

        return switch(type){

            case INVIATA -> new StatoInviata();
            case PRESA_IN_CARICO -> new StatoPresaInCarico();
            case IN_LAVORAZIONE -> new StatoInLavorazione();
            case RISOLTA -> new StatoRisolta();

        };

    }
}
