package si.fri.prpo.projektPolnilnePostaje.dtoji;

import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;

public class PrikazOceneDTO extends UrejanjeOceneDTO {
    Integer idOcena;

    public Integer getIdOcena() {
        return idOcena;
    }

    public void setIdOcena(Integer idOcena) {
        this.idOcena = idOcena;
    }

    public static PrikazOceneDTO toDto(Ocena o) {
        PrikazOceneDTO dto = new PrikazOceneDTO();
        dto.setIdOcena(o.getIdOcena());
        dto.setIdUporabnik(o.getUporabnik().getIdUporabnik());
        dto.setIdPostaja(o.getPolnilnaPostaja().getIdPostaja());
        dto.setOcena(o.getOcena());
        dto.setKomentar(o.getKomentar());
        return dto;
    }

    @Override
    public String toString() {
        return String.format("Ocena {\n   idOcena='%d'\n   idPostaja='%d'\n   idUporabnik='%d'\n   ocena='%d'\n   komentar='%s'\n}",
                this.getIdOcena(), this.getIdPostaja(), this.getIdUporabnik(), this.getOcena(), this.getKomentar());
    }
}
