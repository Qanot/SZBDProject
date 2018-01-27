package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.model.*;
import sample.services.ConnectionController;
import sample.services.PracownikDAO;
import sample.services.ProduktDAO;
import sample.services.RezerwacjaDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static sample.controllers.Controller.showAlertEmptyForm;

public class AddReceiptController {

    Paragon paragon = null;
    ConnectionController cc;

    List<ProduktNaParagonie> produktyNaParagonie= new ArrayList<ProduktNaParagonie>();
    ObservableList<ProduktNaParagonie> produktNaParagonieLista = FXCollections.observableArrayList(produktyNaParagonie);
//    List<Miejsce> miejscaWybrane = new ArrayList<Miejsce>();
//    List<Miejsce> miejscaWolne = new ArrayList<Miejsce>();
//    ObservableList<Miejsce> miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane pane;

    @FXML
    private ChoiceBox<Pracownik> editEmployee;
    @FXML
    private ChoiceBox<Bilet> editBilet;
    @FXML
    private ChoiceBox<Produkt> editProdukt;

    @FXML
    private TableView<Bilet> recordsTableTickets;
    @FXML
    private TableColumn<Bilet, String> dataColumn;
    @FXML
    private TableColumn<Bilet, String> miejsceColumn;
    @FXML
    private TableView<Produkt> recordsTableProducts;
    @FXML
    private TableColumn<Produkt, String> nazwaColumn;
    @FXML
    private TableColumn<Produkt, String> rozmiarColumn;

    @FXML
    private JFXButton applyChanges;

    @FXML
    private JFXButton addTicket;
    @FXML
    private JFXButton deleteTicket;
    @FXML
    private JFXButton addProduct;
    @FXML
    private JFXButton deleteProduct;

    @FXML
    void handleApplyChanges(ActionEvent event) throws IOException{
//        System.out.println("applyChanges!");
//        Klient klient = editEmployee.getValue();
//        Seans seans = editSeans.getValue();
//
//        try{
//            if(klient != null && seans != null && miejscaWybrane.size() > 0){
//                List<MiejsceNaSeansie> miejscaNaSeansie = new ArrayList<MiejsceNaSeansie>();
//                Rezerwacja rezerwacja = new Rezerwacja(klient, miejscaNaSeansie);
//
//                for(Miejsce miejsce : miejscaWybrane){
//                    // id i tak zostanie nadpisane przy wstawianiu
//                    // rezerwacja tez zostanie nadpisana
//                    MiejsceNaSeansie miejsceNaSeansie = new MiejsceNaSeansie(miejsce, seans);
//                    miejscaNaSeansie.add(miejsceNaSeansie);
//                }
//                rezerwacja.setZarezerwowaneMiejsca(miejscaNaSeansie);
//
//                RezerwacjaDAO rezerwacjaDAO = new RezerwacjaDAO(cc);
//                rezerwacjaDAO.insertRezerwacja(rezerwacja);
//                closeWindow();
//            }
//            else {
//                showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
//            }
//
//        }catch (Exception e){
//            showAlertEmptyForm("Niepoprawnie wypełnione pola!");
//        }



    }



    public void initReceiptController(Paragon paragon, ConnectionController cc) {
        this.cc = cc;
        this.paragon = paragon;

        PracownikDAO pracownikDAO = new PracownikDAO(cc);
        List<Pracownik> pracownicy = pracownikDAO.getPracownicy();
        pracownikDAO.closeStatements();
        editEmployee.setItems(FXCollections.observableArrayList(pracownicy));


//        ProduktDAO produktDAO = new ProduktDAO(cc);
//        List<Produkt> produkty = produktDAO.getProdukty();
//        ObservableList<Produkt> produktyLista  = FXCollections.observableArrayList(produkty);
//        editProdukt.setItems(produktyLista);
//        produktDAO.closeStatements();

        dataColumn.setCellValueFactory(
                new PropertyValueFactory<Bilet, String>("rzad"));
        miejsceColumn.setCellValueFactory(
                new PropertyValueFactory<Bilet, String>("miejsce"));
        nazwaColumn.setCellValueFactory(
                new PropertyValueFactory<Produkt, String>("nazwa"));
        rozmiarColumn.setCellValueFactory(
                new PropertyValueFactory<Produkt, String>("rozmiar"));

        recordsTableProducts.setPlaceholder(new Label("Nie wybrano produktów"));
        recordsTableTickets.setPlaceholder(new Label("Nie wybrano biletów"));
    }

