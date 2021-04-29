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
import securityservices.core.component.service.appservices.JsonServiceSerializer;
import securityservices.core.component.service.appservices.UseCaseServiceController;
import securityservices.core.component.service.appservices.serializers.AddServiceController;
import securityservices.core.component.service.appservices.serializers.DeleteServiceController;
import securityservices.core.component.service.appservices.serializers.GetAllServicesController;
import securityservices.core.component.service.appservices.serializers.GetServiceController;
import securityservices.core.component.service.appservices.serializers.ServiceListSerializer;
import securityservices.core.component.service.appservices.serializers.ServiceSerializer;
import securityservices.core.component.service.appservices.serializers.UpdateServiceController;
import securityservices.core.component.service.domain.model.Service;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.core.component.service.domain.services.ServiceMapper;
import securityservices.shared.responses.ResultRequest;

@Path("Services")
public class ServicesResource {

    @Context
    private UriInfo context;
    private UseCaseServiceController useCaseController = new UseCaseServiceController();

    public ServicesResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServices() {
        //Utilitza 2 controladors de AppServices, un per obtenir els services i un altre per serialitzar-los
        GetAllServicesController allServicesController = new GetAllServicesController();
        ResultRequest<List<ServiceDTO>> request = allServicesController.getAll();
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(ServiceListSerializer.makeJsonResult(request.getValue()),
                MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllServicesXML() {
        //Utilitza 2 controladors de AppServices, un per obtenir els services i un altre per serialitzar-los
        GetAllServicesController allServicesController = new GetAllServicesController();
        ResultRequest<List<ServiceDTO>> request = allServicesController.getAll();
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(ServiceListSerializer.makeXMLResult(request.getValue()),
                MediaType.APPLICATION_XML).build();
    }

    @GET
    @Path("/{serviceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServiceByID(@PathParam("serviceId") String id) {
        GetServiceController serviceController = new GetServiceController();
        ResultRequest<ServiceDTO> request = serviceController.getByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(ServiceSerializer.makeJsonResult(request.getValue()),
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/{serviceId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getServiceByIDXML(@PathParam("serviceId") String id) {
        GetServiceController serviceController = new GetServiceController();
        ResultRequest<ServiceDTO> request = serviceController.getByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(ServiceSerializer.makeXMLResult(request.getValue()),
                MediaType.APPLICATION_XML).build();
    }
    
    @DELETE
    @Path("/{serviceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteServiceByID(@PathParam("serviceId") String id) {
        DeleteServiceController deleteServiceController = new DeleteServiceController();
        ResultRequest<String> request = deleteServiceController.deleteByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(),
                MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addService(InputStream bodyParams) {
        String body = "", line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(bodyParams));
            while ((line = reader.readLine()) != null) {
                body += line.trim();
            }
        } catch (IOException ex) {
            return Response.ok("Error Reading...", MediaType.APPLICATION_JSON).build();
        }

        JsonServiceSerializer JSSerializer = new JsonServiceSerializer();
        ResultRequest<ServiceDTO> serviceDTORR = JSSerializer.unserialize(body);
        ServiceDTO sdto;
        AddServiceController addServiceController;

        if (serviceDTORR.failed()) {
            return Response.status(400, serviceDTORR.getError()).build();
        }

        sdto = serviceDTORR.getValue();
        ResultRequest<Service> serviceRR = Service.getInstance(sdto.getCode(), sdto.getName(), sdto.getType(), sdto.getMaker(),
                sdto.getDescription(), sdto.getPrice(), sdto.getPeriodicity(), sdto.getConditions(), sdto.getStartDate(), sdto.getFinishDate());

        if (serviceRR.failed()) {
            return Response.status(400, serviceRR.getError()).build();
        }

        sdto = ServiceMapper.dtoFromComponent(serviceRR.getValue());
        addServiceController = new AddServiceController();

        ResultRequest<String> request = addServiceController.add(sdto);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(), MediaType.APPLICATION_JSON).build();
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateService(InputStream bodyParams) {
        String body = "", line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(bodyParams));
            while ((line = reader.readLine()) != null) {
                body += line.trim();
            }
        } catch (IOException ex) {
            return Response.ok("Error Reading...", MediaType.APPLICATION_JSON).build();
        }

        JsonServiceSerializer JSSerializer = new JsonServiceSerializer();
        ResultRequest<ServiceDTO> serviceDTORR = JSSerializer.unserialize(body);
        ServiceDTO sdto;
        UpdateServiceController updateServiceController;

        if (serviceDTORR.failed()) {
            return Response.status(400, serviceDTORR.getError()).build();
        }

        sdto = serviceDTORR.getValue();
         ResultRequest<Service> serviceRR = Service.getInstance(sdto.getCode(), sdto.getName(), sdto.getType(), sdto.getMaker(),
                sdto.getDescription(), sdto.getPrice(), sdto.getPeriodicity(), sdto.getConditions(), sdto.getStartDate(), sdto.getFinishDate());

        if (serviceRR.failed()) {
            return Response.status(400, serviceRR.getError()).build();
        }

        sdto = ServiceMapper.dtoFromComponent(serviceRR.getValue());
        updateServiceController = new UpdateServiceController();

        ResultRequest<String> request = updateServiceController.update(sdto);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(), MediaType.APPLICATION_JSON).build();
    }
}

