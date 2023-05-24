package model;

import service.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Calator extends User{
    private ArrayList <CardPlata> carduri = new ArrayList<CardPlata>();
    private ArrayList<Cupon> cupoane = new ArrayList<Cupon>();

    public ArrayList <CardPlata>  getCarduri() {
        return carduri;
    }

    public void setCarduri(ArrayList <CardPlata>  carduri) {
        this.carduri = carduri;
    }

    public ArrayList<Cupon> getCupoane() {
        return cupoane;
    }

    public void setCupoane(ArrayList<Cupon> cupoane) {
        this.cupoane = cupoane;
    }

    public Calator(int id, String nume, int rating, Calendar dataInregistrare){
        super(id, nume, rating, dataInregistrare);
    }
    public Calator(String nume, int rating, Calendar dataInregistrare, ArrayList <CardPlata> carduri){
        super(nume, rating, dataInregistrare);
        this.carduri = carduri;
    }


    public String toString(){
        return this.getNume() + ", " + this.getRating() + "/5, "+
                AppService.printData(this.getDataInregistrare()) + ".";
    }
}
