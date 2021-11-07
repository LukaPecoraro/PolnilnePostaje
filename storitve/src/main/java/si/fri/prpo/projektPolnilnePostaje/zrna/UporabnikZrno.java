package si.fri.prpo.projektPolnilnePostaje.zrna;


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
public class UporabnikZrno {

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Inicializacija zrna " + UporabnikZrno.class.getSimpleName());

        //init virov
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinicializacija zrna " + UporabnikZrno.class.getSimpleName());

        //zapiranje virov
    }


    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;

    //Vrne vse uporabnike
    public List<Uporabnik> getUporabniki() {
        return this.em.createNamedQuery("Uporabnik.getAll", Uporabnik.class).getResultList();
    }

    //vrne enega uporabnika
    public Uporabnik getUporabnik(int idUporabnik){
        Uporabnik uporabnik = em.find(Uporabnik.class, idUporabnik);

        return uporabnik;
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

    @Transactional
    public Uporabnik createUporabnik(Uporabnik uporabnik){
        if (uporabnik != null){
            em.persist(uporabnik);
        }
        return uporabnik;
    }

    @Transactional
    public Uporabnik updateUporabnik(Uporabnik uporabnik, String uporabniskoIme){

        //ummm karkol se kle ze updata
        uporabnik.setUporabniskoIme(uporabniskoIme);
        em.merge(uporabnik);

        return uporabnik;
    }

    @Transactional
    public boolean deleteUporabnik(int idUporabnik){
        Uporabnik uporabnik = getUporabnik(idUporabnik);

        if (uporabnik != null){
            em.remove(uporabnik);
            return true;
        }
        return false;
    }


}
