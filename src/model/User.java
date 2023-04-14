package model;

import java.util.ArrayList;
import java.util.Calendar;

public abstract class User {
    private String nume;
    private int rating;
    private Calendar dataInregistrare;
    private ArrayList<Cursa> curse = new ArrayList<Cursa>(100);

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Calendar getDataInregistrare() {
        return dataInregistrare;
    }

    public void setDataInregistrare(Calendar dataInregistrare) {
        this.dataInregistrare = dataInregistrare;
    }

    public ArrayList<Cursa> getCurse() {
        return curse;
    }

    public void setCurse(ArrayList<Cursa> curse) {
        this.curse = curse;
    }

    public User(){};

    public User(String nume, int rating, Calendar dataInregistrare){
        this.nume = nume;
        this.rating = rating;
        this.dataInregistrare = dataInregistrare;
    }
}
