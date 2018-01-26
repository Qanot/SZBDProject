package sample.controllers;

import com.jfoenix.controls.JFXButton;
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

public class  AddReservationController {

    Rezerwacja rezerwacja = null;
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
    private JFXButton applyChanges;

    @FXML
    void handleApplyChanges(ActionEvent event) throws IOException{
        System.out.println("applyChanges!");


    }



    public void initSeansController(Rezerwacja rezerwacja, ConnectionController cc) {
        this.cc = cc;
        this.rezerwacja = rezerwacja;

    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}

