package service;

import model.*;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Random;

public class DatabaseService {
    private Statement stmt, stmt2;
    Calendar timp = Calendar.getInstance();
    private ResultSet resultSet, resultSet2;
    private static DatabaseService databaseService;
    Administrativ admin;
    Random random = new Random();
    private int nrUsers = random.nextInt(1000000000), nrCupoane = random.nextInt(1000000000), nrCarduri= random.nextInt(1000000000);


    private DatabaseService(Connection connection, Administrativ admin) throws SQLException {
        stmt = connection.createStatement();
        stmt2 = connection.createStatement();
        this.admin = admin;
    }

    public Statement getStmt() {
        return stmt;
    }

    public static DatabaseService getDatabaseService(Connection connection, Administrativ admin) throws SQLException {
        if (databaseService == null)
            databaseService = new DatabaseService(connection, admin);
        return databaseService;
    }

    public void createCalator(String nume, int rating, Calendar dataInregistrare) throws SQLException {
       String qrySql = "INSERT INTO User VALUES('" + nume + "'," +
                                                " '" + rating +
                                                "', '" + AppService.printData(dataInregistrare) +
                                                "', + '" + nrUsers + "')";
       int n = stmt.executeUpdate(qrySql);
       qrySql = "INSERT INTO Calator VALUES(" + nrUsers + ")";
       n = stmt.executeUpdate(qrySql);
       System.out.println("A fost adaugat un calator nou!");
       admin.getListaCalatori().add(new Calator(nrUsers++, nume, rating, dataInregistrare));
       AuditService.addLogEntry("createCalator", timp);
    }

    public void createConducator(String nume, int rating, Calendar dataInregistrare) throws SQLException {
        String qrySql = "INSERT INTO User VALUES('" + nume + "'," +
                " '" + rating +
                "', '" + AppService.printData(dataInregistrare) +
                "', '" + nrUsers + "')";
        int n = stmt.executeUpdate(qrySql);

        qrySql = "INSERT INTO Conducator VALUES('" + 0 + "'," +
                " '" + 0 +
                "', '" + nrUsers + "')";
        n = stmt.executeUpdate(qrySql);
        System.out.println("A fost adaugat un conducator nou!");
        admin.getListaConducatori().add(new Conducator(nrUsers++, nume, rating, dataInregistrare));
        AuditService.addLogEntry("createConducator", timp);
    }

    public void updateUser(int id, String nume, Integer rating, Calendar dataInregistrare) throws SQLException{
        boolean firstAdded = false;
        String sqlQry = "UPDATE User SET ";
        User modUser = null;
        for (Calator calator : admin.getListaCalatori()){
            if (calator.getUserId() == id){
                modUser = calator;
                break;
            }
        }
        if (modUser == null)
            for(Conducator conducator : admin.getListaConducatori()){
                if (conducator.getUserId() == id){
                    modUser = conducator;
                    break;
                }
            }
        if (nume != null){
                sqlQry += "nume = '" + nume + "'";
                firstAdded = true;
                modUser.setNume(nume);
        }
        if (rating != null){
            if (firstAdded){
                sqlQry += ", ";
            }
            sqlQry += "rating = " + rating;
            modUser.setRating(rating);
            firstAdded = true;
        }
        if (dataInregistrare != null){
            if (firstAdded){
                sqlQry += ", ";
                modUser.setDataInregistrare(dataInregistrare);
            }
            sqlQry += "dataInregistrare = '" + AppService.printData(dataInregistrare) + "'";
        }
        sqlQry += " WHERE userId = " + id;
        stmt.executeUpdate(sqlQry);
        System.out.println("A fost modificat un user!");
        AuditService.addLogEntry("updateUser", timp);
    }

    public void deleteUser(int id) throws SQLException {
        String sqlQry = "DELETE FROM User WHERE userId = " + id;
        stmt.executeUpdate(sqlQry);
        for (Calator calator : admin.getListaCalatori())
            if (calator.getUserId() == id){
                admin.getListaCalatori().remove(calator);
                break;
            }

        for (Conducator conducator : admin.getListaConducatori())
            if (conducator.getUserId() == id){
                admin.getListaConducatori().remove(conducator);
                break;
            }

        sqlQry = "DELETE FROM Conducator WHERE userId = " + id;
        stmt.executeUpdate(sqlQry);
        sqlQry = "DELETE FROM Calator WHERE userId = " + id;
        stmt.executeUpdate(sqlQry);
        System.out.println("Un user sters!");
        AuditService.addLogEntry("deleteUser", timp);
    }

