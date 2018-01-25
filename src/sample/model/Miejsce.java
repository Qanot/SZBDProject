package sample.model;

public class Miejsce {
    private int id;
    private String rzad;
    private int nrMiejsca;
    private Sala sala;

    public Miejsce(String rzad, int nrMiejsca, Sala sala) {
        this.rzad = rzad;
        this.nrMiejsca = nrMiejsca;
        this.sala = sala;
    }

    public Miejsce(int id, String rzad, int nrMiejsca, Sala sala) {
        this.id = id;
        this.rzad = rzad;
        this.nrMiejsca = nrMiejsca;
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "Miejsce: " + this.getNrMiejsca() + "\nRząd: " + this.getRzad() +
                "\nSala numer: " + this.getSala().getNrSali();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRzad() {
        return rzad;
    }

    public void setRzad(String rzad) {
        this.rzad = rzad;
    }

    public int getNrMiejsca() {
        return nrMiejsca;
    }

    public void setNrMiejsca(int nrMiejsca) {
        this.nrMiejsca = nrMiejsca;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
