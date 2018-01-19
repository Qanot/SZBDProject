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
import sample.model.*;
import sample.services.*;

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

    public ConnectionController getCc() {
        return cc;
    }

    ConnectionController cc;

    public Controller() {

        //enable controller to connect to database
        cc = new ConnectionController();
        cc.open();

        presentedType = "";
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
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                KlientDAO prdao = new KlientDAO(cc);
                List<Klient> lista = prdao.getKlienci();
                Klient tempClient= lista.get(dataToShow.indexOf(selection));
                openEditClientWindow(tempClient);
            }
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
        } else if (presentedType.equals("Products")) {
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                ProduktDAO produktDAO = new ProduktDAO(cc);
                List<Produkt> lista = produktDAO.getProdukty();
                Produkt tempProduct= lista.get(dataToShow.indexOf(selection));
                openEditProductWindow(tempProduct);
            }
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
            controller.initEmployeeController(pracownik, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Employees"; addEmployeesToTableView();} );

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void openEditClientWindow(Klient client){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editClientWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno edycji klietna");
            EditClientController controller = fxmlLoader.<EditClientController>getController();
            controller.initClientController(client, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Clients"; addClientsToTableView();} );

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void openEditProductWindow(Produkt product){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editProductWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno edycji produktu");
            EditProductController controller = fxmlLoader.<EditProductController>getController();
            controller.initProductController(product, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Products"; addProductsToTableView();} );

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
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    KlientDAO prdao = new KlientDAO(cc);
                    List<Klient> lista = prdao.getKlienci();
                    Klient tempClient= lista.get(dataToShow.indexOf(selection));
                    prdao.deleteKlient(tempClient);
                    addClientsToTableView();
                }
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
                    prdao.deletePracownik(tempEmployee);
                    addEmployeesToTableView();
                }
            } else if (presentedType.equals("ProductsOnReceipts")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editProductsOnReceiptWindow.fxml"));
            } else if (presentedType.equals("Products")) {
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    ProduktDAO produktDAO = new ProduktDAO(cc);
                    List<Produkt> lista = produktDAO.getProdukty();
                    Produkt tempProduct= lista.get(dataToShow.indexOf(selection));
                    produktDAO.deleteProdukt(tempProduct);
                    addProductsToTableView();
                }
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
        presentedType = "Seanse";
        addSeanseToTableView();

    }
    private void addSeanseToTableView(){
        SeansDAO seansDAO = new SeansDAO(cc);
        List<Seans> lista = seansDAO.getSeanse();
        dataToShow.clear();
        for(Seans seans: lista){
            dataToShow.add(new RecordToShow(seans.dataEmisjiToString(), seans.toStringTytulSala()));
        }
    }

    @FXML
    private void handleShowHallsButton(ActionEvent event) throws IOException {
        System.out.println("showHallsButton!");
        presentedType = "Halls";
        addHallsToTableView();

    }
    private void addHallsToTableView(){
        SalaDAO salaDAO = new SalaDAO(cc);
        List<Sala> lista = salaDAO.getSale();
        dataToShow.clear();
        for (Sala sala: lista){
            dataToShow.add(new RecordToShow(String.valueOf(sala.getId()), "Sala numer: " + sala.getNrSali()));
        }
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
    private void handleShowProductsButton(ActionEvent event) throws IOException {
        System.out.println("showProducts!");
        presentedType = "Products";
        addProductsToTableView();
    }
    private void addProductsToTableView(){
        ProduktDAO proDAO = new ProduktDAO(cc);
        List<Produkt> lista = proDAO.getProdukty();
        dataToShow.clear();
        for (Produkt produkt: lista){
            dataToShow.add(new RecordToShow(String.valueOf(produkt.getId()), produkt.getNazwa() + " " +produkt.getRozmiarPorcji()));
        }
    }

    @FXML
    private void handleShowTicketsTypesButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
    }

    @FXML
    private void handleShowClientsButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
        presentedType = "Clients";
        addClientsToTableView();

    }
    private void addClientsToTableView(){
        KlientDAO prdao = new KlientDAO(cc);
        List<Klient> lista = prdao.getKlienci();
        dataToShow.clear();
        for (Klient client: lista){
            dataToShow.add(new RecordToShow(String.valueOf(client.getEmail()), client.getImie() + " " +client.getNazwisko()));
        }
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
        RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();

        Sala tempHall = null;
        if(selection != null && presentedType.equals("Halls")){
            SalaDAO salaDAO = new SalaDAO(cc);
            List<Sala> lista = salaDAO.getSale();
            tempHall = lista.get(dataToShow.indexOf(selection));
        }
        openAddHallWindow(tempHall);
    }
    private void openAddHallWindow(Sala sala){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\addHallWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno dodania sali kinowej");
            AddHallController controller = fxmlLoader.<AddHallController>getController();
            controller.initHallController(sala, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Halls"; addHallsToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddEmployeesButton(ActionEvent event) throws IOException {
        System.out.println("addEmployeeButton!");
        RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();

        Pracownik tempEmployee = null;
        if(selection != null && presentedType.equals("Employees")){
            PracownikDAO prdao = new PracownikDAO(cc);
            List<Pracownik> lista = prdao.getPracownicy();
            tempEmployee = lista.get(dataToShow.indexOf(selection));
        }
        openAddEmployeeWindow(tempEmployee);
    }
    private void openAddEmployeeWindow(Pracownik pracownik){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\addEmployeeWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno dodania pracownika");
            AddEmployeeController controller = fxmlLoader.<AddEmployeeController>getController();
            controller.initEmployeeController(pracownik, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Employees"; addEmployeesToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
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
    private void handleAddProductButton(ActionEvent event) throws IOException {
        System.out.println("addProducts!");
        RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
        Produkt product = null;
        if(selection != null && presentedType.equals("Products")){
            ProduktDAO produktDAO = new ProduktDAO(cc);
            List<Produkt> lista = produktDAO.getProdukty();
            product = lista.get(dataToShow.indexOf(selection));
        }
        openAddProductWindow(product);
    }
    private void openAddProductWindow(Produkt product){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\addProductWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno dodania nowego klienta");
            AddProductController controller = fxmlLoader.<AddProductController>getController();
            controller.initProductController(product, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Products"; addProductsToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAddTicketsTypesButton(ActionEvent event) throws IOException {
        System.out.println("addReceiptsButton!");
    }

    @FXML
    private void handleAddClientsButton(ActionEvent event) throws IOException {
        System.out.println("addReceiptsButton!");
        RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
        Klient klient = null;
        if(selection != null && presentedType.equals("Clients")){
            KlientDAO prdao = new KlientDAO(cc);
            List<Klient> lista = prdao.getKlienci();
            klient = lista.get(dataToShow.indexOf(selection));
        }
        openAddClientWindow(klient);
    }
    private void openAddClientWindow(Klient client){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\addClientWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno dodania nowego klienta");
            AddClientController controller = fxmlLoader.<AddClientController>getController();
            controller.initClientController(client, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Clients"; addClientsToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
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
                    PracownikDAO prdao = new PracownikDAO(cc);
                    List<Pracownik> lista = prdao.getPracownicy();
                    Pracownik tempEmployee = lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(tempEmployee.toString());
                }
                else if (presentedType.equals("Clients")){
                    KlientDAO prdao = new KlientDAO(cc);
                    List<Klient> lista = prdao.getKlienci();
                    Klient tempClient = lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(tempClient.toString());
                }
                else if (presentedType.equals("Products")){
                    ProduktDAO proDAO = new ProduktDAO(cc);
                    List<Produkt> lista = proDAO.getProdukty();
                    Produkt tempProduct = lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(tempProduct.toString());
                }
                else if (presentedType.equals("Halls")){
                    SalaDAO salaDAO = new SalaDAO(cc);
                    List<Sala> lista = salaDAO.getSale();
                    Sala hallTemp= lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(hallTemp.toString());
                }
                else if (presentedType.equals("Seanse")){
                    SeansDAO seansDAO = new SeansDAO(cc);
                    List<Seans> lista = seansDAO.getSeanse();
                    Seans seansTemp = lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(seansTemp.toString());
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
    public static void showAlertEmptyForm(String warningText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uwaga!");
        alert.setHeaderText(null);
        alert.setContentText(warningText);
        alert.showAndWait();
    }

}
