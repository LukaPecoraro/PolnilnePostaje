package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazOceneDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjeOceneDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Ocena;
import si.fri.prpo.projektPolnilnePostaje.entitete.PolnilnaPostaja;
import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;
import si.fri.prpo.projektPolnilnePostaje.zrna.OceneZrno;
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
    private OceneZrno oceneZrno;

    @Inject
    private UpravljanjeOcenZrno upravljanjeOcenZrno;


    @GET
    @Operation(summary = "Dolocena ocena",
            description = "Vrni izbrano oceno preko njenega identifikatorja")
    @APIResponses({
            @APIResponse(description = "Ocena",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Ocena.class))
            )
    })
    @Path("{id}")
    public Response getOcena(@PathParam("id") Integer id) {
        PrikazOceneDTO ocena = upravljanjeOcenZrno.vrniOceno(id);
        return ocena != null
                ? Response.ok(ocena).build()
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
    public Response postOcena(UrejanjeOceneDTO ocenaDTO) {
        PrikazOceneDTO ocena = upravljanjeOcenZrno.dodajOceno(ocenaDTO);
        if (ocena != null) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Operation(summary = "Posodobitev ocene",
            description = "Posodobi izbrano oceno")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Ocena uspešno posodobljena"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka")
    })
    @Path("{id}")
    public Response putOcena(@PathParam("id") Integer id, UrejanjeOceneDTO ocenaDTO) {
        PrikazOceneDTO ocena = upravljanjeOcenZrno.posodobiOceno(ocenaDTO, id);
        if (ocena != null) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Operation(summary = "Izbris ocene",
            description = "Izbriši izbrano oceno")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Ocena uspešno izbrisana"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka")
    })
    @Path("{id}")
    public Response deleteOcena(@PathParam("id") Integer id) {
        boolean uspeh = upravljanjeOcenZrno.izbrisiOceno(id);
        if (uspeh) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response getOcene() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<PrikazOceneDTO> ocene = upravljanjeOcenZrno.vrniOcene(query);
        Long stOcen = upravljanjeOcenZrno.vrniSteviloOcen(query);
        return Response.ok(ocene)
                .header("X-Total-Count", stOcen)
                .build();
    }
}
