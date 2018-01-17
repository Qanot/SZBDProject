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

}