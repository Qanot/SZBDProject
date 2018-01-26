package sample.services;


import sample.model.MiejsceNaSeansie;
import sample.model.Rezerwacja;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MiejsceNaSeansieDAO{
    private ConnectionController connectionController;
    //private List<MiejsceNaSeansie> miejscaNaSeansie;

    public MiejsceNaSeansieDAO(ConnectionController connectionController){
        this.setConnectionController(connectionController);

        /*
        super(connectionController);

        miejscaNaSeansie = new ArrayList<MiejsceNaSeansie>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT ID, DATA_GODZINA, FILMY_ID, SALE_ID FROM SEANSE");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM SEANSE WHERE ID = ?");
            this.stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_seans(?, ?, ?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_seans(?, ?, ?, ?)}");
            this.stmtFindById = connectionController.getConn().prepareStatement(
                    "SELECT DATA_GODZINA, FILMY_ID, SALE_ID FROM SEANSE WHERE ID = ?");

        } catch (SQLException ex) {
            Logger.getLogger(RodzajBiletuDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }*/

    }

    public void deleteAllMiejscaNaSeansieForRezerwacja(Rezerwacja rezerwacja){
        // TODO
    }
    

    public void insert(MiejsceNaSeansie miejsceNaSeansie){
        // TODO
    }
    public List<MiejsceNaSeansie> getMiejscaByIdRezerwacji(int idRezerwacji){
        // TODO
        return null;
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }


}
