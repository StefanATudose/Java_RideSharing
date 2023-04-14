package model;

public class Elicopter extends Vehicul{
    private static double pretPerKm = 150.99;
    private int altitudineMax;

    public static double getPretPerKm() {
        return pretPerKm;
    }

    public static void setPretPerKm(double pretPerKm) {
        Elicopter.pretPerKm = pretPerKm;
    }

    public int getAltitudineMax() {
        return altitudineMax;
    }

    public void setAltitudineMax(int altitudineMax) {
        this.altitudineMax = altitudineMax;
    }

    public Elicopter(){}
    public Elicopter(String nume, String inmatriculare, String culoare, Conducator posesor, int altitudineMax){
        super(nume, inmatriculare, culoare, posesor);
        this.altitudineMax = altitudineMax;
    }

    public String toString(){
        return "Elicopter: " + this.getNume() + ", " + this.getNrInmatriculare() + ", " +
                this.getCuloare() + ", " + getPosesor().getNume() + ", atinge altitudine maxima de " +
                this.altitudineMax + " metri.";
    }
}
