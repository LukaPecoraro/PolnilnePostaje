package si.fri.prpo.projektPolnilnePostaje.zrna;


import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class UporabnikZrno {

    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;

    //Vrne vse uporabnike
    public List<Uporabnik> getUporabniki() {
        return this.em.createNamedQuery("Uporabnik.getAll", Uporabnik.class).getResultList();
    }

    // se criteria API
    public List<Uporabnik> getUporabnikiCriteriaAPI() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Uporabnik> q = cb.createQuery(Uporabnik.class);
        Root<Uporabnik> uporabniki = q.from(Uporabnik.class);
        q.select(uporabniki);

        TypedQuery<Uporabnik> query = em.createQuery(q);
        return query.getResultList();

    }


}
