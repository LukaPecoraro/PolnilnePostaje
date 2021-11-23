package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;

import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjeOceneDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;
import si.fri.prpo.projektPolnilnePostaje.zrna.OceneZrno;
import si.fri.prpo.projektPolnilnePostaje.zrna.UpravljanjeOcenZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("ocene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class OceneViri {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private OceneZrno oceneZrno;

    @Inject
    private UpravljanjeOcenZrno upravljanjeOcenZrno;


    @GET
    @Path("{id}")
    public Response getOcene(@PathParam("id") Integer id) {
        Ocena ocena = oceneZrno.getOcena(id);
        return ocena != null
                ? Response.ok(ocena).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("")
    public Response postOcena(UrejanjeOceneDTO ocenaDTO) {
        Ocena ocena = upravljanjeOcenZrno.dodajOceno(ocenaDTO);
        if (ocena != null) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{id}")
    public Response putOcena(@PathParam("id") Integer id, UrejanjeOceneDTO ocenaDTO) {
        Ocena ocena = upravljanjeOcenZrno.posodobiOceno(ocenaDTO, id);
        if (ocena != null) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOcena(@PathParam("id") Integer id) {
        boolean uspeh = upravljanjeOcenZrno.izbrisiOceno(id);
        if (uspeh) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
