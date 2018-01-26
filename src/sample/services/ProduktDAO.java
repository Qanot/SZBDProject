package sample.services;

import sample.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.controllers.Controller.showAlertEmptyForm;

/**
 * ZALOZENIA:
 * id jest niezmienne (klucz podstawowy)
 * przy wstawianiu rekordu id generuje wyzwalacz za pomoca sekwencji
 **/


public class ProduktDAO extends DAO{

    private List<Produkt> produkty;

    public ProduktDAO(ConnectionController connectionController) {
        super(connectionController);
        produkty = new ArrayList<Produkt>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT id, cena, nazwa, rozmiar_porcji FROM PRODUKTY");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM PRODUKTY WHERE ID = ?");
            this.stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_produkt(?, ?, ?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_produkt(?, ?, ?, ?)}");
            this.stmtFindById = connectionController.getConn().prepareStatement(
                    "SELECT cena, nazwa, rozmiar_porcji FROM PRODUKTY WHERE ID = ?");

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
            showAlertEmptyForm("Nie można usunąć produktu, bo jest powiązany z innymi rekordami!");
            Logger.getLogger(ProduktDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }
    }

    public boolean updateProdukt(Produkt produkt) {
        try {
            stmtUpdate.registerOutParameter(1, Types.INTEGER);
            stmtUpdate.setInt(2, produkt.getId());
            stmtUpdate.setDouble(3, produkt.getCena());
            stmtUpdate.setString(4, produkt.getNazwa());
            stmtUpdate.setString(5, produkt.getRozmiarPorcji().name()); // name zwroci dokladnie S lub M lub L
            // nawet jesli nadpiszemy w przyszlosci toString
            stmtUpdate.executeUpdate();
            int wykonananoPoprawnie = stmtUpdate.getInt(1);
            return wykonananoPoprawnie == 1;
        } catch (SQLException ex) {
            Logger.getLogger(ProduktDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
            return false;
        }
    }

    public boolean insertProdukt(Produkt produkt) {
        try {
            stmtInsert.registerOutParameter(1, Types.INTEGER);
            stmtInsert.setDouble(2, produkt.getCena());
            stmtInsert.setString(3, produkt.getNazwa());
            stmtInsert.setString(4, produkt.getRozmiarPorcji().name());
            stmtInsert.registerOutParameter(5, Types.INTEGER);

            stmtInsert.execute();
            int id = stmtInsert.getInt(5);
            produkt.setId(id);

            int wykonanoPoprawnie = stmtInsert.getInt(1);
            return wykonanoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(ProduktDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
            return false;
        }
    }


    public Produkt getProduktById(int id){
        Produkt produkt = null;
        try {
            stmtFindById.setInt(1, id);
            rsSelect = stmtFindById.executeQuery();
            if (rsSelect.next()) {
                Double cena = rsSelect.getDouble(1);
                String nazwa = rsSelect.getString(2);
                String rozmiarString = rsSelect.getString(3);
                rozmiarString = rozmiarString.substring(0, 1); // pierwsza litera
                RozmiarPorcji rozmiarPorcji = RozmiarPorcji.valueOf(rozmiarString);

                produkt = new Produkt(cena, nazwa, rozmiarPorcji);
                produkt.setId(id);
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
        return produkt;
    }

}
