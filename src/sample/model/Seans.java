package sample.model;

import sample.model.Film;

import java.sql.CallableStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Seans {
    private int id;
    private Date dataEmisji;
    private Film film;
    private Sala sala;
    private List<MiejsceNaSeansie> miejscaNaSeansie;
    private List<Miejsce> miejscaWolne;
    private List<Miejsce> miejscaWykupione;
    private List<Miejsce> miejscaZarezerwowane;

    public Seans(Date dataEmisji, Film film, Sala sala) {
        this.dataEmisji = dataEmisji;
        this.film = film;
        this.sala = sala;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataEmisji() {
        return dataEmisji;
    }

    public void setDataEmisji(Date dataEmisji) {
        this.dataEmisji = dataEmisji;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String toStringTest() {
        return "Seans{" +
                "id=" + id +
                ", dataEmisji=" + dataEmisji +
                ", film id=" + film.getId() +
                ", sala id=" + sala.getId() +
                '}';
    }
    public String dataEmisjiToString(){

        LocalDate localDateEmisji = getDataEmisji().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime localTimeEmisji = getDataEmisji().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        return localDateEmisji.toString() + " " + localTimeEmisji.toString();
    }

    public String toStringTytulSala() {
        return film.getTytul() + ", Sala: " + sala.getNrSali();
    }

    @Override
    public String toString() {
        return "Czas emisji: " + dataEmisjiToString() +
                "\nTytuł filmu: " + film.getTytul() +
                "\nNr sali: " + sala.getNrSali()
                +"\nMiejsc wolnych: " + this.getMiejscaWolne().size()
                + "\nMiejsc zarezerwowanych: " + this.getMiejscaZarezerwowane().size()
                + "\nMiejsc wykupionych: " + this.getMiejscaWykupione().size();
    }


    public List<Miejsce> getMiejscaWolne() {
        return miejscaWolne;
    }

    public void setMiejscaWolne(List<Miejsce> miejscaWolne) {
        this.miejscaWolne = miejscaWolne;
    }

    public List<Miejsce> getMiejscaWykupione() {
        return miejscaWykupione;
    }

    public void setMiejscaWykupione(List<Miejsce> miejscaWykupione) {
        this.miejscaWykupione = miejscaWykupione;
    }

    public List<Miejsce> getMiejscaZarezerwowane() {
        return miejscaZarezerwowane;
    }

    public void setMiejscaZarezerwowane(List<Miejsce> miejscaZarezerwowane) {
        this.miejscaZarezerwowane = miejscaZarezerwowane;
    }
}
