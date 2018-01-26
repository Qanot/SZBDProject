package sample.model;

import java.util.Date;
import java.util.List;

public class Paragon {

    private int id;
    private Date dataZakupu;
    private Pracownik pracownikNabijajacyParagon;

    public Pracownik getPracownikNabijajacyParagon() {
        return pracownikNabijajacyParagon;
    }

    public void setPracownikNabijajacyParagon(Pracownik pracownikNabijajacyParagon) {
        this.pracownikNabijajacyParagon = pracownikNabijajacyParagon;
    }

    public List<ProduktNaParagonie> getProduktyNaParagonie() {
        return produktyNaParagonie;
    }

    public void setProduktyNaParagonie(List<ProduktNaParagonie> produktyNaParagonie) {
        this.produktyNaParagonie = produktyNaParagonie;
    }

    private List<ProduktNaParagonie> produktyNaParagonie;

    public Paragon(Date dataZakupu) {
        this.dataZakupu = dataZakupu;
    }

    public Paragon(Date dataZakupu, Pracownik pracownikNabijajacyParagon) {
        this.dataZakupu = dataZakupu;
        this.pracownikNabijajacyParagon = pracownikNabijajacyParagon;
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
