package sample.services;

import sample.model.Plec;
import sample.model.Pracownik;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ZALOZENIA:
 * id jest niezmienne (klucz podstawowy)
 * przy wstawianiu rekordu id generuje wyzwalacz za pomoca sekwencji
 **/


public class PracownikDAO {

    private ConnectionController connectionController;
    private List<Pracownik> pracownicy;
    private PreparedStatement stmtSelect = null;
    private PreparedStatement stmtDelete = null;
    private CallableStatement stmtUpdate = null;
    private CallableStatement stmtInsert = null;
    private ResultSet rsSelect = null;

    public PracownikDAO(ConnectionController connectionController) {
        this.setConnectionController(connectionController);
        pracownicy = new ArrayList<Pracownik>();

        try {
            stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT id, imie, nazwisko, plec, PESEL FROM PRACOWNICY");
            stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM PRACOWNICY WHERE ID = ?");
            stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_pracownika(?, ?, ?, ?, ?)}");
            stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_pracownika(?, ?, ?, ?, ?)}");

        } catch (SQLException ex) {
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }
    }

    private void selectPracownicy() {
        pracownicy.clear();
        try {
            rsSelect = stmtSelect.executeQuery();
            while (rsSelect.next()) {
                int id = rsSelect.getInt(1);
                String imie = rsSelect.getString(2);
                String nazwisko = rsSelect.getString(3);
                String plecString = rsSelect.getString(4);
                plecString = plecString.substring(0, 1); // pierwsza litera
                Plec plec = Plec.valueOf(plecString);
                String PESEL = rsSelect.getString(5);
                Pracownik pracownik = new Pracownik(imie, nazwisko, plec, PESEL);
                pracownik.setId(id);
                pracownicy.add(pracownik);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
    }

    public List<Pracownik> getPracownicy() {
        selectPracownicy();
        return pracownicy;
    }

    public void deletePracownik(Pracownik pracownik) {
        try {
            stmtDelete.setInt(1, pracownik.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }
    }

    public boolean updatePracownik(Pracownik pracownik) {
        try {
            stmtUpdate.registerOutParameter(1, Types.INTEGER);
            stmtUpdate.setInt(2, pracownik.getId());
            stmtUpdate.setString(3, pracownik.getImie());
            stmtUpdate.setString(4, pracownik.getNazwisko());
            stmtUpdate.setString(5, pracownik.getPlec().name()); // name zwroci dokladnie "K" lub "M",
            // nawet jesli nadpiszemy w przyszlosci toString
            stmtUpdate.setString(6, pracownik.getPESEL());
            stmtUpdate.executeUpdate();

            int wykonananoPoprawnie = stmtUpdate.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
            return false;
        }
    }

    public boolean insertPracownik(Pracownik pracownik) {
        try {
            stmtInsert.registerOutParameter(1, Types.INTEGER);
            stmtInsert.setString(2, pracownik.getImie());
            stmtInsert.setString(3, pracownik.getNazwisko());
            stmtInsert.setString(4, pracownik.getPlec().name());
            stmtInsert.setString(5, pracownik.getPESEL());
            stmtInsert.registerOutParameter(6, Types.INTEGER);

            stmtInsert.execute();
            int id = stmtInsert.getInt(6);
            pracownik.setId(id);

            int wykonananoPoprawnie = stmtInsert.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
            return false;
        }
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
