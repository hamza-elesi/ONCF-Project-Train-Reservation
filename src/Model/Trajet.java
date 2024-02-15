package Model;

public class Trajet {
    private int id;
    private String to_location;
    private String from_location;
    private int departure_time;
    private int  arrival_time;
    private int time_difference;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTo_location() {
        return to_location;
    }

    public void setTo_location(String to_location) {
        this.to_location = to_location;
    }

    public String getFrom_location() {
        return from_location;
    }

    public void setFrom_location(String from_location) {
        this.from_location = from_location;
    }

    public int getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(int departure_time) {
        this.departure_time = departure_time;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getTime_difference() {
        return time_difference;
    }

    public void setTime_difference(int time_difference) {
        this.time_difference = time_difference;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Trajet(int id, String to_location, String from_location, int departure_time, int arrival_time, int time_difference, double price) {
        this.id = id;
        this.to_location = to_location;
        this.from_location = from_location;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.time_difference = time_difference;
        this.price = price;
    }
}
