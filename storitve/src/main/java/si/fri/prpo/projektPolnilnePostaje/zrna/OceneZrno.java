package si.fri.prpo.projektPolnilnePostaje.zrna;


import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;

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
public class OceneZrno {

    private Logger log = Logger.getLogger(OceneZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Inicializacija zrna " + OceneZrno.class.getSimpleName());

        //init virov
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinicializacija zrna " + OceneZrno.class.getSimpleName());

        //zapiranje virov
    }


    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;

    //Vrne vse ocene
    public List<Ocena> getOcene() {
        return this.em.createNamedQuery("Ocena.getAll", Ocena.class).getResultList();
    }

    //vrne eni oceno
    public Ocena getOcena(int idOcena){
        Ocena ocena = em.find(Ocena.class, idOcena);

        return ocena;
    }


    // se criteria API
    public List<Ocena> getOcenaiCriteriaAPI() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Ocena> q = cb.createQuery(Ocena.class);
        Root<Ocena> ocene = q.from(Ocena.class);
        q.select(ocene);

        TypedQuery<Ocena> query = em.createQuery(q);
        return query.getResultList();

    }

    @Transactional
    public Ocena createOcena(Ocena ocena){
        if (ocena != null){
            em.persist(ocena);
        }
        return ocena;
    }

    @Transactional
    public Ocena updateOcena(Ocena ocena, String komentar, int ocenaNum){

        ocena.setOcena(ocenaNum);
        ocena.setKomentar(komentar);
        em.merge(ocena);

        return ocena;
    }

    @Transactional
    public boolean deleteOcena(int idOcena){
        Ocena ocena = getOcena(idOcena);

        if (ocena != null){
            em.remove(ocena);
            return true;
        }
        return false;
    }


}
