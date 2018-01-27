package sample.services;

import sample.model.Klient;
import sample.model.MiejsceNaSeansie;
import sample.model.Rezerwacja;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RezerwacjaDAO {

    private ConnectionController connectionController;
    private List<Rezerwacja> rezerwacje;


    public RezerwacjaDAO(ConnectionController connectionController) {

        //rezerwacje = new ArrayList<Rezerwacja>();
        this.setConnectionController(connectionController);
    }

    public List<Rezerwacja> getRezerwacje() {
        PreparedStatement stmtFindAll = null;
        ResultSet rs = null;
        List<Rezerwacja> rezerwacje = new ArrayList<Rezerwacja>();
        try {
            stmtFindAll = connectionController.getConn().prepareStatement(
                    "SELECT ID FROM REZERWACJE");
            rs = stmtFindAll.executeQuery();
            while (rs.next()) {
                int idRezerwacji = rs.getInt("ID");
                Rezerwacja rezerwacja = this.getRezerwacjaById(idRezerwacji);
                rezerwacje.add(rezerwacja);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (stmtFindAll != null) {
                try {
                    stmtFindAll.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }
        return rezerwacje;
    }


    public void insertRezerwacja(Rezerwacja rezerwacja) {
        PreparedStatement stmtInsert = null;
        try {
            // najpierw wstaw rezerwacje
            int idRezerwacji = this.getNextValueOfSequence();
            rezerwacja.setId(idRezerwacji);
            stmtInsert = connectionController.getConn().prepareStatement(
                    "INSERT INTO rezerwacje(id, data_godzina, czy_oplacona, klienci_id) " +
                            "VALUES(?, ?, 'N', ?)");
            stmtInsert.setInt(1, rezerwacja.getId());
            stmtInsert.setDate(2, new java.sql.Date(rezerwacja.getDataUtworzenia().getTime())); // to chyba dziala
            stmtInsert.setInt(3, rezerwacja.getKlientRezerwujacy().getId());
            stmtInsert.execute();

            // a potem wstaw MiejscaNaSeansie
            MiejsceNaSeansieDAO miejsceNaSeansieDAO = new MiejsceNaSeansieDAO(connectionController);
            for (MiejsceNaSeansie miejsceNaSeansie : rezerwacja.getZarezerwowaneMiejsca()) {
                miejsceNaSeansie.setRezerwacja(rezerwacja);
                miejsceNaSeansieDAO.insert(miejsceNaSeansie);
            }

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

    public Rezerwacja getRezerwacjaById(int idRezerwacji) {
        Rezerwacja rezerwacja = null;
        PreparedStatement stmtFindById = null;
        ResultSet rs = null;
        try {
            stmtFindById = connectionController.getConn().prepareStatement(
                    "SELECT DATA_GODZINA, CZY_OPLACONA, KLIENCI_ID " +
                            "FROM REZERWACJE " +
                            "WHERE ID = ?");
            stmtFindById.setInt(1, idRezerwacji);
            rs = stmtFindById.executeQuery();
            if (rs.next()) {
                Date dataUtworzenia = new Date(rs.getTimestamp("DATA_GODZINA").getTime());
                boolean jestOplacona = false;
                String czy_oplacona = rs.getString("CZY_OPLACONA").substring(0, 1);
                if (czy_oplacona.equals("T")) {
                    jestOplacona = true;
                } else {
                    jestOplacona = false;
                }
                int idKlienta = rs.getInt("KLIENCI_ID");

                KlientDAO klientDAO = new KlientDAO(connectionController);
                Klient klientRezerwujacy = klientDAO.getKlientById(idKlienta);
                klientDAO.closeStatements();

                MiejsceNaSeansieDAO miejsceNaSeansieDAO = new MiejsceNaSeansieDAO(connectionController);
                rezerwacja = new Rezerwacja(idRezerwacji, dataUtworzenia, jestOplacona, klientRezerwujacy);
                List<MiejsceNaSeansie> zarezerwowaneMiejsca = miejsceNaSeansieDAO.getMiejscaByIdRezerwacji(rezerwacja);
                rezerwacja.setZarezerwowaneMiejsca(zarezerwowaneMiejsca);

            }


        } catch (SQLException ex) {
            Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia select", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (stmtFindById != null) {
                try {
                    stmtFindById.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }

        return rezerwacja;
    }


    private int getNextValueOfSequence() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int nextValue = -1;
        try {
            String sql = "SELECT REZERWACJA_ID_SEQ.nextval FROM DUAL";
            ps = connectionController.getConn().prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                nextValue = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                    "Błąd pobierania nastepnej wartosci z sekwencji rezerwacje_id_seq", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu ResultSet", ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }
        return nextValue;
    }

    public void updateRezerwacja(Rezerwacja rezerwacja) {
        // usun stare obiekty z bazy
        deleteAllMiejscaNaSeansieForRezerwacja(rezerwacja);
        // a potem wstaw nowe
        MiejsceNaSeansieDAO miejsceNaSeansieDAO = new MiejsceNaSeansieDAO(connectionController);
        for (MiejsceNaSeansie miejsceNaSeansie : rezerwacja.getZarezerwowaneMiejsca()) {
            miejsceNaSeansie.setRezerwacja(rezerwacja);
            miejsceNaSeansieDAO.insert(miejsceNaSeansie);
        }

        // update pozostale rzeczy w samej rezerwacji
        PreparedStatement stmtUpdate =  null;
        try {
            stmtUpdate = connectionController.getConn().prepareStatement(
                    "UPDATE REZERERWACJE " +
                            "SET KLIECI_ID = ?, " +
                            "    CZY_OPLACONA = ? " +
                            "    WHERE ID = ?");
            stmtUpdate.setInt(1, rezerwacja.getKlientRezerwujacy().getId());
            String czy_oplacona;
            if(rezerwacja.isJestOplacona()){
                czy_oplacona = "T";
            } else{
                czy_oplacona = "N";
            }
            stmtUpdate.setString(2, czy_oplacona);
            stmtUpdate.setInt(3, rezerwacja.getId());

            int changes = stmtUpdate.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie zaktualizowano dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        } finally {
            if (stmtUpdate != null) {
                try {
                    stmtUpdate.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }

    }

    public void deleteRezerwacja(Rezerwacja rezerwacja) {
        deleteAllMiejscaNaSeansieForRezerwacja(rezerwacja);

        PreparedStatement stmtDelete =  null;
        try {
            stmtDelete = connectionController.getConn().prepareStatement(
                    "DELETE FROM REZERWACJE WHERE ID = ?");
            stmtDelete.setInt(1, rezerwacja.getId());
            int changes = stmtDelete.executeUpdate();
            if (changes != 1) {
                System.out.println("Błąd! Nie usunieto dokladnie 1 rekordu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                    "Błąd wykonania prekompilowanego polecenia delete", ex);
        } finally {
            if (stmtDelete != null) {
                try {
                    stmtDelete.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RezerwacjaDAO.class.getName()).log(Level.SEVERE,
                            "Błąd zamykania interfejsu PreparedStatement", ex);
                }
            }
        }


    }
    private void deleteAllMiejscaNaSeansieForRezerwacja(Rezerwacja rezerwacja){
        MiejsceNaSeansieDAO miejsceNaSeansieDAO = new MiejsceNaSeansieDAO(connectionController);
        miejsceNaSeansieDAO.deleteAllMiejscaNaSeansieForRezerwacja(rezerwacja);
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

}
