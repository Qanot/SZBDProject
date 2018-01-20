package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Sala;
import sample.services.ConnectionController;
import sample.services.SalaDAO;

import java.io.IOException;

import static sample.controllers.Controller.showAlertEmptyForm;

public class EditHallController {

    Sala hall = null;
    ConnectionController cc;

    @FXML
    private Button applyChanges;
    @FXML
    private TextField editNumber;


    @FXML
    private void handleApplyChanges(ActionEvent event) throws IOException {
        System.out.println("applyChanges!");

        //getting values from forms
        String editedNumber = editNumber.textProperty().getValue();
        //check if not empty
        if (!editedNumber.equals("")) {
            SalaDAO salaDAO = new SalaDAO(cc);

            hall.setNrSali(Integer.parseInt(editedNumber));

            //if false number occupied
            if (!salaDAO.updateSala(hall)) {
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
