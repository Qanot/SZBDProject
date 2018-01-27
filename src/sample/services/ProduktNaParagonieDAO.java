package sample.services;

import sample.model.Paragon;

public class ProduktNaParagonieDAO {

    private ConnectionController connectionController;

    public void deleteAllProduktyNaParagonieForParagon(Paragon paragon){
        deleteAllProduktyForParagon(paragon);
        deleteAllBiletyForParagon(paragon);

    }
    private void deleteAllProduktyForParagon(Paragon paragon){
        // TODO

    }
    private void deleteAllBiletyForParagon(Paragon paragon){
        BiletDAO biletDAO = new BiletDAO(connectionController);
        biletDAO.deleteAllBiletyForParagon(paragon);
        //biletDAO.closeStatemnts(); // jesli w klasie bedzie zamykanie
    }
}
