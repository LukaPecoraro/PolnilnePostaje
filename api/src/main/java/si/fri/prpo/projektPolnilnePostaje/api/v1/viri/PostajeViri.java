package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;

import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.zrna.PolnilnicaZrno;


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
    private PolnilnicaZrno polnilnicaZrno;


    @GET
    public Response getPolnilnaPostajai(){
        List<PolnilnaPostaja> postaje = polnilnicaZrno.getPostaje();
        return Response.status(Response.Status.OK).entity(postaje).build();
    }

    @GET
    @Path("{idPolnilnaPostaja}")
    public Response getPolnilnaPostaja(@PathParam("idPolnilnaPostaja") Integer idPolnilnaPostaja) {
        PolnilnaPostaja postaja = polnilnicaZrno.getPostajaById(idPolnilnaPostaja);
        return postaja != null
                ? Response.ok(postaja).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addPolnilnaPostaja(PolnilnaPostaja postaja) {
        polnilnicaZrno.createPostaja(postaja);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{idPolnilnaPostaja}")
    public Response deletePolnilnaPostaja(@PathParam("idPolnilnaPostaja") Integer idPolnilnaPostaja) {
        polnilnicaZrno.deletePostaja(idPolnilnaPostaja);
        return Response.noContent().build();
    }


}
