package si.fri.prpo.projektPolnilnePostaje.zrna;

import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PolnilnicaZrno {
    private Logger log = Logger.getLogger(PolnilnicaZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Inicializacija zrna " + PolnilnicaZrno.class.getSimpleName());

        //init virov
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinicializacija zrna " + PolnilnicaZrno.class.getSimpleName());

        //zapiranje virov
    }


    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;

    //Vrne vse postaje
    public List<PolnilnaPostaja> getPostaje() {
        return this.em.createNamedQuery("PolnilnaPostaja.getAll", PolnilnaPostaja.class).getResultList();
    }

    //vrne eno postajo
    public PolnilnaPostaja getPostaja(int idPostaja){
        PolnilnaPostaja postaja = em.find(PolnilnaPostaja.class, idPostaja);
        return postaja;
    }


    // se criteria API
    public List<PolnilnaPostaja> getPostajeCriteriaAPI() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<PolnilnaPostaja> q = cb.createQuery(PolnilnaPostaja.class);
        Root<PolnilnaPostaja> postaje = q.from(PolnilnaPostaja.class);
        q.select(postaje);

        TypedQuery<PolnilnaPostaja> query = em.createQuery(q);
        return query.getResultList();
    }

    @Transactional
    public PolnilnaPostaja createPostaja(PolnilnaPostaja postaja){
        if (postaja != null){
            em.persist(postaja);
        }
        return postaja;
    }

    @Transactional
    public PolnilnaPostaja updatePostaja(PolnilnaPostaja postaja){
        return null;
    }

    @Transactional
    public boolean deletePostaja(int idPostaja){
        return false;
    }
}
