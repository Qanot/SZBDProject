package sample.model;

public class Pracownik {

    private int id;
    private String imie;
    private String nazwisko;
    private Plec plec;

    public Pracownik(int id, String imie, String nazwisko, Plec plec) {
        this.setId(id);
        this.setImie(imie);
        this.setNazwisko(nazwisko);
        this.setPlec(plec);
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
}
