package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Klient;
import sample.services.ConnectionController;
import sample.services.KlientDAO;
import java.io.IOException;

import static sample.controllers.Controller.showAlertEmptyForm;

public class EditClientController {

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
                && !editedPassword.equals("") && !editedPhoneNumber.equals("") && !editedLogin.equals("")) {
            client.setImie(editedName);
            client.setNazwisko(editedLastname);
            client.setEmail(editedEmail);
            client.setLogin(editedLogin);
            client.setHaslo(editedPassword);
            client.setTelefon(editedPhoneNumber);

            KlientDAO klientDAO = new KlientDAO(cc);
            if (!klientDAO.updateKlient(client)) {
                showAlertEmptyForm("Login zajęty. Prosze spóbować z innym.");
            } else {
                closeWindow();
            }
        } else {
            showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
        }


    }

    public void initClientController(Klient client, ConnectionController cc) {
        this.client = client;
        this.cc = cc;
        editName.textProperty().setValue(client.getImie());
        editLastname.textProperty().setValue(client.getNazwisko());
        editEmail.textProperty().setValue(client.getEmail());
        editLogin.textProperty().setValue(client.getLogin());
        editPassword.textProperty().setValue(client.getHaslo());
        editPhoneNumber.textProperty().setValue(client.getTelefon());
    }

    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}