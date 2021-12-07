package si.fri.prpo.projektPolnilnePostaje.zrna;


import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class RezervacijeZrno {

    private Logger log = Logger.getLogger(RezervacijeZrno.class.getName());

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
    public List<Rezervacija> getRezervacije(QueryParameters query) {
        return JPAUtils.queryEntities(em, Rezervacija.class, query);
    }

    public List<Rezervacija> getRezervacijeByPostaja(PolnilnaPostaja ps) {
        return this.em.createNamedQuery("Rezervacija.getByPostaja", Rezervacija.class)
                .setParameter("polnilnaPostaja", ps)
                .getResultList();
    }

    //vrne eni oceno
    public Rezervacija getRezervacija(int idRezervacija){
        Rezervacija rezervacija = em.find(Rezervacija.class, idRezervacija);
        return rezervacija;
    }

    @Transactional
    public Rezervacija createRezervacija(Rezervacija rezervacija){
        if (rezervacija != null){
            List<Rezervacija> seznamIstoleznih = em.createNamedQuery("Rezervacija.getByPostajaAndTime", Rezervacija.class)
                    .setParameter("uraZacetka", rezervacija.getUraZacetka())
                    .setParameter("uraKonca", rezervacija.getUraZacetka())
                    .setParameter("datum", rezervacija.getDatumRezervacije())
                    .getResultList();
            if (seznamIstoleznih.isEmpty()) {
                em.persist(rezervacija);
            }
        }
        return null;
    }

    // NOTE: Lahko vrne null, ce rezervacije ni!!!
    // TODO: Preveri, da ni prekrivanj!!!
    @Transactional
    public Rezervacija updateRezervacija(int idRezervacija, Rezervacija rezervacija){
        Rezervacija instanca = getRezervacija(idRezervacija);

        if (instanca != null) {
            instanca.setDatumRezervacije(rezervacija.getDatumRezervacije());
            instanca.setPolnilnaPostaja(rezervacija.getPolnilnaPostaja());
            instanca.setUporabnik(rezervacija.getUporabnik());
            instanca.setUraZacetka(rezervacija.getUraZacetka());
            instanca.setUraKonca(rezervacija.getUraKonca());
            em.merge(instanca);
        }
        return instanca;
    }

    @Transactional
    public boolean deleteRezervacija(int idRezervacija){
        Rezervacija rezervacija = getRezervacija(idRezervacija);

        if (rezervacija != null){
            em.remove(rezervacija);
            return true;
        }
        return false;
    }

    public Long getRezervacijeCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Rezervacija.class, query);
    }
}
