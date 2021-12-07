package si.fri.prpo.projektPolnilnePostaje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjeUporabnikaDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;
import si.fri.prpo.projektPolnilnePostaje.prestrezniki.ValidirajDtoje;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

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

    @ValidirajDtoje
    public Uporabnik dodajUporabnika(DodajanjeUporabnikaDTO dto) {
        Uporabnik u = new Uporabnik();

        u.setEmail(dto.getEmail());
        u.setUporabniskoIme(dto.getUporabniskoIme());
        // TODO: Pravilno kodiranje gesla itd.
        u.setKodiranoGeslo(dto.getGeslo());

        Uporabnik novUporabnik = uz.createUporabnik(u);
        return novUporabnik;
    }

    public Long vrniSteviloUporabnikov(QueryParameters query) {
        return uz.getUporabnikiCount(query);
    }

    public List<Uporabnik> vrniUporabnike(QueryParameters query) {
        return uz.getUporabniki(query);
    }


    public Uporabnik vrniUporabnikaPoId(Integer idUporabnik) {
        return uz.getUporabnikById(idUporabnik);
    }

    public boolean izbrisiUporabnika(Integer idUporabnik) {
        return uz.deleteUporabnik(idUporabnik);
    }
}
