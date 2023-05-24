package model;

public class CardPlata {
    private int cardId;
    private String numar;   ////are 16 caractere si poate incepe si cu 0 asa ca mai bine string
    private int sold;
    private Calator posesor;



    public int getSold() {
        return sold;
    }


    public void setPosesor(Calator posesor) {
        this.posesor = posesor;
    }


    public CardPlata(String numar, int sold){
        this.numar = numar;
        this.sold = sold;
    }
    public CardPlata(String numar, int sold, Calator posesor){
        this.numar = numar;
        this.sold = sold;
        this.posesor = posesor;
    }

    public String toString(){
        return "Cardul bancar al calatorului " + posesor.getNume() + ", cu numarul " + numar + ", ce are un sold de " + sold + " ron.";
    }
}
