package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        Parent root = FXMLLoader.load(getClass().getResource("fxmls/sample.fxml"));
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


    //simple connection example
    public static void makeConnection(){
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "inf127276");
        connectionProps.put("password", "inf127276");
        try {
            System.out.println("Przed błędem");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    connectionProps);
            System.out.println("Połączono z bazą danych");

            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("select count(*) "
                        + "from pracownicy");
                rs.next();
                System.out.println("Zatrudniono " + rs.getInt(1) + " pracownikow w tym:");

                try {
                    rs.close();
                } catch (SQLException e) {
                    /* kod obsługi */
                    System.out.println("Błąd zamknięcia interfejsu ResultSet");
                }
                rs = stmt.executeQuery("select  count(*),  zespoly.nazwa"
                        + " from pracownicy natural join zespoly"
                        + " group by zespoly.nazwa");

                while (rs.next()) {
                    System.out.println("\t" + rs.getInt(1) + " w zespole " + rs.getString(2));
                }

            } catch (SQLException ex) {
                System.out.println("Bład wykonania polecenia" + ex.toString());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        /* kod obsługi */
                        System.out.println("Błąd zamknięcia interfejsu ResultSet");
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        /* kod obsługi */
                        System.out.println("Błąd zamknięcia interfejsu Statement");
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,
                    "nie udało się połączyć z bazą danych", ex);
            System.exit(-1);
        }
        try {
            conn.close();
            System.out.println("Zamknięto połączenie.");
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}