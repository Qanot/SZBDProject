package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.model.Paragon;

public class NewTicketController {

    private Paragon paragon;

    @FXML
    private void handleApplyChanges(ActionEvent event)  {

    }

    public void initController(Paragon paragon){
        this.paragon = paragon;
    }
}
