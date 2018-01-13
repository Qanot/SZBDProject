package sample.model;

import sample.model.Bilet;
import sample.model.Paragon;
import sample.model.Produkt;

public class ProduktNaParagonie {
    /**
     * zwiazdek wylaczny (exclusive relationship)
     * tzn. albo referencja na Bilet
     * (nabijamy bilet na paragon)
     * albo referncja na Produkt (nabijamy na paragon jedzenie lub picie)
     * bedziemy realizowac w taki sam sposob, jak w bazie danych
     * - jedna z referencji jest null, a druga powinna wskazywac na obiekt
     * (jednoczesnie nie mogą być obydwie not null)
     **/
    private int id;
    private Paragon paragonNaKtoryNabitoProdukt;
    private Bilet bilet;
    private Produkt produkt;

    public void setBilet(Bilet bilet) {
        this.bilet = bilet;
        this.produkt = null; // nabijamy bilet
    }

    public void setProdukt(Produkt produkt) {
        this.produkt = produkt; // nabijamy produkt
        this.bilet = null;
    }

}
