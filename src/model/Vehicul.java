package model;

import java.util.ArrayList;

public abstract class Vehicul {
    private String nume;
    private String nrInmatriculare;
    private String culoare;
    private Conducator posesor;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNrInmatriculare() {
        return nrInmatriculare;
    }

    public void setNrInmatriculare(String nrInmatriculare) {
        this.nrInmatriculare = nrInmatriculare;
    }


    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public Conducator getPosesor() {
        return posesor;
    }

    public void setPosesor(Conducator posesor) {
        this.posesor = posesor;
    }

    public Vehicul(){}

    public Vehicul(String nume, String inmatriculare, String culoare, Conducator posesor){
        this.nume = nume;
        this.nrInmatriculare = inmatriculare;
        this.culoare = culoare;
        this.posesor = posesor;
    }
}
