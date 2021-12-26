package si.fri.prpo.projektPolnilnePostaje.dtoji;

import java.util.Date;

public class UrejanjePostajeDTO {
    private Integer idLastnik;
    private String lokacija;

    private Date uraOdprtja;
    private Date uraZaprtja;
    private Float cenaPolnjenja;
    private Float hitrostPolnjenja;
    private Integer tipPrikljucka;

    public Integer getIdLastnik() {
        return idLastnik;
    }

    public void setIdLastnik(Integer idLastnik) {
        this.idLastnik = idLastnik;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public Date getUraOdprtja() {
        return uraOdprtja;
    }

    public void setUraOdprtja(Date uraOdprtja) {
        this.uraOdprtja = uraOdprtja;
    }

    public Date getUraZaprtja() {
        return uraZaprtja;
    }

    public void setUraZaprtja(Date uraZaprtja) {
        this.uraZaprtja = uraZaprtja;
    }

    public Float getCenaPolnjenja() {
        return cenaPolnjenja;
    }

    public void setCenaPolnjenja(Float cenaPolnjenja) {
        this.cenaPolnjenja = cenaPolnjenja;
    }

    public Float getHitrostPolnjenja() {
        return hitrostPolnjenja;
    }

    public void setHitrostPolnjenja(Float hitrostPolnjenja) {
        this.hitrostPolnjenja = hitrostPolnjenja;
    }

    public Integer getTipPrikljucka() {
        return tipPrikljucka;
    }

    public void setTipPrikljucka(Integer tipPrikljucka) {
        this.tipPrikljucka = tipPrikljucka;
    }
}
