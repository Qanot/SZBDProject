package sample;

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
    private Miejsce miejsceNaSali;
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
}