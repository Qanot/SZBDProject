package sample.model;

import java.util.List;

public class Pracownik {

    private int id;
    private String imie;
    private String nazwisko;
    private Plec plec;
    private String PESEL;
    private List<Paragon> paragonyNabitePrzezPracownika;

    public Pracownik(String imie, String nazwisko, Plec plec, String PESEL) {
        this.setImie(imie);
        this.setNazwisko(nazwisko);
        this.setPlec(plec);
        this.setPESEL(PESEL);
    }

    @Override
    public String toString() {
        return "Imie: " + this.getImie() + "\nNazwisko: " + this.getNazwisko() + "\nPłeć: " + this.getPlec() +
                "\nPESEL: " + this.PESEL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Plec getPlec() {
        return plec;
    }

    public void setPlec(Plec plec) {
        this.plec = plec;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }
}
