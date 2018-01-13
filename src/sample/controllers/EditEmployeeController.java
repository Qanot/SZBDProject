package sample.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EditEmployeeController {


    String employeeToEdit = "klient";

    public void openWindow(String klientString){
        try{
            employeeToEdit = klientString;
            System.out.println(employeeToEdit);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editEmployeeWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno edycji pracownika");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
