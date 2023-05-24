package service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AuditService {
    static String filePath = "logs.csv";

    static SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' hh:mm:ss a");


    public static void addLogEntry(String operationType, Calendar timestamp){
        try{
            String formattedDate = sdf.format(timestamp.getTime());
            String content = operationType + ", " + formattedDate + "\n";
            Files.write(Path.of(filePath), content.getBytes(), StandardOpenOption.APPEND);
            System.out.println("Adaugare la DataBaseLog cu succes!");

        }
        catch (IOException e){
            System.out.println("Eroare la adaugarea intrarii in DataBaseLog");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }
}


