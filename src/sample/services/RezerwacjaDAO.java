package sample.services;

import sample.model.Klient;
import sample.model.Rezerwacja;

import java.util.ArrayList;
import java.util.List;

public class RezerwacjaDAO {

    private ConnectionController connectionController;
    private List<Rezerwacja> rezerwacje;
    KlientDAO klientDAO;


    public RezerwacjaDAO(ConnectionController connectionController) {
        rezerwacje = new ArrayList<Rezerwacja>();
        this.setConnectionController(connectionController);
        klientDAO = new KlientDAO(connectionController);
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }
    public void selectRezerwacje(){
        /*
        SELECT ID, DATA_UTWORZENIA, CZY_OPLACONA, KLIENCI_ID...
        Klient klient = klientDAO.getKlientbyId(klienci_id);
        Rezerwacja rezerwacja = new Rezerwacja(..., klient);
        */

    }

    public List<Rezerwacja> getRezerwacje() {
        selectRezerwacje();
        return rezerwacje;
    }

    public void insertRezerwacja(Rezerwacja rezerwacja){
    }

    public void updateRezerwacja(Rezerwacja rezerwacja){
    }

    public void deleteRezerwacja(Rezerwacja rezerwacja){

    }

    public void closeStatements(){

    }
}
