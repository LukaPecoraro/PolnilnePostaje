package si.fri.prpo.projektPolnilnePostaje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;
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
    private OceneZrno oz;

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

    // NOTE: Lahko vrne null!!
    @Transactional
    @ValidirajDtoje
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
    public PolnilnaPostaja posodobiPostajo(UrejanjePostajeDTO dto, int id) {
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

        return pz.updatePostaja(id, novaPs);
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

    public List<Ocena> vrniOcene(int idPostaje, QueryParameters query) {
        PolnilnaPostaja postaja = this.vrniPostajoPoId(idPostaje);
        if (postaja != null) {
            return oz.getOceneZaPostajo(postaja, query);
        }
        return null;
    }

    public Long vrniSteviloOcenZaPostajo(QueryParameters query) {
        return oz.getOceneZaPostajoCount(query);
    }


    public List<Rezervacija> vrniRezervacije(int idPostaje) {
        PolnilnaPostaja postaja = this.vrniPostajoPoId(idPostaje);
        if (postaja != null) {
            return rz.getRezervacijeByPostaja(postaja);
        }
        return null;
    }



    public List<PolnilnaPostaja> vrniPostaje(QueryParameters query) {
        return pz.getPostaje(query);
    }

    // NOTE: Lahko vrne null!!
    public PolnilnaPostaja vrniPostajoPoId(Integer id) {
        if (id == null) {
            log.info("GET Postaja(ID): Manjka argument idPostaja.");
            return null;
        }

        PolnilnaPostaja postaja = pz.getPostajaById(id);
        if (postaja == null) {
            log.info("GET Postaja(ID): Postaja ne obstaja.");
        }
        return postaja;
    }

    public UUID izpisiUUID() {
        log.info("Zrno je obsega @ApplicationScope in ima UUID: " + uid);
        return uid;
    }

    public Long vrniSteviloPostaj(QueryParameters query) {
        return pz.getPostajeCount(query);
    }
}
