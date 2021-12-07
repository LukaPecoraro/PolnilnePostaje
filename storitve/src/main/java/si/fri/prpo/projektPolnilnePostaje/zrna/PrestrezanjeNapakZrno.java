package si.fri.prpo.projektPolnilnePostaje.zrna;

import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjeUporabnikaDTO;
import si.fri.prpo.projektPolnilnePostaje.izjeme.NeveljavenUporabnikDtoIzjema;

import javax.enterprise.context.RequestScoped;
import java.util.logging.Logger;

@RequestScoped
public class PrestrezanjeNapakZrno {
    private Logger log = Logger.getLogger(PrestrezanjeNapakZrno.class.getName());

    public void validirajDodajanjeUporabnikaDTO(DodajanjeUporabnikaDTO uporabnik) {
        if (uporabnik.getUporabniskoIme() == null || uporabnik.getUporabniskoIme().isEmpty()
                || uporabnik.getEmail() == null || uporabnik.getEmail().isEmpty()) {
            String msg = "Podani podatki so pomanjkljivi!";
            throw new NeveljavenUporabnikDtoIzjema(msg);
        }
    }
}
