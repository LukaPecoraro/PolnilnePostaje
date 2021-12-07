package si.fri.prpo.projektPolnilnePostaje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjeRezervacijeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PoizvedbaPoPostajiDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.management.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class UpravljanjeRezervacijZrno {
    @Inject
    private PolnilnicaZrno pz;

    @Inject
    private UporabnikZrno uz;

    @Inject
    private RezervacijeZrno rz;

    private Logger log = Logger.getLogger(UpravljanjeRezervacijZrno.class.getName());
    private UUID uid = UUID.randomUUID();

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

    public List<Rezervacija> vrniRezervacije(QueryParameters query) {
        return rz.getRezervacije(query);
    }

    public Rezervacija vrniRezervacijo(int idRezervacija){
        Rezervacija rezervacija = rz.getRezervacija(idRezervacija);
        if (rezervacija != null) {
            log.info("Rezervacija " + rezervacija.getIdRezervacija() + " najdena.");
        } else {
            log.info("Rezervacija " + idRezervacija + " ni bila najdena.");
        }
        return rezervacija;
    }

    @Transactional
    public boolean izbrisiRezervacijo(int idRezervacija) {
        boolean uspeh = rz.deleteRezervacija(idRezervacija);
        if (uspeh) {
            log.info("Rezervacija " + idRezervacija + " izbrisana.");
        } else {
            log.info("Rezervacija ni bila najdena.");
        }
        return uspeh;
    }

    public UUID izpisiUUID() {
        log.info("Zrno je obsega @RequestScoped in ima UUID: " + uid);
        return uid;
    }

    public Long vrniSteviloRezervacij(QueryParameters query) {
        return rz.getRezervacijeCount(query);
    }
}


