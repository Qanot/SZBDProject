package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.model.Plec;
import sample.model.Pracownik;

import java.io.IOException;

public class AddEmployeeController {

    Pracownik employee = null;

    @FXML
    private Button applyChanges;
    @FXML
    private TextField editName;
    @FXML
    private TextField editLastname;
    @FXML
    private ChoiceBox<String> editPlec = new ChoiceBox<>(FXCollections.observableArrayList("K", "M"));


    public AddEmployeeController(){

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
//        editPlec = new ChoiceBox<String>(FXCollections.observableArrayList("K", "M"));
//        editPlec.getSelectionModel().select(employee.getPlec().toString());


    }






}
