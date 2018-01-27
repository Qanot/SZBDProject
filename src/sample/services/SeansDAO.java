package sample.services;

import sample.model.Film;
import sample.model.Miejsce;
import sample.model.Sala;
import sample.model.Seans;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
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
                seans.setMiejscaWolne(this.getWolneMiejscaDlaSeansu(seans));
                seans.setMiejscaWykupione(this.getWykupioneMiejscaDlaSeansu(seans));
                seans.setMiejscaZarezerwowane(this.getZarezerwowaneMiejscaDlaSeansu(seans));

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
            seans.setMiejscaWolne(this.getWolneMiejscaDlaSeansu(seans));
            seans.setMiejscaWykupione(this.getWykupioneMiejscaDlaSeansu(seans));
            seans.setMiejscaZarezerwowane(this.getZarezerwowaneMiejscaDlaSeansu(seans));

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

            seans.setMiejscaWolne(this.getWolneMiejscaDlaSeansu(seans));
            seans.setMiejscaWykupione(this.getWykupioneMiejscaDlaSeansu(seans));
            seans.setMiejscaZarezerwowane(this.getZarezerwowaneMiejscaDlaSeansu(seans));

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
            FilmDAO filmDAO = new FilmDAO(connectionController);
            if (rsSelect.next()) {
                Date dataEmisji = new Date(rsSelect.getTimestamp("DATA_GODZINA").getTime());
                int idFilmu = rsSelect.getInt("FILMY_ID");
                int idSali = rsSelect.getInt("SALE_ID");

                Film film = filmDAO.getFilmById(idFilmu);

                SalaDAO salaDAO = new SalaDAO(connectionController);
                Sala sala = salaDAO.getSalaById(idSali);
                salaDAO.closeStatements();

                seans = new Seans(dataEmisji, film, sala);
                seans.setId(id);
                seans.setMiejscaWolne(this.getWolneMiejscaDlaSeansu(seans));
                seans.setMiejscaWykupione(this.getWykupioneMiejscaDlaSeansu(seans));
                seans.setMiejscaZarezerwowane(this.getZarezerwowaneMiejscaDlaSeansu(seans));
            }
            filmDAO.closeStatements();
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

    private List<Miejsce> getWolneMiejscaDlaSeansu(Seans seans){
        List<Miejsce> miejscaWolne = new ArrayList<Miejsce>();
        PreparedStatement stmtSelectWolne = null;
        ResultSet rs = null;
        try {
            stmtSelectWolne = connectionController.getConn().prepareStatement(
                    "SELECT MMIEJSCA.ID FROM MIEJSCA MMIEJSCA" +
                            " WHERE SALE_ID = ?" +
                            " AND NOT EXISTS(" +
                            "   SELECT 1 FROM MIEJSCANASEANSIE" +
                            "   WHERE MIEJSCA_ID = MMIEJSCA.ID" +
                            "   AND SEANSE_ID = ?)");
            stmtSelectWolne.setInt(1, seans.getSala().getId());
            stmtSelectWolne.setInt(2, seans.getId());
            rs = stmtSelectWolne.executeQuery();
            MiejsceDAO miejsceDAO = new MiejsceDAO(connectionController);
            while (rs.next()) {
                int idMiejsca = rs.getInt(1);
                Miejsce miejsce = miejsceDAO.getMiejsceById(idMiejsca);
                miejscaWolne.add(miejsce);
            }
            miejsceDAO.closeStatements();

        }catch (SQLException ex) {
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania polecenia select", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (stmtSelectWolne != null) {
                try {
                    stmtSelectWolne.close();
                } catch (SQLException e) {
                    /* kod obsługi */
                    System.out.println("Błąd zamknięcia interfejsu Statement");
                }
            }
        }
        return miejscaWolne;
    }

   private List<Miejsce> getWykupioneMiejscaDlaSeansu(Seans seans){
        List<Miejsce> miejscaWykupione = new ArrayList<Miejsce>();
        PreparedStatement stmtSelectWykupione = null;
        ResultSet rs = null;
        try {
            stmtSelectWykupione = connectionController.getConn().prepareStatement(
                    "SELECT MMIEJSCA.ID FROM MIEJSCA MMIEJSCA" +
                            " WHERE SALE_ID = ?" +
                            " AND EXISTS(" +
                            "   SELECT 1 FROM MIEJSCANASEANSIE" +
                            "   WHERE MIEJSCA_ID = MMIEJSCA.ID" +
                            "   AND SEANSE_ID = ?" +
                            "   AND BILETY_ID IS NOT NULL)");
            stmtSelectWykupione.setInt(1, seans.getSala().getId());
            stmtSelectWykupione.setInt(2, seans.getId());
            rs = stmtSelectWykupione.executeQuery();
            MiejsceDAO miejsceDAO = new MiejsceDAO(connectionController);
            while (rs.next()) {
                int idMiejsca = rs.getInt(1);
                Miejsce miejsce = miejsceDAO.getMiejsceById(idMiejsca);
                miejscaWykupione.add(miejsce);
            }
            miejsceDAO.closeStatements();
        }catch (SQLException ex) {
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania polecenia select", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (stmtSelectWykupione != null) {
                try {
                    stmtSelectWykupione.close();
                } catch (SQLException e) {
                    /* kod obsługi */
                    System.out.println("Błąd zamknięcia interfejsu Statement");
                }
            }
        }
        return miejscaWykupione;
    }

    private List<Miejsce> getZarezerwowaneMiejscaDlaSeansu(Seans seans){

        List<Miejsce> miejscaZarezerwowane = new ArrayList<Miejsce>();
        PreparedStatement stmtSelectZarezerwowane = null;
        ResultSet rs = null;
        try {
            stmtSelectZarezerwowane = connectionController.getConn().prepareStatement(
                    "SELECT MMIEJSCA.ID FROM MIEJSCA MMIEJSCA" +
                            " WHERE SALE_ID = ?" +
                            " AND EXISTS(" +
                            "   SELECT 1 FROM MIEJSCANASEANSIE" +
                            "   WHERE MIEJSCA_ID = MMIEJSCA.ID" +
                            "   AND SEANSE_ID = ?" +
                            "   AND REZERWACJE_ID IS NOT NULL)");
            stmtSelectZarezerwowane.setInt(1, seans.getSala().getId());
            stmtSelectZarezerwowane.setInt(2, seans.getId());
            rs = stmtSelectZarezerwowane.executeQuery();
            MiejsceDAO miejsceDAO = new MiejsceDAO(connectionController);
            while (rs.next()) {
                int idMiejsca = rs.getInt(1);
                Miejsce miejsce = miejsceDAO.getMiejsceById(idMiejsca);
                miejscaZarezerwowane.add(miejsce);
            }
            miejsceDAO.closeStatements();
        }catch (SQLException ex) {
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania polecenia select", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (stmtSelectZarezerwowane != null) {
                try {
                    stmtSelectZarezerwowane.close();
                } catch (SQLException e) {
                    /* kod obsługi */
                    System.out.println("Błąd zamknięcia interfejsu Statement");
                }
            }
        }
        return miejscaZarezerwowane;
    }

}
