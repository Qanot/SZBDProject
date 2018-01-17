package sample.model;

import java.util.Date;

public class Bilet {

    private int id;
    private RodzajBiletu rodzajBiletu;
    private Rezerwacja rezerwacjaPoprzedzajacaSprzedaz; //moze byc null (opcjonalne)
    private MiejsceNaSeansie miejsceNaSeansie;
    ProduktNaParagonie produktNaParagonie; // reprezentuje pozycje na paragonie, ktora jest nabity bilet
}
