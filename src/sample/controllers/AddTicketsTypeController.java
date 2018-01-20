package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import sample.model.RodzajBiletu;
import sample.model.Sala;
import sample.services.ConnectionController;
import sample.services.RodzajBiletuDAO;
import sample.services.SalaDAO;

import java.io.IOException;

import static sample.controllers.Controller.showAlertEmptyForm;

public class AddTicketsTypeController {

    RodzajBiletu rodzajBiletu = null;
    ConnectionController cc;

    @FXML
    private JFXButton applyChanges;
    @FXML
    private JFXTextField editPrice;
    @FXML
    private JFXTextField editName;


    @FXML
    private void handleApplyChanges(ActionEvent event) throws IOException {
        System.out.println("applyChanges!");
        //getting values from forms
        String editedPrice = editPrice.textProperty().getValue();
        String editedName = editName.textProperty().getValue();
        double price;
        try {
            price = Double.parseDouble(editedPrice);
            if (!editedName.equals("") && !editedPrice.equals("")) {
                RodzajBiletu newType = new RodzajBiletu(price, editedName);

                RodzajBiletuDAO rodzajDAO = new RodzajBiletuDAO(cc);
                if (!rodzajDAO.insertRodzajBiletu(newType)) {
                    showAlertEmptyForm("Nie udało sie poprawnie wstawić rekodru. Sprawdź wprowadzane dane oraz połączenie internetowe.");
                } else {
                    closeWindow();
                }
                rodzajDAO.closeStatements();

            }else{
                showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
            }
        } catch (Exception e){
            showAlertEmptyForm("Niepoprawnie wprowadzona cena!");
        }

    }



    public void initTicketsTypeController(RodzajBiletu rodzajBiletu, ConnectionController cc) {
        this.rodzajBiletu = rodzajBiletu;
        this.cc = cc;
        if (rodzajBiletu != null) {
            editPrice.textProperty().setValue(String.valueOf(rodzajBiletu.getCena()));
            editName.textProperty().setValue(rodzajBiletu.getNazwa());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }

}
