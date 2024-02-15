package Model;

public class Tiquet {
    private String classe;
    private String destination;
    private Trajet trajet;
    private CarteDeReduction carteDeReduction;

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public CarteDeReduction getCarteDeReduction() {
        return carteDeReduction;
    }

    public void setCarteDeReduction(CarteDeReduction carteDeReduction) {
        this.carteDeReduction = carteDeReduction;
    }

    public Tiquet(String classe, String destination, Trajet trajet, CarteDeReduction carteDeReduction) {
        this.classe = classe;
        this.destination = destination;
        this.trajet = trajet;
        this.carteDeReduction = carteDeReduction;
    }
}
