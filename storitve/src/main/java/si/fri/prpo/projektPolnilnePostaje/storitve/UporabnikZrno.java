package si.fri.prpo.projektPolnilnePostaje.storitve;


import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class UporabnikZrno {

    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;

    //Vrne vse uporabnike
    public List<Uporabnik> getUporabniki() {
        return this.em.createNamedQuery("Uporabnik.getAll", Uporabnik.class).getResultList();
    }
}
