package sample;

import sample.model.*;

import sample.services.*;

import java.util.List;

public class TestyDAO {
    public static void main(String[] args) {
        ConnectionController cc = new ConnectionController();
        cc.open();
        //testProdukt(cc);
        //testSala(cc);
        //testFilm(cc);
        //testSeans(cc);
        //testSeansWolneWykupioneZarezerwowane(cc);
        testRezerwacja(cc);
        cc.close();


    }

    public static void testRezerwacja(ConnectionController cc){
        RezerwacjaDAO rezerwacjaDAO = new RezerwacjaDAO(cc);
        List<Rezerwacja> rezerwacje = rezerwacjaDAO.getRezerwacje();
        for(Rezerwacja rezerwacja: rezerwacje){
            System.out.println(rezerwacja.toString());
        }
        System.out.println(rezerwacjaDAO.getRezerwacjaById(1));
    }


    public static void testSeansWolneWykupioneZarezerwowane(ConnectionController cc)
    {
        SeansDAO seansDAO = new SeansDAO(cc);
        Seans seans = seansDAO.getSeansById(21);
        System.out.println("MIEJSCA WOLNE");
        for(Miejsce m: seans.getMiejscaWolne()){
            System.out.println(m.toString());
        }

        System.out.println("MIEJSCA WYKUPIONE");
        for(Miejsce m: seans.getMiejscaWykupione()){
            System.out.println(m.toString());
        }

        System.out.println("MIEJSCA ZAREZERWOWANE");
        for(Miejsce m: seans.getMiejscaZarezerwowane()){
            System.out.println(m.toString());
        }

        seansDAO.closeStatements();
        int x = 3;
    }
    public static void testSeans(ConnectionController cc){
        SeansDAO seansDAO = new SeansDAO(cc);
        for(Seans seans: seansDAO.getSeanse()){
            System.out.println(seans.toStringTest());
        }
        Seans seansGet = seansDAO.getSeansById(4);
        System.out.println("Get by id: \t" + seansGet.toStringTest());

        FilmDAO filmDAO = new FilmDAO(cc);
        Film film = filmDAO.getFilmById(7);
        filmDAO.closeStatements();

        SalaDAO salaDAO = new SalaDAO(cc);
        Sala sala = salaDAO.getSalaById(13);
        salaDAO.closeStatements();

        Seans seans1 = new Seans(new java.util.Date(), film, sala);
        seansDAO.insertSeans(seans1);

        for(Seans seans: seansDAO.getSeanse()){
            System.out.println(seans.toStringTest());
        }

        Seans seansDelete = seansDAO.getSeansById(9);
        seansDAO.deleteSeans(seansDelete);

        for(Seans seans: seansDAO.getSeanse()){
            System.out.println(seans.toStringTest());
        }

        Seans seansUpdate = seansDAO.getSeansById(5);
        seansUpdate.setDataEmisji(new java.util.Date());
        seansDAO.updateSeans(seansUpdate);

        System.out.println("Po update: ");
        for(Seans seans: seansDAO.getSeanse()){
            System.out.println(seans.toStringTest());
        }




        seansDAO.closeStatements();
    }

    public static void testKlient(ConnectionController cc) {
        KlientDAO klientDAO = new KlientDAO(cc);
        for (Klient klient : klientDAO.getKlienci()) {
            System.out.println(klient.toStringTest());
        }
        Klient klient1 = klientDAO.getKlienci().get(0);
        klient1.setLogin("krysiahehe");
        klientDAO.updateKlient(klient1);
        for (Klient klient : klientDAO.getKlienci()) {
            System.out.println(klient.toStringTest());
        }
        klientDAO.closeStatements();

    }

    public static void testProdukt(ConnectionController cc) {
        Produkt produkt1 = new Produkt(9.99, "popcorn", RozmiarPorcji.S);
        Produkt produkt2 = new Produkt(14.99, "popcorn", RozmiarPorcji.M);
        Produkt produkt3 = new Produkt(7.99, "cola", RozmiarPorcji.M);
        ProduktDAO produktDAO = new ProduktDAO(cc);
        produktDAO.insertProdukt(produkt1);
        produktDAO.insertProdukt(produkt3);

        for(Produkt produkt : produktDAO.getProdukty()){
            System.out.println(produkt.toStringTest());
        }
        produktDAO.insertProdukt(produkt1);
        produktDAO.insertProdukt(produkt2);
        for(Produkt produkt : produktDAO.getProdukty()){
            System.out.println(produkt.toStringTest());
        }
        Produkt usun  = produktDAO.getProdukty().get(0);
        produktDAO.deleteProdukt(usun);
        for(Produkt produkt : produktDAO.getProdukty()){
            System.out.println(produkt.toStringTest());
        }
        Produkt zmiana  = produktDAO.getProdukty().get(0);
        zmiana.setNazwa("kawa");
        zmiana.setCena(12.99);
        zmiana.setRozmiarPorcji(RozmiarPorcji.L);
        produktDAO.updateProdukt(zmiana);
        for(Produkt produkt : produktDAO.getProdukty()){
            System.out.println(produkt.toStringTest());
        }
        produktDAO.closeStatements();
    }

    public static void testSala(ConnectionController cc){
        SalaDAO salaDAO = new SalaDAO(cc);
        Sala sala1 = new Sala(12);
        Sala sala2 = new Sala(5);
        Sala sala3 = new Sala(3);
        Sala sala4 = new Sala(3);

        salaDAO.insertSala(sala1);
        salaDAO.insertSala(sala2);
        salaDAO.insertSala(sala3);
        salaDAO.insertSala(sala4);

        for(Sala sala: salaDAO.getSale()){
            System.out.println(sala.toStringTest());
        }
        Sala sala2kopia = salaDAO.getSalaById(sala2.getId());
        System.out.println("To samo: " + sala2kopia.toStringTest());
        sala3.setNrSali(54);
        salaDAO.updateSala(sala3);
        salaDAO.insertSala(sala4);
        for(Sala sala: salaDAO.getSale()){
            System.out.println(sala.toStringTest());
        }
        salaDAO.deleteSala(sala1);
        for(Sala sala: salaDAO.getSale()){
            System.out.println(sala.toStringTest());
        }
        salaDAO.closeStatements();



    }
    public static void testFilm(ConnectionController cc){
        FilmDAO filmDAO = new FilmDAO(cc);
        Film film1 = new Film("Thor", 124);
        Film film2 = new Film("Lepiej późno niż później", 108);
        Film film3 = new Film("Zielona Mila", 188);
        Film film4 = new Film("Wzgórza mają oczy", 123);

        filmDAO.insertFilm(film1);
        filmDAO.insertFilm(film2);
        filmDAO.insertFilm(film3);
        filmDAO.insertFilm(film4);
        System.out.println(film1.getId());
        for(Film film : filmDAO.getFilmy()){
            System.out.println(film.toStringTest());
        }
        filmDAO.deleteFilm(film1);
        film4.setTytul("Dziecko Rosemary");
        film4.setCzasTrwaniaWMin(142);
        filmDAO.updateFilm(film4);
        for(Film film : filmDAO.getFilmy()){
            System.out.println(film.toStringTest());
        }
        filmDAO.closeStatements();

    }
}
