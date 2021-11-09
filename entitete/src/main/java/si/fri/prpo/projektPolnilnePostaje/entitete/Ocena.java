package si.fri.prpo.projektPolnilnePostaje.entitete;

import javax.persistence.*;

@NamedQueries(value = {
        @NamedQuery(name = "Ocena.getAll",
                query = "SELECT o FROM ocene o"),
        @NamedQuery(name = "Ocena.getById",
                query = "SELECT o FROM ocene o WHERE o.idOcena = :id"),
        @NamedQuery(name = "Ocena.getByPostaja",
                query = "SELECT o FROM ocene o WHERE o.polnilnaPostaja = :polnilnaPostaja"),
        @NamedQuery(name = "Ocena.getByUporabnik",
                query = "SELECT o FROM ocene o WHERE o.uporabnik = :uporabnik"),
        @NamedQuery(name = "Ocena.update",
                query = "UPDATE ocene SET ocena = :ocena, komentar = :komentar WHERE idOcena = :id"),
        @NamedQuery(name = "Ocena.delete",
                query = "DELETE FROM ocene WHERE idOcena = :id")
})
@Entity(name = "ocene")
public class Ocena {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOcena;

    @ManyToOne
    @JoinColumn(name = "idUporabnik")
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "idPostaja")
    private PolnilnaPostaja polnilnaPostaja;

    private Integer ocena;

    private String komentar;

    public Integer getIdOcena() {
        return idOcena;
    }

    public void setIdOcena(Integer idOcena) {
        this.idOcena = idOcena;
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

    @Override
    public String toString() {
        return "Ocena{" +
                "idOcena=" + idOcena +
                ", uporabnik=" + uporabnik +
                ", polnilnaPostaja=" + polnilnaPostaja +
                ", ocena=" + ocena +
                ", komentar='" + komentar + '\'' +
                '}';
    }
}
