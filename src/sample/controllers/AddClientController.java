package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Klient;
import sample.services.ConnectionController;
import sample.services.KlientDAO;

import java.io.IOException;

import static sample.controllers.Controller.showAlertEmptyForm;

public class AddClientController {

    Klient client = null;
    ConnectionController cc;

    @FXML
    private Button applyChanges;
    @FXML
    private TextField editName;
    @FXML
    private TextField editLastname;
    @FXML
    private TextField editEmail;
    @FXML
    private TextField editLogin;
    @FXML
    private TextField editPassword;
    @FXML
    private TextField editPhoneNumber;


    @FXML
    private void handleApplyChanges(ActionEvent event) throws IOException {
        System.out.println("applyChanges!");

        //getting values from forms

        String editedName = editName.textProperty().getValue();
        String editedLastname = editLastname.textProperty().getValue();
        String editedEmail = editEmail.textProperty().getValue();
        String editedLogin = editLogin.textProperty().getValue();
        String editedPassword = editPassword.textProperty().getValue();
        String editedPhoneNumber = editPhoneNumber.textProperty().getValue();

        if (!editedLastname.equals("") && !editedName.equals("") && !editedEmail.equals("")
                && !editedLogin.equals("") && !editedPassword.equals("") && !editedPhoneNumber.equals("")) {
            Klient newClient = new Klient(editedName, editedLastname, editedEmail,
                    editedLogin, editedPassword, editedPhoneNumber);
            KlientDAO klientDAO = new KlientDAO(cc);
            klientDAO.insertKlient(newClient);
            closeWindow();
        } else {
            showAlertEmptyForm();
        }


    }

    public void initClientController(Klient client, ConnectionController cc) {
        this.client = client;
        this.cc = cc;
        if (client != null) {
            editName.textProperty().setValue(client.getImie());
            editLastname.textProperty().setValue(client.getNazwisko());
            editEmail.textProperty().setValue(client.getEmail());
            editLogin.textProperty().setValue(client.getLogin());
            editPassword.textProperty().setValue(client.getHaslo());
            editPhoneNumber.textProperty().setValue(client.getTelefon());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}