package sample.services;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXScrollPane;
import javafx.scene.text.Text;
import sample.model.Sala;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.controllers.Controller.showAlertEmptyForm;

public class SalaDAO extends DAO{

    private List<Sala> sale;

    public SalaDAO(ConnectionController connectionController) {
        super(connectionController);
        sale = new ArrayList<Sala>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT id, nr_sali from SALE");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM SALE WHERE id = ?");
            this.stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_sala(?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_sale(?, ?)}");
            this.stmtFindById = connectionController.getConn().prepareStatement(
              "SELECT nr_sali FROM SALE WHERE id = ?");

        } catch (SQLException ex) {
            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }
    }


    public List<Sala> getSale() {
        selectSale();
        return sale;
    }

    private void selectSale() {
        sale.clear();
        try {
            rsSelect = stmtSelect.executeQuery();
            while (rsSelect.next()) {
                int id = rsSelect.getInt(1);
                int nrSali = rsSelect.getInt(2);
                Sala sala = new Sala(nrSali);
                sala.setId(id);
                sale.add(sala);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
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
    }

    public void deleteSala(Sala sala) {
        try {
            stmtDelete.setInt(1, sala.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {

            showAlertEmptyForm("Nie można usunąć sali, bo jest powiązana z innymi rekordami!");

            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }
    }
    /**
     *
     * @param sala sala, ktorej nr chcemy zmienic
     * @return true jeśli udało się zmienic nr sali, false w przeciwnym wypadku
     * (istnieje już sala o danym nr sali podanym jako nowy)
     */

    public boolean updateSala(Sala sala) {
        try {
            stmtUpdate.registerOutParameter(1, Types.INTEGER);
            stmtUpdate.setInt(2, sala.getId());
            stmtUpdate.setInt(3, sala.getNrSali());
            stmtUpdate.execute();

            int wykonananoPoprawnie = stmtUpdate.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
            return false;
        }
    }


    /**
     *
     * @param sala sala do wstawienia do relacji
     * @return true jeśli udało się wstawić salę o danym nr sali, false w przeciwnym wypadku
     * (istnieje już sala o danym nr sali)
     */
    public boolean insertSala(Sala sala) {
        try {
            stmtInsert.registerOutParameter(1, Types.INTEGER);
            stmtInsert.setInt(2, sala.getNrSali());
            stmtInsert.registerOutParameter(3, Types.INTEGER);

            stmtInsert.execute();
            int id = stmtInsert.getInt(3);
            sala.setId(id);

            int wykonananoPoprawnie = stmtInsert.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
            return false;
        }
    }
    public Sala getSalaById(int id){
        Sala sala = null;
        try {
            stmtFindById.setInt(1, id);
            rsSelect = stmtFindById.executeQuery();
            if (rsSelect.next()) {
                int nrSali = rsSelect.getInt(1);

                sala = new Sala(nrSali);
                sala.setId(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
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
        return sala;
    }
}