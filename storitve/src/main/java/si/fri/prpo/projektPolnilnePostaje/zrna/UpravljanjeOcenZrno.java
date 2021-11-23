package si.fri.prpo.projektPolnilnePostaje.zrna;

import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjeOceneDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
    public Ocena dodajOceno(UrejanjeOceneDTO dto) {
        Uporabnik uporabnik = uz.getUporabnikById(dto.getIdUporabnik());
        if (uporabnik == null) {
            log.info("CREATE Ocena: Uporabnik ne obstaja.");
            return null;
        }

        PolnilnaPostaja postaja = pz.getPostajaById(dto.getIdPostaja());
        if (postaja == null){
            log.info("CREATE Ocena: Postaja ne obstaja.");
            return null;
        }

        Ocena ocena = new Ocena();
        ocena.setOcena(dto.getOcena());
        ocena.setKomentar(dto.getKomentar());
        ocena.setUporabnik(uporabnik);
        // TODO uporabnik.getOcene().add(ocena)
        ocena.setPolnilnaPostaja(postaja);
        postaja.getOcene().add(ocena);

        return oz.createOcena(ocena);
    }

    // NOTE: Je lahko null!
    @Transactional
    public Ocena posodobiOceno(UrejanjeOceneDTO dto, int id) {
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

        return oz.updateOcena(id, ocena);
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


}
