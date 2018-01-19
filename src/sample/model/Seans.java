package sample.model;

import sample.model.Film;

import java.util.Date;
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
}
