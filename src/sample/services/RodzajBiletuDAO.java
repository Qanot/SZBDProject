package sample.services;

import sample.model.RodzajBiletu;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.controllers.Controller.showAlertEmptyForm;

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
                    "{? = call update_rodzajbiletu(?, ?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_rodzajbiletu(?, ?, ?)}");
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
                RodzajBiletu nowyRodzaj = new RodzajBiletu(cena, nazwa);
                nowyRodzaj.setId(id);
                rodzajeBiletow.add(nowyRodzaj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RodzajBiletuDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RodzajBiletuDAO.class.getName()).log(Level.SEVERE,
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
        try {
            stmtInsert.registerOutParameter(1, Types.INTEGER);
            stmtInsert.setDouble(2, rodzajBiletu.getCena());
            stmtInsert.setString(3, rodzajBiletu.getNazwa());
            stmtInsert.registerOutParameter(4, Types.INTEGER);

            stmtInsert.execute();
            int id = stmtInsert.getInt(4);
            rodzajBiletu.setId(id);

            int wykonanoPoprawnie = stmtInsert.getInt(1);
            return wykonanoPoprawnie == 1;
        } catch (SQLException ex) {
            Logger.getLogger(RodzajBiletu.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
            return false;
        }
    }

    public boolean updateRodzajBiletu(RodzajBiletu rodzajBiletu){
        try {
            stmtUpdate.registerOutParameter(1, Types.INTEGER);
            stmtUpdate.setDouble(2, rodzajBiletu.getId());
            stmtUpdate.setDouble(3, rodzajBiletu.getCena());
            stmtUpdate.setString(4, rodzajBiletu.getNazwa());
            // nawet jesli nadpiszemy w przyszlosci toString
            stmtUpdate.executeUpdate();
            int wykonananoPoprawnie = stmtUpdate.getInt(1);
            return wykonananoPoprawnie == 1;
        } catch (SQLException ex) {
            Logger.getLogger(RodzajBiletuDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
            return false;
        }

    }

    public void deleteRodzajBiletu(RodzajBiletu rodzajBiletu){
        try {
            stmtDelete.setInt(1, rodzajBiletu.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            showAlertEmptyForm("Nie można usunąć rodzaju biletu, bo jest powiązany z innymi rekordami!");
            Logger.getLogger(RodzajBiletuDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }
    }

    public RodzajBiletu getRodzajBiletuById(int id){
        RodzajBiletu rodzajBiletu = null;
        try {
            stmtFindById.setInt(1, id);
            rsSelect = stmtFindById.executeQuery();
            if (rsSelect.next()) {
                double cena = rsSelect.getDouble(1);
                String nazwa = rsSelect.getString(2);

                rodzajBiletu = new RodzajBiletu(cena, nazwa);
                rodzajBiletu.setId(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RodzajBiletuDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
        return rodzajBiletu;
    }
}
