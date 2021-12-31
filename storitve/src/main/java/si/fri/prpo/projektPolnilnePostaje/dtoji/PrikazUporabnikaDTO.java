package si.fri.prpo.projektPolnilnePostaje.dtoji;

import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import java.util.ArrayList;
import java.util.List;

public class PrikazUporabnikaDTO extends UrejanjeUporabnikaDTO {
    private Integer idUporabnik;
    private List<Integer> rezervacije;

    public List<Integer> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Integer> rezervacije) {
        this.rezervacije = rezervacije;
    }

    public Integer getIdUporabnik() {
        return idUporabnik;
    }

    public void setIdUporabnik(Integer idUporabnik) {
        this.idUporabnik = idUporabnik;
    }

    public static PrikazUporabnikaDTO toDto(Uporabnik u) {
        PrikazUporabnikaDTO dto = new PrikazUporabnikaDTO();
        if (u.getIdUporabnik() != null) {
            dto.setIdUporabnik(u.getIdUporabnik());
        } else {
            dto.setIdUporabnik(-1);
        }
        dto.setEmail(u.getEmail());
        dto.setUporabniskoIme(u.getUporabniskoIme());
        dto.setGeslo(u.getKodiranoGeslo());
        List<Integer> list = new ArrayList<>();
        if (u.getRezervacije() != null) {
            for (Rezervacija r : u.getRezervacije()) {
                list.add(r.getIdRezervacija());
            }
        }
        dto.setRezervacije(list);
        return dto;
    }

    @Override
    public String toString() {
        return String.format("Uporabnik %s (%d), email %s",
                this.getUporabniskoIme(), this.getIdUporabnik(), this.getEmail());
    }
}
