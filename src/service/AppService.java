package service;

import model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static resources.Strings.coduriJudete;

public class AppService {
    static Scanner sin = new Scanner(System.in);

    public static Calendar chooseData(){
        System.out.print("Data (DD/MM/YYYY): ");
        String strData = sin.nextLine();
        return stringToData(strData);
    }
    public static Calendar stringToData(String strData){
        Calendar data;
        String[] args;
        if(strData.equals(""))
            return null;
        int zi, luna, an;
        try{
            args = strData.split("/");
            zi = Integer.parseInt(args[0]);
            luna = Integer.parseInt(args[1]);
            an = Integer.parseInt(args[2]);
            data = Calendar.getInstance();
            data.set(an, luna, zi);
        }
        catch (NumberFormatException er){
            System.out.println("Adaugare esuata: Nu ati respectat formatul de data!");
            return null;
        }
        return data;
    }

    public static void addUser(Administrativ admin, DatabaseService db){

        System.out.print("Ce fel de user doriti sa adaugati?(1 pentru conducator si 2 pentru calator) ");
        int optiune = Integer.parseInt(sin.nextLine());
        if (optiune != 1 && optiune != 2){
            System.out.println("Adaugare esuata: Ati selectat o optiune invalida!");
            return;
        }
        System.out.print("Toate campurile sunt obligatorii.\nNume: ");
        String nume = sin.nextLine();

        System.out.print("Rating (1-5): ");
        String strRating = sin.nextLine();
        int rating;
        try{
            rating = Integer.parseInt(strRating);
        }
        catch (NumberFormatException eroare){
            System.out.println("Adaugare esuata.: nu ati scris un numar.");
            return;
        }

        Calendar data = chooseData();
        if (data == null)
            return;

        if (optiune == 1){
            try{
                db.createConducator(nume, rating, data);
            }
            catch (SQLException e){
                System.out.println("Adaugarea conducatorului la BD esuata.");
            }


        }
        else {
            try{
                db.createCalator(nume, rating, data);
            }
            catch (SQLException e){
                System.out.println("Adaugarea calatorului la BD esuata.");
            }

        }
    }

    public static String printData(Calendar data){
        return data.get(5) + "/" + data.get(2) + 1 + "/" + data.get(1);   //pentru DD/MM/YYYY
    }
    public static Conducator chooseConducator(Administrativ admin){
        for (int i = 0; i < admin.getListaConducatori().size(); ++i){
            System.out.println(i+1 + ". " + admin.getListaConducatori().get(i).getNume());
        }
        System.out.print("Alegeti numarul unui conducator: ");
        int indexConduc;
        try{
            indexConduc = sin.nextInt();
            sin.nextLine();
        }
        catch (NumberFormatException err){
            System.out.println("Eroare: format invalid");
            return null;
        }
        return admin.getListaConducatori().get(indexConduc-1);
    }

    public static Calator chooseCalator(Administrativ admin){
        for (int i = 0; i < admin.getListaCalatori().size(); ++i){
            System.out.println(i+1 + ". " + admin.getListaCalatori().get(i).getNume());
        }
        System.out.print("Alegeti numarul unui calator: ");
        int indexCalat;
        try{
           indexCalat = sin.nextInt();
            sin.nextLine();
        }
        catch (NumberFormatException err){
            System.out.println("Eroare: format invalid");
            return null;
        }
        return admin.getListaCalatori().get(indexCalat-1);
    }

    public static Vehicul chooseVehicul(Administrativ admin){
        for (int i = 0; i < admin.getListaVehicule().size(); ++i){
            System.out.println(i+1 + ". " + admin.getListaVehicule().get(i).getNume());
        }
        System.out.print("Alegeti numarul unui vehicul: ");
        int indexVehicul;
        try {
            indexVehicul = sin.nextInt();
            sin.nextLine();
        }
        catch (NumberFormatException err){
            System.out.println("Eroare: format invalid");
            return null;
        }
        return admin.getListaVehicule().get(indexVehicul-1);
    }

    public static Integer chooseCard(Administrativ model, DatabaseService db) throws SQLException {
        System.out.println("Alegeti numarul unui card:");
        db.readCard();
        Integer rez = null;
        try {
            rez = sin.nextInt();
            sin.nextLine();
        }
        catch (NumberFormatException e){
            System.out.println("Nu ati introdus un numar");
            return null;
        }
        return rez;
    }

    public static Integer chooseCupon(Administrativ model, DatabaseService db) throws SQLException {
        System.out.println("Alegeti numarul unui cupon:");
        db.readCupon();
        Integer rez = null;
        try {
            rez = sin.nextInt();
            sin.nextLine();
        }
        catch (NumberFormatException e){
            System.out.println("Nu ati introdus un numar");
            return null;
        }
        return rez;
    }

