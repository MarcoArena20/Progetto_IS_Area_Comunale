package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

//Façade
public class ControllerUtenti {

    public static Long getIdUtenteCorrente(){

        Path path = Path.of("configuration/config.txt");

        try {
            if (!Files.exists(path)) {

                return null;

            }else{

                List<String> lines = Files.readAllLines(path);
                return Long.parseLong(lines.get(0).split(":")[1]);

            }


        }catch(IOException e){

            e.printStackTrace();
            return null;

        }

    }

    public static String getRuoloUtenteCorrente(){

        Path path = Path.of("configuration/config.txt");

        try {
            if (!Files.exists(path)) {

                return null;

            }else{

                List<String> lines = Files.readAllLines(path);
                return lines.get(1).split(":")[1];

            }


        }catch(IOException e){

            e.printStackTrace();
            return null;

        }

    }

    public static void setIdUtenteCorrente(Long idUtenteCorrente, String ruolo){

        // Il primo controllo da fare è verificare se il file esiste, altrimenti va creato da zero con la configurazione
        // di default, ovvero
        // idUtente:
        // ruolo:
        // idSegnalazione:

        Path path = Path.of("configuration/config.txt");

        try {
            if (!Files.exists(path)) {

                Files.createFile(path);
                Files.writeString(path, "idUtente:" + idUtenteCorrente + "\nruolo:" + ruolo + "\nidSegnalazione:\n", StandardOpenOption.APPEND);

            }else{

                List<String> lines = Files.readAllLines(path);
                lines.set(0, "idUtente:" +idUtenteCorrente );
                lines.set(1, "ruolo:" + ruolo);

                Files.write(path, lines);

            }


        }catch(IOException e){

            e.printStackTrace();

        }



    }
}
