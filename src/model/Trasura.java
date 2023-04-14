package model;

public class Trasura extends Vehicul{
    private static double pretPerKm = 1.5;
    private int nrCai;
    private boolean moderna;

    public static double getPretPerKm() {
        return pretPerKm;
    }

    public static void setPretPerKm(double pretPerKm) {
        Trasura.pretPerKm = pretPerKm;
    }

    public int getNrCai() {
        return nrCai;
    }

    public void setNrCai(int nrCai) {
        this.nrCai = nrCai;
    }

    public boolean isModerna() {
        return moderna;
    }

    public void setModerna(boolean moderna) {
        this.moderna = moderna;
    }

    public Trasura(){}

    public Trasura(String nume, String inmatriculare, String culoare, Conducator posesor, int nrCai, boolean moderna){
        super(nume, inmatriculare, culoare, posesor);
        this.nrCai = nrCai;
        this.moderna = moderna;
    }

    public String toString(){
        return "Trasura: " + this.getNume() + ", " + this.getNrInmatriculare() + ", " +
                this.getCuloare() + ", " + getPosesor().getNume() + ", are " +
                this.getNrCai() + " cai" + ((moderna) ? ", este moderna" : ", este vintage");
    }
}
