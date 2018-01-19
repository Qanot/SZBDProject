package sample.model;


import java.util.List;

public class Film {

    private int id;
    private String tytul;
    private int czasTrwaniaWMin;
    private List<Seans> seanse;

    public Film(String tytul, int czasTrwaniaWMin) {
        this.tytul = tytul;
        this.czasTrwaniaWMin = czasTrwaniaWMin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public int getCzasTrwaniaWMin() {
        return czasTrwaniaWMin;
    }

    public void setCzasTrwaniaWMin(int czasTrwaniaWMin) {
        this.czasTrwaniaWMin = czasTrwaniaWMin;
    }

    public List<Seans> getSeanse() {
        return seanse;
    }

    public void setSeanse(List<Seans> seanse) {
        this.seanse = seanse;
    }

    public String toStringTest() {
        return "Film{" +
                "id=" + id +
                ", tytul='" + tytul + '\'' +
                ", czasTrwaniaWMin=" + czasTrwaniaWMin +
                ", seanse=" + seanse +
                '}';
    }

    @Override
    public String toString() {
        return tytul + "; " + String.valueOf(czasTrwaniaWMin) + " min";
    }
}
