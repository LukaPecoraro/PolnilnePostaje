package si.fri.prpo.projektPolnilnePostaje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazUporabnikaDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjeUporabnikaDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;
import si.fri.prpo.projektPolnilnePostaje.prestrezniki.ValidirajDtoje;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class UpravljanjeUporabnikovZrno {

    @Inject
    private UporabnikZrno uz;

    private Logger log = Logger.getLogger(UpravljanjeUporabnikovZrno.class.getName());

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
    public PrikazUporabnikaDTO dodajUporabnika(UrejanjeUporabnikaDTO dto) {
        if (dto.getEmail() == null || dto.getUporabniskoIme() == null) {
            log.info("Podatki nepopolni.");
            return null;
        };

        Uporabnik u = new Uporabnik();
        u.setEmail(dto.getEmail());
        u.setUporabniskoIme(dto.getUporabniskoIme());
        // TODO: Pravilno kodiranje gesla itd.
        u.setKodiranoGeslo(dto.getGeslo());

        Uporabnik novUporabnik = uz.createUporabnik(u);
        if (novUporabnik != null) {
            PrikazUporabnikaDTO nov = PrikazUporabnikaDTO.toDto(novUporabnik);
            return nov;
        }
        log.info("Uporabnika ni bilo moc ustvariti.");
        return null;
    }

    public Long vrniSteviloUporabnikov(QueryParameters query) {
        return uz.getUporabnikiCount(query);
    }

    public List<PrikazUporabnikaDTO> vrniUporabnike(QueryParameters query) {
        return uz.getUporabniki(query)
                .stream().map(PrikazUporabnikaDTO::toDto)
                .collect(Collectors.toList());
    }


    public PrikazUporabnikaDTO vrniUporabnikaPoId(Integer idUporabnik) {
        Uporabnik u = uz.getUporabnikById(idUporabnik);
        if (u != null) {
            return PrikazUporabnikaDTO.toDto(u);
        }
        return null;
    }

    @Transactional
    public boolean izbrisiUporabnika(Integer idUporabnik) {
        return uz.deleteUporabnik(idUporabnik);
    }
}