    public static void addVehicul(Administrativ admin){
        System.out.println("Toate campurile sunt obligatorii.\nCe tip de vehicul doriti sa introduceti (alegeti numarul)\n1 - Masina" +
                "\n2 - Trasura\n3 - Elicopter\n4 - Motocicleta cu atas");
        int optiune = sin.nextInt();
        sin.nextLine();
        if (optiune < 1 || optiune > 4){
            System.out.println("Adaugare esuata: optiune nevalida");
        }

        System.out.print("Nume: ");
        String nume = sin.nextLine();


        System.out.print("Numar de inmatriculare (JJ NN LLL)/(B NNN LLL)/(B NN LLL): ");
        String inputInmatr = sin.nextLine();
        if (inputInmatr.matches("B [1-9][0-9]{1,2} [A-Z]{3}") || inputInmatr.matches("[A-Z]{2} [1-9][0-9] [A-Z]{3}")){
            String[] args = inputInmatr.split(" ");
            if (!coduriJudete.contains(args[0])){
                System.out.println("Adaugare esuata: nu exista codul de judet introdus");
                return;
            }
        }
        else{
            System.out.println("Adaugare esuata: format nevalid");
            return;
        }

        System.out.print("Culoare: ");
        String culoare = sin.next();

        Conducator posesor = AppService.chooseConducator(admin);
        if (posesor == null)
            return;

        if (optiune == 1){          //Masina
            boolean decapotabila;
            System.out.print("Este decapotabila?(da/nu) ");
            String raspDecap = sin.nextLine();
            if (raspDecap.equals("da"))
                decapotabila = true;
            else if(raspDecap.equals("nu"))
                decapotabila = false;
            else{
                System.out.println("Adaugare esuata: raspuns invalid");
                return;
            }

            boolean canDrift;
            System.out.print("Poate face drifturi?(da/nu) ");
            String raspDrift = sin.nextLine();
            if (raspDrift.equals("da"))
                canDrift = true;
            else if(raspDrift.equals("nu"))
                canDrift = false;
            else{
                System.out.println("Adaugare esuata: raspuns invalid");
                return;
            }

            Vehicul masinaNou = new Masina(nume, inputInmatr, culoare, posesor, decapotabila, canDrift);
            posesor.getOwnVehicles().add(masinaNou);
            admin.getListaVehicule().add(masinaNou);
        }
        else if (optiune == 2){        //Trasura
            System.out.print("Nr cai: ");
            int nrCai;
            try{
                nrCai = sin.nextInt();
                sin.nextLine();
            }
            catch (NumberFormatException err) {
                System.out.println("Adaugare esuata: Format nevalid");
                return;
            }

            boolean moderna;
            System.out.println("Este moderna?(da/nu) ");
            String raspModerna = sin.nextLine();
            if (raspModerna.equals("da"))
                moderna = true;
            else if(raspModerna.equals("nu"))
                moderna = false;
            else{
                System.out.println("Adaugare esuata: raspuns invalid");
                return;
            }

            Vehicul trasuraNou = new Trasura(nume, inputInmatr, culoare, posesor, nrCai, moderna);
            posesor.getOwnVehicles().add(trasuraNou);
            admin.getListaVehicule().add(trasuraNou);

        }
        else if (optiune == 3){         //Elicopter
            System.out.print("Altitudinea maxima: ");
            int altMax;
            try{
                altMax = sin.nextInt();
                sin.nextLine();
            }
            catch (NumberFormatException err) {
                System.out.println("Adaugare esuata: Format nevalid");
                return;
            }
            Vehicul elicopterNou = new Elicopter(nume, inputInmatr, culoare, posesor, altMax);
            posesor.getOwnVehicles().add(elicopterNou);
            admin.getListaVehicule().add(elicopterNou);
        }
        else{                           //Motocicleta atas
            boolean vesta;
            System.out.print("Are vesta de biker inclusa pentru pasager?(da/nu) ");
            String raspVesta = sin.nextLine();
            if (raspVesta.equals("da"))
                vesta = true;
            else if(raspVesta.equals("nu"))
                vesta = false;
            else{
                System.out.println("Adaugare esuata: raspuns invalid");
                return;
            }

            Vehicul motoNou = new MotocicletaAtas(nume, inputInmatr, culoare, posesor, vesta);
            posesor.getOwnVehicles().add(motoNou);
            admin.getListaVehicule().add(motoNou);
        }
        System.out.println("Vehicul adaugat cu succes!");
    }

