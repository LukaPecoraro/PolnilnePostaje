package si.fri.prpo.projektPolnilnePostaje.prestrezniki;

import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjeUporabnikaDTO;
import si.fri.prpo.projektPolnilnePostaje.zrna.PrestrezanjeNapakZrno;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@ValidirajDtoje
public class ValidacijaPodatkovInterceptor {

    @Inject
    private PrestrezanjeNapakZrno pnz;

    private Logger log = Logger.getLogger(ValidacijaPodatkovInterceptor.class.getName());

    @AroundInvoke
    public Object validacijaUporabnikov(InvocationContext context) throws Exception {
        if (context.getParameters().length == 1) {
            Object o = context.getParameters()[0];
            if (o instanceof DodajanjeUporabnikaDTO) {
                pnz.validirajDodajanjeUporabnikaDTO((DodajanjeUporabnikaDTO) o);
            }
            if (o instanceof UrejanjePostajeDTO) {
                pnz.validirajDodajanjePostajeDTO((UrejanjePostajeDTO) o);
            }
        }
        return context.proceed();
    }
}
