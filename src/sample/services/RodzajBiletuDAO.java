package sample.services;

import sample.model.RodzajBiletu;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO implement all methods


public class RodzajBiletuDAO extends DAO{

    private ConnectionController connectionController;
    private List<RodzajBiletu>  rodzajeBiletow;
    public RodzajBiletuDAO(ConnectionController connectionController){
        super(connectionController);
        rodzajeBiletow = new ArrayList<RodzajBiletu>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT ID, CENA, NAZWA FROM RODZAJEBILETOW");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM RODZAJEBILETOW WHERE ID = ?");
            this.stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_rodzajbiletu(?, ?, ?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_rodzajbiletu(?, ?, ?, ?)}");
            this.stmtFindById = connectionController.getConn().prepareStatement(
                    "SELECT CENA, NAZWA FROM RODZAJEBILETOW WHERE ID = ?");

        } catch (SQLException ex) {
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }
    }


    private void selectRodzajeBiletow(){
        rodzajeBiletow.clear();
        try {
            rsSelect = stmtSelect.executeQuery();
            while (rsSelect.next()) {
                int id = rsSelect.getInt("ID");
                Double cena = rsSelect.getDouble("CENA");
                String nazwa = rsSelect.getString("NAZWA");


//                FilmDAO filmDAO = new FilmDAO(connectionController);
//                Film film = filmDAO.getFilmById(idFilmu);
//                filmDAO.closeStatements();
//
//                SalaDAO salaDAO = new SalaDAO(connectionController);
//                Sala sala = salaDAO.getSalaById(idSali);
//                salaDAO.closeStatements();
//
//                Seans seans = new Seans(dataEmisji, film, sala);
//                seans.setId(id);
//                seanse.add(seans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
    }

    public List<RodzajBiletu> getRodzajeBiletow() {
        selectRodzajeBiletow();
        return rodzajeBiletow;
    }

    public boolean insertRodzajBiletu(RodzajBiletu rodzajBiletu){
        return true;
    }

    public boolean updateRodzajBiletu(RodzajBiletu rodzajBiletu){
        return true;
    }

    public void deleteRodzajBiletu(RodzajBiletu rodzajBiletu){
    }

    public void closeStatements(){
    }


}
