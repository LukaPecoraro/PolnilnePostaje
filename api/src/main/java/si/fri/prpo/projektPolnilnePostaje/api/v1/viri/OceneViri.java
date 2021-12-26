package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazOceneDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjeOceneDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;
import si.fri.prpo.projektPolnilnePostaje.zrna.UpravljanjeOcenZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("ocene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class OceneViri {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UpravljanjeOcenZrno uoz;


    @GET
    @Operation(summary = "Dolocena ocena",
            description = "Vrni izbrano oceno preko njenega identifikatorja")
    @APIResponses({
            @APIResponse(description = "Ocena",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PrikazOceneDTO.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Ocena ni bila najdena")
    })
    @Path("{id}")
    public Response getOcena(@PathParam("id") Integer id) {
        PrikazOceneDTO ocena = uoz.vrniOceno(id);
        return ocena != null
                ? Response.status(Response.Status.OK).entity(ocena).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Operation(summary = "Nova ocena",
            description = "Dodaj novo oceno")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Ocena uspešno dodana"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka")
    })
    public Response postOcena(UrejanjeOceneDTO dto) {
        PrikazOceneDTO ocena = uoz.dodajOceno(dto);
        return ocena != null
                ? Response.status(Response.Status.CREATED).entity(ocena).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Operation(summary = "Posodobitev ocene",
            description = "Posodobi izbrano oceno")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Ocena uspešno posodobljena"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka"),
            @APIResponse(responseCode = "404",
                    description = "Ocene ni bilo mogoce najti")
    })
    @Path("{id}")
    public Response putOcena(@PathParam("id") Integer id, UrejanjeOceneDTO ocenaDTO) {
        PrikazOceneDTO ocena = uoz.posodobiOceno(ocenaDTO, id);
        return ocena != null
                ? Response.status(Response.Status.OK).entity(ocena).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Operation(summary = "Izbris ocene",
            description = "Izbriši izbrano oceno")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Ocena uspešno izbrisana"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka"),
            @APIResponse(responseCode = "404",
                    description = "Ocene ni bilo mogoce najti")
    })
    @Path("{id}")
    public Response deleteOcena(@PathParam("id") Integer id) {
        boolean uspeh = uoz.izbrisiOceno(id);
        return uspeh
                ? Response.status(Response.Status.OK).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Operation(summary = "Pridobi ocene", description = "Vrne vse ocene.")
    @APIResponses({
            @APIResponse(description = "Seznam ocen", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PrikazOceneDTO.class, type = SchemaType.ARRAY)))
    })
    public Response getOcene() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<PrikazOceneDTO> ocene = uoz.vrniOcene(query);
        Long stOcen = uoz.vrniSteviloOcen(query);
        return Response.ok(ocene)
                .header("X-Total-Count", stOcen)
                .build();
    }
}
