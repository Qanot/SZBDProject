package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

public class EditProductController {

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

        //getting values from forms

        String editedName = editName.textProperty().getValue();
        String editedPrice = editPrice.textProperty().getValue();
        RozmiarPorcji editedSize = (RozmiarPorcji) editSize.getSelectionModel().getSelectedItem();
        try{
            if (!editedPrice.equals("") && !editedName.equals("")){
                product.setNazwa(editedName);
                product.setCena(Double.valueOf(editedPrice));
                product.setRozmiarPorcji(editedSize);
                ProduktDAO produktDAO = new ProduktDAO(cc);
                if (!produktDAO.updateProdukt(product)) {
                    showAlertEmptyForm("Nie udało sie poprawnie wstawić rekodru. Sprawdź połączenie.");
                } else {
                    closeWindow();
                }
                produktDAO.closeStatements();

            }else{
                showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
            }
        } catch (Exception e){
            showAlertEmptyForm("Niepoprawnie wypełnione pola!");
        }



    }

    public void initProductController(Produkt product, ConnectionController cc) {
        editSize.setItems(FXCollections.observableArrayList(RozmiarPorcji.S,RozmiarPorcji.M, RozmiarPorcji.L));
        this.product = product;
        this.cc = cc;
        editName.textProperty().setValue(product.getNazwa());
        editPrice.textProperty().setValue(Double.toString(product.getCena()));
        editSize.getSelectionModel().select(product.getRozmiarPorcji());



    }

    private void closeWindow(){
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}
