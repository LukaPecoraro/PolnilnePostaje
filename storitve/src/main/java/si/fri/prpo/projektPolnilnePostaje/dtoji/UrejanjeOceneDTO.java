package si.fri.prpo.projektPolnilnePostaje.dtoji;

import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;

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

    public static UrejanjeOceneDTO toDto(Ocena o) {
        UrejanjeOceneDTO dto = new UrejanjeOceneDTO();
        dto.setIdUporabnik(o.getUporabnik().getIdUporabnik());
        dto.setIdPostaja(o.getPolnilnaPostaja().getIdPostaja());
        dto.setOcena(o.getOcena());
        dto.setKomentar(o.getKomentar());
        return dto;
    }

    @Override
    public String toString() {
        return String.format("Ocena {\n   idPostaja='%d'\n   idUporabnik='%d'\n   ocena='%d'\n   komentar='%s'\n}",
                this.getIdPostaja(), this.getIdUporabnik(), this.getOcena(), this.getKomentar());
    }
}
