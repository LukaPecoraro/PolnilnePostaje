package si.fri.prpo.projektPolnilnePostaje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazPostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;
import si.fri.prpo.projektPolnilnePostaje.prestrezniki.ValidirajDtoje;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjePolnilnicZrno {

    @Inject
    private PolnilnicaZrno pz;

    @Inject
    private RezervacijeZrno rz;

    @Inject
    private UporabnikZrno uz;

    private Logger log = Logger.getLogger(UpravljanjePolnilnicZrno.class.getName());
    private UUID uid = UUID.randomUUID();

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

    @Transactional
    public PrikazPostajeDTO dodajPostajo(UrejanjePostajeDTO dto) {
        if (dto.getLokacija() == null) {
            log.info("Manjka lokacija.");
            return null;
        }
        if (dto.getCenaPolnjenja() == null) {
            log.info("Manjka cena polnjenja.");
            return null;
        }
        if (dto.getHitrostPolnjenja() == null) {
            log.info("Manjka hitrost polnjenja.");
            return null;
        }
        if (dto.getUraOdprtja() == null) {
            log.info("Manjka ura odprtja.");
            return null;
        }
        if (dto.getUraZaprtja() == null) {
            log.info("Manjka ura zaprtja.");
            return null;
        }

        Uporabnik uporabnik = uz.getUporabnikById(dto.getIdLastnik());
        if (uporabnik == null) {
            log.info("Uporabnik ne obstaja.");
            return null;
        }

        PolnilnaPostaja ps = new PolnilnaPostaja();
        ps.setLokacija(dto.getLokacija());
        ps.setUraOdprtja(dto.getUraOdprtja());
        ps.setUraZaprtja(dto.getUraZaprtja());
        ps.setCenaPolnjenja(dto.getCenaPolnjenja());
        ps.setHitrostPolnjenja(dto.getHitrostPolnjenja());
        ps.setTipPrikljucka(dto.getTipPrikljucka());
        ps.setLastnik(uporabnik);

        PolnilnaPostaja polnilnica = pz.createPostaja(ps);
        if (polnilnica != null) {
            return PrikazPostajeDTO.toDto(polnilnica);
        }
        return null;
    }

    @Transactional
    public PrikazPostajeDTO posodobiPostajo(UrejanjePostajeDTO dto, int id) {
        PolnilnaPostaja ps = pz.getPostajaById(id);

        if (ps == null) {
            log.info("UPDATE Postaja: Postaja ne obstaja.");
            return null;
        }

        PolnilnaPostaja novaPs = new PolnilnaPostaja();
        novaPs.setTipPrikljucka(dto.getTipPrikljucka());
        novaPs.setHitrostPolnjenja(dto.getHitrostPolnjenja());
        novaPs.setCenaPolnjenja(dto.getCenaPolnjenja());
        novaPs.setLokacija(dto.getLokacija());
        novaPs.setUraZaprtja(dto.getUraZaprtja());
        novaPs.setUraOdprtja(dto.getUraOdprtja());

        PolnilnaPostaja polnilnica = pz.updatePostaja(id, novaPs);
        if (polnilnica != null) {
            return PrikazPostajeDTO.toDto(polnilnica);
        }
        return null;
    }

    @Transactional
    public boolean izbrisiPostajo(int id) {
        boolean uspeh = pz.deletePostaja(id);
        if (uspeh) {
            log.info("Izbris uspesen.");
        } else {
            log.info("Izbris neuspesen.");
        }
        return uspeh;
    }

    /*public List<Rezervacija> vrniRezervacije(int idPostaje) {
        PolnilnaPostaja postaja = pz.getPostajaById(idPostaje);
        if (postaja != null) {
            return rz.getRezervacijeByPostaja(postaja);
        }
        return null;
    }*/

    public List<PrikazPostajeDTO> vrniPostaje(QueryParameters query) {
        log.info("Poizvedujem...");
        return pz.getPostaje(query);
    }

    public PrikazPostajeDTO vrniPostajo(Integer id) {
        if (id == null) {
            log.info("Manjka id");
            return null;
        }

        PolnilnaPostaja postaja = pz.getPostajaById(id);
        if (postaja == null) {
            log.info("Postaja ne obstaja.");
            return null;
        }

        return PrikazPostajeDTO.toDto(postaja);
    }

    /*public UUID izpisiUUID() {
        log.info("Zrno je obsega @ApplicationScope in ima UUID: " + uid);
        return uid;
    }*/

    public Long vrniSteviloPostaj(QueryParameters query) {
        return pz.getPostajeCount(query);
    }
}