    public static void addCard(Administrativ admin, DatabaseService db){
        System.out.print("Toate campurile sunt obligatorii\nSold: ");
        int sold;
        try{
            sold = sin.nextInt();
            sin.nextLine();
        }
        catch (NumberFormatException err){
            System.out.println("Adaugare esuata: format numar invalid");
            return;
        }

        System.out.print("Numar card: ");
        String numar = sin.nextLine();
        if (!numar.matches("[0-9]{16}")){
            System.out.println("Adaugare esuata: numarul cardului nu este format din 16 cifre");
            return;
        }
        Calator posesor = chooseCalator(admin);
        if (posesor == null)
            return;


        try{
            db.createCard(numar, sold, posesor.getUserId());
        }
        catch (SQLException e){
            System.out.println("Adaugare esuata la baza de date");
            return;
        }

        CardPlata cardNou = new CardPlata(numar, sold, posesor);
        posesor.getCarduri().add(cardNou);
        System.out.println("Card adaugat cu succes!");
    }

    public static void addTipCupon(Administrativ admin, DatabaseService db){
        int procentual;
        System.out.print("Este de tip procentual sau absolut?(procentual/absolut) ");
        String raspModerna = sin.nextLine();
        if (raspModerna.equals("procentual"))
            procentual = 1;
        else if(raspModerna.equals("absolut"))
            procentual = 0;
        else{
            System.out.println("Adaugare esuata: raspuns invalid");
            return;
        }

        System.out.print("Valoare reducere: ");
        int valoare;
        try{
            valoare = sin.nextInt();
            sin.nextLine();
        }
        catch (NumberFormatException err){
            System.out.println("Adaugare esuata: format numar invalid");
            return;
        }

        Calendar data = chooseData();
        if (data == null)
            return;

        try{
            db.createCupon(procentual, valoare, data);
        }
        catch (SQLException e){
            System.out.println("Adaugare esuata la baza de date");
            return;
        }

        Cupon cupon = new Cupon(procentual, valoare, data);
        admin.getListaCupoane().add(cupon);

    }

    public static void addCursa(Administrativ admin){
        Calendar data = AppService.chooseData();
        Conducator conducator = AppService.chooseConducator(admin);
        Calator calator = AppService.chooseCalator(admin);
        Vehicul vehicul = AppService.chooseVehicul(admin);
        if (conducator == null || calator == null || vehicul == null)
            return;

        System.out.print("Pret: ");
        double pret;
        try{
            pret = sin.nextDouble();
        }
        catch (NumberFormatException err){
            System.out.println("Adaugare esuata: format numar invalid");
            return;
        }

        System.out.print("Punct plecare: ");
        String plecare = sin.nextLine();
        System.out.print("Destinatie: ");
        String destinatie = sin.nextLine();

        Cursa newCursa = new Cursa(data, conducator, calator, vehicul, pret, plecare, destinatie);
        admin.getListaCurse().add(newCursa);
        calator.getCurse().add(newCursa);
        conducator.getCurse().add(newCursa);
    }

    public static void showUseri(Administrativ admin, DatabaseService db){
        /* version 1 with administration item list, use in case database fails
        System.out.println("Calatori:");
        for (Calator calator : admin.getListaCalatori()){
            System.out.println(calator.toString());
        }
        System.out.println("\nConducatori: ");
        for (Conducator conducator : admin.getListaConducatori()){
            System.out.println(conducator.toString());
        }*/
        try{
            db.readConducator();
            db.readCalator();
        }
        catch (SQLException e){
            System.out.println("Afisare useri esuata");
        }

    }

    public static void showVehicule(Administrativ admin){
        for (Vehicul vehicul : admin.getListaVehicule()){
            System.out.println(vehicul.toString());
        }
    }

    public static void showCurse(Administrativ admin){
        for (Cursa cursa : admin.getListaCurse()){
            System.out.println(cursa.toString());
        }
    }

