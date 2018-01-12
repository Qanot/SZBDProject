package sample.model;

import java.util.Date;

public class Bilet {

    private Date dataZakupu;
    private int id;
    private RodzajBiletu rodzajBiletu;
    private Pracownik pracownikKtorySprzedal;
    private Rezerwacja rezerwacjaPoprzedzajacaSprzedaz; //moze byc null (opcjonalne)
    private MiejsceNaSeansie miejsceNaSeansie;
}
