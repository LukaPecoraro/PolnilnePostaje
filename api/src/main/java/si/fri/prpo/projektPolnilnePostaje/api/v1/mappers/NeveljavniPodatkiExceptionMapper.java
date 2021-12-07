package si.fri.prpo.projektPolnilnePostaje.api.v1.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavniPodatkiExceptionMapper implements
        ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napake\":\""+ e.getMessage() +"\"}")
                .build();
    }
}
