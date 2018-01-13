package sample.model;

public class Klient {

    private String imie;
    private String nazwisko;
    private String email;
    private String login;
    private String haslo;
    private String telefon;

    public Klient(String imie, String nazwisko, String email, String login, String haslo, String telefon) {
        this.setImie(imie);
        this.setNazwisko(nazwisko);
        this.setEmail(email);
        this.setLogin(login);
        this.setHaslo(haslo);
        this.setTelefon(telefon);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
