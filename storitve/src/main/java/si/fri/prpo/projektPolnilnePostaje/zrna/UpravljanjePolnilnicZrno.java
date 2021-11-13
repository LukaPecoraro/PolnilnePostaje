package si.fri.prpo.projektPolnilnePostaje.zrna;

import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PoizvedbaPoPostajiDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjePolnilnicZrno {

    @Inject
    private PolnilnicaZrno pz;

    @Inject
    private LastnikZrno lz;

    @Inject
    private UporabnikZrno uz;

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

    // NOTE: Lahko vrne null!!
    @Transactional
    public PolnilnaPostaja dodajPostajo(DodajanjePostajeDTO dto) {
        Uporabnik uporabnik = uz.getUporabnikById(dto.getIdLastnik());
        if (uporabnik == null) {
            log.info("CREATE Postaja: Uporabnik ne obstaja.");
            return null;
        }

        PolnilnaPostaja ps = new PolnilnaPostaja();
        ps.setLokacija(dto.getLokacija());
        ps.setUraOdprtja(dto.getUraOdprtja());
        ps.setUraZaprtja(dto.getUraZaprtja());
        ps.setCenaPolnjenja(dto.getCenaPolnjenja());
        ps.setHitrostPolnjenja(dto.getHitrostPolnjenja());
        ps.setTipPrikljucka(dto.getTipPrikljucka());

        // TODO: Dodaj lastnika!!!

        return pz.createPostaja(ps);
    }

    // NOTE: Je lahko null!
    @Transactional
    public PolnilnaPostaja posodobiPostajo(UrejanjePostajeDTO dto) {
        PolnilnaPostaja ps = pz.getPostajaById(dto.getIdPostaja());

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

        // TODO: Se lastnika!

        return pz.updatePostaja(dto.getIdPostaja(), novaPs);
    }

    public List<PolnilnaPostaja> vrniPostaje() {
        return pz.getPostaje();
    }

    // NOTE: Lahko vrne null!!
    public PolnilnaPostaja vrniPostajoPoId(PoizvedbaPoPostajiDTO dto) {
        Integer idPostaja = dto.getIdPostaja();
        if (idPostaja == null) {
            log.info("GET Postaja(ID): Manjka argument idPostaja.");
            return null;
        }

        PolnilnaPostaja postaja = pz.getPostajaById(idPostaja);
        if (postaja == null) {
            log.info("GET Postaja(ID): Postaja ne obstaja.");
        }
        return postaja;
    }
}
