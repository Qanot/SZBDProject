package sample.services;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectionController {
    private Connection conn = null;
    private Properties connectionProps = new Properties();
    public ConnectionController(){
        connectionProps.put("user", "inf127324");
        connectionProps.put("password", "inf12732");
    }
    public void open(){
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    connectionProps);
            System.out.println("Udało się połączyć z bazą danych.");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE,
                "nie udało się połączyć z bazą danych!", ex);
            System.exit(-1);
        }
    }
    public void close(){
        try {
            conn.close();
            System.out.println("Zamknięto połączenie.");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, "błąd przy zamykaniu połączenia!", ex);
        }
    }

    public Connection getConn() {
        return conn;
    }
}
