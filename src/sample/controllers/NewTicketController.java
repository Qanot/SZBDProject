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
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.model.*;
import sample.services.ConnectionController;
import sample.services.RezerwacjaDAO;
import sample.services.RodzajBiletuDAO;
import sample.services.SeansDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static sample.controllers.Controller.showAlertEmptyForm;

public class NewTicketController {
    ConnectionController connectionController;
    List<Miejsce> miejscaWolne = new ArrayList<Miejsce>();
    ObservableList<Miejsce> miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton applyChanges;

    @FXML
    private ChoiceBox<RodzajBiletu> editRodzajBiletu;

    @FXML
    private ChoiceBox<Seans> editSeans;

    @FXML
    private ChoiceBox<Miejsce> editMiejsce;

    private Paragon paragon;

    @FXML
    private void handleApplyChanges(ActionEvent event)  {
        System.out.println("Zatwierdź zmiany!");

        RodzajBiletu rodzajBiletu = editRodzajBiletu.getValue();
        Seans seans = editSeans.getValue();
        Miejsce miejsce = editMiejsce.getValue();

        try {
            if (rodzajBiletu != null && seans != null && miejsce != null) {
                Bilet bilet = new Bilet(rodzajBiletu);
                MiejsceNaSeansie miejsceNaSeansie = new MiejsceNaSeansie(miejsce, seans);
                bilet.setMiejsceNaSeansie(miejsceNaSeansie);
                miejsceNaSeansie.setBilet(bilet);
                paragon.getBilety().add(bilet);

                closeConnection();
                closeWindow();


            } else {
                showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
            }

        } catch (Exception e) {
            showAlertEmptyForm("Niepoprawnie wypełnione pola!");
        }




    }

    public void initController(Paragon paragon){
        if(paragon == null){


            System.out.println("KUBA NIE DAWAJ MI PROSZE NULLA! :)");
        }
        connectionController = new ConnectionController();
        connectionController.open();

        this.paragon = paragon;

        RodzajBiletuDAO rodzajBiletuDAO = new RodzajBiletuDAO(connectionController);
        List<RodzajBiletu> rodzajeBiletow = rodzajBiletuDAO.getRodzajeBiletow();
        ObservableList<RodzajBiletu> rodzajeBiletowLista = FXCollections.observableArrayList(rodzajeBiletow);
        editRodzajBiletu.setItems(rodzajeBiletowLista);
        rodzajBiletuDAO.closeStatements();


        SeansDAO seansDAO = new SeansDAO(connectionController);
        List<Seans> seanse = seansDAO.getSeanse();
        ObservableList<Seans> seanseLista = FXCollections.observableArrayList(seanse);
        editSeans.setItems(seanseLista);
        seansDAO.closeStatements();

    }
    @FXML
    public void comboActionSeans() {
        System.out.println("Akcja wyboru seansu!!");
        Seans selectedSeans = editSeans.getValue();
        miejscaWolne = new ArrayList<Miejsce>(selectedSeans.getMiejscaWolne()); // tworzymy kopie
        if (miejscaWolne.size() == 0) {
            okienkoWiadomosci("Niestety na ten seans nie ma już miejsc.");
        }

        /***
         * TUTAJ WYWALAM Z TEJ LISTY MIEJSCA, KTORE SA JUZ NABITE NA PARAGON, ALE JESZCZE NIE MA ICH W BAZIE
         */


        for (Iterator<Miejsce> iterator = miejscaWolne.iterator(); iterator.hasNext();) {
            Miejsce miejsce = iterator.next();

            for(Bilet bilet: paragon.getBilety()){
                Seans seansNaBilecie = bilet.getMiejsceNaSeansie().getSeans();
                Miejsce miejsceNaBilecie = bilet.getMiejsceNaSeansie().getMiejsce();
                // TAKI EQUALS:
                if(seansNaBilecie.getId() == selectedSeans.getId() && miejsceNaBilecie.getId() == miejsce.getId()){
                    // usun
                    iterator.remove();
                }
            }
        }


        miejscaWolneLista = FXCollections.observableArrayList(miejscaWolne);
        editMiejsce.setItems(miejscaWolneLista);

    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
    public void closeConnection(){
        connectionController.close();
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
