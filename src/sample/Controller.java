package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class Controller {

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("There was a click!");
    }
}
