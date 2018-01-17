package sample.services;

import sample.model.RodzajBiletu;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO implement all methods


public class RodzajBiletuDAO {
    private ConnectionController connectionController;
    private List<RodzajBiletu>  rodzajeBiletow;
    public RodzajBiletuDAO(){
        rodzajeBiletow = new ArrayList<RodzajBiletu>();
        this.setConnectionController(connectionController);
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }
    private void selectRodzajeBiletow(){

    }

    public List<RodzajBiletu> getRodzajeBiletow() {
        selectRodzajeBiletow();
        return rodzajeBiletow;
    }

    public boolean insertRodzajBiletu(RodzajBiletu rodzajBiletu){
        return true;
    }

    public boolean updateRodzajBiletu(RodzajBiletu rodzajBiletu){
        return true;
    }

    public void deleteRodzajBiletu(RodzajBiletu rodzajBiletu){
    }

    public void closeStatements(){
    }


}
