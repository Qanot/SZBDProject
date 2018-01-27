package sample.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.model.*;
import sample.services.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static sample.controllers.Controller.showAlertEmptyForm;

public class AddReservationController {

    Rezerwacja rezerwacja = null;
    ConnectionController cc;
    List<Miejsce> miejscaWybrane = new ArrayList<Miejsce>();
    ObservableList<Miejsce> miejscaWybraneLista = FXCollections.observableArrayList(miejscaWybrane);
    List<Miejsce> miejscaWolne = new ArrayList<Miejsce>();
    ObservableList<Miejsce> miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane pane;

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
    void handleApplyChanges(ActionEvent event) throws IOException {
        System.out.println("applyChanges!");
        Klient klient = editKlient.getValue();
        Seans seans = editSeans.getValue();

        try {
            if (klient != null && seans != null && miejscaWybrane.size() > 0) {
                List<MiejsceNaSeansie> miejscaNaSeansie = new ArrayList<MiejsceNaSeansie>();
                Rezerwacja rezerwacja = new Rezerwacja(klient, miejscaNaSeansie);

                for (Miejsce miejsce : miejscaWybrane) {
                    // id i tak zostanie nadpisane przy wstawianiu
                    // rezerwacja tez zostanie nadpisana
                    MiejsceNaSeansie miejsceNaSeansie = new MiejsceNaSeansie(miejsce, seans);
                    miejscaNaSeansie.add(miejsceNaSeansie);
                }
                rezerwacja.setZarezerwowaneMiejsca(miejscaNaSeansie);

                RezerwacjaDAO rezerwacjaDAO = new RezerwacjaDAO(cc);
                rezerwacjaDAO.insertRezerwacja(rezerwacja);
                closeWindow();
            } else {
                showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
            }

        } catch (Exception e) {
            showAlertEmptyForm("Niepoprawnie wypełnione pola!");
        }


    }


    public void initReservationController(Rezerwacja rezerwacja, ConnectionController cc) {
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

        recordsTable.setPlaceholder(new Label("Nie wybrano miejsc"));
    }

    @FXML
    void handleAddSeat(ActionEvent event) throws IOException {
        System.out.println("Dodaj miejsce!");
        if (editMiejsce.getValue() != null) {
            System.out.println("Not null");
            Miejsce miejsceWybrane = editMiejsce.getValue();
            miejscaWybrane.add(miejsceWybrane);
            miejscaWolne.remove(miejsceWybrane);
            miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);
            editMiejsce.setItems(miejscaWolneLista);
            miejscaWybraneLista = FXCollections.observableArrayList(miejscaWybrane);
            recordsTable.setItems(miejscaWybraneLista);
        } else {
            okienkoWiadomosci("Proszę najpierw wybrać z listy miejsce do dodania.");

        }
    }

    @FXML
    void handleDeleteSeat(ActionEvent event) throws IOException {
        System.out.println("Usun miejsce!");
        Miejsce selection = recordsTable.getSelectionModel().getSelectedItem();
        if (selection != null) {
            System.out.println("Not null usun");
            miejscaWybrane.remove(selection);
            miejscaWolne.add(selection);
            miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);
            editMiejsce.setItems(miejscaWolneLista);
            miejscaWybraneLista = FXCollections.observableArrayList(miejscaWybrane);
            recordsTable.setItems(miejscaWybraneLista);

        } else {
            okienkoWiadomosci("Proszę najpierw zaznaczyć miejsce do usunięcia.");
        }
    }

    @FXML
    public void comboActionSeans() {
        System.out.println("Akcja!!!!");
        miejscaWybrane.clear();
        Seans selectedSeans = editSeans.getValue();
        //SeansDAO seansDAO = new SeansDAO(cc);
        miejscaWolne = new ArrayList<Miejsce>(selectedSeans.getMiejscaWolne());
        if (miejscaWolne.size() == 0) {
            okienkoWiadomosci("Niestety na ten seans nie ma już miejsc.");
        }
        miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);
        miejscaWybraneLista = FXCollections.observableArrayList(miejscaWybrane);

        editMiejsce.setItems(miejscaWolneLista);
        recordsTable.setItems(miejscaWybraneLista);
        //seansDAO.closeStatements();
    }

    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }

    private void okienkoWiadomosci(String message) {
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

