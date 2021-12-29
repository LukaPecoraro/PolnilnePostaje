package si.fri.prpo.projektPolnilnePostaje.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.projektPolnilnePostaje.dtoji.PrikazRezervacijeDTO;
import si.fri.prpo.projektPolnilnePostaje.dtoji.UrejanjeRezervacijeDTO;
import si.fri.prpo.projektPolnilnePostaje.entitete.Rezervacija;
import si.fri.prpo.projektPolnilnePostaje.zrna.UpravljanjeRezervacijZrno;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("rezervacije")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE, HEAD, OPTIONS")
public class RezervacijeViri {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UpravljanjeRezervacijZrno rz;

    @GET
    @PermitAll
    @Operation(summary = "Seznam rezervacij",
            description = "Vrne seznam rezervacij")
    @APIResponses({
            @APIResponse(description = "Seznam rezervacij",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Rezervacija.class, type = SchemaType.ARRAY)),
                    headers = @Header(name="X-Total-Count", description = "Število vrnjenih rezervacij"))
    })
    public Response getRezervacije() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<PrikazRezervacijeDTO> rezervacije = rz.vrniRezervacije(query);
        Long steviloRezervacij = rz.vrniSteviloRezervacij(query);
        return Response.ok(rezervacije)
                .header("X-Total-Count", steviloRezervacij)
                .build();
    }

    @GET
    @Operation(summary = "Dolocena rezervacija",
            description = "Vrni izbrano rezervacijo preko njenega identifikatorja")
    @APIResponses({
            @APIResponse(description = "Rezervacija",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Rezervacija.class))
            )
    })
    @Path("{id}")
    public Response getRezervacija(@PathParam("id") Integer id) {
        PrikazRezervacijeDTO r = rz.vrniRezervacijo(id);
        return r != null
                ? Response.ok(r).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Operation(summary = "Nova rezervacija",
            description = "Dodaj novo rezervacija")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Rezervacija uspešno dodana"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka")
    })
    public Response addRezervacija(UrejanjeRezervacijeDTO r) {
        PrikazRezervacijeDTO rezervacija = rz.dodajRezervacijo(r);
        return rezervacija != null
                ? Response.status(Response.Status.CREATED).entity(rezervacija).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Operation(summary = "Izbris rezervacije",
            description = "Izbriši izbrano rezervacijo")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Rezervacija uspešno izbrisana"),
            @APIResponse(responseCode = "405",
                    description = "Validacijska napaka")
    })
    @Path("{id}")
    public Response deleteRezervacija(@PathParam("id") Integer id) {
        boolean uspeh = rz.izbrisiRezervacijo(id);
        if (uspeh) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
