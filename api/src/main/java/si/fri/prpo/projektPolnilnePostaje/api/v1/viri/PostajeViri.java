package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.projektPolnilnePostaje.dtoji.DodajanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;
import si.fri.prpo.projektPolnilnePostaje.zrna.OceneZrno;
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
    private UpravljanjePolnilnicZrno upz;

    @GET
    public Response getPolnilnaPostaje(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<PolnilnaPostaja> postaje = upz.vrniPostaje(query);
        Long stPostaj = upz.vrniSteviloPostaj(query);
        return Response.ok(postaje)
                .header("X-Total-Count", stPostaj)
                .build();
    }

    @GET
    @Path("{idPostaje}")
    public Response getPolnilnaPostaja(@PathParam("idPostaje") Integer idPostaje) {
        PolnilnaPostaja postaja = upz.vrniPostajoPoId(idPostaje);
        return postaja != null
                ? Response.ok(postaja).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addPolnilnaPostaja(DodajanjePostajeDTO postaja) {
        PolnilnaPostaja ps = upz.dodajPostajo(postaja);
        if (ps != null) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{idPostaje}")
    public Response changePolnilnaPostaja(@PathParam("idPostaje") Integer idPostaje, UrejanjePostajeDTO dto) {
        PolnilnaPostaja ps = upz.posodobiPostajo(dto, idPostaje);
        if (ps != null) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{idPostaje}")
    public Response deletePolnilnaPostaja(@PathParam("idPostaje") Integer idPostaje) {
        boolean uspeh = upz.izbrisiPostajo(idPostaje);
        if (uspeh) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{idPostaje}/ocene")
    public Response getOcenePostaje(@PathParam("idPostaje") Integer idPostaje) {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Ocena> ocene = upz.vrniOcene(idPostaje, query);
        if (ocene != null) {
            return Response.ok(ocene)
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{idPostaje}/rezervacije")
    public Response getRezervacijePostaje(@PathParam("idPostaje") Integer idPostaje) {
        List<Rezervacija> rezervacije = upz.vrniRezervacije(idPostaje);
        if (rezervacije != null) {
            return Response.status(Response.Status.OK).entity(rezervacije).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
