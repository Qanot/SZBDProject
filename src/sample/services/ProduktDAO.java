package sample.services;

import sample.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ZALOZENIA:
 * id jest niezmienne (klucz podstawowy)
 * przy wstawianiu rekordu id generuje wyzwalacz za pomoca sekwencji
 **/


public class ProduktDAO {

    private ConnectionController connectionController;
    private List<Produkt> produkty;
    private PreparedStatement stmtSelect = null;
    private PreparedStatement stmtDelete = null;
    private PreparedStatement stmtUpdate = null;
    private CallableStatement stmtInsert = null;
    private ResultSet rsSelect = null;

    public ProduktDAO(ConnectionController connectionController) {
        this.setConnectionController(connectionController);
        produkty = new ArrayList<Produkt>();

        try {
            stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT id, cena, nazwa, rozmiar_porcji FROM PRODUKTY");
            stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM PRACOWNICY WHERE ID = ?");
            stmtUpdate = connectionController.getConn().prepareStatement(
                    "UPDATE PRODUKTY SET CENA = ?, NAZWA = ?, ROZMIR_PORCJI = ? WHERE ID = ?");
            stmtInsert = connectionController.getConn().prepareCall(
                    "{call wstaw_produkt(?, ?, ?, ?)}"); //TO DO

        } catch (SQLException ex) {
            Logger.getLogger(ProduktDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }
    }

    private void selectProdukty() {
        produkty.clear();
        try {
            rsSelect = stmtSelect.executeQuery();
            while (rsSelect.next()) {
                int id = rsSelect.getInt(1);
                Double cena = rsSelect.getDouble(2);
                String nazwa = rsSelect.getString(3);
                String rozmiarString = rsSelect.getString(4);
                rozmiarString = rozmiarString.substring(0, 1); // pierwsza litera
                RozmiarPorcji rozmiarPorcji = RozmiarPorcji.valueOf(rozmiarString);
                Produkt produkt = new Produkt(cena, nazwa, rozmiarPorcji);
                produkt.setId(id);
                produkty.add(produkt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduktDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProduktDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
    }

    public List<Produkt> getProdukty() {
        selectProdukty();
        return produkty;
    }

    public void deleteProdukt(Produkt produkt) {
        try {
            stmtDelete.setInt(1, produkt.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduktDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }
    }

    public void updateProdukt(Produkt produkt) {
        try {
            stmtUpdate.setDouble(1, produkt.getCena());
            stmtUpdate.setString(2, produkt.getNazwa());
            stmtUpdate.setString(3, produkt.getRozmiarPorcji().name()); // name zwroci dokladnie "K" lub "M",
            // nawet jesli nadpiszemy w przyszlosci toString
            stmtUpdate.setInt(4, produkt.getId());
            int changes = stmtUpdate.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie zmodyfikowano dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduktDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
        }
    }

    public void insertProdukt(Produkt produkt) {
        try {
            stmtInsert.setDouble(1, produkt.getCena());
            stmtInsert.setString(2, produkt.getNazwa());
            stmtInsert.setString(3, produkt.getRozmiarPorcji().name());
            stmtInsert.registerOutParameter(4, Types.INTEGER);
            stmtInsert.execute();
            int id = stmtInsert.getInt(4);
            produkt.setId(id);
        } catch (SQLException ex) {
            Logger.getLogger(ProduktDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
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
