package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;
import si.fri.prpo.projektPolnilnePostaje.zrna.UporabnikZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UporabnikiViri {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikZrno uporabnikZrno;


    @GET
    public Response getUporabniki(){
        List<Uporabnik> uporabniki = uporabnikZrno.getUporabniki();
        return Response.status(Response.Status.OK).entity(uporabniki).build();
    }

    @GET
    @Path("{idUporabnik}")
    public Response getUporabnik(@PathParam("idUporabnik") Integer idUporabnik) {
        Uporabnik uporabnik = uporabnikZrno.getUporabnikById(idUporabnik);
        return uporabnik != null
                ? Response.ok(uporabnik).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addUporabnik(Uporabnik uporabnik) {
        uporabnikZrno.createUporabnik(uporabnik);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{idUporabnik}")
    public Response deleteUporabnik(@PathParam("idUporabnik") Integer idUporabnik) {
        uporabnikZrno.deleteUporabnik(idUporabnik);
        return Response.noContent().build();
    }


}
