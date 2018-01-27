package sample.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Paragon {

    private int id;
    private Date dataZakupu;
    private Pracownik pracownikNabijajacyParagon;

    private List<Produkt> produkty;
    private List<Bilet> bilety;



    public Paragon() {
        this.dataZakupu = new Date();
        produkty = new ArrayList<>();
        bilety = new ArrayList<>();
    }

    public Paragon(Date dataZakupu, Pracownik pracownikNabijajacyParagon) {
        this.dataZakupu = dataZakupu;
        this.pracownikNabijajacyParagon = pracownikNabijajacyParagon;
    }

    public Pracownik getPracownikNabijajacyParagon() {

        return pracownikNabijajacyParagon;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataZakupu() {
        return dataZakupu;
    }

    public void setDataZakupu(Date dataZakupu) {
        this.dataZakupu = dataZakupu;
    }

    public String getDataZakupuToString() {

        LocalDate localDateZakupu = getDataZakupu().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime localTimeZakupu = getDataZakupu().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        return localDateZakupu.toString() + " " + localTimeZakupu.toString();
    }

    public void setPracownikNabijajacyParagon(Pracownik pracownikNabijajacyParagon) {
        this.pracownikNabijajacyParagon = pracownikNabijajacyParagon;
    }


    public List<Produkt> getProdukty() {
        return produkty;
    }

    public void setProdukty(List<Produkt> produkty) {
        this.produkty = produkty;
    }

    public List<Bilet> getBilety() {
        return bilety;
    }

    public void setBilety(List<Bilet> bilety) {
        this.bilety = bilety;
    }

    @Override
    public String toString() {
        int rozmiar = bilety.size() + produkty.size();
        return "Id: "+ id +
                "\nData zakupu: " + this.getDataZakupuToString() +
                "\nPracownik: " + pracownikNabijajacyParagon.getImie() + " " + pracownikNabijajacyParagon.getNazwisko() +
                "\nPESEL: " + pracownikNabijajacyParagon.getPESEL() +
                "\nLiczba produktów: " + rozmiar +
                "\nProdukty ze sklepiku: " +
                produktyToString()
                + "\nBilety: "
                + biletyToString();
    }
    private String produktyToString(){
        if(produkty.size() == 0)
            return "brak";
        else{
            String wynik = "";
            int i = 1;
            for(Produkt produkt: produkty){
                wynik += "\n" + Integer.toString(i) + ". " + produkt.toString();
                i += 1;
            }
            return wynik;
        }
    }

    private String biletyToString(){
        if(bilety.size() == 0)
            return "brak";
        else{
            String wynik = "";
            int i = 1;
            for(Bilet bilet : bilety){
                wynik += "\n" + Integer.toString(i) + ". " + bilet.toString();
                i += 1;
            }
            return wynik;
        }
    }
}
