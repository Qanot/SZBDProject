package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.Controller;
import sample.model.*;
import sample.services.*;

import java.util.Date;

public class Main extends Application {

    public static void main(String[] args) {
//        makeConnection();
//        ConnectionController cc = new ConnectionController();
//        cc.open();
//        ParagonDAO paragonDAO = new ParagonDAO(cc);
//        PracownikDAO pracownikDAO = new PracownikDAO(cc);
//        Pracownik prac = pracownikDAO.getPracownicy().get(0);
//        Paragon paragon1 = new Paragon(new Date(), prac);
//        paragonDAO.insertParagon(paragon1);
//        MiejsceDAO miejsceDAO = new MiejsceDAO(cc);
//        SalaDAO salaDAO = new SalaDAO(cc);
//        Sala sala = salaDAO.getSalaById(12);
//        Miejsce miejsce1 = new Miejsce("I", 1, sala);
//        Miejsce miejsce2 = new Miejsce("I", 2, sala);
//        Miejsce miejsce3 = new Miejsce("I", 3, sala);
//        Miejsce miejsce4 = new Miejsce("I", 4, sala);
//        System.out.println("Id sali to: " + sala.getId());
//        miejsceDAO.insertMiejsce(miejsce1);
//        miejsceDAO.insertMiejsce(miejsce2);
//        miejsceDAO.insertMiejsce(miejsce3);
//        miejsceDAO.insertMiejsce(miejsce4);

        launch(args);
    }

    public static void initApp() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmls/sample.fxml"));
        Parent root = (Parent) loader.load();
        Controller controller = (Controller) loader.getController();
        primaryStage.setOnHiding(event -> {
            controller.getCc().close();
        });
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


}