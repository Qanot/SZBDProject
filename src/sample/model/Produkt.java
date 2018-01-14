package sample.model;

public class Produkt {
    private int id;
    private double cena;
    private String nazwa;
    private RozmiarPorcji rozmiarPorcji;

    public Produkt(double cena, String nazwa, RozmiarPorcji rozmiarPorcji) {
        this.cena = cena;
        this.nazwa = nazwa;
        this.rozmiarPorcji = rozmiarPorcji;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public RozmiarPorcji getRozmiarPorcji() {
        return rozmiarPorcji;
    }

    public void setRozmiarPorcji(RozmiarPorcji rozmiarPorcji) {
        this.rozmiarPorcji = rozmiarPorcji;
    }
}
