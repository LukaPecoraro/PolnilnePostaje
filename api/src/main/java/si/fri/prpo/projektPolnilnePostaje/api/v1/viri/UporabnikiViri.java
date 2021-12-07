package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
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
    @Operation(summary = "Seznam uporabnikov",
            description = "Vrne seznam uporabnikov")
    @APIResponses({
            @APIResponse(description = "Seznam uporabnikov",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY)),
                    headers = @Header(name="X-Total-Count", description = "Število vrnjenih uporabnikov"))
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
    @Operation(summary = "Določeni uporabnik",
            description = "Vrni izbranega uporabnika prek njegovega identifikatorja")
    @APIResponses({
            @APIResponse(description = "Uporabnik",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            )
    })
    @Path("{idUporabnik}")
    public Response getUporabnik(@Parameter(
            description = "ID uporabnika",
            required = true) @PathParam("idUporabnik") Integer idUporabnik) {
        Uporabnik uporabnik = uporabnikZrno.getUporabnikById(idUporabnik);
        return uporabnik != null
                ? Response.ok(uporabnik).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Operation(summary = "Nov uporabnik",
            description = "Dodaj novega uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Uporabnik uspešno dodan"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka")
    })
    public Response addUporabnik(@RequestBody(
            description = "ODT objekt za novega uporabnika",
            required = true,
            content = @Content(schema = @Schema(implementation = Uporabnik.class)))
                                             Uporabnik uporabnik) {
        Uporabnik u = uporabnikZrno.createUporabnik(uporabnik);
        return Response
                .status(Response.Status.CREATED)
                .entity(u)
                .build();
    }

    @DELETE
    @Operation(summary = "Izbris uporabnika",
            description = "Izbriši izbranega uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Uporabnik uspešno izbrisan"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka")
    })
    @Path("{idUporabnik}")
    public Response deleteUporabnik(@Parameter(
            description = "ID uporabnika",
            required = true)
                                        @PathParam("idUporabnik") Integer idUporabnik) {
        uporabnikZrno.deleteUporabnik(idUporabnik);
        return Response.status(Response.Status.OK).build();
    }
}
