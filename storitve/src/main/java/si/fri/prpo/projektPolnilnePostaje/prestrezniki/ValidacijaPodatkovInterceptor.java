package si.fri.prpo.projektPolnilnePostaje.prestrezniki;

import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjeUporabnikaDTO;
import si.fri.prpo.projektPolnilnePostaje.izjeme.NeveljavenUporabnikDtoIzjema;
import si.fri.prpo.projektPolnilnePostaje.zrna.PrestrezanjeNapakZrno;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@ValidirajUporabnike
public class ValidacijaPodatkovInterceptor {

    @Inject
    private PrestrezanjeNapakZrno pnz;

    private Logger log = Logger.getLogger(ValidacijaPodatkovInterceptor.class.getName());

    @AroundInvoke
    public Object validacijaUporabnikov(InvocationContext context) throws Exception {
        if (context.getParameters().length == 1 &&
                context.getParameters()[0] instanceof DodajanjeUporabnikaDTO) {
            DodajanjeUporabnikaDTO uporabnik = (DodajanjeUporabnikaDTO) context.getParameters()[0];
            pnz.validirajDodajanjeUporabnikaDTO(uporabnik);
        }
        return context.proceed();
    }
}
