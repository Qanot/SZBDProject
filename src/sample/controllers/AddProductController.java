package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Plec;
import sample.model.Pracownik;
import sample.model.Produkt;
import sample.model.RozmiarPorcji;
import sample.services.ConnectionController;
import sample.services.PracownikDAO;
import sample.services.ProduktDAO;

import java.io.IOException;

import static sample.controllers.Controller.showAlertEmptyForm;


public class AddProductController {

    Produkt product = null;
    ConnectionController cc;

    @FXML
    private Button applyChanges;
    @FXML
    private TextField editName;
    @FXML
    private TextField editPrice;
    @FXML
    private ChoiceBox editSize;

    @FXML
    private void handleApplyChanges(ActionEvent event) throws IOException {
        System.out.println("applyChanges!");

        String editedName = editName.textProperty().getValue();
        String editedPrice = editPrice.textProperty().getValue();
        RozmiarPorcji editedSize = (RozmiarPorcji) editSize.getSelectionModel().getSelectedItem();

        if (!editedPrice.equals("") && !editedName.equals("")){
            Produkt newProduct = new Produkt(Double.valueOf(editedPrice), editedName, editedSize);
            ProduktDAO produktDAO = new ProduktDAO(cc);
            if (!produktDAO.insertProdukt(newProduct)) {
                showAlertEmptyForm("Nie udało sie poprawnie wstawić rekodru. Sprawdź połączenie.");
            } else {
                closeWindow();
            }

        }else{
            showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
        }
    }

    public void initProductController(Produkt product, ConnectionController cc) {
        editSize.setItems(FXCollections.observableArrayList(RozmiarPorcji.S,RozmiarPorcji.M, RozmiarPorcji.L));
        this.product = product;
        this.cc = cc;
        if (product != null){
            editName.textProperty().setValue(product.getNazwa());
            editPrice.textProperty().setValue(Double.toString(product.getCena()));
            editSize.getSelectionModel().select(product.getRozmiarPorcji());
        }




    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}

