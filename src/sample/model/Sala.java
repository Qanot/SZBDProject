package sample.model;

public class Sala {

    private int nrSali;
    private int nrSaliStary;

    public Sala(int nrSali) {
        this.setNrSali(nrSali);
        this.nrSaliStary = nrSali;
    }

    public int getNrSali() {
        return nrSali;
    }
    /**
     * GUI wgl sie nie martwi starym nr sali,
     * DAO korzysta ze starego nr Sali przy updatowaniu,
     * po update DAO samo dba o ustawienie starego nr sali na nowy
     * poprzez wywolanie sala.setNrSali(sala.getNrSali)
     */
    public void setNrSali(int nrSali) {
        this.nrSaliStary = this.nrSali;
        this.nrSali = nrSali;
    }

    public int getNrSaliStary() {
        return nrSaliStary;
    }
}
