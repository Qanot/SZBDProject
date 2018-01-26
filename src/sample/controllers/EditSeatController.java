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
import sample.model.Miejsce;
import sample.model.Sala;
import sample.model.Seans;
import sample.services.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static sample.controllers.Controller.showAlertEmptyForm;

public class EditSeatController {

    Miejsce miejsce = null;
    Sala sala = null;
    ConnectionController cc;


    @FXML
    private JFXTextField editRow;
    @FXML
    private JFXTextField editNumber;
    @FXML
    private ChoiceBox<Sala> editSala;
    @FXML
    private JFXButton applyChanges;

    @FXML
    void handleApplyChanges(ActionEvent event) throws IOException{
        System.out.println("applyChanges!");

        String editedNumber = editNumber.textProperty().getValue();
        String editedRow = editRow.textProperty().getValue();
        Sala editedSala = editSala.getValue();
        try{
            if(editedNumber != null && editedRow != null && editedSala != null){
                int editedNumberInt = Integer.parseInt(editedNumber);
                miejsce.setNrMiejsca(editedNumberInt);
                miejsce.setRzad(editedRow);
                miejsce.setSala(editedSala);

                MiejsceDAO miejsceDAO = new MiejsceDAO(cc);
                if (!miejsceDAO.updateMiejsce(miejsce)) {
                    showAlertEmptyForm("Istnieje już takie miejsce w podanej sali. Proszę wybrać inne wartości.");
                } else {
                    closeWindow();
                }
                miejsceDAO.closeStatements();
            } else {
                showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
            }
        } catch (Exception e){
            showAlertEmptyForm("Niepoprawnie wypełnione pola!");
        }
    }



    public void initSeatController(Miejsce miejsce, ConnectionController cc) {
        this.cc = cc;
        this.miejsce = miejsce;


        SalaDAO salaDAO = new SalaDAO(cc);
        List<Sala> sale = salaDAO.getSale();
        salaDAO.closeStatements();
        ObservableList<Sala> saleLista = FXCollections.observableArrayList(sale);
        editSala.setItems(saleLista);

        if(this.miejsce != null){
            Sala selectedSala = null;
            for(Sala salaInSale : sale){
                if(salaInSale.getNrSali() == miejsce.getSala().getNrSali()){ //equals
                    selectedSala = salaInSale;
                    break;
                }
            }

            editSala.getSelectionModel().select(selectedSala);
            editNumber.setText(String.valueOf(miejsce.getNrMiejsca()));
            editRow.setText(miejsce.getRzad());
        }
    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}

