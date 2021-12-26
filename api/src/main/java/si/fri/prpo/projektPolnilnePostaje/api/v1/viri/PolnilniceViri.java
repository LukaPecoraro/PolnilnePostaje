package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjePostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazPostajeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
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
public class PolnilniceViri {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UpravljanjePolnilnicZrno upz;

    @GET
    @Operation(summary = "Seznam postaj",
            description = "Vrne seznam postaj")
    @APIResponses({
            @APIResponse(description = "Seznam postaj",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PrikazPostajeDTO.class, type = SchemaType.ARRAY)),
                    headers = @Header(name="X-Total-Count", description = "Število vrnjenih polnilnih postaj"))
    })
    public Response getPolnilnePostaje(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<PrikazPostajeDTO> postaje = upz.vrniPostaje(query);
        Long stPostaj = upz.vrniSteviloPostaj(query);
        return Response.ok(postaje)
                .header("X-Total-Count", stPostaj)
                .build();
    }

    @GET
    @Operation(summary = "Dolocena postajo",
            description = "Vrni izbrano postajo preko njenega identifikatorja")
    @APIResponses({
            @APIResponse(description = "Postaja",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PolnilnaPostaja.class))
            )
    })
    @Path("{idPostaje}")
    public Response getPolnilnaPostaja(@PathParam("idPostaje") Integer idPostaje) {
        PrikazPostajeDTO postaja = upz.vrniPostajo(idPostaje);
        return postaja != null
                ? Response.ok(postaja).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Operation(summary = "Nova postaja",
            description = "Dodaj novo postajo")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Postaja uspešno dodana"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka")
    })
    public Response addPolnilnaPostaja(UrejanjePostajeDTO postaja) {
        PrikazPostajeDTO ps = upz.dodajPostajo(postaja);
        if (ps != null) {
            return Response.status(Response.Status.CREATED).entity(ps).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Operation(summary = "Posodobitev postaje",
            description = "Posodobi izbrano postajo")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Postaja uspešno posodobljena"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka")
    })
    @Path("{idPostaje}")
    public Response changePolnilnaPostaja(@PathParam("idPostaje") Integer idPostaje, UrejanjePostajeDTO dto) {
        PrikazPostajeDTO ps = upz.posodobiPostajo(dto, idPostaje);
        if (ps != null) {
            return Response.status(Response.Status.OK).entity(ps).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Operation(summary = "Izbris postaje",
            description = "Izbriši izbrano postajo")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Postaja uspešno izbrisana"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka")
    })
    @Path("{idPostaje}")
    public Response deletePolnilnaPostaja(@PathParam("idPostaje") Integer idPostaje) {
        boolean uspeh = upz.izbrisiPostajo(idPostaje);
        if (uspeh) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /*@GET
    @Operation(summary = "Seznam ocen za postajo",
            description = "Vrne seznam ocen za postajo")
    @APIResponses({
            @APIResponse(description = "Seznam ocen",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Ocena.class, type = SchemaType.ARRAY)),
                    headers = @Header(name="X-Total-Count", description = "Število vrnjenih ocen"))
    })
    @Path("{idPostaje}/ocene")
    public Response getOcenePostaje(@PathParam("idPostaje") Integer idPostaje) {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        Long oceneCount = upz.vrniSteviloOcenZaPostajo(query);;

        return Response
                .ok(upz.vrniOcene(idPostaje, query))
                .header("X-Total-Count", oceneCount)
                .build();
    }*/

    /*@GET
    @Operation(summary = "Seznam rezervacij za postajo",
            description = "Vrne seznam rezervacij za postajo")
    @APIResponses({
            @APIResponse(description = "Seznam rezervacij",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Rezervacija.class, type = SchemaType.ARRAY)))
    })
    @Path("{idPostaje}/rezervacije")
    public Response getRezervacijePostaje(@PathParam("idPostaje") Integer idPostaje) {

        List<Rezervacija> rezervacije = upz.vrniRezervacije(idPostaje);
        if (rezervacije != null) {
            return Response.status(Response.Status.OK).entity(rezervacije).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }*/
}
