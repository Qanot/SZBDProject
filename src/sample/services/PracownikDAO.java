package sample.services;

import sample.model.Plec;
import sample.model.Pracownik;
import sample.model.Produkt;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.controllers.Controller.showAlertEmptyForm;

/**
 * ZALOZENIA:
 * id jest niezmienne (klucz podstawowy)
 * przy wstawianiu rekordu id generuje wyzwalacz za pomoca sekwencji
 **/


public class PracownikDAO extends DAO {

    private List<Pracownik> pracownicy;

    public PracownikDAO(ConnectionController connectionController) {
        super(connectionController);
        pracownicy = new ArrayList<Pracownik>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT id, imie, nazwisko, plec, PESEL FROM PRACOWNICY");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM PRACOWNICY WHERE ID = ?");
            this.stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_pracownika(?, ?, ?, ?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_pracownika(?, ?, ?, ?, ?)}");
            this.stmtFindById = connectionController.getConn().prepareStatement(
                    "SELECT imie, nazwisko, plec, PESEL FROM PRACOWNICY WHERE id = ?");

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
            showAlertEmptyForm("Nie można usunąć pracownika, bo jest powiązany z innymi rekordami!");
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

    public Pracownik getPracownikById(int id) {
        Pracownik pracownik = null;
        try {
            stmtFindById.setInt(1, id);
            rsSelect = stmtFindById.executeQuery();
            if (rsSelect.next()) {
                String imie = rsSelect.getString(1);
                String nazwisko = rsSelect.getString(2);
                String plecString = rsSelect.getString(3);
                plecString = plecString.substring(0, 1); // pierwsza litera
                Plec plec = Plec.valueOf(plecString);
                String PESEL = rsSelect.getString(4);
                pracownik = new Pracownik(imie, nazwisko, plec, PESEL);
                pracownik.setId(id);
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
        return pracownik;
    }


}
