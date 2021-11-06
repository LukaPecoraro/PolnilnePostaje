package si.fri.prpo.projektPolnilnePostaje.entitete;

import javax.persistence.*;

@NamedQueries(value = {
        @NamedQuery(name = "Ocena.getAll", query = "SELECT o FROM ocene o"),
        @NamedQuery(name = "Ocena.getById", query = "SELECT o FROM ocene o WHERE o.idOcena = :id"),
        @NamedQuery(name = "Ocena.getByPostaja", query = "SELECT o FROM ocene o WHERE o.polnilnaPostaja = :polnilnaPostaja"),
        @NamedQuery(name = "Ocena.getByUporabnik", query = "SELECT o FROM ocene o WHERE o.uporabnik = :uporabnik"),
        @NamedQuery(name = "Ocena.update", query = "UPDATE ocene SET ocena = :ocena, komentar = :komentar WHERE idOcena = :id"),
        @NamedQuery(name = "Ocena.delete", query = "DELETE FROM ocene WHERE idOcena = :id")
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




}
