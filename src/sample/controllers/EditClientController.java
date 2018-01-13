package sample.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.Klient;

import java.io.IOException;

public class EditClientController {

    String clinetToEdit = "klient";

    public void openWindow(String klientString){
        try{
            clinetToEdit = klientString;
            System.out.println(clinetToEdit);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editClientWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno edycji klienta");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }



}
