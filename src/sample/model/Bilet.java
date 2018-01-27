package sample.model;

import java.util.Date;

public class Bilet {

    private int id;
    private RodzajBiletu rodzajBiletu;
    private Rezerwacja rezerwacjaPoprzedzajacaSprzedaz; //moze byc null (opcjonalne)
    private MiejsceNaSeansie miejsceNaSeansie;
    ProduktNaParagonie produktNaParagonie; // reprezentuje pozycje na paragonie, ktora jest nabity bilet

    public Bilet(RodzajBiletu rodzajBiletu) {
        this.rodzajBiletu = rodzajBiletu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public MiejsceNaSeansie getMiejsceNaSeansie() {
        return miejsceNaSeansie;
    }

    public void setMiejsceNaSeansie(MiejsceNaSeansie miejsceNaSeansie) {
        this.miejsceNaSeansie = miejsceNaSeansie;
    }


    public RodzajBiletu getRodzajBiletu() {
        return rodzajBiletu;
    }

    public void setRodzajBiletu(RodzajBiletu rodzajBiletu) {
        this.rodzajBiletu = rodzajBiletu;
    }

    @Override
    public String toString() {
        return rodzajBiletu.getNazwa() + miejsceNaSeansie.getSeans().getFilm().getTytul() +
                miejsceNaSeansie.getMiejsce().getRzad() +
                "/" + miejsceNaSeansie.getMiejsce().getNrMiejsca();
    }
}
