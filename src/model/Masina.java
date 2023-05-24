package model;

public class Masina extends Vehicul{
    private static double pretPerKm = 1.9;
    private boolean decapotabila;
    private boolean canDrift;

    public static double getPretPerKm() {
        return pretPerKm;
    }

    public Masina(String nume, String inmatriculare, String culoare, Conducator posesor, boolean decapotabila, boolean canDrift){
        super(nume, inmatriculare, culoare, posesor);
        this.decapotabila = decapotabila;
        this.canDrift = canDrift;
    }

    public String toString(){
        return "Masina: " + this.getNume() + ", " + this.getNrInmatriculare() + ", " +
                this.getCuloare() + ", " + getPosesor().getNume() +
                ((decapotabila) ? ", decapotabila" : "nu e decapotabila") +
                ((canDrift) ? ", poate face drifturi" : ", nu poate face drifturi");
    }
}
