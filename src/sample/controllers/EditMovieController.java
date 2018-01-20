package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import sample.model.Film;
import sample.model.Sala;
import sample.model.Seans;
import sample.services.ConnectionController;
import sample.services.FilmDAO;
import sample.services.SalaDAO;
import sample.services.SeansDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static sample.controllers.Controller.showAlertEmptyForm;

public class EditMovieController {

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
        String editedDuration = editDuration.textProperty().getValue();
        int duration;

        try {
            editedDuration = editDuration.textProperty().getValue();
            duration = Integer.parseInt(editedDuration);
            if (!editedTitle.equals("") && !editedDuration.equals("")) {
                movie.setTytul(editedTitle);
                movie.setCzasTrwaniaWMin(duration);

                FilmDAO produktDAO = new FilmDAO(cc);
                if (!produktDAO.updateFilm(movie)) {
                    showAlertEmptyForm("Nie udało sie poprawnie wstawić rekodru. Sprawdź połączenie.");
                } else {
                    closeWindow();
                }
                produktDAO.closeStatements();
            } else {
                showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
            }
        } catch (Exception e){
            showAlertEmptyForm("Niepoprawnie wprowadzona długośc filmu!");
        }
    }



    public void initMovieController(Film movie, ConnectionController cc) {
        this.cc = cc;
        this.movie = movie;
        editTitle.textProperty().setValue(movie.getTytul());
        editDuration.textProperty().setValue(Integer.toString(movie.getCzasTrwaniaWMin()));

    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}

