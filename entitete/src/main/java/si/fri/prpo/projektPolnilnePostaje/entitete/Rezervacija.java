package si.fri.prpo.projektPolnilnePostaje.entitete;

import javax.persistence.*;
import java.sql.Date;

@NamedQueries(value = {
        @NamedQuery(name = "Rezervacija.getAll", query = "SELECT r FROM Rezervacija r"),
        @NamedQuery(name = "Rezervacija.getById", query = "SELECT r FROM Rezervacija r WHERE r.idRezervacija = :id"),
        @NamedQuery(name = "Rezervacija.getByPostaja", query = "SELECT r FROM Rezervacija r WHERE r.polnilnaPostaja = :polnilnaPostaja"),
        @NamedQuery(name = "Rezervacija.getByUporabnik", query = "SELECT r FROM Rezervacija r WHERE r.uporabnik = :uporabnik"),

        @NamedQuery(name = "Rezervacija.delete", query = "DELETE FROM Rezervacija WHERE idRezervacija = :id")
})

@Entity
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

}