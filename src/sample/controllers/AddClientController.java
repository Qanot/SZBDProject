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

        try{
            //check if not empty
            if (!editedLastname.equals("") && !editedName.equals("") && !editedEmail.equals("")
                    && !editedLogin.equals("") && !editedPassword.equals("") && !editedPhoneNumber.equals("")) {

                Klient newClient = new Klient(editedName, editedLastname, editedEmail, editedLogin, editedPassword, editedPhoneNumber);
                KlientDAO klientDAO = new KlientDAO(cc);

                //if false login occupied
                if (!klientDAO.insertKlient(newClient)) {
                    klientDAO.closeStatements();
                    showAlertEmptyForm("Niepoprawne dane! Przyczyną błędu może być zajęty login lub nieporawny numer telefonu. Spróbuj jeszcze raz.");
                } else {
                    klientDAO.closeStatements();
                    closeWindow();
                }
            } else {

                showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
            }
        } catch (Exception e){
            showAlertEmptyForm("Niepoprawnie wypełnione pola!");
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