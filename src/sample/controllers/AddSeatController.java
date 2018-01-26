package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.model.Miejsce;
import sample.model.Sala;
import sample.services.ConnectionController;
import sample.services.MiejsceDAO;
import sample.services.SalaDAO;
import sample.utils.ToRoman;

import java.io.IOException;
import java.util.List;

import static sample.controllers.Controller.showAlertEmptyForm;

public class AddSeatController {

    Miejsce miejsce = null;
    Sala sala = null;
    ConnectionController cc;


    @FXML
    private JFXTextField editRow;
    @FXML
    private JFXTextField editNumber;
    @FXML
    private JFXTextField editMultiRow;
    @FXML
    private JFXTextField editMultiNumber;
    @FXML
    private ChoiceBox<Sala> editSala;
    @FXML
    private ChoiceBox<Sala> editMultiSala;
    @FXML
    private JFXButton applyChanges;
    @FXML
    private JFXCheckBox isMulti;
    @FXML
    private Pane singlePane;
    @FXML
    private Pane multiPane;

    @FXML
    void handleApplyChanges(ActionEvent event) throws IOException{
        System.out.println("applyChanges!");
        if (!isMulti.isSelected()) {
            String editedNumber = editNumber.textProperty().getValue();
            String editedRow = editRow.textProperty().getValue();
            Sala editedSala = editSala.getValue();
            try {
                if (editedNumber != null && editedRow != null && editedSala != null) {
                    int editedNumberInt = Integer.parseInt(editedNumber);
                    Miejsce newSeat = new Miejsce(editedRow, editedNumberInt, editedSala);
                    MiejsceDAO miejsceDAO = new MiejsceDAO(cc);
                    if (!miejsceDAO.insertMiejsce(newSeat)) {
                        showAlertEmptyForm("Istnieje już takie miejsce w podanej sali. Proszę wybrać inne wartości.");
                    } else {
                        closeWindow();
                    }
                    miejsceDAO.closeStatements();
                } else {
                    showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
                }
            } catch (Exception e) {
                showAlertEmptyForm("Niepoprawnie wypełnione pola!");
            }
        }
        else{
            String editedNumber = editMultiNumber.textProperty().getValue();
            String editedRow = editMultiRow.textProperty().getValue();
            Sala editedSala = editMultiSala.getValue();
            try {
                if (editedNumber != null && editedRow != null && editedSala != null) {
                    int editedNumberInt = Integer.parseInt(editedNumber);
                    int editedRowInt = Integer.parseInt(editedRow);

                    int counterHowManyFailed = 0;
                    MiejsceDAO miejsceDAO = new MiejsceDAO(cc);
                    for (int i = 0; i < editedRowInt; i++) {
                        String romanNumber  = ToRoman.toRoman(i+1);
                        for (int j = 0; j < editedNumberInt; j++) {
                            Miejsce newSeat = new Miejsce(romanNumber, j + 1, editedSala);
                            if (!miejsceDAO.insertMiejsce(newSeat)){
                                counterHowManyFailed++;
                            }
                        }
                    }
                    if(counterHowManyFailed > 0) {
                        showAlertEmptyForm("Wprowadzone dane pokrywają " + counterHowManyFailed + " już istniejące miejsc/a. ");
                    }
                    miejsceDAO.closeStatements();
                    closeWindow();
                } else {
                    showAlertEmptyForm("Puste pola! Proszę uzupełnić niekompletne formularze.");
                }
            } catch (Exception e) {
                showAlertEmptyForm("Niepoprawnie wypełnione pola!");
            }

        }
    }



    public void initSeatController(Miejsce miejsce, ConnectionController cc) {
        this.cc = cc;
        this.miejsce = miejsce;

        SalaDAO salaDAO = new SalaDAO(cc);
        List<Sala> sale = salaDAO.getSale();
        salaDAO.closeStatements();
        ObservableList<Sala> saleLista = FXCollections.observableArrayList(sale);
        editSala.setItems(saleLista);
        editMultiSala.setItems(saleLista);

        if(this.miejsce != null){
            Sala selectedSala = null;
            for(Sala salaInSale : sale){
                if(salaInSale.getNrSali() == miejsce.getSala().getNrSali()){ //equals
                    selectedSala = salaInSale;
                    break;
                }
            }
            editSala.getSelectionModel().select(selectedSala);
            editMultiSala.getSelectionModel().select(selectedSala);
            editNumber.setText(String.valueOf(miejsce.getNrMiejsca()));
            editRow.setText(miejsce.getRzad());
            editMultiNumber.setText(String.valueOf(miejsce.getNrMiejsca()));
            editMultiRow.setText(miejsce.getRzad());
        }

        isMulti.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (new_val.booleanValue()){
                    singlePane.visibleProperty().setValue(false);
                    multiPane.visibleProperty().setValue(true);
                }
                else {
                    singlePane.visibleProperty().setValue(true);
                    multiPane.visibleProperty().setValue(false);
                }
            }
        });

    }
    private void closeWindow() {
        Stage stage = (Stage) applyChanges.getScene().getWindow();
        stage.close();
    }
}

