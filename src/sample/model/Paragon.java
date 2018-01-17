package sample.model;

import java.util.Date;
import java.util.List;

public class Paragon {

    private int id;
    private Date dataZakupu;
    private Pracownik pracownikNabijajacyParagon;
    private List<ProduktNaParagonie> produktyNaParagonie;

    public Paragon(Date dataZakupu) {
        this.dataZakupu = dataZakupu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataZakupu() {
        return dataZakupu;
    }

    public void setDataZakupu(Date dataZakupu) {
        this.dataZakupu = dataZakupu;
    }
}
