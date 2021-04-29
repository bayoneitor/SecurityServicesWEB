package securityservices.userinterface.web.restadapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import securityservices.core.component.client.appservices.JsonClientSerializer;
import securityservices.core.component.client.appservices.UseCaseClientController;
import securityservices.core.component.client.appservices.serializers.AddClientController;
import securityservices.core.component.client.appservices.serializers.ClientListSerializer;
import securityservices.core.component.client.appservices.serializers.ClientSerializer;
import securityservices.core.component.client.appservices.serializers.DeleteClientController;
import securityservices.core.component.client.appservices.serializers.GetAllClientsController;
import securityservices.core.component.client.appservices.serializers.GetClientController;
import securityservices.core.component.client.appservices.serializers.UpdateClientController;
import securityservices.core.component.client.domain.model.Client;
import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.core.component.client.domain.services.ClientMapper;
import securityservices.shared.responses.ResultRequest;

@Path("Clients")
public class ClientsResource {

    @Context
    private UriInfo context;
    private UseCaseClientController useCaseController = new UseCaseClientController();

    public ClientsResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        //Utilitza 2 controladors de AppServices, un per obtenir els clients i un altre per serialitzar-los
        GetAllClientsController allClientsController = new GetAllClientsController();
        ResultRequest<List<ClientDTO>> request = allClientsController.getAll();
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(ClientListSerializer.makeJsonResult(request.getValue()),
                MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllClientsXML() {
        //Utilitza 2 controladors de AppServices, un per obtenir els clients i un altre per serialitzar-los
        GetAllClientsController allClientsController = new GetAllClientsController();
        ResultRequest<List<ClientDTO>> request = allClientsController.getAll();
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(ClientListSerializer.makeXMLResult(request.getValue()),
                MediaType.APPLICATION_XML).build();
    }

    @GET
    @Path("/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientByID(@PathParam("clientId") String id) {
        GetClientController clientController = new GetClientController();
        ResultRequest<ClientDTO> request = clientController.getByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(ClientSerializer.makeJsonResult(request.getValue()),
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/{clientId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getClientByIDXML(@PathParam("clientId") String id) {
        GetClientController clientController = new GetClientController();
        ResultRequest<ClientDTO> request = clientController.getByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(ClientSerializer.makeXMLResult(request.getValue()),
                MediaType.APPLICATION_XML).build();
    }
    
    @DELETE
    @Path("/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClientByID(@PathParam("clientId") String id) {
        DeleteClientController deleteClientController = new DeleteClientController();
        ResultRequest<String> request = deleteClientController.deleteByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(),
                MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(InputStream bodyParams) {
        String body = "", line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(bodyParams));
            while ((line = reader.readLine()) != null) {
                body += line.trim();
            }
        } catch (IOException ex) {
            return Response.ok("Error Reading...", MediaType.APPLICATION_JSON).build();
        }

        JsonClientSerializer JCSerializer = new JsonClientSerializer();
        ResultRequest<ClientDTO> clientDTORR = JCSerializer.unserialize(body);
        ClientDTO cdto;
        AddClientController addClientController;

        if (clientDTORR.failed()) {
            return Response.status(400, clientDTORR.getError()).build();
        }

        cdto = clientDTORR.getValue();
        ResultRequest<Client> clientRR = Client.getInstance(cdto.getCode(), cdto.getName(), cdto.getAddress(), cdto.getPhone(),
                cdto.getEmail(), cdto.isCompany(), cdto.getBirthday(), cdto.getNumEquipments(), cdto.getPassword());

        if (clientRR.failed()) {
            return Response.status(400, clientRR.getError()).build();
        }

        cdto = ClientMapper.dtoFromComponent(clientRR.getValue());
        addClientController = new AddClientController();

        ResultRequest<String> request = addClientController.add(cdto);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(), MediaType.APPLICATION_JSON).build();
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateClient(InputStream bodyParams) {
        String body = "", line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(bodyParams));
            while ((line = reader.readLine()) != null) {
                body += line.trim();
            }
        } catch (IOException ex) {
            return Response.ok("Error Reading...", MediaType.APPLICATION_JSON).build();
        }

        JsonClientSerializer JCSerializer = new JsonClientSerializer();
        ResultRequest<ClientDTO> clientDTORR = JCSerializer.unserialize(body);
        ClientDTO cdto;
        UpdateClientController updateClientController;

        if (clientDTORR.failed()) {
            return Response.status(400, clientDTORR.getError()).build();
        }

        cdto = clientDTORR.getValue();
        ResultRequest<Client> clientRR = Client.getInstance(cdto.getCode(), cdto.getName(), cdto.getAddress(), cdto.getPhone(),
                cdto.getEmail(), cdto.isCompany(), cdto.getBirthday(), cdto.getNumEquipments(), cdto.getPassword());

        if (clientRR.failed()) {
            return Response.status(400, clientRR.getError()).build();
        }

        cdto = ClientMapper.dtoFromComponent(clientRR.getValue());
        updateClientController = new UpdateClientController();

        ResultRequest<String> request = updateClientController.update(cdto);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(), MediaType.APPLICATION_JSON).build();
    }
}
