package sample.model;

import sample.model.Klient;

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
        return "Id rezerwacji: " + id
                + "\nData utworzenia: " + dataUtworzeniaToString()
                + "\n" + jestOplaconaToString()
                + "\nLogin klienta rezerwującego: " + klientRezerwujacy.getLogin()
                + "\nLiczba zarezerwowanych miejsc: " + zarezerwowaneMiejsca.size()
                + "\nDane seansu:\n"
                + zarezerwowaneMiejsca.get(0).getSeans().toString();
    }
    public String jestOplaconaToString(){
        if(jestOplacona){
            return "Opłacona";
        } else{
            return "Nieopłacona";
        }
    }
    public String dataUtworzeniaToString(){

        LocalDate localDateEmisji = getDataUtworzenia().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime localTimeEmisji = getDataUtworzenia().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        return localDateEmisji.toString() + " " + localTimeEmisji.toString();
    }

}