package sample.services;

import sample.model.Paragon;
import sample.model.Pracownik;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class ParagonDAO extends DAO {


    private List<Paragon> paragony;

    public ParagonDAO(ConnectionController connectionController) {

        super(connectionController);
        paragony = new ArrayList<Paragon>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT id, data_godzina, pracownicy_id FROM PARAGONY");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM PARAGONY WHERE ID = ?");
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
                int id = rsSelect.getInt("ID");
                Date dataZakupu = new java.util.Date(rsSelect.getTimestamp("DATA_GODZINA").getTime());
                int idPracownika = rsSelect.getInt("PRACOWNICY_ID");

                Pracownik pracownik = pracownikDAO.getPracownikById(idPracownika);

                Paragon nowyParagon = new Paragon(dataZakupu, pracownik);
                nowyParagon.setId(id);

//                nowyParagon.setProduktyNaParagonie(this.getProduktuNaParagonie(nowyParagon); //jeszcze nie zrobione
                paragony.add(nowyParagon);
            }
            pracownikDAO.closeStatements();
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

    public boolean updateParagon(Paragon paragon) {
        try {
            stmtUpdate.registerOutParameter(1, Types.INTEGER);
            stmtUpdate.setInt(2, paragon.getId());
            stmtUpdate.setDate(3, new java.sql.Date(paragon.getDataZakupu().getTime())); // to chyba dziala
            stmtUpdate.setInt(4, paragon.getPracownikNabijajacyParagon().getId());

            stmtUpdate.execute();

//            paragon.setProduktyNaParagonie(this.getProduktyNaParagonie());

            int wykonananoPoprawnie = stmtUpdate.getInt(1);
            return wykonananoPoprawnie == 1;
        } catch (SQLException ex) {
            Logger.getLogger(ParagonDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
        }
        return false;
    }

    public boolean insertParagon(Paragon paragon) {
        try {
            stmtInsert.registerOutParameter(1, Types.INTEGER);
            stmtInsert.setDate(2, new java.sql.Date(paragon.getDataZakupu().getTime())); // to chyba dziala
            stmtInsert.setInt(3, paragon.getPracownikNabijajacyParagon().getId());
            stmtInsert.registerOutParameter(4, Types.INTEGER);

            stmtInsert.execute();
            int id = stmtInsert.getInt(4);
            paragon.setId(id);
//            paragon.setProduktyNaParagonie(this.getProduktyNaParagonie());

            int wykonananoPoprawnie = stmtInsert.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(ParagonDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
        }
        return false;
    }
    public List<Paragon> getParagony() {
        selectParagony();
        return paragony;
    }

    public Paragon getParagonById(int id) {
        Paragon paragon = null;
        try {
            stmtFindById.setInt(1, id);
            rsSelect = stmtFindById.executeQuery();
            PracownikDAO pracownikDAO = new PracownikDAO(connectionController);
            if (rsSelect.next()) {
                Date dataZakupu = new Date(rsSelect.getTimestamp("DATA_GODZINA").getTime());
                int idPracownika= rsSelect.getInt("PRACOWNICY_ID");

                Pracownik pracownik = pracownikDAO.getPracownikById(idPracownika);

                paragon = new Paragon(dataZakupu, pracownik);
                paragon.setId(id);
//                paragon.setProduktyNaParagonie(this.getProduktyNaParagonie(paragon));
               }
            pracownikDAO.closeStatements();
        } catch (SQLException ex) {
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
        return paragon;
    }
}
