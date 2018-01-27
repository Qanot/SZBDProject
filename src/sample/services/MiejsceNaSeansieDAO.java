package sample.services;


import sample.model.Miejsce;
import sample.model.MiejsceNaSeansie;
import sample.model.Rezerwacja;
import sample.model.Seans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MiejsceNaSeansieDAO{
    private ConnectionController connectionController;

    public MiejsceNaSeansieDAO(ConnectionController connectionController){
        this.setConnectionController(connectionController);
    }

    public void deleteAllMiejscaNaSeansieForRezerwacja(Rezerwacja rezerwacja){

        /** UWAGA! ZNAJDUJE MIEJSCANASEANSIE DO USUNIECIA POD ID REZERWACJI,
         *  A NIE PO LISCIE ZAREZERWOWANEMIEJSCA, KTORA JUZ MOGLA
         *  ULEC ZMIANIE
         */
        PreparedStatement stmtDelete = null;
        try {
            stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM MIEJSCANASEANSIE " +
                            "WHERE REZERWACJE_ID = ?");

            stmtDelete.setInt(1, rezerwacja.getId());
            stmtDelete.executeUpdate();

        }catch (SQLException ex) {
            Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania polecenia delete", ex);
        } finally {
            if (stmtDelete!= null) {
                try {
                    stmtDelete.close();
                } catch (SQLException e) {
                    /* kod obsługi */
                    System.out.println("Błąd zamknięcia interfejsu Statement");
                }
            }
        }

    }

    public void insert(MiejsceNaSeansie miejsceNaSeansie){
        // TODO
        /**
         * miejsceNaSeansie ma informacje o Rezerwacji ALBO Bilecie:)
         */
        PreparedStatement stmtInsert = null;
        try {
            int id = this.getNextValueOfSequence();
            System.out.println("Id wynosi: " + id);
            stmtInsert = connectionController.getConn().prepareStatement(
                    "INSERT INTO MIEJSCANASEANSIE(ID, BILETY_ID, REZERWACJE_ID, MIEJSCA_ID, SEANSE_ID) " +
                            "VALUES(?, ?, ?, ?, ?)");


            stmtInsert.setInt(1, id); // miejsce na seansie dotyczy rezerwacji
            if(miejsceNaSeansie.getRezerwacja() != null){
                stmtInsert.setNull(2, java.sql.Types.INTEGER);
                stmtInsert.setInt(3, miejsceNaSeansie.getRezerwacja().getId());
            } else{ // miejsce na seansie dotyczy biletu
                stmtInsert.setInt(2, miejsceNaSeansie.getBilet().getId());
                stmtInsert.setNull(3, java.sql.Types.INTEGER);
            }
            stmtInsert.setInt(4, miejsceNaSeansie.getMiejsce().getId());
            stmtInsert.setInt(5, miejsceNaSeansie.getSeans().getId());

            stmtInsert.execute();

        }catch (SQLException ex) {
            Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania polecenia insert", ex);
        } finally {
            if (stmtInsert!= null) {
                try {
                    stmtInsert.close();
                } catch (SQLException e) {
                    /* kod obsługi */
                    System.out.println("Błąd zamknięcia interfejsu Statement");
                }
            }
        }

    }
    public List<MiejsceNaSeansie> getMiejscaByIdRezerwacji(Rezerwacja rezerwacja){
        List<MiejsceNaSeansie> miejscaNaSeansie = new ArrayList<MiejsceNaSeansie>();

        PreparedStatement stmtSelect = null;
        ResultSet rs = null;
        try {
            stmtSelect = connectionController.getConn().prepareStatement(
                    "SELECT ID, MIEJSCA_ID, SEANSE_ID " +
                            "FROM MIEJSCANASEANSIE " +
                            "WHERE REZERWACJE_ID = ?");
            stmtSelect.setInt(1, rezerwacja.getId());

            rs = stmtSelect.executeQuery();
            MiejsceDAO miejsceDAO = new MiejsceDAO(connectionController);
            SeansDAO seansDAO = new SeansDAO(connectionController);
            while(rs.next()) {
                int id = rs.getInt(1);
                int idMiejsca = rs.getInt(2);
                int idSeansu = rs.getInt(3);

                Miejsce miejsce = miejsceDAO.getMiejsceById(idMiejsca);
                Seans seans = seansDAO.getSeansById(idSeansu);
                MiejsceNaSeansie miejsceNaSeansie = new MiejsceNaSeansie(id, miejsce, seans, rezerwacja);
                miejscaNaSeansie.add(miejsceNaSeansie);
            }
            miejsceDAO.closeStatements();
            seansDAO.closeStatements();

        }catch (SQLException ex) {
            Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania polecenia select", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (stmtSelect!= null) {
                try {
                    stmtSelect.close();
                } catch (SQLException e) {
                    /* kod obsługi */
                    System.out.println("Błąd zamknięcia interfejsu Statement");
                }
            }
        }
        return miejscaNaSeansie;
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    private int getNextValueOfSequence() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int nextValue = -1;
        try {
            String sql = "SELECT miejscenaseansie_id_seq.nextval FROM DUAL";
            ps = connectionController.getConn().prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                nextValue = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                    "Błąd pobierania nastepnej wartosci z sekwencji rezerwacje_id_seq", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }
        return nextValue;
    }
}
