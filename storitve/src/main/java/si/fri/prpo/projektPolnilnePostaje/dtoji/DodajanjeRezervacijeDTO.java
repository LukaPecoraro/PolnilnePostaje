package si.fri.prpo.projektPolnilnePostaje.dtoji;

import java.util.Date;

public class DodajanjeRezervacijeDTO {
    private Integer idPostaja;
    private Integer idUporabnik;
    private Date datumRezervacije;
    private Date uraZacetka;
    private Date uraKonca;

    public Integer getIdPostaja() {
        return idPostaja;
    }

    public void setIdPostaja(Integer idPostaja) {
        this.idPostaja = idPostaja;
    }

    public Integer getIdUporabnik() {
        return idUporabnik;
    }

    public void setIdUporabnik(Integer idUporabnik) {
        this.idUporabnik = idUporabnik;
    }

    public Date getDatumRezervacije() {
        return datumRezervacije;
    }

    public void setDatumRezervacije(Date datumRezervacije) {
        this.datumRezervacije = datumRezervacije;
    }

    public Date getUraZacetka() {
        return uraZacetka;
    }

    public void setUraZacetka(Date uraZacetka) {
        this.uraZacetka = uraZacetka;
    }

    public Date getUraKonca() {
        return uraKonca;
    }

    public void setUraKonca(Date uraKonca) {
        this.uraKonca = uraKonca;
    }
}
