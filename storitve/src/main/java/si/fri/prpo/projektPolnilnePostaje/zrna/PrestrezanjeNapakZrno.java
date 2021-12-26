package si.fri.prpo.projektPolnilnePostaje.zrna;

import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjeUporabnikaDTO;
import si.fri.prpo.projektPolnilnePostaje.izjeme.NeveljavenUporabnikDtoIzjema;
import si.fri.prpo.projektPolnilnePostaje.izjeme.NeveljavnaPostajaDtoIzjema;

import javax.enterprise.context.RequestScoped;
import java.util.logging.Logger;

@RequestScoped
public class PrestrezanjeNapakZrno {
    private Logger log = Logger.getLogger(PrestrezanjeNapakZrno.class.getName());

    public void validirajDodajanjeUporabnikaDTO(UrejanjeUporabnikaDTO uporabnik) {
        if (uporabnik.getUporabniskoIme() == null || uporabnik.getUporabniskoIme().isEmpty() ||
                uporabnik.getEmail() == null || uporabnik.getEmail().isEmpty() ||
                uporabnik.getGeslo() == null || uporabnik.getGeslo().isEmpty()) {
            String msg = "Manjka uporabniško ime, e-poštni naslov in/ali geslo!";
            throw new NeveljavenUporabnikDtoIzjema(msg);
        }
    }

    public void validirajDodajanjePostajeDTO(UrejanjePostajeDTO postaja) {
        if (postaja.getLokacija() == null || postaja.getLokacija().isEmpty() ||
                postaja.getIdLastnik() == null || postaja.getUraOdprtja() == null ||
                postaja.getUraZaprtja() == null) {
            log.info(postaja.getLokacija());
            log.info(postaja.getIdLastnik().toString());
            log.info(postaja.getUraOdprtja().toString());
            log.info(postaja.getUraZaprtja().toString());
            String msg = "Manjka lokacija, lastnik in/ali uri obratovanja.";
            throw new NeveljavnaPostajaDtoIzjema(msg);
        }
    }
}
