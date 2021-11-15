package si.fri.prpo.projektPolnilnePostaje.zrna;

import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjeRezervacijeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PoizvedbaPoPostajiDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

public class UpravljanjeRezervacijZrno {
    @Inject
    private PolnilnicaZrno pz;

    @Inject
    private UporabnikZrno uz;

    @Inject
    private RezervacijeZrno rz;

    private Logger log = Logger.getLogger(UpravljanjeRezervacijZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Inicializacija zrna " + UpravljanjeRezervacijZrno.class.getSimpleName());

        //init virov
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinicializacija zrna " + UpravljanjeRezervacijZrno.class.getSimpleName());

        //zapiranje virov
    }

    // NOTE: Lahko vrne null!!
    @Transactional
    public Rezervacija dodajRezervacijo(DodajanjeRezervacijeDTO dto) {
        Uporabnik uporabnik = uz.getUporabnikById(dto.getIdUporabnik());
        if (uporabnik == null) {
            log.info("CREATE Rezervacija: Uporabnik ne obstaja.");
            return null;
        }

        PolnilnaPostaja postaja = pz.getPostajaById(dto.getIdPostaja());
        if (postaja == null){
            log.info("CREATE Rezervacija: Postaja ne obstaja.");
            return null;
        }

        Rezervacija novaRezervacija = new Rezervacija();
        //dodas v obe smeri da je pravilna relacija
        novaRezervacija.setUporabnik(uporabnik);
        uporabnik.getRezervacije().add(novaRezervacija);
        novaRezervacija.setPolnilnaPostaja(postaja);
        postaja.getRezervacije().add(novaRezervacija);

        novaRezervacija.setDatumRezervacije(dto.getDatumRezervacije());
        novaRezervacija.setUraZacetka(dto.getUraZacetka());
        novaRezervacija.setUraKonca(dto.getUraKonca());

        return rz.createRezervacija(novaRezervacija);
    }

}


