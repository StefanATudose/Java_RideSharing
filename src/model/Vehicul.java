package model;

public abstract class Vehicul {
    private String nume;
    private String nrInmatriculare;
    private String culoare;
    private Conducator posesor;

    public String getNume() {
        return nume;
    }

    public String getNrInmatriculare() {
        return nrInmatriculare;
    }


    public String getCuloare() {
        return culoare;
    }

    public Conducator getPosesor() {
        return posesor;
    }

    public Vehicul(String nume, String inmatriculare, String culoare, Conducator posesor){
        this.nume = nume;
        this.nrInmatriculare = inmatriculare;
        this.culoare = culoare;
        this.posesor = posesor;
    }
}
