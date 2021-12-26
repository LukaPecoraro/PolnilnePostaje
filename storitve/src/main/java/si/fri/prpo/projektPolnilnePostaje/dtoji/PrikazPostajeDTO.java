package si.fri.prpo.projektPolnilnePostaje.dtoji;

import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;

public class PrikazPostajeDTO extends UrejanjePostajeDTO {
    private Integer idPostaja;

    public Integer getIdPostaja() {
        return idPostaja;
    }

    public void setIdPostaja(Integer idPostaja) {
        this.idPostaja = idPostaja;
    }

    public static PrikazPostajeDTO toDto(PolnilnaPostaja postaja) {
        PrikazPostajeDTO dto = new PrikazPostajeDTO();
        dto.setIdPostaja(postaja.getIdPostaja());
        if (postaja.getLastnik() != null) {
            dto.setIdLastnik(postaja.getLastnik().getIdUporabnik());
        }
        dto.setCenaPolnjenja(postaja.getCenaPolnjenja());
        dto.setLokacija(postaja.getLokacija());
        dto.setTipPrikljucka(postaja.getTipPrikljucka());
        dto.setHitrostPolnjenja(postaja.getHitrostPolnjenja());
        dto.setUraOdprtja(postaja.getUraOdprtja());
        dto.setUraZaprtja(postaja.getUraZaprtja());
        return dto;
    }
}