    public void readConducator() throws SQLException {
        String sqlQry = "SELECT * FROM Conducator JOIN User WHERE Conducator.userId = User.userId";
        resultSet = stmt.executeQuery(sqlQry);
        while (resultSet.next()){
            System.out.println(resultSet.getString("nume") + ", rating de " + resultSet.getInt("rating") +
                    " data inregistrare " + resultSet.getString("dataInregistrare") + ", profit de " +
                    resultSet.getDouble("castigTotal") + ", bonusuri acordate " + resultSet.getInt("nrBonusuriAcordate"));
        }
        AuditService.addLogEntry("readConducator", timp);
    }

    public void readCalator() throws SQLException {
        String sqlQry = "SELECT * FROM Calator JOIN User WHERE Calator.userId = User.userId";
        resultSet = stmt.executeQuery(sqlQry);
        while (resultSet.next()){
            System.out.println(resultSet.getString("nume") + ", rating de " + resultSet.getInt("rating") +
                    " data inregistrare " + resultSet.getString("dataInregistrare"));
        }
        AuditService.addLogEntry("readCalator", timp);
    }

    public void createCard(String numar, int sold, int userId) throws SQLException{
        String qrySql = "INSERT INTO CardPlata VALUES('" + nrCarduri++ + "'," +
                " '" + numar +
                "', '" + sold +
                "', + '" + userId + "')";
        int n = stmt.executeUpdate(qrySql);
        qrySql = "SELECT * FROM User WHERE userId = " + userId;
        resultSet = stmt.executeQuery(qrySql);
        resultSet.next();
        System.out.println("A fost adaugat un card nou calatorului " + resultSet.getString("nume") + "!");
        AuditService.addLogEntry("createCard", timp);
    }

    public void updateCard(int id, String numar, Integer sold, Integer userId) throws SQLException{
        boolean firstAdded = false;
        String sqlQry = "UPDATE CardPlata SET ";

        if (numar != null){
            sqlQry += "numar = '" + numar + "'";
            firstAdded = true;
        }
        if (sold != null){
            if (firstAdded){
                sqlQry += ", ";
            }
            sqlQry += "sold = " + sold;
            firstAdded = true;
        }
        if (userId != null){
            if (firstAdded){
                sqlQry += ", ";
            }
            sqlQry += "userId = " + userId;
        }
        sqlQry += " WHERE cardId = " + id;
        int n = stmt.executeUpdate(sqlQry);
        System.out.println("A fost modificat un card!");
        AuditService.addLogEntry("updateCard", timp);
    }

    public void deleteCard(int id) throws SQLException {
        String sqlQry = "DELETE FROM CardPlata WHERE cardId = " + id;
        int n = stmt.executeUpdate(sqlQry);
        System.out.println("Un card sters!");
        AuditService.addLogEntry("deleteCard", timp);
    }

    public void readCard() throws SQLException {
        String sqlQry = "SELECT * FROM CardPlata";
        resultSet = stmt.executeQuery(sqlQry);
        while (resultSet.next()){
            String sqlQry2 = "SELECT * FROM User WHERE userId = " + resultSet.getInt("userId");
            resultSet2 = stmt2.executeQuery(sqlQry2);
            if (resultSet2.next())
                System.out.println((resultSet.getInt("cardId")-5000) + ". Card cu numarul " + resultSet.getString("numar") + ", avand un sold de " + resultSet.getInt("sold") +
                    ", avand posesorul " + resultSet2.getString("nume"));
            else{
                System.out.println((resultSet.getInt("cardId")-5000) + ". Card cu numarul " + resultSet.getString("numar") + ", avand un sold de " + resultSet.getInt("sold") +
                        ", neavand deocamdata posesor");
            }
        }
        AuditService.addLogEntry("readCard", timp);
    }

