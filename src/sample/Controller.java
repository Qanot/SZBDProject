package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    private Button editRecordButton;
    @FXML
    private ListView<String> searchList = new ListView<>();
    @FXML
    private TextField filterField;
    @FXML
    private TableView<RecordToShow> recordsTable;
    @FXML
    private TableColumn<RecordToShow, String> idColumn;
    @FXML
    private TableColumn<RecordToShow, String> dataColumn;

    private ObservableList<RecordToShow> dataToShow = FXCollections.observableArrayList();
    private ObservableList<RecordToShow> filteredData = FXCollections.observableArrayList();

    public Controller() {
        // Add some sample data to the master data
        dataToShow.add(new RecordToShow("Hans", "Muster"));
        dataToShow.add(new RecordToShow("Ruth", "Mueller"));
        dataToShow.add(new RecordToShow("Heinz", "Kurz"));
        dataToShow.add(new RecordToShow("Cornelia", "Meier"));
        dataToShow.add(new RecordToShow("Werner", "Meyer"));
        dataToShow.add(new RecordToShow("Lydia", "Kunz"));
        dataToShow.add(new RecordToShow("Anna", "Best"));
        dataToShow.add(new RecordToShow("Stefan", "Meier"));
        dataToShow.add(new RecordToShow("Martin", "Mueller"));

        // Initially add all data to filtered data
        filteredData.addAll(dataToShow);

        // Listen for changes in master data.
        // Whenever the master data changes we must also update the filtered data.
        dataToShow.addListener(new ListChangeListener<RecordToShow>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends RecordToShow> change) {
                updateFilteredData();
            }
        });
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("There was a click!");
    }

    @FXML
    private void handleEditRecordButton(ActionEvent event) throws IOException {
        System.out.println("editRecordButton!");
    }

    @FXML
    private void handleDeleteRecordButton(ActionEvent event) throws IOException {
        System.out.println("DeleteRecordButton!");
        dataToShow.remove(recordsTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleAddTicketButton(ActionEvent event) throws IOException {
        System.out.println("addTicketButton!");
        dataToShow.add(new RecordToShow("Lydia", "Kunz"));
    }

    @FXML
    private void handleAddReservationButton(ActionEvent event) throws IOException {
        System.out.println("addReservationButton!");
    }

    @FXML
    private void handleAddSeansButton(ActionEvent event) throws IOException {
        System.out.println("addSeansButton!");
    }

    @FXML
    private void handleAddMovieButton(ActionEvent event) throws IOException {
        System.out.println("addMovieButton!");
    }

    @FXML
    private void handleShowTicketsButton(ActionEvent event) throws IOException {
        System.out.println("showTicketsButton!");
    }

    @FXML
    private void handleShowReservationsButton(ActionEvent event) throws IOException {
        System.out.println("showReservationsButton!");
    }

    @FXML
    private void handleShowMoviesButton(ActionEvent event) throws IOException {
        System.out.println("showMoviesButton!");
    }

    @FXML
    private void handleShowSeanseButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
    }




    @FXML
    private void initialize() {
        // Initialize the person table
        idColumn.setCellValueFactory(
                new PropertyValueFactory<RecordToShow, String>("id"));
        dataColumn.setCellValueFactory(
                new PropertyValueFactory<RecordToShow, String>("data"));

        // Add filtered data to the table
        recordsTable.setItems(filteredData);

        // Listen for text changes in the filter text field
        filterField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                updateFilteredData();
            }
        });
    }

    /**
     * Updates the filteredData to contain all data from the dataToShow that
     * matches the current filter.
     */
    private void updateFilteredData() {
        filteredData.clear();

        for (RecordToShow p : dataToShow) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    /**
     * Returns true if the record matches the current filter. Lower/Upper case
     * is ignored.
     *
     * @param record
     * @return
     */
    private boolean matchesFilter(RecordToShow record) {
        String filterString = filterField.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (record.getId().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (record.getData().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<RecordToShow, ?>> sortOrder = new ArrayList<>(recordsTable.getSortOrder());
        recordsTable.getSortOrder().clear();
        recordsTable.getSortOrder().addAll(sortOrder);
    }



    public class RecordToShow {

        private String id;
        private String data;

        public RecordToShow(String id, String data) {
            this.id = id;
            this.data = data;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

}
