package si.fri.prpo.projektPolnilnePostaje.entitete;

import javax.persistence.*;
import java.util.Date;

@NamedQueries(value = {
        @NamedQuery(name = "Rezervacija.getAll",
                query = "SELECT r FROM rezervacije r"),
        @NamedQuery(name = "Rezervacija.getById",
                query = "SELECT r FROM rezervacije r WHERE r.idRezervacija = :id"),
        @NamedQuery(name = "Rezervacija.getByPostaja",
                query = "SELECT r FROM rezervacije r WHERE r.polnilnaPostaja = :polnilnaPostaja"),
        @NamedQuery(name = "Rezervacija.getByUporabnik",
                query = "SELECT r FROM rezervacije r WHERE r.uporabnik = :uporabnik"),
        @NamedQuery(name = "Rezervacija.delete",
                query = "DELETE FROM rezervacije WHERE idRezervacija = :id"),
        @NamedQuery(name = "Rezervacija.getByPostajaAndTime",
                query = "SELECT r FROM rezervacije r WHERE ((r.uraZacetka BETWEEN :uraZacetka AND :uraKonca)" +
                        "OR (r.uraKonca BETWEEN :uraZacetka AND :uraKonca)) AND r.datumRezervacije = :datum")
})
@Entity(name = "rezervacije")
public class Rezervacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRezervacija;

    @ManyToOne
    @JoinColumn(name = "idUporabnik")
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "idPostaja")
    private PolnilnaPostaja polnilnaPostaja;


    @Temporal(TemporalType.DATE)
    private Date datumRezervacije;

    @Temporal(TemporalType.TIME)
    private Date uraZacetka;

    @Temporal(TemporalType.TIME)
    private Date uraKonca;


    public Integer getIdRezervacija() {
        return idRezervacija;
    }

    public void setIdRezervacija(Integer idRezervacija) {
        this.idRezervacija = idRezervacija;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public PolnilnaPostaja getPolnilnaPostaja() {
        return polnilnaPostaja;
    }

    public void setPolnilnaPostaja(PolnilnaPostaja polnilnaPostaja) {
        this.polnilnaPostaja = polnilnaPostaja;
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

    @Override
    public String toString() {
        return "Rezervacija{" +
                "idRezervacija=" + idRezervacija +
                ", uporabnik=" + uporabnik +
                ", polnilnaPostaja=" + polnilnaPostaja +
                ", datumRezervacije=" + datumRezervacije +
                ", uraZacetka=" + uraZacetka +
                ", uraKonca=" + uraKonca +
                '}';
    }
}
