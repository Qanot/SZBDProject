package sample.services;

import sample.model.Film;
import sample.model.Miejsce;
import sample.model.Sala;
import sample.model.Seans;

import java.lang.management.MonitorInfo;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MiejsceDAO extends DAO {

    private List<Miejsce> miejsca;

    public MiejsceDAO(ConnectionController connectionController) {
        super(connectionController);
        miejsca = new ArrayList<Miejsce>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT ID, RZAD, NR_MIEJSCA, SALE_ID FROM MIEJSCA");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM MIEJSCA WHERE ID = ?");
            this.stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_miejsce(?, ?, ?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_miejsce(?, ?, ?, ?)}");
            this.stmtFindById = connectionController.getConn().prepareStatement(
                    "SELECT RZAD, NR_MIEJSCA, SALE_ID FROM MIEJSCA WHERE ID = ?");

        } catch (SQLException ex) {
            Logger.getLogger(MiejsceDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }
    }

    public void selectSeanse() {
        try {
            rsSelect = stmtSelect.executeQuery();
            miejsca.clear();
            SalaDAO salaDAO = new SalaDAO(connectionController);
            while (rsSelect.next()) {
                int id = rsSelect.getInt("ID");
                String rzad = rsSelect.getString("RZAD");
                int nr_miejsca = rsSelect.getInt("NR_MIEJSCA");
                int idSali = rsSelect.getInt("SALE_ID");

                Sala sala = salaDAO.getSalaById(idSali);

                Miejsce miejsce = new Miejsce(rzad, nr_miejsca, sala);
                miejsce.setId(id);
                miejsca.add(miejsce);
            }
            salaDAO.closeStatements();
        } catch (SQLException ex) {
            Logger.getLogger(MiejsceDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MiejsceDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
    }

    public List<Miejsce> getSeanse() {
        selectSeanse();
        return miejsca;
    }

    public boolean insertMiejsce(Miejsce miejsce) {
        try {
            stmtInsert.registerOutParameter(1, Types.INTEGER);
            stmtInsert.setString(2, miejsce.getRzad()); // to chyba dziala
            stmtInsert.setInt(3, miejsce.getNrMiejsca());
            stmtInsert.setInt(4, miejsce.getSala().getId());
            stmtInsert.registerOutParameter(5, Types.INTEGER);

            stmtInsert.execute();
            int id = stmtInsert.getInt(5);
            miejsce.setId(id);

            int wykonananoPoprawnie = stmtInsert.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(MiejsceDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
            return false;
        }
    }

    public boolean updateSeans(Miejsce miejsce) {
        try {
            stmtUpdate.registerOutParameter(1, Types.INTEGER);
            stmtUpdate.setInt(2, miejsce.getId());
            stmtUpdate.setString(3, miejsce.getRzad());
            stmtUpdate.setInt(4, miejsce.getNrMiejsca());
            stmtUpdate.setInt(5, miejsce.getSala().getId());

            stmtUpdate.execute();

            int wykonananoPoprawnie = stmtUpdate.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(MiejsceDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
            return false;
        }
    }


    public void deleteSeans(Miejsce miejsce) {
        try {
            stmtDelete.setInt(1, miejsce.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiejsceDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }
    }

    public Miejsce getMiejsceById(int id) {
        Miejsce miejsce = null;
        try {
            stmtFindById.setInt(1, id);
            rsSelect = stmtFindById.executeQuery();
            if (rsSelect.next()) {
                String rzad = rsSelect.getString("RZAD");
                int nr_miejsca = rsSelect.getInt("NR_MIEJSCA");
                int idSali = rsSelect.getInt("SALE_ID");

                SalaDAO salaDAO = new SalaDAO(connectionController);
                Sala sala = salaDAO.getSalaById(idSali);
                salaDAO.closeStatements();

                miejsce = new Miejsce(rzad, nr_miejsca, sala);
                miejsce.setId(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiejsceDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MiejsceDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
        return miejsce;
    }


}
