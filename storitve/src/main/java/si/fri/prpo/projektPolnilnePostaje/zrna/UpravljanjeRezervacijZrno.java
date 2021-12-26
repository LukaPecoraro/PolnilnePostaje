package si.fri.prpo.projektPolnilnePostaje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazRezervacijeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjeRezervacijeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    @Transactional
    public PrikazRezervacijeDTO dodajRezervacijo(UrejanjeRezervacijeDTO dto) {
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

        Rezervacija novaRezervacija = new Rezervacija();
        novaRezervacija.setUporabnik(uporabnik);
        novaRezervacija.setPolnilnaPostaja(postaja);
        novaRezervacija.setDatumRezervacije(dto.getDatumRezervacije());
        novaRezervacija.setUraZacetka(dto.getUraZacetka());
        novaRezervacija.setUraKonca(dto.getUraKonca());

        Rezervacija r = rz.createRezervacija(novaRezervacija);
        if (r != null) {
            uporabnik.getRezervacije().add(r);
            postaja.getRezervacije().add(r);
            return PrikazRezervacijeDTO.toDto(r);
        }
        return null;
    }

    public List<PrikazRezervacijeDTO> vrniRezervacije(QueryParameters query) {
        return rz.getRezervacije(query).stream()
                .map(PrikazRezervacijeDTO::toDto)
                .collect(Collectors.toList());
    }

    public PrikazRezervacijeDTO vrniRezervacijo(int idRezervacija){
        Rezervacija rezervacija = rz.getRezervacija(idRezervacija);
        if (rezervacija != null) {
            log.info("Rezervacija " + rezervacija.getIdRezervacija() + " najdena.");
            return PrikazRezervacijeDTO.toDto(rezervacija);
        } else {
            log.info("Rezervacija " + idRezervacija + " ni bila najdena.");
        }
        return null;
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


