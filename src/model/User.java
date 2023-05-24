package model;

import java.util.ArrayList;
import java.util.Calendar;

public abstract class User {
    private int userId;
    private String nume;
    private int rating;
    private Calendar dataInregistrare;
    private ArrayList<Cursa> curse = new ArrayList<Cursa>(100);

    public String getNume() {
        return nume;
    }

    public int getUserId() {
        return userId;
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

    public User(int id, String nume, int rating, Calendar dataInregistrare){
        this.userId = id;
        this.nume = nume;
        this.rating = rating;
        this.dataInregistrare = dataInregistrare;
    }
    public User(String nume, int rating, Calendar dataInregistrare){
        this.nume = nume;
        this.rating = rating;
        this.dataInregistrare = dataInregistrare;
    }
}
