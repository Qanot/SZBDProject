package sample.services;

import sample.model.Klient;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ZALOZENIA:
 * login jest kluczem unikalnym,
 * przy wstawianiu rekordu, jesli metoda insert zwroci false, oznacza to, ze dany login juz istnieje
 * (naruszenie ograniczenia integralnosciowego)
 **/

public class KlientDAO extends DAO{

    private List<Klient> klienci;

    public KlientDAO(ConnectionController connectionController){
        super(connectionController);
        klienci = new ArrayList<Klient>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT id, imie, nazwisko, email, login, haslo, telefon FROM KLIENCI");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM KLIENCI WHERE id = ?");
            this.stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_klienta(?, ?, ?, ?, ?, ?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_klienta(?, ?, ?, ?, ?, ?, ?)}");
            this.stmtFindById = connectionController.getConn().prepareStatement(
              "SELECT imie, nazwisko, email, login, haslo, telefon FROM PRACOWNICY WHERE ID = ?");

        } catch (SQLException ex) {
            Logger.getLogger(KlientDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }
    }

    private void selectKlienci() {
        klienci.clear();
        try {
            rsSelect = stmtSelect.executeQuery();
            while (rsSelect.next()) {
                int id = rsSelect.getInt(1);
                String imie = rsSelect.getString(2);
                String nazwisko = rsSelect.getString(3);
                String email = rsSelect.getString(4);
                String login = rsSelect.getString(5);
                String haslo = rsSelect.getString(6);
                String telefon = rsSelect.getString(7);

                Klient klient = new Klient(imie, nazwisko, email, login, haslo, telefon);
                klient.setId(id);
                klienci.add(klient);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KlientDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KlientDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
    }

    public List<Klient> getKlienci() {
        selectKlienci();
        return klienci;
    }

    public void deleteKlient(Klient klient) {
        try {
            stmtDelete.setInt(1, klient.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(KlientDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }
    }

    public boolean updateKlient(Klient klient) {
        try {
            stmtUpdate.registerOutParameter(1, Types.INTEGER);
            stmtUpdate.setInt(2, klient.getId());
            stmtUpdate.setString(3, klient.getImie());
            stmtUpdate.setString(4, klient.getNazwisko());
            stmtUpdate.setString(5, klient.getEmail());
            stmtUpdate.setString(6, klient.getHaslo());
            stmtUpdate.setString(7, klient.getTelefon());
            stmtUpdate.setString(8, klient.getLogin());

            stmtUpdate.execute();

            int wykonananoPoprawnie = stmtUpdate.getInt(1);
            return wykonananoPoprawnie == 1;
        } catch (SQLException ex) {
            Logger.getLogger(KlientDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
            return false;
        }
    }

    /**
     *
     * @param klient klient do wstawienia do relacji
     * @return true jeśli udało się wstawić klienta o danym loginie, false w przeciwnym wypadku
     * (login zajęty)
     */
    public boolean insertKlient(Klient klient) {
        try {

            stmtInsert.registerOutParameter(1, Types.INTEGER);
            stmtInsert.setString(2, klient.getImie());
            stmtInsert.setString(3, klient.getNazwisko());
            stmtInsert.setString(4, klient.getEmail());
            stmtInsert.setString(5, klient.getLogin());
            stmtInsert.setString(6, klient.getHaslo());
            stmtInsert.setString(7, klient.getTelefon());
            stmtInsert.registerOutParameter(8, Types.INTEGER);

            stmtInsert.execute();
            int id = stmtInsert.getInt(8);
            klient.setId(id);

            int wykonananoPoprawnie = stmtInsert.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(KlientDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
            return false;
        }
    }

    public Klient getKlientById(int id){
        Klient klient = null;
        try {
            stmtFindById.setInt(1, id);
            rsSelect = stmtFindById.executeQuery();
            if (rsSelect.next()) {
                String imie = rsSelect.getString(1);
                String nazwisko = rsSelect.getString(2);
                String email = rsSelect.getString(3);
                String login = rsSelect.getString(4);
                String haslo = rsSelect.getString(5);
                String telefon = rsSelect.getString(6);

                klient = new Klient(imie, nazwisko, email, login, haslo, telefon);
                klient.setId(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KlientDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KlientDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
        return klient;
    }
}