    public void createCupon(int procentual, int valoare, Calendar dataExpirare) throws SQLException {
        String qrySql = "INSERT INTO Cupon VALUES('" + nrCupoane + "'," +
                " '" + procentual +
                "', '" + valoare +
                "', + '" + AppService.printData(dataExpirare) + "')";
        int n = stmt.executeUpdate(qrySql);
        System.out.println("A fost adaugat un cupon nou!");
        admin.getListaCupoane().add(new Cupon(nrCupoane++, procentual, valoare, dataExpirare));
        AuditService.addLogEntry("createCupon", timp);
    }
    public void updateCupon(int id, Integer procentual, Integer valoare, Calendar dataExpirare) throws SQLException{
        boolean firstAdded = false;
        Cupon modCupon = null;
        for (Cupon cupon : admin.getListaCupoane()){
            if (cupon.getCuponId() == id) {
                modCupon = cupon;
                break;
            }
        }
        if (modCupon == null){
            System.out.println("Nu ati ales un cupon valid. Editare esuata");
            return;
        }
        String sqlQry = "UPDATE Cupon SET ";
        if (procentual != null){
            sqlQry += "procentual = " + procentual;
            firstAdded = true;
            modCupon.setProcentual(procentual);
        }
        if (valoare != null){
            if (firstAdded){
                sqlQry += ", ";
            }
            sqlQry += "valoare = " + valoare;
            firstAdded = true;
            modCupon.setValoare(valoare);
        }
        if (dataExpirare != null){
            if (firstAdded){
                sqlQry += ", ";
            }
            modCupon.setDataExpirare(dataExpirare);
            sqlQry += "dataExpirare = '" + AppService.printData(dataExpirare) + "'";
        }
        sqlQry += " WHERE cuponId = " + id;
        int n = stmt.executeUpdate(sqlQry);
        System.out.println("A fost modificat un cupon!");
        AuditService.addLogEntry("updateCupon", timp);
    }

    public void deleteCupon(int id) throws SQLException {
        String sqlQry = "DELETE FROM Cupon WHERE cuponId = " + id;
        for( Cupon cupon : admin.getListaCupoane())
            if (cupon.getCuponId() == id){
                admin.getListaCupoane().remove(cupon);
                break;
            }
        int n = stmt.executeUpdate(sqlQry);
        if (n > 0)
            System.out.println("Un cupon sters!");
        else
            System.out.println("Nu a fost gasit cuponul ales");
        AuditService.addLogEntry("deleteCupon", timp);
    }

    public void readCupon() throws SQLException {
        String sqlQry = "SELECT * FROM Cupon";
        resultSet = stmt.executeQuery(sqlQry);
        while (resultSet.next()){
            if (resultSet.getBoolean("procentual"))
                System.out.println((resultSet.getInt("cuponId")-1000) + ". Cupon de tip procentual, de valoare " + resultSet.getInt("valoare") + ", expira la data de " +
                       resultSet.getString("dataExpirare"));
            else{
                System.out.println((resultSet.getInt("cuponId")-1000) + ". Cupon de tip absolut, de valoare " + resultSet.getInt("valoare") + ", expira la data de " +
                        resultSet.getString("dataExpirare"));
            }
        }
        AuditService.addLogEntry("readCupon", timp);
    }

    public void adaugareCuponLaCalatorInDb(int cuponId, int userId) throws SQLException {
        String sqlQuery = "INSERT INTO AssociativeCuponUser VALUES('" + userId + "', '" + cuponId + "')";
        int n = stmt.executeUpdate(sqlQuery);
    }

    public boolean checkCalatorInDB(DatabaseService db) throws SQLException {
        String sqlQry = "SELECT * FROM Cupon";
        resultSet = stmt.executeQuery(sqlQry);
        int contor = 0;
        while (resultSet.next()){
            contor++;
            if (contor > 3)
                return true;
        }
        return false;
    }

    public boolean checkConducatorInDB(DatabaseService db) throws SQLException {
        String sqlQry = "SELECT * FROM Cupon";
        resultSet = stmt.executeQuery(sqlQry);
        int contor = 0;
        while (resultSet.next()){
            contor++;
            if (contor > 3)
                return true;
        }
        return false;
    }
    public boolean checkCuponInDB(DatabaseService db) throws SQLException {
        String sqlQry = "SELECT * FROM Cupon";
        resultSet = stmt.executeQuery(sqlQry);
        int contor = 0;
        while (resultSet.next()){
            contor++;
            if (contor > 3)
                return true;
        }
        return false;
    }
    public boolean checkCardInDB(DatabaseService db) throws SQLException {
        String sqlQry = "SELECT * FROM Cupon";
        resultSet = stmt.executeQuery(sqlQry);
        int contor = 0;
        while (resultSet.next()){
            contor++;
            if (contor > 3)
                return true;
        }
        return false;
    }
}
