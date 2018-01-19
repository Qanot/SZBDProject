package sample.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.*;
import sample.services.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static sample.controllers.Controller.showAlertEmptyForm;

public class  AddSeansController {

    Seans seans = null;
    Film film = null;
    Sala sala = null;
    ConnectionController cc;

    @FXML
    private JFXDatePicker editDate;

    @FXML
    private JFXTimePicker editTime;

    @FXML
    private ChoiceBox<Film> editFilm;

    @FXML
    private ChoiceBox<Sala> editSala;

    @FXML
    private Button applyChanges;

    @FXML
    void handleApplyChanges(ActionEvent event) throws IOException{
        System.out.println("applyChanges!");
        LocalDate editedDate = editDate.getValue();
        LocalTime editedTime = editTime.getValue();
        LocalDateTime editedDateTime = LocalDateTime.of(editedDate, editedTime);
        Date editedDataEmisji =  Date.from(editedDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Film editedFilm = editFilm.getValue();
        Sala editedSala = editSala.getValue();
        if(editedDataEmisji != null && editedFilm != null && editedSala != null){
            Seans seans = new Seans(editedDataEmisji,editedFilm, editedSala);
            SeansDAO seansDAO = new SeansDAO(cc);
            if (!seansDAO.insertSeans(seans)) {
                showAlertEmptyForm("Istnieje już seans o podanej dacie, godzinie, filmie i sali. Proszę wybrać inne wartości.");
            } else {
                closeWindow();
            }
            seansDAO.closeStatements();
        } else {
            showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
        }
        

    }



    public void initSeansController(Seans seans, ConnectionController cc) {
        this.cc = cc;
        this.seans = seans;

        FilmDAO filmDAO = new FilmDAO(cc);
        List<Film> filmy = filmDAO.getFilmy();
        filmDAO.closeStatements();
        ObservableList<Film> filmyLista = FXCollections.observableArrayList(filmy);
        editFilm.setItems(filmyLista);

        SalaDAO salaDAO = new SalaDAO(cc);
        List<Sala> sale = salaDAO.getSale();
        salaDAO.closeStatements();

        ObservableList<Sala> saleLista = FXCollections.observableArrayList(sale);
        editSala.setItems(saleLista);




        /*
        if (this.employee != null){
            editName.textProperty().setValue(employee.getImie());
            editLastname.textProperty().setValue(employee.getNazwisko());
            editPlec.getSelectionModel().select(employee.getPlec());
        }
        */
    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}

