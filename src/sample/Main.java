package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.Controller;
import sample.model.*;
import sample.services.ConnectionController;
import sample.services.KlientDAO;
import sample.services.PracownikDAO;
import sample.services.ProduktDAO;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmls/sample.fxml"));
        Parent root = (Parent)loader.load();
        Controller controller = (Controller)loader.getController();
        primaryStage.setOnHiding( event -> {controller.getCc().close();} );
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
//        makeConnection();
//        ConnectionController cc = new ConnectionController();
//        cc.open();
//        ProduktDAO proDAO = new ProduktDAO(cc);
//        Produkt produkt = new Produkt(15.5, "Duży popcorn", RozmiarPorcji.L);
//        Produkt produkt2 = new Produkt(12.5, "Sredni Popcorn", RozmiarPorcji.M);
//        Produkt produkt3 = new Produkt(10.0, "Mały Popcorn", RozmiarPorcji.S);
//        proDAO.insertProdukt(produkt);
//        proDAO.insertProdukt(produkt2);
//        proDAO.insertProdukt(produkt3);
//        List<Produkt> lista = proDAO.getProdukty();
//        for (Produkt pra: lista){
//            System.out.println(pra.getId() + " " + pra.getCena());
//        }
//        KlientDAO kldao =new KlientDAO(cc);
//        Klient klient1 = new Klient("Jan", "Węglarz", "j.w@put.poznan.pl", "janek123", "janek123", "123456789");
//        Klient klient2 = new Klient("Rafał", "Klaus", "r.k@put.poznan.pl", "rafałek", "rafiXDDD", "019238032");
//        Klient klient3 = new Klient("Matuesz", "Lango", "m.l@put.poznan.pl", "MLmaster", "WIruler", "999");
//        kldao.insertKlient(klient1);
//        kldao.insertKlient(klient2);
//        kldao.insertKlient(klient3);

//        List<Pracownik> lista = prdao.getPracownicy();
//        for (Pracownik pra: lista){
//            System.out.println(pra.getId());
//        }
//        pracownik.setNazwisko("kowalski");
//        prdao.updatePracownik(pracownik);

        launch(args);
    }

    public static void initApp(){

    }



}