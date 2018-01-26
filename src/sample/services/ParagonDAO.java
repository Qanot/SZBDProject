package sample.services;

import sample.model.Paragon;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO dostosowac do nowego modelu bazy (w drugiej kolejnosci, bo nie jest lisciem (ma dziecko pracownik)

public class ParagonDAO extends DAO {


    private List<Paragon> paragony;
//    private PreparedStatement stmtSelect = null;
//    private PreparedStatement stmtDelete = null;
//    private PreparedStatement stmtUpdate = null;
//    private CallableStatement stmtInsert = null;
//    private CallableStatement stmtFindById = null;
//    private ResultSet rsSelect = null;

    public ParagonDAO(ConnectionController connectionController) {
        super(connectionController);
        paragony = new ArrayList<Paragon>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT id, data_godzina, pracownicy_id FROM PARAGONY");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM PARAGONY WHERE ID = ?");
//            stmtUpdate = connectionController.getConn().prepareStatement(
//                    "UPDATE PARAGONY SET data_godzina = ? WHERE ID = ?");
            this.stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_paragon(?, ?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call insert_paragon(?, ?, ?)}");
            this.stmtFindById = connectionController.getConn().prepareStatement(
                    "SELECT data_godzina, pracownicy_id FROM PARAGONY WHERE ID = ?");

        } catch (SQLException ex) {
            Logger.getLogger(ParagonDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }
    }

    private void selectParagony() {
        paragony.clear();
        try {
            rsSelect = stmtSelect.executeQuery();
            PracownikDAO pracownikDAO = new PracownikDAO(connectionController);

            while (rsSelect.next()) {
                int id = rsSelect.getInt(1);
                Date data = rsSelect.getDate(2);

                Paragon paragon = new Paragon(data);
                paragon.setId(id);
                paragony.add(paragon);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParagonDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ParagonDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
    }

    public void deleteParagon(Paragon paragon) {
        try {
            stmtDelete.setInt(1, paragon.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParagonDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }
    }

    public void updateParagon(Paragon paragon) {
        try {
            stmtUpdate.setDate(1, new java.sql.Date(paragon.getDataZakupu().getTime()));
            // stmtUpdate.setDate(1, paragon.getDataZakupu()); // TODO test
            stmtUpdate.setInt(2, paragon.getId());
            int changes = stmtUpdate.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie zmodyfikowano dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParagonDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
        }
    }

    public void insertParagon(Paragon paragon) {
        try {
            stmtInsert.setDate(1, new java.sql.Date(paragon.getDataZakupu().getTime()));
            stmtInsert.registerOutParameter(2, Types.INTEGER);
            stmtInsert.execute();
            int id = stmtInsert.getInt(2);
            paragon.setId(id);

        } catch (SQLException ex) {
            Logger.getLogger(ParagonDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
        }
    }

    public List<Paragon> getParagony() {
        return paragony;
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    public void closeStatements() {
        if (stmtSelect != null) {
            try {
                stmtSelect.close();
            } catch (SQLException e) {
                /* kod obsługi */
                System.out.println("Błąd zamknięcia interfejsu Statement");
            }
        }
        if (stmtInsert != null) {
            try {
                stmtInsert.close();
            } catch (SQLException e) {
                /* kod obsługi */
                System.out.println("Błąd zamknięcia interfejsu Statement");
            }
        }
        if (stmtUpdate != null) {
            try {
                stmtUpdate.close();
            } catch (SQLException e) {
                /* kod obsługi */
                System.out.println("Błąd zamknięcia interfejsu Statement");
            }
        }
        if (stmtDelete != null) {
            try {
                stmtDelete.close();
            } catch (SQLException e) {
                /* kod obsługi */
                System.out.println("Błąd zamknięcia interfejsu Statement");
            }
        }
    }
}
