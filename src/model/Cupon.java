package model;

import service.AppService;

import java.io.ObjectStreamException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;

//(reducere)
public class Cupon {


    //false pentru absolut, true pentru procentual
    private boolean procentual;

    //suma in lei daca absolut, procentul daca procentual
    private int valoare;
    private Calendar dataExpirare;

    public boolean isProcentual() {
        return procentual;
    }

    public void setProcentual(boolean procentual) {
        this.procentual = procentual;
    }

    public int getValoare() {
        return valoare;
    }

    public void setValoare(int valoare) {
        this.valoare = valoare;
    }

    public Calendar getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(Calendar dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public Cupon(){}
    public Cupon(boolean procent, int val, Calendar dataExpirare){
        this.procentual = procent;
        this.valoare = val;
        this.dataExpirare = dataExpirare;
    }

    public String toString(){
        return "Cupon valabil pana in data de " + AppService.printData(dataExpirare) + ", " + ((procentual) ?
        "care reduce pretul cursei cu un procent de " + valoare + " la suta." : "care reduce pretul cursei cu " + valoare + " ron.");
    }

    @Override
    public int hashCode() {
        return Objects.hash(procentual, valoare, AppService.printData(dataExpirare));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        if (this.valoare != ((Cupon) obj).getValoare())
            return false;
        if (!Objects.equals(this.procentual, ((Cupon) obj).isProcentual()))
            return false;
        return Objects.equals(AppService.printData(this.dataExpirare), AppService.printData(((Cupon) obj).getDataExpirare()));
    }
}
