package sample.services;

import sample.model.Film;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;


public class FilmDAO extends DAO{

    private List<Film> filmy;

    public FilmDAO(ConnectionController connectionController) {
        super(connectionController);
        filmy = new ArrayList<Film>();

        try {
            this.stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT ID, TYTUL, CZAS_TRWANIA FROM FILMY");
            this.stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM FILMY WHERE ID = ?");
            this.stmtUpdate = connectionController.getConn().prepareCall(
                    "{? = call update_film(?, ?, ?)}");
            this.stmtInsert = connectionController.getConn().prepareCall(
                    "{? = call wstaw_film(?, ?, ?)}");
            this.stmtFindById = connectionController.getConn().prepareStatement(
                    "SELECT TYTYL, CZAS_TRWANIA FROM FILMY WHERE ID = ?");

        } catch (SQLException ex) {
            Logger.getLogger(FilmDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }
    }

    public void selectFilmy(){
        filmy.clear();
        try {
            rsSelect = stmtSelect.executeQuery();
            while (rsSelect.next()) {
                int id = rsSelect.getInt(1);
                String tytul = rsSelect.getString(2);
                int czasTrwaniaWMin = rsSelect.getInt(3);

                Film film = new Film(tytul, czasTrwaniaWMin);
                film.setId(id);
                filmy.add(film);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rsSelect != null) {
                try {
                    rsSelect.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FilmDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
        }
    }

    public List<Film> getFilmy() {
        selectFilmy();
        return filmy;
    }

    public boolean insertFilm(Film film){
        try {
            stmtInsert.registerOutParameter(1, Types.INTEGER);
            stmtInsert.setString(2, film.getTytul());
            stmtInsert.setInt(3, film.getCzasTrwaniaWMin());
            stmtInsert.registerOutParameter(4, Types.INTEGER);

            stmtInsert.execute();
            int id = stmtInsert.getInt(4);
            film.setId(id);

            int wykonananoPoprawnie = stmtInsert.getInt(1);
            return wykonananoPoprawnie == 1;

        } catch (SQLException ex) {
            Logger.getLogger(FilmDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
            return false;
        }
    }

    public boolean updateFilm(Film film){
        // TODO
        return true;
    }

    public void deleteFilm(Film film){
        try {
            stmtDelete.setInt(1, film.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }

    }
    public Film getFilmById(int id){
        // TODO 
        return null;
    }
}
