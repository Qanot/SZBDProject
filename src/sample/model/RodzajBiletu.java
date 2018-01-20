package sample.model;

import java.util.List;

public class RodzajBiletu {

    private int id; //klucz podstawowy
    private double cena;
    private String nazwa;
    private List<Bilet> sprzedaneBiletyDanegoRodzaju;

    public RodzajBiletu(double cena, String nazwa) {
        this.cena = cena;
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return "Nazwa: " + getNazwa() + "\nCena: " + getCena() + " PLN" +
                "\nID w systemie: " + getId();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public double getCena() { return cena; }

    public void setCena(double cena) {  this.cena = cena;  }

    public String getNazwa() {  return nazwa; }

    public void setNazwa(String nazwa) { this.nazwa = nazwa; }

    public List<Bilet> getSprzedaneBiletyDanegoRodzaju() {
        return sprzedaneBiletyDanegoRodzaju;
    }

    public void setSprzedaneBiletyDanegoRodzaju(List<Bilet> sprzedaneBiletyDanegoRodzaju) {
        this.sprzedaneBiletyDanegoRodzaju = sprzedaneBiletyDanegoRodzaju;
    }
}
