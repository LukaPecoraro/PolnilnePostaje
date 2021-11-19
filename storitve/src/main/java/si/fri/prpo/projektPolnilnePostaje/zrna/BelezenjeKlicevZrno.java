package si.fri.prpo.projektPolnilnePostaje.zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ApplicationScoped
public class BelezenjeKlicevZrno {

    private HashMap<String, Integer> steviloKlicev;
    private Logger log = Logger.getLogger(LastnikZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Belezenje klicev inicializirano...");
        steviloKlicev = new HashMap<String, Integer>();
    }

    @PreDestroy
    private void destroy() {
        log.info("Belezenje klicev suspendirano. Stevec se bo resetiral.");
    }

    public int getSteviloKlicev(String imeMetode) {
        Integer kliciMetode = steviloKlicev.get(imeMetode);
        if (kliciMetode != null) {
            return kliciMetode;
        }
        return 1;
    }

    public void povecajStevecKlicev(String imeMetode) {
        Integer kliciMetode = steviloKlicev.get(imeMetode);
        if (kliciMetode != null) {
            kliciMetode += 1;
        } else {
            kliciMetode = 1;
        }
        steviloKlicev.put(imeMetode, kliciMetode);
        log.info("Stevilo klicev metode '" + imeMetode + "': " + kliciMetode);
    }
}
