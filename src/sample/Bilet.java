package sample;

public class Bilet {
    // TODO jaki format daty i godz?
    // private format dataZakupu;
    // private format godzinaZakupu;
    private int id;
    RodzajBiletu rodzajBiletu;
    Pracownik pracownikKtorySprzedal;
    Rezerwacja rezerwacjaPoprzedzajacaSprzedaz; //moze byc null (opcjonalne)
    MiejsceNaSeansie miejsceNaSeansie;
}