    public static void showCarduri (Administrativ admin, DatabaseService db){
        try{
            db.readCard();
        }
        catch (SQLException e){
            System.out.println("Afisare carduri esuata din cauza BD");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
    }

    public static void showCupoane(Administrativ admin, DatabaseService db){
        try{
            db.readCupon();
        }
        catch (SQLException e){
            System.out.println("Afisare cupoane esuata din cauza BD");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
    }

    public static void afisStatistici (Administrativ admin){
        System.out.println("Statistici sumare: ");

        System.out.println();
        System.out.println("Profitul generat de aplicatie: " + Administrativ.getCastig());
        System.out.println();
        System.out.println("Conducatorul cu cele mai multe curse efectuate: ");
        int maxim = -1;
        Conducator ales = null;
        for (Conducator conducator : admin.getListaConducatori()){
            if (conducator.getCurse().size() > maxim){
                maxim = conducator.getCurse().size();
                ales = conducator;
            }
        }
        System.out.println(ales.toString());
        System.out.println("A efectuat " + maxim + " curse.\n");

        System.out.println("Calatorul cu cele mai valoroase cupoane: ");
        maxim = 0;
        Calator calAles = null;
        for (Calator calator : admin.getListaCalatori()){
            int suma = 0;
            for (Cupon cupon : calator.getCupoane()){
                suma += cupon.getValoare();
            }
            if (suma > maxim){
                calAles = calator;
                maxim = suma;
            }
        }
        if (calAles == null){
            System.out.println("Statisticile de cupoane nu au putut fi generate deoarece niciun calator nu a primit cupoane. Acordati intai cupoane pentru a efectua operatiunea!");
            return;
        }
        System.out.println(calAles.toString());
        System.out.println("Are o valoare acumulata a cupoanelor, si procentuala si absoluta, de " + maxim + " unitati.");
    }

    public static void acordaCupoane(Administrativ admin, DatabaseService db){
        Random rnd = new Random();
        if (admin.getListaCupoane().size() < 1){
            System.out.println("Operatiune esuata: Nu exista niciun tip de cupon in sistem!");
            return;
        }

        for (int i = 0; i < admin.getListaCalatori().size(); ++i){
            for (int j = 0; j < admin.getListaCalatori().get(i).getCurse().size() / 3; ++j){
                int randIndexCupon = rnd.nextInt(admin.getListaCupoane().size());
                for (Cupon cupon : admin.getListaCupoane()){
                    if (--randIndexCupon < 0) {
                        admin.getListaCalatori().get(i).getCupoane().add(cupon);
                        try {
                            db.adaugareCuponLaCalatorInDb(cupon.getCuponId(), admin.getListaCalatori().get(i).getUserId());
                        }
                        catch (SQLException e){
                            System.out.println("Acordarea cupoanelor in BD esuata");
                            System.out.println(e.getMessage());
                            System.out.println(e.getSQLState());
                        }
                        break;
                    }
                }
            }
        }

        System.out.println("Cupoane acordate cu succes!");
    }

    public static void calculeazaDistMaxCalatorii(Administrativ admin){
        Calator calator = chooseCalator(admin);
        if (calator == null)
            return;
        double sold_total = 0;
        for (CardPlata card : calator.getCarduri()){
            sold_total += card.getSold();
        }
        System.out.println("Masina: " + sold_total / Masina.getPretPerKm() + " kilometri.");
        System.out.println("Trasura: " + sold_total / Trasura.getPretPerKm() + " kilometri.");
        System.out.println("Elicopter: " + sold_total / Elicopter.getPretPerKm() + " kilometri.");
        System.out.println("Motocicleta cu atas: " + sold_total / MotocicletaAtas.getPretPerKm() + " kilometri.");
    }

    public static void acordareBonusConducatori (Administrativ admin){
        for (int i = 0; i < admin.getListaConducatori().size(); ++i){
            // nu acordam un bonus de doua ori pe aceleasi curse
            int cateBonusuri = admin.getListaConducatori().get(i).getCurse().size() / 5 - admin.getListaConducatori().get(i).getNrBonusuriAcordate();
            admin.getListaConducatori().get(i).setCastigTotal(admin.getListaConducatori().get(i).getCastigTotal()
                    + 20 * cateBonusuri);
            admin.getListaConducatori().get(i).setNrBonusuriAcordate(admin.getListaConducatori().get(i).getNrBonusuriAcordate() + cateBonusuri);
        }

        System.out.println("Bonusuri acordate cu succes!");
    }

    public static void stergereTrasuriCalSingur(Administrativ admin){
        for (int i = 0; i < admin.getListaVehicule().size(); ++i){
            if (admin.getListaVehicule().get(i) instanceof Trasura){
                if (((Trasura) admin.getListaVehicule().get(i) ).getNrCai() == 1){
                    System.out.println("Sterg trasura " + ((Trasura) admin.getListaVehicule().get(i)).getNume());
                    admin.getListaVehicule().remove(i);
                    --i;
                }
            }
        }
        for (Conducator conducator : admin.getListaConducatori()) {
            for (int i = 0; i < conducator.getOwnVehicles().size(); ++i) {
                if (conducator.getOwnVehicles().get(i) instanceof Trasura) {
                    if (((Trasura) conducator.getOwnVehicles().get(i)).getNrCai() == 1) {
                        conducator.getOwnVehicles().remove(i);
                        --i;
                    }
                }
            }
        }
        System.out.println("Stergerea trasurilor cu un cal efectuata cu succes!");
    }
    public static void taxare(Administrativ admin){
        for (Cursa cursa : admin.getListaCurse()){
            if (!cursa.isTaxata()){
                cursa.setTaxata(true);
                double taxa = cursa.getPret() * 0.2;
                cursa.getConducator().setCastigTotal(cursa.getConducator().getCastigTotal() - cursa.getPret() * 0.2);
                Administrativ.setCastig(Administrativ.getCastig() + taxa);
            }
        }
        System.out.println("Taxare efectuata cu succes");
    }

    public static void editUser(Administrativ admin, DatabaseService db){
        User ales = null;
        System.out.println("Doriti sa editati un <calator> sau un <conducator>?");
        String rez = sin.nextLine();
        if (rez.equals("")){
            System.out.println("Alegere invalida, editare esuata");
        }
        else if (rez.equals("calator"))
            ales = chooseCalator(admin);
        else if(rez.equals("conducator"))
            ales = chooseConducator(admin);
        else
            System.out.println("Alegere invalida, editare esuata");
        
        if(ales == null){
            System.out.println("Utilizator nevalid, editare esuata");
            return;
        }
            
        
        System.out.println("Campurile sunt optionale, dar macar un camp trebuie completat");
        System.out.print("Nume: ");
        String nume = sin.nextLine();
        if (nume.equals(""))
            nume = null;

        System.out.print("Rating (1-5): ");
        String strRating = sin.nextLine();
        Integer rating;
        if (strRating.equals(""))
            rating = null;
        else{
            try{
                rating = Integer.parseInt(strRating);
            }
            catch (NumberFormatException eroare){
                System.out.println("Adaugare esuata.: nu ati scris un numar.");
                return;
            }
        }


        Calendar data = chooseData();
        if (data == null && rating == null && nume == null){
            System.out.println("Editare esuata; macar un camp trebuie modificat");
            return;
        }
        try {
            db.updateUser(ales.getUserId(), nume, rating, data);
        }
        catch (SQLException e) {
            System.out.println("Editare esuata din cauza bazei de date");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            return;
        }
    }

    public static void editCard(Administrativ admin, DatabaseService db){
        CardPlata ales = null;
        Integer idAles;
        try{
            idAles = chooseCard(admin, db);
        }
        catch (SQLException e){
            System.out.println("Eroare BD");
            return;
        }
        if (idAles == null)
            return;

        System.out.println("Campurile sunt optionale, dar macar un camp trebuie completat");
        System.out.print("Numar card: ");
        String numar = sin.nextLine();
        if (numar.equals(""))
            numar = null;

        System.out.print("Sold: ");
        String soldStr = sin.nextLine();
        Integer sold;
        if (soldStr.equals(""))
            sold = null;
        else{
            try{
                sold = Integer.parseInt(soldStr);
            }
            catch (NumberFormatException eroare){
                System.out.println("Adaugare esuata.: nu ati scris un numar.");
                return;
            }
        }


        Calator aless = chooseCalator(admin);
        if (numar == null && sold == null && aless == null){
            System.out.println("Editare esuata; macar un camp trebuie modificat");
            return;
        }
        try {
            db.updateCard(idAles, numar, sold, aless.getUserId());
        }
        catch (SQLException e) {
            System.out.println("Editare esuata din cauza bazei de date");
            return;
        }
    }

    public static void editCupon(Administrativ admin, DatabaseService db){
        Cupon ales = null;
        Integer idAles;
        try{
            idAles = chooseCupon(admin, db);

        }
        catch (SQLException e){
            System.out.println("Eroare BD");
            return;
        }
        if (idAles == null)
            return;

        System.out.println("Campurile sunt optionale, dar macar un camp trebuie completat");
        System.out.println("<procentual> sau <absolut>: ");
        String numar = sin.nextLine();
        Integer procentual;
        if (numar.equals(""))
            procentual = null;
        else if(numar.equals("procentual")){
            procentual = 1;
        }
        else if(numar.equals("absolut")){
            procentual = 0;
        }
        else {
            System.out.println("Varianta invalida, editare esuata");
            return;
        }

        System.out.print("Valoare: ");
        String valStr = sin.nextLine();
        Integer val;
        if (valStr.equals(""))
            val = null;
        else{
            try{
                val = Integer.parseInt(valStr);
            }
            catch (NumberFormatException eroare){
                System.out.println("Adaugare esuata.: nu ati scris un numar.");
                return;
            }
        }


        Calendar data = chooseData();
        if (procentual == null && val == null && data == null){
            System.out.println("Editare esuata; macar un camp trebuie modificat");
            return;
        }
        try {
            db.updateCupon(idAles, procentual, val, data);
        }
        catch (SQLException e) {
            System.out.println("Editare esuata din cauza bazei de date");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            return;
        }
    }

    public static void deleteUser(Administrativ admin, DatabaseService db){
        System.out.println("Doriti sa stergeti <conducator> sau <calator>: ");
        String rez = sin.nextLine();
        if (rez == null){
            System.out.println("Alegere nevalida, stergere imposibila");
        }
        else if(rez.equals("calator")){
            Calator ales = chooseCalator(admin);
            try{
                db.deleteUser(ales.getUserId());
            }
            catch (SQLException e){
                System.out.println("Eroare bd, abort");
                return;
            }

        }
        else if(rez.equals("conducator")){
            Conducator ales = chooseConducator(admin);
            try{
                db.deleteUser(ales.getUserId());
            }
            catch (SQLException e){
                System.out.println("Eroare bd, abort");
                return;
            }
        }
        else{
            System.out.println("alegere nevalida, stergere imposibila");
            return;
        }
    }

    public static void deleteCard(Administrativ admin, DatabaseService bd){
        try{
            Integer ales = chooseCard(admin, bd);
            bd.deleteCard(ales);
        }
        catch (SQLException e){
            System.out.println("Eroare BD, abort");
        }
    }

    public static void deleteCupon(Administrativ admin, DatabaseService bd){
        try{
            Integer ales = chooseCupon(admin, bd);
            bd.deleteCupon(ales);
        }
        catch (SQLException e){
            System.out.println("Eroare BD, abort");
        }
    }

    public static void popularePentruTestare(Administrativ admin, DatabaseService db){
        Calendar timp = Calendar.getInstance();
        try{
            if (!db.checkCalatorInDB(db)){
                db.createCalator("Ion Pop", 4, timp);
                db.createCalator("Alex Ver", 2, timp);
                db.createCalator("Maria Andu", 3, timp);
                db.createCalator("Carmen Carol", 1, timp);
                db.createCalator("Mihai Stefan", 5, timp);
            }

            if(!db.checkConducatorInDB(db))
            {
                db.createConducator("Vasile Grigorescu", 2, timp);
                db.createConducator("Alexandru Madon", 3, timp);
                db.createConducator("Stefan Ghiorghidiu", 3, timp);
                db.createConducator("Ion Glanetasu", 1, timp);
                db.createConducator("Maria Remarque", 4, timp);
            }

            if (!db.checkCuponInDB(db))
            {
                db.createCupon(1, 10, timp);
                db.createCupon(0, 5, timp);
                db.createCupon(1, 2, timp);
                db.createCupon(1, 15, timp);
                db.createCupon(0, 25, timp);
            }

            if (!db.checkCardInDB(db))
            {
                db.createCard("1234567890123456", 123, 0);
                db.createCard("1234567890123356", 432, 0);
                db.createCard("1234567890163456", 12, 0);
                db.createCard("1234567899123456", 53, 1);
                db.createCard("1234567190123456", 1233, 1);
                db.createCard("1234587890123456", 132, 1);
                db.createCard("1234567890123456", 125, 2);
                db.createCard("1834567890123456", 234, 2);
                db.createCard("1134567890123456", 22, 2);
                db.createCard("9234567890123456", 64, 3);
                db.createCard("1734567890123456", 234, 3);
                db.createCard("1234567840123456", 745, 3);
                db.createCard("1234567835123456", 234, 4);
                db.createCard("123456712123456", 1236, 4);
                db.createCard("1234567690123456", 433, 4);
            }
        }
        catch (SQLException e){
            System.out.println("Adaugare esuata la DB a datelor de test");
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
            return;
        }


        CardPlata card1 = new CardPlata("1234567890123456", 123);
        CardPlata card2 = new CardPlata("1234567890123356", 432);
        CardPlata card3 = new CardPlata("1234567890163456", 12);
        CardPlata card4 = new CardPlata("1234567899123456", 53);
        CardPlata card5 = new CardPlata("1234567190123456", 1233);
        CardPlata card6 = new CardPlata("1234587890123456", 132);
        CardPlata card7 = new CardPlata("1234567890123456", 125);
        CardPlata card8 = new CardPlata("1834567890123456", 234);
        CardPlata card9 = new CardPlata("1134567890123456", 22);
        CardPlata card10 = new CardPlata("9234567890123456", 64);
        CardPlata card11 = new CardPlata("1734567890123456", 234);
        CardPlata card12 = new CardPlata("1234567840123456", 745);
        CardPlata card13 = new CardPlata("1234567835123456", 234);
        CardPlata card14 = new CardPlata("123456712123456", 1236);
        CardPlata card15 = new CardPlata("1234567690123456", 433);

        ArrayList <CardPlata> carduri1 = new ArrayList<CardPlata>();
        ArrayList <CardPlata> carduri2 = new ArrayList<CardPlata>();
        ArrayList <CardPlata> carduri3 = new ArrayList<CardPlata>();
        ArrayList <CardPlata> carduri4 = new ArrayList<CardPlata>();
        ArrayList <CardPlata> carduri5 = new ArrayList<CardPlata>();

        carduri1.add(card1);
        carduri1.add(card2);
        carduri1.add(card3);
        carduri2.add(card4);
        carduri2.add(card5);
        carduri2.add(card6);
        carduri3.add(card7);
        carduri3.add(card8);
        carduri3.add(card9);
        carduri4.add(card10);
        carduri4.add(card11);
        carduri4.add(card12);
        carduri5.add(card13);
        carduri5.add(card14);
        carduri5.add(card15);

        Calator cal1 = new Calator("Ion Pop", 4, timp, carduri1);
        Calator cal2 = new Calator("Alex Ver", 2, timp, carduri2);
        Calator cal3 = new Calator("Maria Andu", 3, timp, carduri3);
        Calator cal4 = new Calator("Carmen Carol", 1, timp, carduri4);
        Calator cal5 = new Calator("Mihai Stefan", 5, timp, carduri5);

        card1.setPosesor(cal1);
        card2.setPosesor(cal1);
        card3.setPosesor(cal1);
        card4.setPosesor(cal2);
        card5.setPosesor(cal2);
        card6.setPosesor(cal2);
        card7.setPosesor(cal3);
        card8.setPosesor(cal3);
        card9.setPosesor(cal3);
        card10.setPosesor(cal4);
        card11.setPosesor(cal4);
        card12.setPosesor(cal4);
        card13.setPosesor(cal5);
        card14.setPosesor(cal5);
        card15.setPosesor(cal5);

        admin.getListaCalatori().add(cal1);
        admin.getListaCalatori().add(cal2);
        admin.getListaCalatori().add(cal3);
        admin.getListaCalatori().add(cal4);
        admin.getListaCalatori().add(cal5);

        try{
            ResultSet resultSet = db.getStmt().executeQuery("SELECT * FROM Calator, User where Calator.userId = User.userId and Calator.userId > 4");

            while(resultSet.next()){
                admin.getListaCalatori().add(new Calator(resultSet.getInt("userId"), resultSet.getString("nume"),
                        resultSet.getInt("rating"), AppService.stringToData(resultSet.getString("dataInregistrare"))));
            }
        }
        catch (SQLException e){
            System.out.println("Adaugarea calatorilor din baza de date in aplicatie esuata.");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            return;
        }
        Conducator cond1= new Conducator("Vasile Grigorescu", 2, timp);
        Conducator cond2= new Conducator("Alexandru Madon", 3, timp);
        Conducator cond3= new Conducator("Stefan Ghiorghidiu", 3, timp);
        Conducator cond4= new Conducator("Ion Glanetasu", 1, timp);
        Conducator cond5= new Conducator("Maria Remarque", 4, timp);
        try{
            ResultSet resultSet = db.getStmt().executeQuery("SELECT * FROM Conducator, User where Conducator.userId = User.userId and Conducator.userId > 9");
            while(resultSet.next()){
                admin.getListaConducatori().add(new Conducator(resultSet.getInt("userId"), resultSet.getString("nume"),
                        resultSet.getInt("rating"), AppService.stringToData(resultSet.getString("dataInregistrare"))));
            }
        }
        catch (SQLException e){
            System.out.println("Adaugarea conducatorilor din baza de date in aplicatie esuata.");
            return;
        }


        Vehicul veh1 = new Elicopter("JumpJet", "IS 21 AEU", "verde", cond1, 21314);
        Vehicul veh2 = new Masina("BMW", "IS 31 BAE", "negru", cond1, true, false);
        Vehicul veh3 = new MotocicletaAtas("Chopper", "B 21 CAE", "rosu", cond2, false);
        Vehicul veh4 = new MotocicletaAtas("Striker", "VS 53 REU", "gri", cond2, true);
        Vehicul veh5 = new Masina("VW", "MA 94 NOS", "alb", cond2, false, true);
        Vehicul veh6 = new Elicopter("AirBus", "IS 21 AEU", "rosu", cond3, 21314);
        Vehicul veh7 = new Trasura("VinVin", "IS 11 ZEU", "verde", cond3, 3, false);
        Vehicul veh8 = new MotocicletaAtas("Slayer", "BN 12 BAU", "maro", cond4, false);
        Vehicul veh9 = new Masina("Audi", "IS 66 MON", "verde", cond4, false, false);
        Vehicul veh10 = new MotocicletaAtas("Nitroo", "IS 21 ROL", "albastru", cond4, false);
        Vehicul veh11 = new Trasura("Dukeness", "IS 21 XOS", "verde", cond5, 1, false);
        Vehicul veh12 = new Masina("Chevrolet", "IS 31 ZOO", "violet", cond5, false, false);
        Vehicul veh13 = new MotocicletaAtas("Slayder", "IS 11 MIN", "portocaliu", cond5, true);

        cond1.getOwnVehicles().add(veh1);
        cond1.getOwnVehicles().add(veh2);
        cond2.getOwnVehicles().add(veh3);
        cond2.getOwnVehicles().add(veh4);
        cond2.getOwnVehicles().add(veh5);
        cond3.getOwnVehicles().add(veh6);
        cond3.getOwnVehicles().add(veh7);
        cond4.getOwnVehicles().add(veh8);
        cond4.getOwnVehicles().add(veh9);
        cond4.getOwnVehicles().add(veh10);
        cond5.getOwnVehicles().add(veh11);
        cond5.getOwnVehicles().add(veh12);
        cond5.getOwnVehicles().add(veh13);

        admin.getListaVehicule().add(veh1);
        admin.getListaVehicule().add(veh2);
        admin.getListaVehicule().add(veh3);
        admin.getListaVehicule().add(veh4);
        admin.getListaVehicule().add(veh5);
        admin.getListaVehicule().add(veh6);
        admin.getListaVehicule().add(veh7);
        admin.getListaVehicule().add(veh8);
        admin.getListaVehicule().add(veh9);
        admin.getListaVehicule().add(veh10);
        admin.getListaVehicule().add(veh11);
        admin.getListaVehicule().add(veh12);
        admin.getListaVehicule().add(veh13);

        admin.getListaConducatori().add(cond1);
        admin.getListaConducatori().add(cond2);
        admin.getListaConducatori().add(cond3);
        admin.getListaConducatori().add(cond4);
        admin.getListaConducatori().add(cond5);

        Cursa c1 = new Cursa(timp, cond1, cal5, veh2, 17.64, "Biblioteca", "Universitate");
        Cursa c2 = new Cursa(timp, cond1, cal4, veh1, 53, "Ateneu", "Liceu");
        Cursa c3 = new Cursa(timp, cond2, cal2, veh3, 23, "Opera", "Universitate");
        Cursa c4 = new Cursa(timp, cond2, cal3, veh3, 55, "Teatru", "Universitate");
        Cursa c5 = new Cursa(timp, cond3, cal3, veh6, 111, "Primarie", "Ateneu");
        Cursa c6 = new Cursa(timp, cond3, cal2, veh7, 75, "Minister", "Primarie");
        Cursa c7 = new Cursa(timp, cond4, cal1, veh10, 34, "Sediu", "Universitate");
        Cursa c8 = new Cursa(timp, cond4, cal4, veh8, 674, "Liceu", "Teatru");
        Cursa c9 = new Cursa(timp, cond5, cal4, veh12, 234, "Biblioteca", "Universitate");
        Cursa c10 = new Cursa(timp, cond5, cal1, veh13, 22, "Sediu", "Biblioteca");

        cond1.getCurse().add(c1);
        cond1.getCurse().add(c2);
        cond2.getCurse().add(c3);
        cond2.getCurse().add(c4);
        cond3.getCurse().add(c5);
        cond3.getCurse().add(c6);
        cond4.getCurse().add(c7);
        cond4.getCurse().add(c8);
        cond5.getCurse().add(c9);
        cond5.getCurse().add(c10);

        cal5.getCurse().add(c1);
        cal4.getCurse().add(c2);
        cal2.getCurse().add(c3);
        cal3.getCurse().add(c4);
        cal3.getCurse().add(c5);
        cal2.getCurse().add(c6);
        cal1.getCurse().add(c7);
        cal4.getCurse().add(c8);
        cal4.getCurse().add(c9);
        cal1.getCurse().add(c10);

        admin.getListaCurse().add(c1);
        admin.getListaCurse().add(c2);
        admin.getListaCurse().add(c3);
        admin.getListaCurse().add(c4);
        admin.getListaCurse().add(c5);
        admin.getListaCurse().add(c6);
        admin.getListaCurse().add(c7);
        admin.getListaCurse().add(c8);
        admin.getListaCurse().add(c9);
        admin.getListaCurse().add(c10);

        Cupon cup1 = new Cupon(1, 10, timp);
        Cupon cup2 = new Cupon(0, 5, timp);
        Cupon cup3 = new Cupon(1, 2, timp);
        Cupon cup4 = new Cupon(1, 15, timp);
        Cupon cup5 = new Cupon(0, 25, timp);

        admin.getListaCupoane().add(cup1);
        admin.getListaCupoane().add(cup2);
        admin.getListaCupoane().add(cup3);
        admin.getListaCupoane().add(cup4);
        admin.getListaCupoane().add(cup5);

    }
}
