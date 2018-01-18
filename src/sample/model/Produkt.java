package sample.model;

import java.util.List;

public class Produkt {
    private int id;
    private double cena;
    private String nazwa;
    private RozmiarPorcji rozmiarPorcji;
    private List<ProduktNaParagonie> prodNaParBedaceProduktem;

    public Produkt(double cena, String nazwa, RozmiarPorcji rozmiarPorcji) {
        this.cena = cena;
        this.nazwa = nazwa;
        this.rozmiarPorcji = rozmiarPorcji;
    }

    @Override
    public String toString() {
        return "Nazwa: " + this.getNazwa() + "\nCena: " + this.getCena() + " PLN" +
            "\nRozmiar: " + this.getRozmiarPorcji() + "\nID w bazie: " + this.getId();
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
