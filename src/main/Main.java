package main;

import model.Administrativ;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import model.*;
import service.*;


public class Main {
    static Connection connection;
    static DatabaseService db;
    public static void main(String[] args) {
        Scanner sin = new Scanner(System.in);
        Administrativ admin = new Administrativ();

        String dbUrl = "jdbc:mysql://localhost:3306/mysqljdbc";
        String username = "root";
        String password = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, username, password);
            db = DatabaseService.getDatabaseService(connection, admin);
            AppService.popularePentruTestare(admin, db);
        }
        catch (Exception e){
            System.out.println("Conexiunea cu baza de data nu a putut fi stabilita! Va rugam sa incercati din nou.");
            return;
        }

        System.out.println("Bun venit la aplicatia mea de ride-sharing.");
        while(true){
            System.out.println("\nAlegeti o comanda dintre urmatoarele, folosind numarul ei: ");
            System.out.println("1. Creare obiect");
            System.out.println("2. Afisare obiecte existente");
            System.out.println("3. Acordare cate un cupon la intamplare fiecarui calator pentru fiecare 3 calatorii efectuate de acesta");
            System.out.println("4. Afisarea distantei maxime pe care o poate face un calator pentru fiecare tip de vehicul pe baza soldului total de pe toate cardurile sale");
            System.out.println("5. Acordarea unui bonus de cate 20 de ron pentru fiecare conducator pentru fiecare 5 curse efectuate");
            System.out.println("6. Protectia animalelor a interzis abuzul cailor! Stergerea tuturor trasurilor care sunt trase de un singur cal");
            System.out.println("7. Taxarea tuturor curselor care nu au fost taxate deja");
            System.out.println("8. Afisarea unui sumar de statistici");
            System.out.println("9. Editare obiect");
            System.out.println("10. Stergere obiect");
            System.out.println("11. Iesire");
            int optiune;
            try {
                optiune = sin.nextInt();
            }
            catch (NumberFormatException err){
                System.out.println("Eroare: format invalid");
                continue;
            }
            if (optiune == 1){
                System.out.println("Alegeti un obect de creat: ");
                System.out.println("1. User");
                System.out.println("2. Vehicul");
                System.out.println("3. Cursa");
                System.out.println("4. Cupon");
                System.out.println("5. Card de plata");
                int op2;
                try {
                    op2 = sin.nextInt();
                }
                catch (NumberFormatException err){
                    System.out.println("Eroare: format invalid");
                    continue;
                }
                if (op2 == 1){
                    AppService.addUser(admin, db);
                }
                else if (op2 == 2){
                    AppService.addVehicul(admin);
                }
                else if (op2 == 3){
                    AppService.addCursa(admin);
                }
                else if (op2 == 4){
                    AppService.addTipCupon(admin, db);
                }
                else if (op2 == 5) {
                    AppService.addCard(admin, db);
                }
            }
            else if(optiune == 2){
                System.out.println("Alegeti un obect de afisat: ");
                System.out.println("1. User");
                System.out.println("2. Vehicul");
                System.out.println("3. Cursa");
                System.out.println("4. Cupon");
                System.out.println("5. Card de plata");
                int op2;
                try {
                    op2 = sin.nextInt();
                }
                catch (NumberFormatException err){
                    System.out.println("Eroare: format invalid");
                    continue;
                }
                if (op2 == 1){
                    AppService.showUseri(admin, db);
                }
                else if (op2 == 2){
                    AppService.showVehicule(admin);
                }
                else if (op2 == 3){
                    AppService.showCurse(admin);
                }
                else if (op2 == 4){
                    AppService.showCupoane(admin, db);
                }
                else if (op2 == 5) {
                    AppService.showCarduri(admin, db);
                }
            }
            else if(optiune == 3){
                    AppService.acordaCupoane(admin, db);
            }
            else if(optiune == 4){
                    AppService.calculeazaDistMaxCalatorii(admin);
            }
            else if(optiune == 5){
                    AppService.acordareBonusConducatori(admin);
            }
            else if(optiune == 6){
                    AppService.stergereTrasuriCalSingur(admin);
            }
            else if(optiune == 7){
                    AppService.taxare(admin);
            }
            else if(optiune == 8){
                    AppService.afisStatistici(admin);
            }
            else if(optiune == 9){
                System.out.println("Alegeti un obect de editat: ");
                System.out.println("1. User");
                System.out.println("2. Cupon");
                System.out.println("3. Card de plata");
                int op2;
                try {
                    op2 = sin.nextInt();
                }
                catch (NumberFormatException err){
                    System.out.println("Eroare: format invalid");
                    continue;
                }
                if (op2 == 1){
                    AppService.editUser(admin, db);
                }
                else if (op2 == 2){
                    AppService.editCupon(admin, db);
                }
                else if (op2 == 3){
                    AppService.editCard(admin, db);
                }
                else{
                    System.out.println("optiune nevalida. back...");
                }
            }
            else if(optiune == 10){
                System.out.println("Alegeti un obect de sters: ");
                System.out.println("1. User");
                System.out.println("2. Cupon");
                System.out.println("3. Card de plata");
                int op2;
                try {
                    op2 = sin.nextInt();
                }
                catch (NumberFormatException err){
                    System.out.println("Eroare: format invalid");
                    continue;
                }
                if (op2 == 1){
                    AppService.deleteUser(admin, db);
                }
                else if (op2 == 2){
                    AppService.deleteCupon(admin, db);
                }
                else if (op2 == 3){
                    AppService.deleteCard(admin, db);
                }
                else{
                    System.out.println("optiune nevalida. back...");
                }
            }
            else if (optiune == 11){
                try{
                    connection.close();
                }
                catch (SQLException e){
                    System.out.println("Inchiderea bazei de date esuata. Iesire din aplicatie...");
                }
                break;
            }
            else if (optiune == 15){
                for (Calator calator : admin.getListaCalatori())
                    System.out.println(calator.getNume());
            }
            else{
                System.out.println("Eroare: Optiune inexistenta");
                continue;
            }

        }


    }
}