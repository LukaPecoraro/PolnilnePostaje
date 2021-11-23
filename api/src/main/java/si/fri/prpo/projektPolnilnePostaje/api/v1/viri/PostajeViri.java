package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;

import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;
import si.fri.prpo.projektPolnilnePostaje.zrna.OceneZrno;
import si.fri.prpo.projektPolnilnePostaje.zrna.PolnilnicaZrno;
import si.fri.prpo.projektPolnilnePostaje.zrna.RezervacijeZrno;
import si.fri.prpo.projektPolnilnePostaje.zrna.UpravljanjePolnilnicZrno;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("postaje")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PostajeViri {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UpravljanjePolnilnicZrno polnilnicaZrno;

    @Inject
    private OceneZrno oceneZrno;

    @GET
    public Response getPolnilnaPostaje(){
        List<PolnilnaPostaja> postaje = polnilnicaZrno.vrniPostaje();
        return Response.status(Response.Status.OK).entity(postaje).build();
    }

    @GET
    @Path("{idPolnilnaPostaja}")
    public Response getPolnilnaPostaja(@PathParam("idPolnilnaPostaja") Integer idPolnilnaPostaja) {
        PolnilnaPostaja postaja = polnilnicaZrno.vrniPostajoPoId(idPolnilnaPostaja);
        return postaja != null
                ? Response.ok(postaja).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addPolnilnaPostaja(DodajanjePostajeDTO postaja) {
        PolnilnaPostaja ps = polnilnicaZrno.dodajPostajo(postaja);
        if (ps != null) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{idPolnilnaPostaja}")
    public Response deletePolnilnaPostaja(@PathParam("idPolnilnaPostaja") Integer idPolnilnaPostaja) {
        boolean uspeh = polnilnicaZrno.izbrisiPostajo(idPolnilnaPostaja);
        if (uspeh) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{idPostaje}/ocene")
    public Response getOcenePostaje(@PathParam("idPostaje") Integer idPostaje) {
        List<Ocena> ocene = polnilnicaZrno.vrniOcene(idPostaje);
        return Response.status(Response.Status.OK).entity(ocene).build();
    }
}
