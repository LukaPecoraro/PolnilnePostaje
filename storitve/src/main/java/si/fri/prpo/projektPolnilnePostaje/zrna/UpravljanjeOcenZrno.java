package si.fri.prpo.projektPolnilnePostaje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazOceneDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjeOceneDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.sql.Array;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeOcenZrno {

    @Inject
    private PolnilnicaZrno pz;

    @Inject
    private OceneZrno oz;

    @Inject
    private UporabnikZrno uz;

    private Logger log = Logger.getLogger(UpravljanjePolnilnicZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Inicializacija zrna " + UpravljanjePolnilnicZrno.class.getSimpleName());

        //init virov
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinicializacija zrna " + UpravljanjePolnilnicZrno.class.getSimpleName());

        //zapiranje virov
    }

    // NOTE: Lahko vrne null!!
    @Transactional
    public PrikazOceneDTO dodajOceno(UrejanjeOceneDTO dto) {
        log.info("se dela");
        Uporabnik uporabnik = uz.getUporabnikById(dto.getIdUporabnik());
        if (uporabnik == null) {
            log.info("CREATE Ocena: Uporabnik ne obstaja.");
            return null;
        }
        log.info("se dela");

        PolnilnaPostaja postaja = pz.getPostajaById(dto.getIdPostaja());
        if (postaja == null){
            log.info("CREATE Ocena: Postaja ne obstaja.");
            return null;
        }
        log.info("se dela");

        Ocena ocena = new Ocena();
        ocena.setOcena(dto.getOcena());
        ocena.setKomentar(dto.getKomentar());
        ocena.setUporabnik(uporabnik);
        // TODO uporabnik.getOcene().add(ocena)
        ocena.setPolnilnaPostaja(postaja);
        postaja.getOcene().add(ocena);

        Ocena o = oz.createOcena(ocena);

        return PrikazOceneDTO.toDto(o);
    }

    // NOTE: Je lahko null!
    @Transactional
    public PrikazOceneDTO posodobiOceno(UrejanjeOceneDTO dto, int id) {
        PolnilnaPostaja postaja = pz.getPostajaById(dto.getIdPostaja());
        if (postaja == null){
            log.info("CREATE Ocena: Postaja ne obstaja.");
            return null;
        }

        Uporabnik uporabnik = uz.getUporabnikById(dto.getIdUporabnik());
        if (uporabnik == null) {
            log.info("CREATE Ocena: Uporabnik ne obstaja.");
            return null;
        }

        Ocena ocena = new Ocena();
        ocena.setOcena(dto.getOcena());
        ocena.setKomentar(dto.getKomentar());
        ocena.setUporabnik(uporabnik);

        Ocena o = oz.updateOcena(id, ocena);

        return PrikazOceneDTO.toDto(o);
    }

    @Transactional
    public boolean izbrisiOceno(int id) {
        boolean uspeh = oz.deleteOcena(id);
        if (uspeh) {
            log.info("Izbris uspesen.");
        } else {
            log.info("Izbris neuspesen.");
        }
        return uspeh;
    }

    public PrikazOceneDTO vrniOceno(int id) {
        Ocena o = oz.getOcena(id);
        log.info("se dela");
        if (o == null) {
            return null;
        }
        return PrikazOceneDTO.toDto(o);
    }

    public List<PrikazOceneDTO> vrniOcene(QueryParameters query) {
        return oz.getOcene(query);
    }

    public Long vrniSteviloOcen(QueryParameters query) {
        return oz.getOceneCount(query);
    }
}
