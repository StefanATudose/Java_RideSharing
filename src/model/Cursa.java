package model;

import service.AppService;

import java.util.Calendar;
import java.util.Date;

public class Cursa {
    private boolean taxata = false;
    private Calendar data;
    private Conducator conducator;
    private Calator calator;
    private Vehicul vehicul;
    private double pret;
    private String punctPlecare;
    private String destinatie;

    public boolean isTaxata() {
        return taxata;
    }

    public void setTaxata(boolean taxata) {
        this.taxata = taxata;
    }

    public String getPunctPlecare() {
        return punctPlecare;
    }

    public void setPunctPlecare(String punctPlecare) {
        this.punctPlecare = punctPlecare;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Conducator getConducator() {
        return conducator;
    }

    public void setConducator(Conducator conducator) {
        this.conducator = conducator;
    }

    public Calator getCalator() {
        return calator;
    }

    public void setCalator(Calator calator) {
        this.calator = calator;
    }

    public Vehicul getVehicul() {
        return vehicul;
    }

    public void setVehicul(Vehicul vehicul) {
        this.vehicul = vehicul;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public Cursa(){}

    public Cursa(Calendar data, Conducator conducator, Calator calator, Vehicul vehicul, double pret, String punctPlecare, String destinatie){
        this.data = data;
        this.conducator = conducator;
        this.calator = calator;
        this.vehicul = vehicul;
        this.pret = pret;
        this.conducator.setCastigTotal(this.conducator.getCastigTotal() + this.pret);

    }

    public String toString(){
        return "Cursa din data " + AppService.printData(this.data) + ", efectuata de conducatorul " + this.conducator.getNume() +
                ", pentru calatorul " + this.calator.getNume() + ", efectuata cu vehiculul " + this.vehicul.getNume() + ", avand un pret de " + this.getPret() +
                ", pe traseul " + this.getPunctPlecare() + "-" + this.getDestinatie();
    }

}
