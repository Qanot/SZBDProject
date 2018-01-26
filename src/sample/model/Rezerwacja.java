package sample.model;

import sample.model.Klient;

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
}