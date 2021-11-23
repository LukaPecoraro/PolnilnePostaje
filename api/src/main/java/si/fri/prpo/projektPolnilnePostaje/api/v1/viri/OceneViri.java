package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;

import si.fri.prpo.projektPolnilnePostaje.zrna.OceneZrno;
import si.fri.prpo.projektPolnilnePostaje.zrna.UpravljanjePolnilnicZrno;

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

    // TODO
    @GET
    @Path("{id}")
    public Response getOcene(@PathParam("id") Integer id) {
        return null;
    }

    // TODO
    @POST
    @Path("")
    public Response postOcena() {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("{id}")
    public Response putOcena(@PathParam("id") Integer id) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOcena(@PathParam("id") Integer id) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
