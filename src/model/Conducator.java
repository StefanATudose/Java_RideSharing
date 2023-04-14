package model;

import service.AppService;

import java.util.ArrayList;
import java.util.Calendar;

public class Conducator extends User {
    private ArrayList<Vehicul> ownVehicles = new ArrayList<Vehicul>();
    private double castigTotal;
    private int nrBonusuriAcordate;

    public ArrayList<Vehicul> getOwnVehicles() {
        return ownVehicles;
    }

    public void setOwnVehicles(ArrayList<Vehicul>  ownVehicles) {
        this.ownVehicles = ownVehicles;
    }

    public double getCastigTotal() {
        return castigTotal;
    }

    public void setCastigTotal(double castigTotal) {
        this.castigTotal = castigTotal;
    }

    public int getNrBonusuriAcordate() {
        return nrBonusuriAcordate;
    }

    public void setNrBonusuriAcordate(int nrBonusuriAcordate) {
        this.nrBonusuriAcordate = nrBonusuriAcordate;
    }

    public Conducator (){}

    public Conducator(String nume, int rating, Calendar dataInregistrare){
        super(nume, rating, dataInregistrare);
    }
    public Conducator(String nume, int rating, Calendar dataInregistrare, ArrayList<Vehicul> ownVehicles){
        super(nume, rating, dataInregistrare);
        this.ownVehicles = ownVehicles;
    }

    public String toString(){
        return this.getNume() + ", " + this.getRating() + "/5, "+
                AppService.printData(this.getDataInregistrare()) + ", castig de " + this.getCastigTotal()
                + ", " + this.getNrBonusuriAcordate() + " bonusuri primite.";
    }
}
