package model;

public class MotocicletaAtas extends Vehicul{
    private static double pretPerKm = 1.8;
    private boolean vestaBikerInclusa;

    public static double getPretPerKm() {
        return pretPerKm;
    }

    public static void setPretPerKm(double pretPerKm) {
        MotocicletaAtas.pretPerKm = pretPerKm;
    }

    public boolean isVestaBikerInclusa() {
        return vestaBikerInclusa;
    }

    public void setVestaBikerInclusa(boolean vestaBikerInclusa) {
        this.vestaBikerInclusa = vestaBikerInclusa;
    }

    public MotocicletaAtas(){}
    public MotocicletaAtas(String nume, String inmatriculare, String culoare, Conducator posesor, boolean vestaBikerInclusa){
        super(nume, inmatriculare, culoare, posesor);
        this.vestaBikerInclusa = vestaBikerInclusa;
    }

    public String toString(){
        return "Motocicleta cu atas: " + this.getNume() + ", " + this.getNrInmatriculare() + ", " +
                this.getCuloare() + ", " + getPosesor().getNume() +
                ((vestaBikerInclusa) ? ", are vesta biker inclusa" : ", nu are vesta biker inclusa");
    }
}
