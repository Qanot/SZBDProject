package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.model.Klient;
import sample.model.Miejsce;
import sample.model.Rezerwacja;
import sample.model.Seans;
import sample.services.ConnectionController;
import sample.services.KlientDAO;
import sample.services.SeansDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddReceiptController {

    Rezerwacja rezerwacja = null;
    ConnectionController cc;
    List<Miejsce> miejscaWybrane = new ArrayList<Miejsce>();
    ObservableList<Miejsce> miejscaWolneLista;
    List<Miejsce> miejscaWolne = new ArrayList<Miejsce>();

    @FXML
    private ChoiceBox<Klient> editKlient;

    @FXML
    private ChoiceBox<Seans> editSeans;

    @FXML
    private ChoiceBox<Miejsce> editMiejsce;

    @FXML
    private TableView<Miejsce> recordsTable;
    @FXML
    private TableColumn<Miejsce, String> rzadColumn;
    @FXML
    private TableColumn<Miejsce, String> nrMiejscaColumn;

    @FXML
    private JFXButton applyChanges;

    @FXML
    private JFXButton dodajMiejsceButton;

    @FXML
    private JFXButton usunMiejsceButton;

    @FXML
    void handleApplyChanges(ActionEvent event) throws IOException{
        System.out.println("applyChanges!");


    }



    public void initSeansController(Rezerwacja rezerwacja, ConnectionController cc) {
        this.cc = cc;
        this.rezerwacja = rezerwacja;

        KlientDAO klientDAO = new KlientDAO(cc);
        List<Klient> klienci = klientDAO.getKlienci();
        klientDAO.closeStatements();
        ObservableList<Klient> klienciLista = FXCollections.observableArrayList(klienci);
        editKlient.setItems(klienciLista);

        SeansDAO seansDAO = new SeansDAO(cc);
        List<Seans> seanse = seansDAO.getSeanse();
        ObservableList<Seans> seanseLista = FXCollections.observableArrayList(seanse);
        editSeans.setItems(seanseLista);
        seansDAO.closeStatements();

        rzadColumn.setCellValueFactory(
                new PropertyValueFactory<Miejsce, String>("rzad"));
        nrMiejscaColumn.setCellValueFactory(
                new PropertyValueFactory<Miejsce, String>("nrMiejsca"));


        // TODO Oliwia w sobote



    }
    @FXML
    void handleAddSeat(ActionEvent event) throws IOException{
        System.out.println("Dodaj miejsce!");
        if(editMiejsce.getValue() != null){
            System.out.println("Not null");
            Miejsce miejsceWybrane = editMiejsce.getValue();
            miejscaWybrane.add(miejsceWybrane);
            miejscaWolne.remove(miejsceWybrane);
            miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);
            editMiejsce.setItems(miejscaWolneLista);
        }
    }
    @FXML
    void handleDeleteSeat(ActionEvent event) throws IOException{
        System.out.println("Usun miejsce!");
    }

    @FXML
    public void comboActionSeans(){
        System.out.println("Akcja!!!!");
        miejscaWybrane.clear();
        /** UWAGA! ON CACHUJE POPRZEDNIE WYBORY **/

        Seans selectedSeans = editSeans.getValue();
        //SeansDAO seansDAO = new SeansDAO(cc);
        miejscaWolne = selectedSeans.getMiejscaWolne();
        miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);

        editMiejsce.setItems(miejscaWolneLista);
        //seansDAO.closeStatements();
    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}

