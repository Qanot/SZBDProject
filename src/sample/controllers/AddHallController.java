package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import sample.model.Sala;
import sample.services.ConnectionController;
import sample.services.SalaDAO;

import java.io.IOException;

import static sample.controllers.Controller.showAlertEmptyForm;

public class AddHallController {

    Sala hall = null;
    ConnectionController cc;

    @FXML
    private JFXButton applyChanges;
    @FXML
    private JFXTextField editNumber;


    @FXML
    private void handleApplyChanges(ActionEvent event) throws IOException {
        System.out.println("applyChanges!");

        //getting values from forms
        String editedNumber = editNumber.textProperty().getValue();
        //check if not empty
        if (!editedNumber.equals("")) {

            Sala newSala = new Sala(Integer.parseInt(editedNumber));
            SalaDAO salaDAO = new SalaDAO(cc);


            //if false number occupied
            if (!salaDAO.insertSala(newSala)) {
                showAlertEmptyForm("Numer sali zajęty. Prosze spóbować z innym.");
            } else {

                closeWindow();
            }
            salaDAO.closeStatements();
        } else {
            showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
        }

    }

    public void initHallController(Sala hall, ConnectionController cc) {
        this.hall = hall;
        this.cc = cc;
        if (hall != null) {
            editNumber.textProperty().setValue(String.valueOf(hall.getNrSali()));
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }

}
