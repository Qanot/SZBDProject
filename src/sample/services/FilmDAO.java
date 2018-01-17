package sample.services;

import sample.model.Film;

import java.util.ArrayList;
import java.util.List;

// TODO implement all methods

public class FilmDAO {
    private ConnectionController connectionController;
    private List<Film> filmy;


    public FilmDAO(ConnectionController connectionController) {
        filmy = new ArrayList<Film>();
        this.setConnectionController(connectionController);
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }
    public void selectFilmy(){

    }

    public List<Film> getFilmy() {
        selectFilmy();
        return filmy;
    }

    public boolean insertFilm(Film film){
        return true;
    }

    public boolean updateFilm(Film film){
        return true;
    }

    public void deleteFilm(Film film){

    }

    public void closeStatements(){

    }
}
