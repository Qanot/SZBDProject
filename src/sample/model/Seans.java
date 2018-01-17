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
    private List<Miejsce> miejscaZajete;
}
