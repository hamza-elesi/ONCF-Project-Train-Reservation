package Model;

import java.sql.Date;

public class Reservation {
    private Date date;
    private String statut;
    private Tiquet tiquet;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Tiquet getTiquet() {
        return tiquet;
    }

    public void setTiquet(Tiquet tiquet) {
        this.tiquet = tiquet;
    }

    public Reservation(Date date, String statut, Tiquet tiquet) {
        this.date = date;
        this.statut = statut;
        this.tiquet = tiquet;
    }
}
