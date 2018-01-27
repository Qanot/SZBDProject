package sample.services;

import sample.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BiletDAO {
    private ConnectionController connectionController;

    public BiletDAO(ConnectionController connectionController){
        this.setConnectionController(connectionController);

    }
    public Bilet getBiletById(int idBiletu) {
        Bilet bilet = null;
        PreparedStatement stmtFindById = null;
        ResultSet rs = null;
        try {
            // TODO SOBOTA SELECT REZERWACJE_ID ??? czy bedzie potrzeba?
            stmtFindById = connectionController.getConn().prepareStatement(
                    "SELECT RODZAJEBILETOW_ID " +
                            "FROM BILETY " +
                            "WHERE ID = ?");
            stmtFindById.setInt(1, idBiletu);
            rs = stmtFindById.executeQuery();
            if (rs.next()) {
                int idRodzajBiletu = rs.getInt("RODZAJEBILETOW_ID");
                RodzajBiletuDAO rodzajBiletuDAO = new RodzajBiletuDAO(connectionController);
                RodzajBiletu rodzajBiletu = rodzajBiletuDAO.getRodzajBiletuById(idRodzajBiletu);
                rodzajBiletuDAO.closeStatements();

                MiejsceNaSeansieDAO miejsceNaSeansieDAO = new MiejsceNaSeansieDAO(connectionController);
                bilet = new Bilet(rodzajBiletu);
                bilet.setId(idBiletu);
                MiejsceNaSeansie miejsceNaSeansie = miejsceNaSeansieDAO.getMiejsceByIdBiletu(bilet);
                bilet.setMiejsceNaSeansie(miejsceNaSeansie);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BiletDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BiletDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (stmtFindById != null) {
                try {
                    stmtFindById.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BiletDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }
        return bilet;
    }

    private void deleteMiejsceNaSeansieForBilet(Bilet bilet){
        MiejsceNaSeansieDAO miejsceNaSeansieDAO = new MiejsceNaSeansieDAO(connectionController);
        miejsceNaSeansieDAO.deleteMiejsceNaSeansieForBilet(bilet);
    }

    public void deleteBilet(Bilet bilet){
        deleteMiejsceNaSeansieForBilet(bilet);

        PreparedStatement stmtDelete =  null;
        try {
            stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM BILETY WHERE ID = ?");
            stmtDelete.setInt(1, bilet.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BiletDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        } finally {
            if (stmtDelete != null) {
                try {
                    stmtDelete.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BiletDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }
    }


    public void insertBilet(Bilet bilet){
        // TODO SOBOTA REZERWACJE_ID CZY POTRZEBNE??
        PreparedStatement stmtInsert = null;
        try {
            // najpierw wstaw bilet
            int idBiletu = this.getNextValueOfSequence();
            bilet.setId(idBiletu);
            stmtInsert = connectionController.getConn().prepareStatement(
                    "INSERT INTO BILETY(id, RODZAJEBILETOW_ID) " +
                            "VALUES(?, ?)");
            stmtInsert.setInt(1, bilet.getId());
            stmtInsert.setInt(2, bilet.getRodzajBiletu().getId());
            stmtInsert.execute();

            // a potem wstaw MiejscaNaSeansie
            MiejsceNaSeansieDAO miejsceNaSeansieDAO = new MiejsceNaSeansieDAO(connectionController);
            miejsceNaSeansieDAO.insert(bilet.getMiejsceNaSeansie());

        } catch (SQLException ex) {
            Logger.getLogger(SeansDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia insert", ex);
        } finally {
            if (stmtInsert != null) {
                try {
                    stmtInsert.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
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
            String sql = "SELECT BILET_ID_SEQ.nextval FROM DUAL";
            ps = connectionController.getConn().prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                nextValue = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BiletDAO.class.getName()).log(Level.SEVERE,
                    "Błąd pobierania nastepnej wartosci z sekwencji rezerwacje_id_seq", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BiletDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BiletDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }
        return nextValue;
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }


}
