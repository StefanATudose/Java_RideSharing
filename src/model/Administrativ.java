package model;

import java.util.*;

public class Administrativ {
    class cmpCupoane implements Comparator<Cupon> {
        @Override
        public int compare(Cupon x, Cupon y) {
            return x.getValoare() - y.getValoare();
        }
    }

    private static double castig = 0;
    private ArrayList <Vehicul> listaVehicule = new ArrayList <Vehicul> ();
    private TreeSet<Cupon> listaCupoane = new TreeSet<Cupon>(new cmpCupoane());
    private ArrayList <Conducator> listaConducatori = new ArrayList <Conducator> ();
    private ArrayList <Calator> listaCalatori = new ArrayList <Calator> ();
    private ArrayList <Cursa> listaCurse = new ArrayList <Cursa>();

    public ArrayList<Cursa> getListaCurse() {
        return listaCurse;
    }


    public static double getCastig() {
        return castig;
    }

    public static void setCastig(double castig) {
        Administrativ.castig = castig;
    }

    public ArrayList<Vehicul> getListaVehicule() {
        return listaVehicule;
    }


    public TreeSet<Cupon> getListaCupoane() {
        return listaCupoane;
    }


    public ArrayList<Conducator> getListaConducatori() {
        return listaConducatori;
    }


    public ArrayList<Calator> getListaCalatori() {
        return listaCalatori;
    }

}
