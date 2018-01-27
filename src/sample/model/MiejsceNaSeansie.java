package sample.model;

public class MiejsceNaSeansie {

    /**
     * zwiazdek wylaczny (exclusive relationship)
     * tzn. albo referencja na Bilet
     * (ktos kupil w kasie bez rezerwacji albo rezerwacja zostala oplacona i mamy fizycznie bilet)
     * albo referncja na Rezerwacje- miejsce zarezerwowane, ale jeszcze nie wykupione
     * bedziemy realizowac w taki sam sposob, jak w bazie danych
     * - jedna z referencji jest null, a druga powinna wskazywac na obiekt
     * (jednoczesnie nie mogą być obydwie not null)
     **/

    private int id;
    private Miejsce miejsce;
    private Seans seans;
    private Rezerwacja rezerwacja;
    private Bilet bilet;


    public void setBilet(Bilet bilet) {
        this.bilet = bilet;
        this.rezerwacja = null; // UWAGA! miejsce na seansie wykupione, juz nie jest zarezerwowane
    }

    public void setRezerwacja(Rezerwacja rezerwacja) {
        this.rezerwacja = rezerwacja; // ktos zarezerwowal miejsce
        this.bilet = null; // w normalnej sytuacji nie bylo biletu (nadpisujemy null)- najpierw rezerwacja, potem zakup;
        // chyba ze, ktos (np. pracownik) edytuje historie i anulowuje bilet
    }

    public MiejsceNaSeansie(int id, Miejsce miejsce, Seans seans, Rezerwacja rezerwacja) {
        this.id = id;
        this.miejsce = miejsce;
        this.seans = seans;
        this.setRezerwacja(rezerwacja);
    }

    public MiejsceNaSeansie(int id, Miejsce miejsce, Seans seans, Bilet bilet) {
        this.id = id;
        this.miejsce = miejsce;
        this.seans = seans;
        this.setBilet(bilet);
    }

    public MiejsceNaSeansie(Miejsce miejsce, Seans seans) {
        this.miejsce = miejsce;
        this.seans = seans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Miejsce getMiejsce() {
        return miejsce;
    }

    public void setMiejsce(Miejsce miejsce) {
        this.miejsce = miejsce;
    }

    public Seans getSeans() {
        return seans;
    }

    public void setSeans(Seans seans) {
        this.seans = seans;
    }

    public Rezerwacja getRezerwacja() {
        return rezerwacja;
    }

    public Bilet getBilet() {
        return bilet;
    }
}