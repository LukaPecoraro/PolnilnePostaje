package entitete;

import jdk.jfr.Name;
import org.eclipse.persistence.annotations.PrimaryKey;

import javax.persistence.*;

@Entity(name = "uporabniki")
@NamedQueries(value = {
        @NamedQuery(name = "Uporabnik.getAll",
                query = "SELECT u FROM uporabniki u"),
        @NamedQuery(name = "Uporabnik.getById",
                query = "SELECT u FROM uporabniki u WHERE u.idUporabnik = :id"),
        @NamedQuery(name = "Uporabnik.update",
                query = "UPDATE uporabniki SET uporabniskoIme = :uporabniskoIme," +
                        "email = :email, kodiranoGeslo = :kodiranoGeslo WHERE idUporabnik = :id"),
        @NamedQuery(name = "Uporabnik.delete", query = "DELETE FROM uporabniki WHERE idUporabnik = :id")
        // TODO: getRezervacije in getOcene
})
public class Uporabnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUporabnik;
    private String uporabniskoIme;
    private String email;
    private String kodiranoGeslo;

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

    @Override
    public String toString() {
        return String.format("Uporabnik %s (%s)",
                this.uporabniskoIme, this.email);
    }
}
