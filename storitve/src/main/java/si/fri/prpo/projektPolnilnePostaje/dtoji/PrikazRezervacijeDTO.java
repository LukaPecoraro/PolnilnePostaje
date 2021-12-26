package si.fri.prpo.projektPolnilnePostaje.dtoji;

import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;

public class PrikazRezervacijeDTO extends UrejanjeRezervacijeDTO {
    private Integer idRezervacija;

    public Integer getIdRezervacija() {
        return idRezervacija;
    }

    public void setIdRezervacija(Integer idRezervacija) {
        this.idRezervacija = idRezervacija;
    }

    public static PrikazRezervacijeDTO toDto(Rezervacija r) {
        PrikazRezervacijeDTO dto = new PrikazRezervacijeDTO();
        dto.setIdRezervacija(r.getIdRezervacija());
        dto.setIdUporabnik(r.getUporabnik().getIdUporabnik());
        dto.setIdPostaja(r.getPolnilnaPostaja().getIdPostaja());
        dto.setDatumRezervacije(r.getDatumRezervacije());
        dto.setUraZacetka(r.getUraZacetka());
        dto.setUraKonca(r.getUraKonca());
        return dto;
    }
}
