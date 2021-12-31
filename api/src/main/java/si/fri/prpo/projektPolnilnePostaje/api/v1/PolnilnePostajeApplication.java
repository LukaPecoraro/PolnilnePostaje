package si.fri.prpo.projektPolnilnePostaje.api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("v1")
@DeclareRoles({"user", "admin"})
@OpenAPIDefinition(info = @Info(title = "Polnilne postaje API", version = "v1",
        contact = @Contact()), servers = @Server(url = "http://localhost:8080"),
        security = @SecurityRequirement(name = "openid-connect"))
public class PolnilnePostajeApplication extends Application {

}
