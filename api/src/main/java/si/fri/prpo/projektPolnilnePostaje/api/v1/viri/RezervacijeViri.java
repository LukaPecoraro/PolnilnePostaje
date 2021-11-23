package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;

import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjeRezervacijeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;
import si.fri.prpo.projektPolnilnePostaje.zrna.RezervacijeZrno;
import si.fri.prpo.projektPolnilnePostaje.zrna.UpravljanjeRezervacijZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("rezervacije")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class RezervacijeViri {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UpravljanjeRezervacijZrno rezervacijeZrno;

    @GET
    public Response getRezervacije() {
        List<Rezervacija> rezervacije = rezervacijeZrno.vrniRezervacije();
        return Response.status(Response.Status.OK).entity(rezervacije).build();
    }

    @GET
    @Path("{id}")
    public Response getRezervacija(@PathParam("id") Integer id) {
        Rezervacija r = rezervacijeZrno.vrniRezervacijo(id);
        return r != null
                ? Response.ok(r).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addRezervacija(DodajanjeRezervacijeDTO r) {
        rezervacijeZrno.dodajRezervacijo(r);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteRezervacija(@PathParam("id") Integer id) {
        boolean uspeh = rezervacijeZrno.izbrisiRezervacijo(id);
        if (uspeh) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
