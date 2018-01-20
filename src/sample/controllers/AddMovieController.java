package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import sample.model.Film;
import sample.services.ConnectionController;
import sample.services.FilmDAO;

import java.io.IOException;
import java.text.ParseException;

import static sample.controllers.Controller.showAlertEmptyForm;

public class AddMovieController {

    Film movie = null;
    ConnectionController cc;

    @FXML
    private JFXTextField editTitle;

    @FXML
    private JFXTextField editDuration;

    @FXML
    private JFXButton applyChanges;

    @FXML
    void handleApplyChanges(ActionEvent event) throws IOException{
        System.out.println("applyChanges!");

        String editedTitle = editTitle.textProperty().getValue();
        String editedDuration = "";
        int duration;
        try{
            editedDuration = editDuration.textProperty().getValue();
            duration = Integer.parseInt(editedDuration);
            if (!editedTitle.equals("") && !editedDuration.equals("")){

                Film newMovie = new Film(editedTitle, duration);

                FilmDAO filmDAO = new FilmDAO(cc);
                if (!filmDAO.insertFilm(newMovie)) {
                    showAlertEmptyForm("Nie udało sie poprawnie wstawić rekodru. Sprawdź połączenie.");
                } else {
                    filmDAO.closeStatements();
                    closeWindow();
                }
                filmDAO.closeStatements();

            }else{
                showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
            }
        }catch (Exception e){
            showAlertEmptyForm("Niepoprawna długość filmu!");
        }

    }



    public void initMovieController(Film movie, ConnectionController cc) {
        this.cc = cc;
        this.movie = movie;
        if (movie != null) {
            editTitle.textProperty().setValue(movie.getTytul());
            editDuration.textProperty().setValue(Integer.toString(movie.getCzasTrwaniaWMin()));
        }

    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}

