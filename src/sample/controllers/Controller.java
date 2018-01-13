package sample.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import sample.model.Pracownik;
import sample.services.ConnectionController;
import sample.services.PracownikDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @FXML
    private ScrollPane infoField;
    @FXML
    private TextFlow infoTextFlow;
    @FXML
    private Text infoText = new Text();

    private ObservableList<RecordToShow> dataToShow = FXCollections.observableArrayList();
    private ObservableList<RecordToShow> filteredData = FXCollections.observableArrayList();
    private String presentedType;

    ConnectionController cc;

    public Controller() {

        //enable controller to connect to database
        cc = new ConnectionController();
        cc.open();
//        // Add some sample data to the master data
//        dataToShow.add(new RecordToShow("Hans", "Muster"));
//        dataToShow.add(new RecordToShow("Ruth", "Mueller"));
//        dataToShow.add(new RecordToShow("Heinz", "Kurz"));
//        dataToShow.add(new RecordToShow("Cornelia", "Meier"));
//        dataToShow.add(new RecordToShow("Werner", "Meyer"));
//        dataToShow.add(new RecordToShow("Lydia", "Kunz"));
//        dataToShow.add(new RecordToShow("Anna", "Best"));
//        dataToShow.add(new RecordToShow("Stefan", "Meier"));
//        dataToShow.add(new RecordToShow("Martin", "Mueller"));
        //set Type o presented records
        presentedType = "";
        // Initially add all data to filtered data
//        filteredData.addAll(dataToShow);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("There was a click!");
    }

    @FXML
    private void handleEditRecordButton(ActionEvent event) throws IOException {
        System.out.println("editRecordButton!");


        if (presentedType.equals("Tickets")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editTicketWindow.fxml"));
        } else if (presentedType.equals("Movies")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editMovieWindow.fxml"));
        } else if (presentedType.equals("Clients")) {
            new EditClientController().openWindow("Jakub - klient");
        } else if (presentedType.equals("Seats")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editSeatWindow.fxml"));
        } else if (presentedType.equals("SeatsOnSeans")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editSeatOnSeansWindow.fxml"));
        } else if (presentedType.equals("Receipts")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editReceiptWindow.fxml"));
        } else if (presentedType.equals("Employees")) {
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                PracownikDAO prdao = new PracownikDAO(cc);
                List<Pracownik> lista = prdao.getPracownicy();
                Pracownik tempEmployee = lista.get(dataToShow.indexOf(selection));
                openEditEmployeeWindow(tempEmployee);
            }
        } else if (presentedType.equals("ProductsOnReceipts")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editProductsOnReceiptWindow.fxml"));
        } else if (presentedType.equals("Reservations")) {
            new EditReservationController().openWindow("Jakub - rezerwacja");
        } else if (presentedType.equals("TypesOfTickets")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editTypesOfTicketWindow.fxml"));
        } else if (presentedType.equals("Halls")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editHallWindow.fxml"));
        } else if (presentedType.equals("Seanse")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editSeansWindow.fxml"));
        }
    }

    private void openEditEmployeeWindow(Pracownik pracownik){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editEmployeeWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno edycji pracownika");
            EditEmployeeController controller = fxmlLoader.<EditEmployeeController>getController();
            controller.initEmployee(pracownik);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteRecordButton(ActionEvent event) throws IOException {
        System.out.println("DeleteRecordButton!");
        if(recordsTable.getSelectionModel().getSelectedIndex() != -1){
            if (presentedType.equals("Tickets")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editTicketWindow.fxml"));
            } else if (presentedType.equals("Movies")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editMovieWindow.fxml"));
            } else if (presentedType.equals("Clients")) {
                new EditClientController().openWindow("Jakub - klient");
            } else if (presentedType.equals("Seats")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editSeatWindow.fxml"));
            } else if (presentedType.equals("SeatsOnSeans")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editSeatOnSeansWindow.fxml"));
            } else if (presentedType.equals("Receipts")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editReceiptWindow.fxml"));
            } else if (presentedType.equals("Employees")) {
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
//            if(selection != null){
//                PracownikDAO prdao = new PracownikDAO(cc);
//                List<Pracownik> lista = prdao.getPracownicy();
//                Pracownik tempEmployee = lista.get(dataToShow.indexOf(selection));
//                openEditEmployeeWindow(tempEmployee);
//            }
            } else if (presentedType.equals("ProductsOnReceipts")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editProductsOnReceiptWindow.fxml"));
            } else if (presentedType.equals("Reservations")) {
//                new EditReservationController().openWindow("Jakub - rezerwacja");
            } else if (presentedType.equals("TypesOfTickets")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editTypesOfTicketWindow.fxml"));
            } else if (presentedType.equals("Halls")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editHallWindow.fxml"));
            } else if (presentedType.equals("Seanse")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editSeansWindow.fxml"));
            }
        }

    }

    @FXML
    private void handleAddTicketsButton(ActionEvent event) throws IOException {
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
    private void handleAddMoviesButton(ActionEvent event) throws IOException {
        System.out.println("addMovieButton!");
    }

    @FXML
    private void handleShowTicketsButton(ActionEvent event) throws IOException {
        System.out.println("showTicketsButton!");
        presentedType = "Tickets";

    }

    @FXML
    private void handleShowReservationsButton(ActionEvent event) throws IOException {
        System.out.println("showReservationsButton!");
        presentedType = "Reservations";
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
    private void handleShowHallsButton(ActionEvent event) throws IOException {
        System.out.println("showHallsButton!");
    }

    @FXML
    private void handleShowEmployeesButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
        presentedType = "Employees";
        addEmployeesToTableView();
    }

    private void addEmployeesToTableView(){
        PracownikDAO prdao = new PracownikDAO(cc);
        List<Pracownik> lista = prdao.getPracownicy();
        dataToShow.clear();
        for (Pracownik pracownik: lista){
            dataToShow.add(new RecordToShow(String.valueOf(pracownik.getId()), pracownik.getImie() + " " +pracownik.getNazwisko()));
        }
    }

    @FXML
    private void handleShowReceiptsButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
    }

    @FXML
    private void handleShowProductsOnReceiptsButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
    }

    @FXML
    private void handleShowTicketsTypesButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
    }

    @FXML
    private void handleShowClientsButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
        presentedType = "Clients";
    }

    @FXML
    private void handleShowSeatsButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
    }

    @FXML
    private void handleShowSeatsOnSeansButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
    }

    @FXML
    private void handleAddHallsButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
    }

    @FXML
    private void handleAddEmployeesButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
    }

    @FXML
    private void handleAddReceiptsButton(ActionEvent event) throws IOException {
        System.out.println("addReceiptsButton!");
    }

    @FXML
    private void handleAddProductsOnReceiptsButton(ActionEvent event) throws IOException {
        System.out.println("addReceiptsButton!");
    }

    @FXML
    private void handleAddTicketsTypesButton(ActionEvent event) throws IOException {
        System.out.println("addReceiptsButton!");
    }

    @FXML
    private void handleAddClientsButton(ActionEvent event) throws IOException {
        System.out.println("addReceiptsButton!");
    }

    @FXML
    private void handleAddSeatsButton(ActionEvent event) throws IOException {
        System.out.println("addReceiptsButton!");
    }

    @FXML
    private void handleSeatsOnSeansButton(ActionEvent event) throws IOException {
        System.out.println("addReceiptsButton!");
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
        dataToShow.addListener(new ListChangeListener<RecordToShow>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends RecordToShow> change) {
                updateFilteredData();
            }
        });
        recordsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (presentedType.equals("Employees")){
                    System.out.println("Indeks zaznaczonego pola: " + dataToShow.indexOf(newSelection) );
                    PracownikDAO prdao = new PracownikDAO(cc);
                    List<Pracownik> lista = prdao.getPracownicy();
                    Pracownik tempEmployee = lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText("ID: " + tempEmployee.getId() + "\nImie: " + tempEmployee.getImie() +
                    "\nNazwisko: " + tempEmployee.getNazwisko() + "\nPłeć: " + tempEmployee.getPlec());
                }
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
        public String toString(){  return id + " " + data; }

    }

}
