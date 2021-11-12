package si.fri.prpo.projektPolnilnePostaje.zrna;


import si.fri.prpo.projektPolnilnePostaje.entitete.LastnikPostaje;
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
public class LastnikZrno {

    private Logger log = Logger.getLogger(LastnikZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Inicializacija zrna " + LastnikZrno.class.getSimpleName());

        //init virov
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinicializacija zrna " + LastnikZrno.class.getSimpleName());

        //zapiranje virov
    }


    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;

    //Vrne vse lastnike
    public List<LastnikPostaje> getLastniki() {
        return this.em.createNamedQuery("LastnikPostaje.getAll", LastnikPostaje.class).getResultList();
    }

    //vrne enega lastnika
    public LastnikPostaje getLastnikById(int idUporabnik) {
        LastnikPostaje uporabnik = em.find(LastnikPostaje.class, idUporabnik);
        return uporabnik;
    }

    @Transactional
    public LastnikPostaje createLastnik(LastnikPostaje lastnik){
        if (lastnik != null){
            em.persist(lastnik);
        }
        return lastnik;
    }

    @Transactional
    public boolean deleteLastnik(int idUporabnik){
        Uporabnik uporabnik = getLastnikById(idUporabnik);
        if (uporabnik != null){
            em.remove(uporabnik);
            return true;
        }
        return false;
    }
}
