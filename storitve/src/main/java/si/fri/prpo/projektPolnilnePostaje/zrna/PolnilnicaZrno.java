package si.fri.prpo.projektPolnilnePostaje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazOceneDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazPostajeDTO;
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
    public List<PrikazPostajeDTO> getPostaje(QueryParameters query) {
        List<PolnilnaPostaja> pol = JPAUtils.queryEntities(em, PolnilnaPostaja.class, query);
        log.info("Seznam dobljen.");
        List<PrikazPostajeDTO> dtoji = pol.stream().map(PrikazPostajeDTO::toDto).collect(Collectors.toList());
        log.info("Seznam konvertiran.");
        return dtoji;
    }

    //vrne eno postajo
    public PolnilnaPostaja getPostajaById(int idPostaja){
        PolnilnaPostaja postaja = em.find(PolnilnaPostaja.class, idPostaja);
        return postaja;
    }

    public Long getPostajeCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, PolnilnaPostaja.class, query);
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

    public List<PolnilnaPostaja> getPostajeByLokacija(String lokacija) {
        List<PolnilnaPostaja> postaje = em.createNamedQuery("PolnilnaPostaja.getByLokacija", PolnilnaPostaja.class)
                .setParameter("lokacija", lokacija)
                .getResultList();
        return postaje;
    }

    public List<PolnilnaPostaja> getPostajeByTipPrikljucka(String prikljucek) {
        List<PolnilnaPostaja> postaje = em.createNamedQuery("PolnilnaPostaja.getByPrikljucek", PolnilnaPostaja.class)
                .setParameter("prikljucek", prikljucek)
                .getResultList();
        return postaje;
    }

    @Transactional
    public PolnilnaPostaja createPostaja(PolnilnaPostaja postaja){
        log.info("Ustvarjam postajo...");
        if (postaja != null){
            em.persist(postaja);
        }
        return postaja;
    }

    // NOTE: Lahko vrne tudi null, ce postaje ni!!
    @Transactional
    public PolnilnaPostaja updatePostaja(int idPostaja, PolnilnaPostaja postaja){
        PolnilnaPostaja instanca = getPostajaById(idPostaja);
        if (instanca != null) {
            instanca.setLokacija(postaja.getLokacija());
            instanca.setUraOdprtja(postaja.getUraOdprtja());
            instanca.setUraZaprtja(postaja.getUraZaprtja());
            instanca.setTipPrikljucka(postaja.getTipPrikljucka());
            instanca.setHitrostPolnjenja(postaja.getHitrostPolnjenja());
            instanca.setCenaPolnjenja(postaja.getCenaPolnjenja());
            em.merge(instanca);
        }
        return instanca;
    }

    @Transactional
    public boolean deletePostaja(int idPostaja){
        PolnilnaPostaja postaja = getPostajaById(idPostaja);
        if (postaja != null) {
            em.remove(postaja);
            return true;
        }
        return false;
    }
}
