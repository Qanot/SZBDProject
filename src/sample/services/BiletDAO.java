package sample.services;

import sample.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BiletDAO {
    private ConnectionController connectionController;

    public BiletDAO(ConnectionController connectionController){
        this.connectionController = connectionController;

    }
    public Bilet getBiletById(int idBiletu) {

        return null;
    }

    public void deleteBilet(Bilet bilet){

    }


    public void insertBilet(Bilet bilet){

    }

}
