package Model;

public class CarteDeReduction {
    private String id;
    private String card_type;
    private String full_name;
    private String email;
    private double phone ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPhone() {
        return phone;
    }

    public void setPhone(double phone) {
        this.phone = phone;
    }

    public CarteDeReduction(String id, String card_type, String full_name, String email, double phone) {
        this.id = id;
        this.card_type = card_type;
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
    }
}
