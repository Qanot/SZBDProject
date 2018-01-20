package sample.services;

import sample.model.Film;
import sample.model.Sala;
import sample.model.Seans;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeansDAO extends DAO {

    private List<Seans> seanse;

    public SeansDAO(ConnectionController connectionController) {
        super(connectionController);
        seanse = new ArrayList<Seans>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT ID, DATA_GODZINA, FILMY_ID, SALE_ID FROM SEANSE");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM SEANSE WHERE ID = ?");
            this.stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_seans(?, ?, ?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_seans(?, ?, ?, ?)}");
            this.stmtFindById = connectionController.getConn().prepareStatement(
                    "SELECT DATA_GODZINA, FILMY_ID, SALE_ID FROM SEANSE WHERE ID = ?");

        } catch (SQLException ex) {
            Logger.getLogger(RodzajBiletuDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }
    }

    public void selectSeanse() {

        try {
            rsSelect = stmtSelect.executeQuery();
            seanse.clear();
            FilmDAO filmDAO = new FilmDAO(connectionController);
            SalaDAO salaDAO = new SalaDAO(connectionController);
            while (rsSelect.next()) {
                int id = rsSelect.getInt("ID");
                Date dataEmisji = new Date(rsSelect.getTimestamp("DATA_GODZINA").getTime());
                int idFilmu = rsSelect.getInt("FILMY_ID");
                int idSali = rsSelect.getInt("SALE_ID");

                Film film = filmDAO.getFilmById(idFilmu);
                Sala sala = salaDAO.getSalaById(idSali);

                Seans seans = new Seans(dataEmisji, film, sala);
                seans.setId(id);
                seanse.add(seans);
            }
            filmDAO.closeStatements();
            salaDAO.closeStatements();
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
    }

    public List<Seans> getSeanse() {
        selectSeanse();
        return seanse;
    }

    public boolean insertSeans(Seans seans) {
        try {
            stmtInsert.registerOutParameter(1, Types.INTEGER);
            stmtInsert.setDate(2, new java.sql.Date(seans.getDataEmisji().getTime())); // to chyba dziala
            //stmtInsert.setObject(2, seans.getDataEmisji()); // nie dziala :/
            stmtInsert.setInt(3, seans.getFilm().getId());
            stmtInsert.setInt(4, seans.getSala().getId());
            stmtInsert.registerOutParameter(5, Types.INTEGER);

            stmtInsert.execute();
            int id = stmtInsert.getInt(5);
            seans.setId(id);

            int wykonananoPoprawnie = stmtInsert.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
            return false;
        }
    }

    public boolean updateSeans(Seans seans) {
        try {
            stmtUpdate.registerOutParameter(1, Types.INTEGER);
            stmtUpdate.setInt(2, seans.getId());
            stmtUpdate.setDate(3, new java.sql.Date(seans.getDataEmisji().getTime())); // to chyba dziala
            stmtUpdate.setInt(4, seans.getFilm().getId());
            stmtUpdate.setInt(5, seans.getSala().getId());

            stmtUpdate.execute();

            int wykonananoPoprawnie = stmtUpdate.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
            return false;
        }
    }


    public void deleteSeans(Seans seans) {
        try {
            stmtDelete.setInt(1, seans.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }
    }

    public Seans getSeansById(int id) {
        Seans seans = null;
        try {
            stmtFindById.setInt(1, id);
            rsSelect = stmtFindById.executeQuery();
            if (rsSelect.next()) {
                Date dataEmisji = new Date(rsSelect.getTimestamp("DATA_GODZINA").getTime());
                int idFilmu = rsSelect.getInt("FILMY_ID");
                int idSali = rsSelect.getInt("SALE_ID");

                FilmDAO filmDAO = new FilmDAO(connectionController);
                Film film = filmDAO.getFilmById(idFilmu);
                filmDAO.closeStatements();

                SalaDAO salaDAO = new SalaDAO(connectionController);
                Sala sala = salaDAO.getSalaById(idSali);
                salaDAO.closeStatements();

                seans = new Seans(dataEmisji, film, sala);
                seans.setId(id);
            }
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
        return seans;
    }


}
