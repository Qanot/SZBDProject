package sample.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Rezerwacja {
    private int id;
    private Date dataUtworzenia;
    private boolean jestOplacona;
    private Klient klientRezerwujacy;
    private List<MiejsceNaSeansie> zarezerwowaneMiejsca; // not null
    private List<Bilet> wykupioneBilety; // moze byc nullem


    public Rezerwacja(Klient klientRezerwujacy, List<MiejsceNaSeansie> zarezerwowaneMiejsca) {
        dataUtworzenia = new Date();
        jestOplacona = false;
        this.klientRezerwujacy = klientRezerwujacy;
        this.zarezerwowaneMiejsca = zarezerwowaneMiejsca;
    }

    public Rezerwacja(int id, Date dataUtworzenia, boolean jestOplacona, Klient klientRezerwujacy) {
        this.id = id;
        this.dataUtworzenia = dataUtworzenia;
        this.jestOplacona = jestOplacona;
        this.klientRezerwujacy = klientRezerwujacy;
        this.zarezerwowaneMiejsca = zarezerwowaneMiejsca;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataUtworzenia() {
        return dataUtworzenia;
    }

    public void setDataUtworzenia(Date dataUtworzenia) {
        this.dataUtworzenia = dataUtworzenia;
    }

    public boolean isJestOplacona() {
        return jestOplacona;
    }

    public void setJestOplacona(boolean jestOplacona) {
        this.jestOplacona = jestOplacona;
    }

    public Klient getKlientRezerwujacy() {
        return klientRezerwujacy;
    }

    public void setKlientRezerwujacy(Klient klientRezerwujacy) {
        this.klientRezerwujacy = klientRezerwujacy;
    }

    public List<MiejsceNaSeansie> getZarezerwowaneMiejsca() {
        return zarezerwowaneMiejsca;
    }

    public void setZarezerwowaneMiejsca(List<MiejsceNaSeansie> zarezerwowaneMiejsca) {
        this.zarezerwowaneMiejsca = zarezerwowaneMiejsca;
    }

    @Override
    public String toString() {
        String wynik = "";
        wynik = "Id rezerwacji: " + id
                + "\nData utworzenia: " + getDataUtworzeniaToString()
                + "\n" + jestOplaconaToString()
                + "\nLogin klienta rezerwującego: " + klientRezerwujacy.getLogin();
        if(zarezerwowaneMiejsca!= null && zarezerwowaneMiejsca.size() > 0){
            wynik += "\nDane seansu:\n"
                    + zarezerwowaneMiejsca.get(0).getSeans().toString()
                    + "\nLiczba zarezerwowanych miejsc: " + zarezerwowaneMiejsca.size()
                    + "\nLista zarezerwowanych miejsc:\n"
                    + getZarezerwowaneMiejscaToString();
        }else{
            System.out.println("NIE MA MIEJSC DLA REZERWACJI");
        }
        return wynik;
    }
    private String getZarezerwowaneMiejscaToString(){
        String wynik = "";
        int i = 1;
        for(MiejsceNaSeansie miejsceNaSeansie: zarezerwowaneMiejsca){
            wynik += "\t" + Integer.toString(i) + ". " + miejsceNaSeansie.getMiejsce().getRzad()
            + "/" + miejsceNaSeansie.getMiejsce().getNrMiejsca() + "\n";
        }
        return wynik;

    }
    private String jestOplaconaToString(){
        if(jestOplacona){
            return "Opłacona";
        } else{
            return "Nieopłacona";
        }
    }
    public String getDataUtworzeniaToString(){

        LocalDate localDateEmisji = getDataUtworzenia().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime localTimeEmisji = getDataUtworzenia().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        return localDateEmisji.toString() + " " + localTimeEmisji.toString();
    }

}