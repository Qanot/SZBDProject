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



        if (presentedType.equals("Movies")) {
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                FilmDAO filmDAO = new FilmDAO(cc);
                List<Film> lista = filmDAO.getFilmy();
                Film tempMovie= lista.get(dataToShow.indexOf(selection));
                filmDAO.closeStatements();
                openEditMovieWindow(tempMovie);
            }
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editMovieWindow.fxml"));
        } else if (presentedType.equals("Clients")) {
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                KlientDAO prdao = new KlientDAO(cc);
                List<Klient> lista = prdao.getKlienci();
                Klient tempClient= lista.get(dataToShow.indexOf(selection));
                prdao.closeStatements();
                openEditClientWindow(tempClient);
            }
        } else if (presentedType.equals("Seats")) {
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                MiejsceDAO miejsceDAO = new MiejsceDAO(cc);
                List<Miejsce> lista = miejsceDAO.getMiejsca();
                Miejsce tempSeat = lista.get(dataToShow.indexOf(selection));
                miejsceDAO.closeStatements();
                openEditSeatWindow(tempSeat);
            }
        } else if (presentedType.equals("Receipts")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editReceiptWindow.fxml"));
        } else if (presentedType.equals("Employees")) {
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                PracownikDAO prdao = new PracownikDAO(cc);
                List<Pracownik> lista = prdao.getPracownicy();
                Pracownik tempEmployee = lista.get(dataToShow.indexOf(selection));
                prdao.closeStatements();
                openEditEmployeeWindow(tempEmployee);
            }
        } else if (presentedType.equals("Products")) {
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                ProduktDAO produktDAO = new ProduktDAO(cc);
                List<Produkt> lista = produktDAO.getProdukty();
                Produkt tempProduct= lista.get(dataToShow.indexOf(selection));
                produktDAO.closeStatements();
                openEditProductWindow(tempProduct);
            }
        } else if (presentedType.equals("Reservations")) {
            showAlertEmptyForm("Rezerwacji nie można edytować! Istnieje możliwość usnięcia rezerwacji i dodania nowej.");
            //new EditReservationController().openWindow("Jakub - rezerwacja");
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null) {
                RezerwacjaDAO rezerwacjaDAO = new RezerwacjaDAO(cc);
                List<Rezerwacja> lista = rezerwacjaDAO.getRezerwacje();
                Rezerwacja tempRezerwacja = lista.get(dataToShow.indexOf(selection));
                // TODO Oliwia
                // openEditReservationWindow(tempRezerwacja);
            }

        } else if (presentedType.equals("TypesOfTickets")) {
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                RodzajBiletuDAO rodzajDAO = new RodzajBiletuDAO(cc);
                List<RodzajBiletu> lista = rodzajDAO.getRodzajeBiletow();
                RodzajBiletu typeTemp= lista.get(dataToShow.indexOf(selection));
                rodzajDAO.closeStatements();
                openEditTicketsTypeWindow(typeTemp);
            }
        } else if (presentedType.equals("Halls")) {
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                SalaDAO salaDAO = new SalaDAO(cc);
                List<Sala> lista = salaDAO.getSale();
                Sala tempHall= lista.get(dataToShow.indexOf(selection));
                salaDAO.closeStatements();
                openEditHallWindow(tempHall);
            }
        } else if (presentedType.equals("Seanse")) {
            RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
            if(selection != null){
                SeansDAO senasDAO = new SeansDAO(cc);
                List<Seans> lista = senasDAO.getSeanse();
                Seans tempSeans = lista.get(dataToShow.indexOf(selection));
                senasDAO.closeStatements();
                openEditSeansWindow(tempSeans);
            }
        }
        infoText.setText("");
    }

    private void openEditSeansWindow(Seans seans){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editSeansWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno edycji seansu");
            EditSeansController controller = fxmlLoader.<EditSeansController>getController();
            controller.initSeansController(seans, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Seanse"; addSeanseToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
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
    private void openEditHallWindow(Sala hall){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editHallWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno edycji sali");
            EditHallController controller = fxmlLoader.<EditHallController>getController();
            controller.initHallController(hall, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Halls"; addHallsToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void openEditMovieWindow(Film movie){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editMovieWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno edycji filmu");
            EditMovieController controller = fxmlLoader.<EditMovieController>getController();
            controller.initMovieController(movie, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Movies"; addMoviesToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void openEditTicketsTypeWindow(RodzajBiletu ticketsType){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editTicketsTypeWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno edycji rodzaju biletu");
            EditTicketsTypeController controller = fxmlLoader.<EditTicketsTypeController>getController();
            controller.initTicketsTypeController(ticketsType, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "TypesOfTickets"; addTicketsTypesToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void openEditSeatWindow(Miejsce miejsce){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editSeatWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno edycji miejsca");
            EditSeatController controller = fxmlLoader.<EditSeatController>getController();
            controller.initSeatController(miejsce, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Seats"; addSeatsToTableView();} );

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
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    FilmDAO filmDAO = new FilmDAO(cc);
                    List<Film> lista = filmDAO.getFilmy();
                    Film tempMovie= lista.get(dataToShow.indexOf(selection));
                    filmDAO.deleteFilm(tempMovie);
                    filmDAO.closeStatements();
                    addMoviesToTableView();
                }
            } else if (presentedType.equals("Clients")) {
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    KlientDAO prdao = new KlientDAO(cc);
                    List<Klient> lista = prdao.getKlienci();
                    Klient tempClient= lista.get(dataToShow.indexOf(selection));
                    prdao.deleteKlient(tempClient);
                    prdao.closeStatements();
                    addClientsToTableView();
                }
            } else if (presentedType.equals("Seats")) {
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    MiejsceDAO miejsceDAO = new MiejsceDAO(cc);
                    List<Miejsce> lista = miejsceDAO.getMiejsca();
                    Miejsce tempMiejsce = lista.get(dataToShow.indexOf(selection));
                    miejsceDAO.deleteMiejsce(tempMiejsce);
                    miejsceDAO.closeStatements();
                    addSeatsToTableView();
                }
            } else if (presentedType.equals("SeatsOnSeans")) {
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editSeatOnSeansWindow.fxml"));
            } else if (presentedType.equals("Receipts")) {
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    ParagonDAO paragonDAO = new ParagonDAO(cc);
                    List<Paragon> lista = paragonDAO.getParagony();
                    Paragon tempEmployee = lista.get(dataToShow.indexOf(selection));
                    paragonDAO.deleteParagon(tempEmployee);
                    paragonDAO.closeStatements();
                    addReceiptsToTableView();
                }
            } else if (presentedType.equals("Employees")) {
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    PracownikDAO prdao = new PracownikDAO(cc);
                    List<Pracownik> lista = prdao.getPracownicy();
                    Pracownik tempEmployee = lista.get(dataToShow.indexOf(selection));
                    prdao.deletePracownik(tempEmployee);
                    prdao.closeStatements();
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
                    produktDAO.closeStatements();
                    addProductsToTableView();
                }
//            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\editProductsOnReceiptWindow.fxml"));
            } else if (presentedType.equals("Reservations")) {
//                new EditReservationController().openWindow("Jakub - rezerwacja");
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    System.out.println("Usun rezerwacje!");
                    RezerwacjaDAO rezerwacjaDAO = new RezerwacjaDAO(cc);
                    List<Rezerwacja> lista = rezerwacjaDAO.getRezerwacje();
                    Rezerwacja tempRezerwacja = lista.get(dataToShow.indexOf(selection));
                    rezerwacjaDAO.deleteRezerwacja(tempRezerwacja);
                    addReservationToTableView();
                }

            } else if (presentedType.equals("TypesOfTickets")) {
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    RodzajBiletuDAO rodzajDAO = new RodzajBiletuDAO(cc);
                    List<RodzajBiletu> lista = rodzajDAO.getRodzajeBiletow();
                    RodzajBiletu typeTemp = lista.get(dataToShow.indexOf(selection));
                    rodzajDAO.deleteRodzajBiletu(typeTemp);
                    rodzajDAO.closeStatements();
                    addTicketsTypesToTableView();
                }
            } else if (presentedType.equals("Halls")) {
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    SalaDAO salaDAO = new SalaDAO(cc);
                    List<Sala> lista = salaDAO.getSale();
                    Sala tempHall = lista.get(dataToShow.indexOf(selection));
                    salaDAO.deleteSala(tempHall);
                    salaDAO.closeStatements();
                    addHallsToTableView();
                }
            } else if (presentedType.equals("Seanse")) {
                RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
                if(selection != null){
                    SeansDAO seansDAO = new SeansDAO(cc);
                    List<Seans> lista = seansDAO.getSeanse();
                    Seans tempSeans = lista.get(dataToShow.indexOf(selection));
                    seansDAO.deleteSeans(tempSeans);
                    seansDAO.closeStatements();
                    addSeanseToTableView();
                }
            }
            infoText.setText("");
        }

    }

    @FXML
    private void handleAddTicketsButton(ActionEvent event) throws IOException {
        System.out.println("addTicketButton!");

    }

    @FXML
    private void handleAddReservationButton(ActionEvent event) throws IOException {
        System.out.println("addReservationButton!");
        // TODO Oliwia
        RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
        Rezerwacja tempReservation = null;
        if(selection != null && presentedType.equals("Reservations")){
            RezerwacjaDAO rezerwacjaDAO = new RezerwacjaDAO(cc);
            List<Rezerwacja> lista = rezerwacjaDAO.getRezerwacje();
            tempReservation = lista.get(dataToShow.indexOf(selection));
        }
        openAddReservationWindow(tempReservation);
    }
    private void openAddReservationWindow(Rezerwacja rezerwacja){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\addReservationWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno dodania rezerwacji");
            AddReservationController controller = fxmlLoader.<AddReservationController>getController();
            controller.initReservationController(rezerwacja, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Reservations"; addReservationToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddSeansButton(ActionEvent event) throws IOException {
        System.out.println("addSeansButton!");
        // TODO Oliwia
        RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
        Seans tempSeans = null;
        if(selection != null && presentedType.equals("Seanse")){
            SeansDAO seansDAO = new SeansDAO(cc);
            List<Seans> lista = seansDAO.getSeanse();
            tempSeans = lista.get(dataToShow.indexOf(selection));
            seansDAO.closeStatements();
        }
        openAddSeansWindow(tempSeans);
    }
    private void openAddSeansWindow(Seans seans){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\addSeansWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno dodania seansu");
            AddSeansController controller = fxmlLoader.<AddSeansController>getController();
            controller.initSeansController(seans, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Seanse"; addSeanseToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddMoviesButton(ActionEvent event) throws IOException {
        System.out.println("addMovieButton!");
        RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();

        Film tempMovie = null;
        if(selection != null && presentedType.equals("Movies")){
            FilmDAO filmDAO = new FilmDAO(cc);
            List<Film> lista = filmDAO.getFilmy();
            tempMovie = lista.get(dataToShow.indexOf(selection));
            filmDAO.closeStatements();
        }
        openAddMovieWindow(tempMovie);
    }
    private void openAddMovieWindow(Film movie){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\addMovieWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno dodania seansu");
            AddMovieController controller = fxmlLoader.<AddMovieController>getController();
            controller.initMovieController(movie, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Movies"; addMoviesToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
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
        addReservationToTableView();
    }
    private void addReservationToTableView(){
        ConnectionController cc4 = new ConnectionController();
        cc4.open();
        RezerwacjaDAO rezerwacjaDAO = new RezerwacjaDAO(cc4);
        List<Rezerwacja> lista = rezerwacjaDAO.getRezerwacje();
        dataToShow.clear();
        for(Rezerwacja rezerwacja: lista){
            dataToShow.add(new RecordToShow(rezerwacja.getDataUtworzeniaToString(),
                    rezerwacja.getKlientRezerwujacy().getLogin()));
        }
        cc4.close();

    }

    @FXML
    private void handleShowMoviesButton(ActionEvent event) throws IOException {
        System.out.println("showMoviesButton!");
        presentedType = "Movies";
        addMoviesToTableView();
    }
    private void addMoviesToTableView(){
        FilmDAO filmDAO = new FilmDAO(cc);
        List<Film> lista = filmDAO.getFilmy();
        filmDAO.closeStatements();
        dataToShow.clear();
        for(Film movie: lista){
            dataToShow.add(new RecordToShow(movie.getTytul(), String.valueOf(movie.getCzasTrwaniaWMin())));
        }
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
        seansDAO.closeStatements();
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
        salaDAO.closeStatements();
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
        prdao.closeStatements();
        dataToShow.clear();
        for (Pracownik pracownik: lista){
            dataToShow.add(new RecordToShow(pracownik.getPESEL(), pracownik.getImie() + " " +pracownik.getNazwisko()));
        }
    }

    @FXML
    private void handleShowReceiptsButton(ActionEvent event) throws IOException {
        System.out.println("showReceiptsButton!");
        presentedType = "Receipts";
        addReceiptsToTableView();
    }
    private void addReceiptsToTableView(){
        ParagonDAO paragonDAO = new ParagonDAO(cc);
        List<Paragon> lista = paragonDAO.getParagony();
        paragonDAO.closeStatements();
        dataToShow.clear();
        for (Paragon paragon: lista){
            dataToShow.add(new RecordToShow("Data zakupu:", paragon.getDataZakupuToString()));
        }
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
        proDAO.closeStatements();
        dataToShow.clear();
        for (Produkt produkt: lista){
            dataToShow.add(new RecordToShow(produkt.getNazwa(), "Rozmiar: " +produkt.getRozmiarPorcji()));
        }
    }

    @FXML
    private void handleShowTicketsTypesButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
        presentedType = "TypesOfTickets";
        addTicketsTypesToTableView();
    }
    private void addTicketsTypesToTableView(){
        RodzajBiletuDAO rodzajDAO = new RodzajBiletuDAO(cc);
        List<RodzajBiletu> lista = rodzajDAO.getRodzajeBiletow();
        rodzajDAO.closeStatements();
        dataToShow.clear();
        for (RodzajBiletu rodzaj: lista){
            dataToShow.add(new RecordToShow(rodzaj.getNazwa(), String.valueOf(rodzaj.getCena()) + " PLN"));
        }
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
        prdao.closeStatements();
        dataToShow.clear();
        for (Klient client: lista){
            dataToShow.add(new RecordToShow(String.valueOf(client.getEmail()), client.getImie() + " " +client.getNazwisko()));
        }
    }

    @FXML
    private void handleShowSeatsButton(ActionEvent event) throws IOException {
        System.out.println("showSeanseButton!");
        presentedType = "Seats";
        addSeatsToTableView();
    }
    private void addSeatsToTableView(){
        MiejsceDAO miejsceDAO = new MiejsceDAO(cc);
        List<Miejsce> lista = miejsceDAO.getMiejsca();
        miejsceDAO.closeStatements();
        dataToShow.clear();
        for (Miejsce miejsce: lista){
            dataToShow.add(new RecordToShow("Miejsce " + miejsce.getNrMiejsca() + ", rząd: " + miejsce.getRzad(),
                    "Sala numer: " + miejsce.getSala().getNrSali()));
        }
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
            salaDAO.closeStatements();
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
            prdao.closeStatements();
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
        RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
        Paragon paragon = new Paragon();
        if(selection != null && presentedType.equals("Receipts")){
            ParagonDAO paragonDAO = new ParagonDAO(cc);
            List<Paragon> lista = paragonDAO.getParagony();
            paragon = lista.get(dataToShow.indexOf(selection));
            paragonDAO.closeStatements();
        }
        openAddReceiptWindow(paragon);
    }
    private void openAddReceiptWindow(Paragon paragon){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\addReceiptWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno dodania nowego paragonu");
            AddReceiptController controller = fxmlLoader.<AddReceiptController>getController();
            controller.initReceiptController(paragon, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Receipts"; addReceiptsToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
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
            produktDAO.closeStatements();
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
        RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
        RodzajBiletu rodzajBiletu = null;
        if(selection != null && presentedType.equals("TypesOfTickets")){
            RodzajBiletuDAO rodzajBiletuDAO = new RodzajBiletuDAO(cc);
            List<RodzajBiletu> lista = rodzajBiletuDAO.getRodzajeBiletow();
            rodzajBiletu = lista.get(dataToShow.indexOf(selection));
            rodzajBiletuDAO.closeStatements();
        }
        openAddTicketsTypesWindow(rodzajBiletu);
    }
    private void openAddTicketsTypesWindow(RodzajBiletu rodzajBiletu){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\addTicketsTypeWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno dodania nowego rodzaju biletu");
            AddTicketsTypeController controller = fxmlLoader.<AddTicketsTypeController>getController();
            controller.initTicketsTypeController(rodzajBiletu, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "TypesOfTickets"; addTicketsTypesToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
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
            prdao.closeStatements();
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
        RecordToShow selection = recordsTable.getSelectionModel().getSelectedItem();
        Miejsce miejsce = null;
        if(selection != null && presentedType.equals("Seats")){
            MiejsceDAO miejsceDAO = new MiejsceDAO(cc);
            List<Miejsce> lista = miejsceDAO.getMiejsca();
            miejsce = lista.get(dataToShow.indexOf(selection));
            miejsceDAO.closeStatements();
        }
        openAddSeatWindow(miejsce);
    }
    private void openAddSeatWindow(Miejsce miejsce){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("..\\fxmls\\addSeatWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Okno dodania nowego miejsca");
            AddSeatController controller = fxmlLoader.<AddSeatController>getController();
            controller.initSeatController(miejsce, cc);
            stage.setScene(scene);
            stage.show();
            stage.setOnHiding( event -> {presentedType = "Seats"; addSeatsToTableView();} );
        } catch (IOException e){
            e.printStackTrace();
        }
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
        recordsTable.setPlaceholder(new Label("Nie wybrano żadnych rekordów"));
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
                    prdao.closeStatements();
                }
                else if (presentedType.equals("Clients")){
                    KlientDAO prdao = new KlientDAO(cc);
                    List<Klient> lista = prdao.getKlienci();
                    Klient tempClient = lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(tempClient.toString());
                    prdao.closeStatements();
                }
                else if (presentedType.equals("Products")){
                    ProduktDAO proDAO = new ProduktDAO(cc);
                    List<Produkt> lista = proDAO.getProdukty();
                    Produkt tempProduct = lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(tempProduct.toString());
                    proDAO.closeStatements();
                }
                else if (presentedType.equals("Halls")){
                    SalaDAO salaDAO = new SalaDAO(cc);
                    List<Sala> lista = salaDAO.getSale();
                    Sala hallTemp= lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(hallTemp.toString());
                    salaDAO.closeStatements();
                }
                else if (presentedType.equals("Seanse")){
                    ConnectionController cc2 = new ConnectionController();
                    cc2.open();
                    SeansDAO seansDAO = new SeansDAO(cc2);
                    List<Seans> lista = seansDAO.getSeanse();
                    Seans seansTemp = lista.get(dataToShow.indexOf(newSelection));
                    if(!seansTemp.equals(null)) {
                        infoText.setText(seansTemp.toString());
                    }
                    seansDAO.closeStatements();
                    cc2.close();
                }
                else if (presentedType.equals("Movies")){
                    FilmDAO movieDAO = new FilmDAO(cc);
                    List<Film> lista = movieDAO.getFilmy();
                    Film movieTemp= lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(movieTemp.toString2());
                    movieDAO.closeStatements();
                }
                else if (presentedType.equals("TypesOfTickets")){
                    RodzajBiletuDAO rodzajDAO= new RodzajBiletuDAO(cc);
                    List<RodzajBiletu> lista = rodzajDAO.getRodzajeBiletow();
                    RodzajBiletu typeTemp= lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(typeTemp.toString());
                    rodzajDAO.closeStatements();
                }
                else if (presentedType.equals("Seats")){
                    MiejsceDAO miejsceDAO= new MiejsceDAO(cc);
                    List<Miejsce> lista = miejsceDAO.getMiejsca();
                    Miejsce seatTemp= lista.get(dataToShow.indexOf(newSelection));
                    infoText.setText(seatTemp.toString());
                    miejsceDAO.closeStatements();
                }
                else if (presentedType.equals("Reservations")) {
                    ConnectionController cc3 = new ConnectionController();
                    cc3.open();
                    RezerwacjaDAO rezerwacjaDAO = new RezerwacjaDAO(cc3);
                    List<Rezerwacja> lista = rezerwacjaDAO.getRezerwacje();
                    Rezerwacja reservationTemp = lista.get(dataToShow.indexOf(newSelection));
                    if(!reservationTemp.equals(null)) {
                        infoText.setText(reservationTemp.toString());
                    }
                    cc3.close();
                }
                else if (presentedType.equals("Receipts")) {
                    ConnectionController cc2 = new ConnectionController();
                    cc2.open();
                    ParagonDAO paragonDAO = new ParagonDAO(cc2);
                    List<Paragon> lista = paragonDAO.getParagony();
                    Paragon paragonTemp = lista.get(dataToShow.indexOf(newSelection));
                    if(!paragonTemp.equals(null)) {
                        infoText.setText(paragonTemp.toString());
                    }
                    cc2.close();
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
