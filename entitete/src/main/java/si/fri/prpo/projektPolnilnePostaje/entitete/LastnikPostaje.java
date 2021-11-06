package si.fri.prpo.projektPolnilnePostaje.entitete;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "lastniki")
@Table
@DiscriminatorValue("lastnik")
@NamedQueries(value = {
        @NamedQuery(name = "LastnikPostaje.getAll", query = "SELECT l FROM lastniki l")
})
public class LastnikPostaje extends Uporabnik {
    private Integer telefonska;

    @OneToMany
    private Set<PolnilnaPostaja> postaje;

    public Set<PolnilnaPostaja> getPostaje() {
        return postaje;
    }

    public void setPostaje(Set<PolnilnaPostaja> postaje) {
        this.postaje = postaje;
    }

    public Integer getTelefonska() {
        return telefonska;
    }

    public void setTelefonska(Integer telefonska) {
        this.telefonska = telefonska;
    }
}
