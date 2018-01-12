package sample.services;

import sample.model.Plec;
import sample.model.Pracownik;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** ZALOZENIA:
 * id jest niezmienne (klucz podstawowy)
 * przy wstawianiu rekordu id generuje wyzwalacz za pomoca sekwencji **/

// prawie gotowe TODO insert

public class PracownikDAO {
    private ConnectionController connectionController;
    private List<Pracownik> pracownicy;
    private PreparedStatement stmtSelect = null;
    private PreparedStatement stmtDelete = null;
    private PreparedStatement stmtUpdate = null;
    private CallableStatement stmtInsert = null;
    private ResultSet rsSelect = null;

    public PracownikDAO(ConnectionController connectionController){
        this.setConnectionController(connectionController);
        pracownicy = new ArrayList<Pracownik>();

        try{
            stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT id, imie, nazwisko, plec FROM PRACOWNICY");
            stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM PRACOWNICY WHERE ID = ?");
            stmtUpdate = connectionController.getConn().prepareStatement(
                    "UPDATE PRACOWNICY SET IMIE = ?, NAZWISKO = ?, PLEC = ? WHERE ID = ?");
            stmtInsert = connectionController.getConn().prepareCall(
                    "{call wstaw_pracownika(?, ?, ?, ?)}");
            selectPracownicy();

        } catch(SQLException ex){
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd przygotowania prekompilowanego polecenia", ex);
        }


    }
    private void selectPracownicy(){
        pracownicy.clear();
        try{
            rsSelect = stmtSelect.executeQuery();
            while(rsSelect.next()){
                int id = rsSelect.getInt(1);
                String imie = rsSelect.getString(2);
                String nazwisko = rsSelect.getString(3);
                String plecString = rsSelect.getString(4);
                plecString = plecString.substring(0,1); // pierwsza litera
                Plec plec = Plec.valueOf(plecString);
                Pracownik pracownik = new Pracownik(id, imie, nazwisko, plec);
                pracownicy.add(pracownik);
            }
        } catch(SQLException ex){
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        }

    }
    public List<Pracownik> getPracownicy() {
        selectPracownicy();
        return pracownicy;
    }
    public void deletePracownik(Pracownik pracownik){
        try{
            stmtDelete.setInt(1, pracownik.getId());
            int changes = stmtDelete.executeUpdate();
            if(changes != 1){
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch(SQLException ex){
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        }


    }
    public void updatePracownik(Pracownik pracownik){
        try {
            stmtUpdate.setString(1, pracownik.getImie());
            stmtUpdate.setString(2, pracownik.getNazwisko());
            stmtUpdate.setString(3, pracownik.getPlec().name()); // name zwroci dokladnie "K" lub "M",
            // nawet jesli nadpiszemy w przyszlosci toString
            stmtUpdate.setInt(4, pracownik.getId());
            int changes = stmtUpdate.executeUpdate();
            if(changes != 1){
                System.out.println("Błąd! Nie zmodyfikowano dokladnie 1 rekordu");
            }

        }catch (SQLException ex){
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia update", ex);
        }


    }
    public void insertPracownik(Pracownik pracownik){
        try{
            stmtInsert.setString(1, pracownik.getImie());
            stmtInsert.setString(2, pracownik.getNazwisko());
            stmtInsert.setString(3, pracownik.getPlec().name());
            stmtInsert.registerOutParameter(4, Types.INTEGER);
            stmtInsert.execute();
            int id = stmtInsert.getInt(4);
            pracownik.setId(id);

        }catch (SQLException ex){
            Logger.getLogger(PracownikDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
        }

    }

    public void setConnectionController(ConnectionController connectionController){
        this.connectionController = connectionController;
    }


}
