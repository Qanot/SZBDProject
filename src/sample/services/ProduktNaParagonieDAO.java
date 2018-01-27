package sample.services;

import sample.model.Bilet;
import sample.model.Paragon;
import sample.model.Produkt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProduktNaParagonieDAO {


    private ConnectionController connectionController;

    public ProduktNaParagonieDAO(ConnectionController cc) {
        connectionController = cc;
    }

    public void deleteAll(Paragon paragon){
        deleteAllProduktyForParagon(paragon);
        deleteAllBiletyForParagon(paragon);
    }

    public void insertAll(Paragon paragon){
        insrtAllProduktyForParagon(paragon);
        insertAllBiletyForParagon(paragon);
    }

    public List<Bilet> getAllBilety(Paragon paragon){
        PreparedStatement stmtFindAll = null;
        ResultSet rs = null;
        List<Bilet> bilety = new ArrayList<Bilet>();
        BiletDAO biletDAO = new BiletDAO(connectionController);
        try {
            stmtFindAll = connectionController.getConn().prepareStatement(
                    "SELECT BILETY_ID FROM PRODUKTYNAPARAGONIE WHERE BILETY_ID IS NOT NULL");
            rs = stmtFindAll.executeQuery();
            while (rs.next()) {
                int idBiletu = rs.getInt("BILETY_ID");
                Bilet bilet = biletDAO.getBiletById(idBiletu);
                bilety.add(bilet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (stmtFindAll != null) {
                try {
                    stmtFindAll.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }
        return bilety;

    }
    public List<Produkt> getAllProdukty(Paragon paragon){
        PreparedStatement stmtFindAll = null;
        ResultSet rs = null;
        List<Produkt> produkty = new ArrayList<Produkt>();

        ProduktDAO produktDAO = new ProduktDAO(connectionController);
        try {
            stmtFindAll = connectionController.getConn().prepareStatement(
                    "SELECT PRODUKTY_ID FROM PRODUKTYNAPARAGONIE WHERE PRODUKTY_ID IS NOT NULL");
            rs = stmtFindAll.executeQuery();
            while (rs.next()) {
                int idProduktu = rs.getInt("PRODUKTY_ID");
                Produkt produkt = produktDAO.getProduktById(idProduktu);
                produkty.add(produkt); /** UWAGA! LISTA GŁĘBOKICH KOPII **/
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (stmtFindAll != null) {
                try {
                    stmtFindAll.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }
        produktDAO.closeStatements();
        return produkty;

    }
    private void insrtAllProduktyForParagon(Paragon paragon){
        // tutaj tylko wstawiamy odpowiednie produkty na paragonie
        for(Produkt produkt: paragon.getProdukty()){
            insertSingleProdukt(paragon.getId(), produkt);
        }
    }

    private void insertAllBiletyForParagon(Paragon paragon){
        BiletDAO biletDAO = new BiletDAO(connectionController);
        // najpierw wstaw bilet (zeby poznac jego id), a potem odpowiadajacy mu produkt na paragonie
        for(Bilet bilet: paragon.getBilety()){
            biletDAO.insertBilet(bilet);
            insertSingleBilet(paragon.getId(), bilet);

        }
    }
    private void deleteAllProduktyForParagon(Paragon paragon){
        /** UWAGA! ZNAJDUJE MIEJSCANASEANSIE DO USUNIECIA PO ID PARAGONU
         */
        PreparedStatement stmtDelete = null;
        try {
            stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM PRODUKTYNAPARAGONIE " +
                            "WHERE PARAGONY_ID = ? " +
                            "AND PRODUKTY_ID IS NOT NULL");

            stmtDelete.setInt(1, paragon.getId());
            stmtDelete.executeUpdate();

        }catch (SQLException ex) {
            Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
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

    private void deleteAllBiletyForParagon(Paragon paragon){
        /**
         * 1. usun wszystkie produkty na paragonie
         * 2. usun wszystkie bilety na podstawie id z listy produktow na paragonie (model)
         */
        /** UWAGA! ZNAJDUJE MIEJSCANASEANSIE DO USUNIECIA PO ID PARAGONU
         */
        PreparedStatement stmtDelete = null;
        try {
            stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM PRODUKTYNAPARAGONIE " +
                            "WHERE PARAGONY_ID = ? " +
                            "AND BILETY_ID IS NOT NULL");

            stmtDelete.setInt(1, paragon.getId());
            stmtDelete.executeUpdate();

        }catch (SQLException ex) {
            Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
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

        BiletDAO biletDAO = new BiletDAO(connectionController);
        for(Bilet bilet: paragon.getBilety()){
            biletDAO.deleteBilet(bilet);
        }


    }

    private void insertSingleBilet(int idParagonu, Bilet bilet){
        PreparedStatement stmtInsert = null;
        try {
            int idProduktuNaParagonie = this.getNextValueOfSequence();
            stmtInsert = connectionController.getConn().prepareStatement(
                    "INSERT INTO PRODUKTYNAPARAGONIE(ID, PARAGONY_ID, BILETY_ID) " +
                            "VALUES(?, ?, ?)");
            stmtInsert.setInt(1, idProduktuNaParagonie);
            stmtInsert.setInt(2, idParagonu);
            stmtInsert.setInt(3, bilet.getId());
            stmtInsert.execute();

        } catch (SQLException ex) {
            Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
        } finally {
            if (stmtInsert != null) {
                try {
                    stmtInsert.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }
    }

    private void insertSingleProdukt(int idParagonu, Produkt produkt){
        PreparedStatement stmtInsert = null;
        try {
            int idProduktuNaParagonie = this.getNextValueOfSequence();
            stmtInsert = connectionController.getConn().prepareStatement(
                    "INSERT INTO PRODUKTYNAPARAGONIE(ID, PARAGONY_ID, PRODUKTY_ID) " +
                            "VALUES(?, ?, ?)");
            stmtInsert.setInt(1, idProduktuNaParagonie);
            stmtInsert.setInt(2, idParagonu);
            stmtInsert.setInt(3, produkt.getId());
            stmtInsert.execute();

        } catch (SQLException ex) {
            Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
        } finally {
            if (stmtInsert != null) {
                try {
                    stmtInsert.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MiejsceNaSeansieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }

    }

    private int getNextValueOfSequence() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int nextValue = -1;
        try {
            String sql = "SELECT produktnaparagonie_id_seq.nextval FROM DUAL";
            ps = connectionController.getConn().prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                nextValue = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
                    "Błąd pobierania nastepnej wartosci z sekwencji rezerwacje_id_seq", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProduktNaParagonieDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }
        return nextValue;
    }


}
