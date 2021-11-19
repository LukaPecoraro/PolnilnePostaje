package si.fri.prpo.projektPolnilnePostaje.prestrezniki;

import si.fri.prpo.projektPolnilnePostaje.zrna.BelezenjeKlicevZrno;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    @Inject
    BelezenjeKlicevZrno kz;

    @AroundInvoke
    public Object beleziKlic(InvocationContext ctx) throws Exception {
        String imeMetode = ctx.getMethod().getDeclaringClass().getSimpleName() + "." + ctx.getMethod().getName();
        kz.povecajStevecKlicev(imeMetode);
        return ctx.proceed();
    }
}
