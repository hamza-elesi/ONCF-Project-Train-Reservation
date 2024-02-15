package Model;

public class Voyageur implements  IVoyageur{
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public Voyageur(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void acheterBillet() {

    }

    @Override
    public void imprimerBillet() {

    }
}
