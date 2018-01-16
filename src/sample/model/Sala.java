package sample.model;

import java.util.List;

public class Sala {
    private int id;
    private int nrSali;
    private List<Seans> seanseWSali;
    private List<Miejsce> miejscaWSali;

    public Sala(int nrSali) {
        this.nrSali = nrSali;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrSali() {
        return nrSali;
    }

    public void setNrSali(int nrSali) {
        this.nrSali = nrSali;
    }
}
