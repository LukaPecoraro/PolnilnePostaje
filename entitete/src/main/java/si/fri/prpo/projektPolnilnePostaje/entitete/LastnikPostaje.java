package si.fri.prpo.projektPolnilnePostaje.entitete;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "lastniki")
@Table
@DiscriminatorValue("lastnik")
@NamedQueries(value = {
        @NamedQuery(name = "LastnikPostaje.getAll",
                query = "SELECT DISTINCT l FROM lastniki l INNER JOIN uporabniki u WHERE l.idUporabnik = u.idUporabnik"),
        @NamedQuery(name = "LastnikPostaje.getById",
                query = "SELECT DISTINCT l FROM lastniki l WHERE l.idUporabnik = :id"),
        @NamedQuery(name = "LastnikPostaje.getByTelefonska",
                query = "SELECT l FROM lastniki l INNER JOIN uporabniki u WHERE l.telefonska = :tel AND u.idUporabnik = l.idUporabnik"),
        @NamedQuery(name = "LastnikPostaje.getPolnilnice",
                query = "SELECT p FROM lastniki l JOIN l.postaje p WHERE l.idUporabnik = :id")
})
public class LastnikPostaje extends Uporabnik {
    private String telefonska;

    @OneToMany
    private Set<PolnilnaPostaja> postaje;

    public Set<PolnilnaPostaja> getPostaje() {
        return postaje;
    }

    public void setPostaje(Set<PolnilnaPostaja> postaje) {
        this.postaje = postaje;
    }

    public String getTelefonska() {
        return telefonska;
    }

    public void setTelefonska(String telefonska) {
        this.telefonska = telefonska;
    }
}
