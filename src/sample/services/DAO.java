package sample.services;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DAO {
    protected ConnectionController connectionController;
    protected PreparedStatement stmtSelect = null;
    protected PreparedStatement stmtDelete = null;
    protected CallableStatement stmtUpdate = null;
    protected CallableStatement stmtInsert = null;
    protected ResultSet rsSelect = null;
    protected PreparedStatement stmtFindById = null;

    public DAO(ConnectionController connectionController) {
        this.setConnectionController(connectionController);
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    public void closeStatements() {
        if (stmtSelect != null) {
            try {
                stmtSelect.close();
            } catch (SQLException e) {
                /* kod obsługi */
                System.out.println("Błąd zamknięcia interfejsu Statement");
            }
        }
        if (stmtInsert != null) {
            try {
                stmtInsert.close();
            } catch (SQLException e) {
                /* kod obsługi */
                System.out.println("Błąd zamknięcia interfejsu Statement");
            }
        }
        if (stmtUpdate != null) {
            try {
                stmtUpdate.close();
            } catch (SQLException e) {
                /* kod obsługi */
                System.out.println("Błąd zamknięcia interfejsu Statement");
            }
        }
        if (stmtDelete != null) {
            try {
                stmtDelete.close();
            } catch (SQLException e) {
                /* kod obsługi */
                System.out.println("Błąd zamknięcia interfejsu Statement");
            }
        }
        if (stmtFindById != null) {
            try {
                stmtDelete.close();
            } catch (SQLException e) {
                /* kod obsługi */
                System.out.println("Błąd zamknięcia interfejsu Statement");
            }
        }
    }
}
