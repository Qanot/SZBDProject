package sample.services;

import sample.model.Bilet;
import sample.model.Paragon;
import sample.model.Produkt;

import java.util.List;

public class ProduktNaParagonieDAO {


    private ConnectionController connectionController;

    public void deleteAll(Paragon paragon){
        deleteAllProduktyForParagon(paragon);
        deleteAllBiletyForParagon(paragon);
    }

    public void insertAll(Paragon paragon){
        insrtAllProduktyForParagon(paragon);
        insertAllBiletyForParagon(paragon);
    }

    public List<Bilet> getAllBilety(Paragon paragon){
        // TODO
        return null;
    }
    public List<Produkt> getAllProdukty(Paragon paragon){
        // TODO
        return null;
    }
    private void insrtAllProduktyForParagon(Paragon paragon){
        // TODO

    }
    private void insertAllBiletyForParagon(Paragon paragon){
        // TODO
    }
    private void deleteAllProduktyForParagon(Paragon paragon){
        // TODO

    }
    private void deleteAllBiletyForParagon(Paragon paragon){
        // TODO
    }
}
