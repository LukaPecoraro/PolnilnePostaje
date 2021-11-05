package entitete;

import javax.persistence.*;

@NamedQueries(value = {
        @NamedQuery(name = "Ocena.getAll", query = "SELECT o FROM Ocena o"),
        @NamedQuery(name = "Ocena.getById", query = "SELECT o FROM Ocena o WHERE o.idOcena = :id"),
        @NamedQuery(name = "Ocena.getByPostaja", query = "SELECT o FROM Ocena o WHERE o.polnilnaPostaja = :polnilnaPostaja"),
        @NamedQuery(name = "Ocena.getByUporabnik", query = "SELECT o FROM Ocena o WHERE o.uporabnik = :uporabnik"),
        @NamedQuery(name = "Ocena.update", query = "UPDATE Ocena SET ocena = :ocena, komentar = :komentar WHERE idOcena = :id"),
        @NamedQuery(name = "Ocena.delete", query = "DELETE FROM Ocena WHERE idOcena = :id")
})

@Entity
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
