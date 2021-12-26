package si.fri.prpo.projektPolnilnePostaje.zrna;


import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazOceneDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    //Vrne vse ocene za postajo
    public List<PrikazOceneDTO> getOcene(QueryParameters query) {
        return JPAUtils.queryEntities(em, Ocena.class, query)
                .stream().map(PrikazOceneDTO::toDto)
                .collect(Collectors.toList());
    }

    public Long getOceneCount(QueryParameters query){
        return JPAUtils.queryEntitiesCount(em, Ocena.class, query);
    }

    //vrne eno oceno
    public Ocena getOcena(int idOcena){
        return em.find(Ocena.class, idOcena);
    }


    // se criteria API
    public List<Ocena> getOcenaCriteriaAPI() {
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
    public Ocena updateOcena(int idOcena, Ocena ocena){
        Ocena instanca = getOcena(idOcena);
        if (instanca != null) {
            if (ocena.getOcena() != null) instanca.setOcena(ocena.getOcena());
            if (ocena.getKomentar() != null) instanca.setKomentar(ocena.getKomentar());
            if (ocena.getUporabnik() != null) instanca.setUporabnik(ocena.getUporabnik());
            if (ocena.getPolnilnaPostaja() != null) instanca.setPolnilnaPostaja(ocena.getPolnilnaPostaja());
            em.merge(instanca);
        }
        return instanca;
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
