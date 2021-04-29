package securityservices.userinterface.web.restadapters;

import java.io.File;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Clientsfile")
public class ClientsfileResource {
    @Context
    private UriInfo context;

    public ClientsfileResource() {
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getJsonFile() {
        File file = new File("C:\\Users\\david\\Desktop\\M3\\Jsons\\client.json");
        if (file.exists()) {
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") //optional
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("FileResource Not Found")
                    .build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getJsonXml() {
        File file = new File("C:\\Users\\david\\Desktop\\M3\\Jsons\\client.xml");
        if (file.exists()) {
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") //optional
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("FileResource Not Found")
                    .build();
        }
    }
}
