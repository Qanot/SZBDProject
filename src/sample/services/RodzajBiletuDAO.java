package sample.services;

import sample.model.RodzajBiletu;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO


public class RodzajBiletuDAO {
    private ConnectionController connectionController;
    private List<RodzajBiletu>  rodzajeBiletow;
    public RodzajBiletuDAO(){
        rodzajeBiletow = new ArrayList<RodzajBiletu>();
    }

    private void selectRodzajeBiletow(){


    }
    public List<RodzajBiletu> getRodzajeBiletow() {
        selectRodzajeBiletow();
        return rodzajeBiletow;
    }

}