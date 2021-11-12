package si.fri.prpo.projektPolnilnePostaje.zrna;


import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;

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
import java.util.Date;
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
    public List<Rezervacija> getOcene() {
        return this.em.createNamedQuery("Rezervacija.getAll", Rezervacija.class).getResultList();
    }

    //vrne eni oceno
    public Rezervacija getRezervacija(int idRezervacija){
        Rezervacija rezervacija = em.find(Rezervacija.class, idRezervacija);
        return rezervacija;
    }

    @Transactional
    public Rezervacija createRezervacija(Rezervacija rezervacija){
        if (rezervacija != null){
            em.persist(rezervacija);
        }
        return rezervacija;
    }

    // NOTE: Lahko vrne null, ce rezervacije ni!!!
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


}