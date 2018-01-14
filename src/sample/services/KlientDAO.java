package sample.services;

import sample.model.Klient;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ZALOZENIA:
 * login jest niezmienny (klucz podstawowy), wiec nie mozna go updatowac
 * przy wstawianiu rekordu, jesli metoda insert zwroci false, oznacza to, ze dany login juz istnieje
 * (naruszenie ograniczenia integralnosciowego)
 **/

// TODO prztestowac

public class KlientDAO {
    private ConnectionController connectionController;
    private List<Klient> klienci;
    private PreparedStatement stmtSelect = null;
    private PreparedStatement stmtDelete = null;
    private PreparedStatement stmtUpdate = null;
    private CallableStatement stmtInsert = null;
    private ResultSet rsSelect = null;

    public KlientDAO(ConnectionController connectionController){
        this.setConnectionController(connectionController);
        klienci = new ArrayList<Klient>();

        try {
            stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT imie, nazwisko, email, login, haslo, telefon FROM KLIENCI");
            stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM KLIENCI WHERE LOGIN = ?");
            stmtUpdate = connectionController.getConn().prepareStatement(
                    "UPDATE KLIENCI SET imie = ?, nazwisko = ?, email = ?, haslo =?, telefon = ? WHERE LOGIN = ?");
            stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_klienta(?, ?, ?, ?, ?, ?)}");
            selectKlienci();

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
                String imie = rsSelect.getString(1);
                String nazwisko = rsSelect.getString(2);
                String email = rsSelect.getString(3);
                String login = rsSelect.getString(4);
                String haslo = rsSelect.getString(5);
                String telefon = rsSelect.getString(6);

                Klient klient = new Klient(imie, nazwisko, email, login, haslo, telefon);
                klienci.add(klient);
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

    public List<Klient> getKlienci() {
        selectKlienci();
        return klienci;
    }

    public void deleteKlient(Klient klient) {
        try {
            stmtDelete.setString(1, klient.getLogin());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }
    }

    public void updatePracownik(Klient klient) {
        try {
            stmtUpdate.setString(1, klient.getImie());
            stmtUpdate.setString(2, klient.getNazwisko());
            stmtUpdate.setString(3, klient.getEmail());
            stmtUpdate.setString(4, klient.getHaslo());
            stmtUpdate.setString(5, klient.getTelefon());
            stmtUpdate.setString(6, klient.getLogin());

            int changes = stmtUpdate.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie zmodyfikowano dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
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

            stmtInsert.execute();

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

}
