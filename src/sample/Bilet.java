package sample;

public class Bilet {
    // TODO jaki format daty i godz?
    // private format dataZakupu;
    // private format godzinaZakupu;
    private int id;
    private RodzajBiletu rodzajBiletu;
    private Pracownik pracownikKtorySprzedal;
    private Rezerwacja rezerwacjaPoprzedzajacaSprzedaz; //moze byc null (opcjonalne)
    private MiejsceNaSeansie miejsceNaSeansie;
}
