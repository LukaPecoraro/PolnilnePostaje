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

    // TODO: Ce bo cas, dodaj varnostne prepreke za spammanje ocen
    @Transactional
    public PrikazOceneDTO dodajOceno(UrejanjeOceneDTO dto) {
        Uporabnik uporabnik = uz.getUporabnikById(dto.getIdUporabnik());
        if (uporabnik == null) {
            log.info("Uporabnik ne obstaja.");
            return null;
        }

        PolnilnaPostaja postaja = pz.getPostajaById(dto.getIdPostaja());
        if (postaja == null){
            log.info("Postaja ne obstaja.");
            return null;
        }
        Integer ocenaInt = dto.getOcena();
        if (ocenaInt == null) {
            log.info("Ocena ni bila podana.");
            return null;
        }
        String komentar = dto.getKomentar();
        if (komentar == null) {
            dto.setKomentar("");
        }

        Ocena ocena = new Ocena();
        ocena.setOcena(dto.getOcena());
        ocena.setKomentar(dto.getKomentar());
        ocena.setUporabnik(uporabnik);
        ocena.setPolnilnaPostaja(postaja);

        Ocena o = oz.createOcena(ocena);

        if (o != null) {
            postaja.getOcene().add(o);
            return PrikazOceneDTO.toDto(o);
        }
        return null;
    }

    @Transactional
    public PrikazOceneDTO posodobiOceno(UrejanjeOceneDTO dto, int id) {
        PolnilnaPostaja postaja = null;
        if (dto.getIdPostaja() != null) {
            postaja = pz.getPostajaById(dto.getIdPostaja());
            if (postaja == null){
                log.info("Postaja ne obstaja.");
                return null;
            }
        }

        Uporabnik uporabnik = null;
        if (dto.getIdUporabnik() != null) {
            uporabnik = uz.getUporabnikById(dto.getIdUporabnik());
            if (uporabnik == null) {
                log.info("Uporabnik ne obstaja.");
                return null;
            }
        }

        Ocena ocena = new Ocena();
        ocena.setOcena(dto.getOcena());
        ocena.setKomentar(dto.getKomentar());
        ocena.setUporabnik(uporabnik);
        ocena.setPolnilnaPostaja(postaja);

        Ocena o = oz.updateOcena(id, ocena);

        if (o != null) {
            return PrikazOceneDTO.toDto(o);
        }
        return null;
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
