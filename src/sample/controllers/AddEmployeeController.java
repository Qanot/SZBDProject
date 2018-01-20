package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Plec;
import sample.model.Pracownik;
import sample.services.ConnectionController;
import sample.services.PracownikDAO;
import java.io.IOException;

import static sample.controllers.Controller.showAlertEmptyForm;


public class AddEmployeeController {

    Pracownik employee = null;
    ConnectionController cc;

    @FXML
    private Button applyChanges;
    @FXML
    private TextField editName;
    @FXML
    private TextField editLastname;
    @FXML
    private ChoiceBox editPlec;
    @FXML
    private TextField editPESEL;


    @FXML
    private void handleApplyChanges(ActionEvent event) throws IOException {
        System.out.println("applyChanges!");
        String editedName = editName.textProperty().getValue();
        String editedLastname = editLastname.textProperty().getValue();
        String editedPESEL = editPESEL.textProperty().getValue();
        Plec editedSex = (Plec) editPlec.getSelectionModel().getSelectedItem();
        if (!editedLastname.equals("") && !editedName.equals("") && !editedPESEL.equals("") && editedSex != null) {
            Pracownik newEmployee = new Pracownik(editedName, editedLastname, editedSex, editedPESEL);
            PracownikDAO pracownikDAO = new PracownikDAO(cc);
            if (!pracownikDAO.insertPracownik(newEmployee)) {
                pracownikDAO.closeStatements();
                showAlertEmptyForm("PESEL już znajduje sie w bazie. Prosze spóbować z innym.");
            } else {
                pracownikDAO.closeStatements();
                closeWindow();
            }
        } else {
            showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
        }
    }

    public void initEmployeeController(Pracownik employee, ConnectionController cc) {
        editPlec.setItems(FXCollections.observableArrayList(Plec.K, Plec.M));
        this.employee = employee;
        this.cc = cc;
        if (this.employee != null){
            editName.textProperty().setValue(employee.getImie());
            editLastname.textProperty().setValue(employee.getNazwisko());
            editPlec.getSelectionModel().select(employee.getPlec());
        }
    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}

