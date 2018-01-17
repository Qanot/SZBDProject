//package sample.controllers;
//
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//import sample.model.Plec;
//import sample.model.Pracownik;
//import sample.model.Produkt;
//import sample.model.RozmiarPorcji;
//import sample.services.ConnectionController;
//import sample.services.PracownikDAO;
//import sample.services.ProduktDAO;
//
//import java.io.IOException;
//
//import static sample.controllers.Controller.showAlertEmptyForm;
//
//public class EditProductController {
//
//    Produkt product = null;
//    ConnectionController cc;
//
//    @FXML
//    private Button applyChanges;
//    @FXML
//    private TextField editName;
//    @FXML
//    private TextField editPrice;
//    @FXML
//    private ChoiceBox editSize;
//
//
//
//    @FXML
//    private void handleApplyChanges(ActionEvent event) throws IOException {
//        System.out.println("applyChanges!");
//
//        //getting values from forms
//
//        String editedName = editName.textProperty().getValue();
//        String editedPrice = editPrice.textProperty().getValue();
//        RozmiarPorcji editedSize = (RozmiarPorcji) editSize.getSelectionModel().getSelectedItem();
//        if (!editedPrice.equals("") && !editedName.equals("")){
//            product.setNazwa(editedName);
//            product.setCena(Double.valueOf(editedPrice));
//            product.setRozmiarPorcji(editedSize);
//            ProduktDAO produktDAO = new ProduktDAO(cc);
//            produktDAO.updateProdukt(product);
//            closeWindow();
//        }else{
//            showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
//        }
//
//
//    }
//
//    public void initProductController(Produkt product, ConnectionController cc) {
//        editSize.setItems(FXCollections.observableArrayList(Plec.K, Plec.M));
//        this.product = product;
//        this.cc = cc;
//        editName.textProperty().setValue(product.getImie());
//        editPrice.textProperty().setValue(product.getNazwisko());
//        editSize.getSelectionModel().select(product.getPlec());
//
//
//
//    }
//
//    private void closeWindow(){
//        Stage stage = (Stage) applyChanges.getScene().getWindow();
//        stage.close();
//    }
//}
