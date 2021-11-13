package si.fri.prpo.projektPolnilnePostaje.dtoji;

public class PoizvedbaPoPostajiDTO {

    private Integer idPostaja;
    private String lokacija;
    private String tipPrikljucka;

    public Integer getIdPostaja() {
        return idPostaja;
    }

    public void setIdPostaja(Integer idPostaja) {
        this.idPostaja = idPostaja;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getTipPrikljucka() {
        return tipPrikljucka;
    }

    public void setTipPrikljucka(String tipPrikljucka) {
        this.tipPrikljucka = tipPrikljucka;
    }
}
