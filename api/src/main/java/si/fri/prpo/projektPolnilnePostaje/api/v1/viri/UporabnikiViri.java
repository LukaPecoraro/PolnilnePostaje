package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
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
    @Operation(summary = "Pridobi uporabnike",
            description = "Pridobi seznam uporabnikov z možnostjo paginacije in sortiranja.")
    @APIResponses({
            @APIResponse(description = "Seznam uporabnikov", responseCode = "200")
    })
    public Response getUporabniki(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        Long uporabnikiCount = uporabnikZrno.getUporabnikiCount(query);

        return Response
                .ok(uporabnikZrno.getUporabniki(query))
                .header("X-Total-Count", uporabnikiCount)
                .build();
    }

    @GET
    @Operation(summary = "Pridobi določenega uporabnika",
            description = "Pridobi izbranega uporabnika prek njegovega identifikatorja")
    @APIResponses({
            @APIResponse(description = "Uporabnik",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class)),
                    headers = {@Header(name = "X-Total-Count")})
    })
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
