package si.fri.prpo.projektPolnilnePostaje.entitete;

import jdk.jfr.Name;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "polnilnePostaje")
@NamedQueries(value = {
        @NamedQuery(name = "PolnilnaPostaja.getAll",
                query = "SELECT p FROM polnilnePostaje p"),
        @NamedQuery(name = "PolnilnaPostaja.getById",
                query = "SELECT p FROM polnilnePostaje p WHERE p.idPostaja = :id"),
        @NamedQuery(name = "PolnilnaPostaja.getByLokacija",
                query = "SELECT p FROM polnilnePostaje p WHERE p.lokacija LIKE :lokacija"),
        @NamedQuery(name = "PolnilnaPostaja.getByPrikljucek",
                query = "SELECT p FROM polnilnePostaje p WHERE p.tipPrikljucka = :prikljucek"),
        @NamedQuery(name = "PolnilnaPostaja.update",
                query = "UPDATE polnilnePostaje SET lokacija = :lokacija," +
                        "uraOdprtja = :uraOdprtja, uraZaprtja = :uraZaprtja," +
                        "cenaPolnjenja = :cenaPolnjenja, tipPrikljucka = :tipPrikljucka," +
                        "hitrostPolnjenja = :hitrostPolnjenja, lastnik = :lastnik " +
                        "WHERE idPostaja = :id"),
        @NamedQuery(name = "PolnilnaPostaja.delete",
                query = "DELETE FROM polnilnePostaje WHERE idPostaja = :id")
})
public class PolnilnaPostaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPostaja;
    private String lokacija;
    @Temporal(TemporalType.TIME)
    private Date uraOdprtja;

    @Temporal(TemporalType.TIME)
    private Date uraZaprtja;
    private Float cenaPolnjenja;
    private Integer tipPrikljucka;
    private Float hitrostPolnjenja;

    @OneToMany(mappedBy = "polnilnaPostaja")
    private Set<Ocena> ocene;

    @OneToMany(mappedBy = "polnilnaPostaja")
    private Set<Rezervacija> rezervacije;


    @ManyToOne
    @JoinColumn(name = "idUporabnik")
    private LastnikPostaje lastnik;

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

    public Integer getTipPrikljucka() {
        return tipPrikljucka;
    }

    public void setTipPrikljucka(Integer tipPrikljucka) {
        this.tipPrikljucka = tipPrikljucka;
    }

    public Float getHitrostPolnjenja() {
        return hitrostPolnjenja;
    }

    public void setHitrostPolnjenja(Float hitrostPolnjenja) {
        this.hitrostPolnjenja = hitrostPolnjenja;
    }

    public Set<Ocena> getOcene() {
        return ocene;
    }

    public LastnikPostaje getLastnik() {
        return lastnik;
    }

    public void setOcene(Set<Ocena> ocene) {
        this.ocene = ocene;
    }

    public Set<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(Set<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }
}
