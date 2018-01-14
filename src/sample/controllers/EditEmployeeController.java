package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.model.Plec;
import sample.model.Pracownik;
import sample.services.ConnectionController;
import sample.services.PracownikDAO;

import java.io.IOException;
import java.util.List;

import static sample.controllers.Controller.showAlertEmptyForm;

public class EditEmployeeController {

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
    private void handleApplyChanges(ActionEvent event) throws IOException {
        System.out.println("applyChanges!");

        //getting values from forms

        String editedName = editName.textProperty().getValue();
        String editedLastname = editLastname.textProperty().getValue();
        Plec editedSex = (Plec) editPlec.getSelectionModel().getSelectedItem();
        if (!editedLastname.equals("") && !editedName.equals("")){
            employee.setImie(editedName);
            employee.setNazwisko(editedLastname);
            employee.setPlec(editedSex);
            PracownikDAO pracownikDAO = new PracownikDAO(cc);
            pracownikDAO.updatePracownik(employee);
            closeWindow();
        }else{
            showAlertEmptyForm();
        }


    }

    public void initEmployeeController(Pracownik employee, ConnectionController cc) {
        editPlec.setItems(FXCollections.observableArrayList(Plec.K, Plec.M));
        this.employee = employee;
        this.cc = cc;
        editName.textProperty().setValue(employee.getImie());
        editLastname.textProperty().setValue(employee.getNazwisko());
        editPlec.getSelectionModel().select(employee.getPlec());



    }

    private void closeWindow(){
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
     }
}
