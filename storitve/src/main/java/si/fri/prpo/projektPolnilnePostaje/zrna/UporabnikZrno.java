package si.fri.prpo.projektPolnilnePostaje.zrna;


import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;
import si.fri.prpo.projektPolnilnePostaje.prestrezniki.BeleziKlice;

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
    @BeleziKlice
    public List<Uporabnik> getUporabniki() {
        return this.em.createNamedQuery("Uporabnik.getAll", Uporabnik.class).getResultList();
    }

    // se medota da s queryParameters pridobi, za paging pa sorting
    public List<Uporabnik> getUporabniki(QueryParameters query) {
        return JPAUtils.queryEntities(em, Uporabnik.class, query);
    }

    public Long getUporabnikiCount(QueryParameters query){
        return JPAUtils.queryEntitiesCount(em, Uporabnik.class, query);
    }

    //vrne enega uporabnika
    @BeleziKlice
    public Uporabnik getUporabnikById(int idUporabnik) {
        Uporabnik uporabnik = em.find(Uporabnik.class, idUporabnik);
        return uporabnik;
    }

    @BeleziKlice
    public List<Uporabnik> getUporabnikiByIme(String uporabniskoIme) {
        List<Uporabnik> uporabniki = em.createNamedQuery("Uporabnik.getByUsername", Uporabnik.class)
                .setParameter("ime", uporabniskoIme)
                .getResultList();
        return uporabniki;
    }

    @BeleziKlice
    public List<Uporabnik> getUporabnikiByEmail(String email) {
        List<Uporabnik> uporabniki = em.createNamedQuery("Uporabnik.getByEmail", Uporabnik.class)
                .setParameter("email", email)
                .getResultList();
        return uporabniki;
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

    // NOTE: Lahko vrne tudi null, ce uporabnika ni!!!
    @Transactional
    public Uporabnik updateUporabnik(int idUporabnik, Uporabnik uporabnik){
        Uporabnik instanca = getUporabnikById(idUporabnik);
        if (instanca != null) {
            instanca.setUporabniskoIme(uporabnik.getUporabniskoIme());
            instanca.setEmail(uporabnik.getEmail());
            instanca.setKodiranoGeslo(uporabnik.getKodiranoGeslo());
            em.merge(uporabnik);
        }
        return instanca;
    }

    @Transactional
    public boolean deleteUporabnik(int idUporabnik){
        Uporabnik uporabnik = getUporabnikById(idUporabnik);
        if (uporabnik != null){
            em.remove(uporabnik);
            return true;
        }
        return false;
    }
}
