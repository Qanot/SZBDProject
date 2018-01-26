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
        /*

        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(dataEmisji);   // assigns calendar to given date
        return calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH + 1) + "." +
                calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE);
                */
    }

    public String toStringTytulSala() {
        return film.getTytul() + ", Sala: " + sala.getNrSali();
    }

    @Override
    public String toString() {
        return "Czas emisji: " + dataEmisjiToString() +
                "\nTytuł filmu: " + film.getTytul() +
                "\nNr sali: " + sala.getNrSali();
    }
}
