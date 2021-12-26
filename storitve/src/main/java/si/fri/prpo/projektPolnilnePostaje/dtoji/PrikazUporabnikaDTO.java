package si.fri.prpo.projektPolnilnePostaje.dtoji;

import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

public class PrikazUporabnikaDTO extends UrejanjeUporabnikaDTO {
    private Integer idUporabnik;

    public Integer getIdUporabnik() {
        return idUporabnik;
    }

    public void setIdUporabnik(Integer idUporabnik) {
        this.idUporabnik = idUporabnik;
    }

    public static PrikazUporabnikaDTO toDto(Uporabnik u) {
        PrikazUporabnikaDTO dto = new PrikazUporabnikaDTO();
        dto.setIdUporabnik(u.getIdUporabnik());
        dto.setEmail(u.getEmail());
        dto.setUporabniskoIme(u.getUporabniskoIme());
        dto.setGeslo(u.getKodiranoGeslo());
        return dto;
    }
}
