package sample.services;

import sample.model.Sala;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO dostosowac do nowego modelu bazy (w drugiej kolejnosci, bo nie jest lisciem (ma dziecko pracownik)

public class SalaDAO {

//    private ConnectionController connectionController;
//    private List<Sala> sale;
//    private PreparedStatement stmtSelect = null;
//    private PreparedStatement stmtDelete = null;
//    private CallableStatement stmtUpdate = null;
//    private CallableStatement stmtInsert = null;
//    private ResultSet rsSelect = null;
//
//    public SalaDAO(ConnectionController connectionController) {
//        this.setConnectionController(connectionController);
//        sale = new ArrayList<Sala>();
//
//        try {
//            stmtSelect = connectionController.getConn().prepareStatement(
//                    "SELECT nr_sali from SALE");
//            stmtDelete = connectionController.getConn().prepareStatement(
//                    "DELETE FROM SALE WHERE NR_SALI = ?");
//            stmtUpdate = connectionController.getConn().prepareCall(
//                    "{? = call update_sala(?, ?)}");
//            stmtInsert = connectionController.getConn().prepareCall(
//                    "{? = call wstaw_sale(?)}");
//
//        } catch (SQLException ex) {
//            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
//                    "Błąd przygotowania prekompilowanego polecenia", ex);
//        }
//    }
//    public void setConnectionController(ConnectionController connectionController) {
//        this.connectionController = connectionController;
//    }
//
//    public List<Sala> getSale() {
//        return sale;
//    }
//
//    private void selectSale() {
//        sale.clear();
//        try {
//            rsSelect = stmtSelect.executeQuery();
//            while (rsSelect.next()) {
//                int nrSali = rsSelect.getInt(1);
//                Sala sala = new Sala(nrSali);
//                sale.add(sala);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
//                    "Błąd wykonania prekompilowanego polecenia select", ex);
//        } finally {
//            if (rsSelect != null) {
//                try {
//                    rsSelect.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
//                            "Błąd zamykania interfejsu ResultSet", ex);
//                }
//            }
//        }
//    }
//
//    public void deleteSala(Sala sala) {
//        try {
//            stmtDelete.setInt(1, sala.getNrSali());
//            int changes = stmtDelete.executeUpdate();
//            if (changes != 1) {
//                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
//                    "Błąd wykonania prekompilowanego polecenia delete", ex);
//        }
//    }
//    /**
//     *
//     * @param sala sala, ktorej nr chcemy zmienic
//     * @return true jeśli udało się zmienic nr sali, false w przeciwnym wypadku
//     * (istnieje już sala o danym nr sali podanym jako nowy)
//     */
//    public boolean updateSala(Sala sala) {
//        try {
//            stmtUpdate.registerOutParameter(1, Types.INTEGER);
//            stmtUpdate.setInt(2, sala.getNrSaliStary());
//            stmtUpdate.setInt(3, sala.getNrSali());
//            stmtUpdate.execute();
//
//            sala.setNrSali(sala.getNrSali()); // ustawi nrSaliStary na wartosc rowna nrSali
//
//            int wykonananoPoprawnie = stmtUpdate.getInt(1);
//            return wykonananoPoprawnie == 1;
//
//        } catch (SQLException ex) {
//            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
//                    "Błąd wykonania prekompilowanego polecenia update", ex);
//            return false;
//        }
//    }
//
//
//    /**
//     *
//     * @param sala sala do wstawienia do relacji
//     * @return true jeśli udało się wstawić salę o danym nr sali, false w przeciwnym wypadku
//     * (istnieje już sala o danym nr sali)
//     */
//    public boolean insertSala(Sala sala) {
//        try {
//            stmtInsert.registerOutParameter(1, Types.INTEGER);
//            stmtInsert.setInt(2, sala.getNrSali());
//            stmtInsert.execute();
//
//            int wykonananoPoprawnie = stmtInsert.getInt(1);
//            return wykonananoPoprawnie == 1;
//
//        } catch (SQLException ex) {
//            Logger.getLogger(SalaDAO.class.getName()).log(Level.SEVERE,
//                    "Błąd wykonania prekompilowanego polecenia insert", ex);
//            return false;
//        }
//    }
//
//    public void closeStatements() {
//        if (stmtSelect != null) {
//            try {
//                stmtSelect.close();
//            } catch (SQLException e) {
//                /* kod obsługi */
//                System.out.println("Błąd zamknięcia interfejsu Statement");
//            }
//        }
//        if (stmtInsert != null) {
//            try {
//                stmtInsert.close();
//            } catch (SQLException e) {
//                /* kod obsługi */
//                System.out.println("Błąd zamknięcia interfejsu Statement");
//            }
//        }
//        if (stmtUpdate != null) {
//            try {
//                stmtUpdate.close();
//            } catch (SQLException e) {
//                /* kod obsługi */
//                System.out.println("Błąd zamknięcia interfejsu Statement");
//            }
//        }
//        if (stmtDelete != null) {
//            try {
//                stmtDelete.close();
//            } catch (SQLException e) {
//                /* kod obsługi */
//                System.out.println("Błąd zamknięcia interfejsu Statement");
//            }
//        }
//    }
//
//

}
