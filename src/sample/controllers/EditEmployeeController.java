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

import java.io.IOException;

public class EditEmployeeController {

    Pracownik employee = null;

    @FXML
    private Button applyChanges;
    @FXML
    private TextField editName;
    @FXML
    private TextField editLastname;
    @FXML
    private ChoiceBox editPlec =  new ChoiceBox(FXCollections.observableArrayList("K", "M"));

    public EditEmployeeController(){
        editPlec.setItems(FXCollections.observableArrayList(Plec.K, Plec.M));
    }

    @FXML
    private void handleApplyChanges(ActionEvent event) throws IOException {
        System.out.println("applyChanges!");
//        Pracownik employeeToUpdate = new Pracownik(employee.getImie(),)
    }

    public void initEmployee(Pracownik employee) {
        this.employee = employee;
        editName.textProperty().setValue(employee.getImie());
        editLastname.textProperty().setValue(employee.getNazwisko());
        editPlec.getSelectionModel().select(employee.getPlec());



    }




}