    @FXML
    void handleAddTicket(ActionEvent event) throws IOException{
//        System.out.println("Dodaj miejsce!");
//        if(editMiejsce.getValue() != null){
//            System.out.println("Not null");
//            Miejsce miejsceWybrane = editMiejsce.getValue();
//            miejscaWybrane.add(miejsceWybrane);
//            miejscaWolne.remove(miejsceWybrane);
//            miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);
//            editMiejsce.setItems(miejscaWolneLista);
//            miejscaWybraneLista = FXCollections.observableArrayList(miejscaWybrane);
//            recordsTable.setItems(miejscaWybraneLista);
//        } else{
//            okienkoWiadomosci("Proszę najpierw wybrać z listy miejsce do dodania.");
//
//        }
    }
    @FXML
    void handleAddProduct(ActionEvent event) throws IOException{
//        System.out.println("Dodaj miejsce!");
//        if(editMiejsce.getValue() != null){
//            System.out.println("Not null");
//            Miejsce miejsceWybrane = editMiejsce.getValue();
//            miejscaWybrane.add(miejsceWybrane);
//            miejscaWolne.remove(miejsceWybrane);
//            miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);
//            editMiejsce.setItems(miejscaWolneLista);
//            miejscaWybraneLista = FXCollections.observableArrayList(miejscaWybrane);
//            recordsTable.setItems(miejscaWybraneLista);
//        } else{
//            okienkoWiadomosci("Proszę najpierw wybrać z listy miejsce do dodania.");
//
//        }
    }
    @FXML
    void handleDeleteTicket(ActionEvent event) throws IOException{
//        System.out.println("Usun miejsce!");
//        Miejsce selection = recordsTable.getSelectionModel().getSelectedItem();
//        if(selection != null){
//            System.out.println("Not null usun");
//            miejscaWybrane.remove(selection);
//            miejscaWolne.add(selection);
//            miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);
//            editMiejsce.setItems(miejscaWolneLista);
//            miejscaWybraneLista = FXCollections.observableArrayList(miejscaWybrane);
//            recordsTable.setItems(miejscaWybraneLista);
//
//        } else{
//            okienkoWiadomosci("Proszę najpierw zaznaczyć miejsce do usunięcia.");
//        }
    }
    @FXML
    void handleDeleteProduct(ActionEvent event) throws IOException{
//        System.out.println("Usun miejsce!");
//        Miejsce selection = recordsTable.getSelectionModel().getSelectedItem();
//        if(selection != null){
//            System.out.println("Not null usun");
//            miejscaWybrane.remove(selection);
//            miejscaWolne.add(selection);
//            miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);
//            editMiejsce.setItems(miejscaWolneLista);
//            miejscaWybraneLista = FXCollections.observableArrayList(miejscaWybrane);
//            recordsTable.setItems(miejscaWybraneLista);
//
//        } else{
//            okienkoWiadomosci("Proszę najpierw zaznaczyć miejsce do usunięcia.");
//        }
    }

    @FXML
    public void comboActionSeans(){
//        System.out.println("Akcja!!!!");
//        miejscaWybrane.clear();
//        Seans selectedSeans = editSeans.getValue();
//        //SeansDAO seansDAO = new SeansDAO(cc);
//        miejscaWolne = new ArrayList<Miejsce>(selectedSeans.getMiejscaWolne());
//        if(miejscaWolne.size() == 0){
//            okienkoWiadomosci("Niestety na ten seans nie ma już miejsc.");
//        }
//        miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);
//        miejscaWybraneLista = FXCollections.observableArrayList(miejscaWybrane);
//
//        editMiejsce.setItems(miejscaWolneLista);
//        recordsTable.setItems(miejscaWybraneLista);
//        //seansDAO.closeStatements();
    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
    private void okienkoWiadomosci(String message){
        BoxBlur blur = new BoxBlur(3, 3, 3);
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Label("Uwaga!"));
        JFXButton button = new JFXButton("OK, rozumiem");
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                anchorPane.setEffect(null);
            }
        });
        content.setActions(button);
        anchorPane.setEffect(blur);
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                anchorPane.setEffect(null);
            }
        });
        content.setBody(new Label(message));
        dialog.show();
    }
}
