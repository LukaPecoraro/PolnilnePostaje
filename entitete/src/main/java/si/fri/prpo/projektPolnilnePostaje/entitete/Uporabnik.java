package si.fri.prpo.projektPolnilnePostaje.entitete;

import jdk.jfr.Name;
import org.eclipse.persistence.annotations.PrimaryKey;

import javax.persistence.*;
import java.util.List;

@Entity(name = "uporabniki")
@Table
@Inheritance(strategy= InheritanceType.JOINED)
@DiscriminatorColumn(name="tip", discriminatorType=DiscriminatorType.STRING,length=20)
@DiscriminatorValue("uporabnik")
@NamedQueries(value = {
        @NamedQuery(name = "Uporabnik.getAll",
                query = "SELECT u FROM uporabniki u"),
        @NamedQuery(name = "Uporabnik.getById",
                query = "SELECT u FROM uporabniki u WHERE u.idUporabnik = :id"),
        @NamedQuery(name = "Uporabnik.getByUsername",
                query = "SELECT u FROM uporabniki u WHERE u.uporabniskoIme LIKE :ime"),
        @NamedQuery(name = "Uporabnik.getByEmail",
                query = "SELECT u FROM uporabniki u WHERE u.email LIKE :email"),
        @NamedQuery(name = "Uporabnik.update",
                query = "UPDATE uporabniki SET uporabniskoIme = :uporabniskoIme," +
                        "email = :email, kodiranoGeslo = :kodiranoGeslo WHERE idUporabnik = :id"),
        @NamedQuery(name = "Uporabnik.delete", query = "DELETE FROM uporabniki WHERE idUporabnik = :id")
})
public class Uporabnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUporabnik;
    private String uporabniskoIme;
    private String email;
    private String kodiranoGeslo;

    @OneToMany(mappedBy = "uporabnik")
    private List<Rezervacija> rezervacije;


    public Integer getIdUporabnik() {
        return idUporabnik;
    }

    public void setIdUporabnik(Integer idUporabnik) {
        this.idUporabnik = idUporabnik;
    }

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKodiranoGeslo() {
        return kodiranoGeslo;
    }

    public void setKodiranoGeslo(String kodiranoGeslo) {
        this.kodiranoGeslo = kodiranoGeslo;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

    @Override
    public String toString() {
        return String.format("Uporabnik %s (%s)",
                this.uporabniskoIme, this.email);
    }
}
