package si.fri.prpo.projektPolnilnePostaje.dtoji;

import java.util.Date;

public class UrejanjeOceneDTO {
    private Integer idPostaja;
    private Integer idUporabnik;
    private Integer ocena;
    private String komentar;

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

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
}
