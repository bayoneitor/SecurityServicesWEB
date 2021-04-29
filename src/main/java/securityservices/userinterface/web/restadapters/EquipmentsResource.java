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
import securityservices.core.component.equipment.appservices.JsonEquipmentSerializer;
import securityservices.core.component.equipment.appservices.UseCaseEquipmentController;
import securityservices.core.component.equipment.appservices.serializers.AddEquipmentController;
import securityservices.core.component.equipment.appservices.serializers.DeleteEquipmentController;
import securityservices.core.component.equipment.appservices.serializers.EquipmentListSerializer;
import securityservices.core.component.equipment.appservices.serializers.EquipmentSerializer;
import securityservices.core.component.equipment.appservices.serializers.GetAllEquipmentsController;
import securityservices.core.component.equipment.appservices.serializers.GetEquipmentController;
import securityservices.core.component.equipment.appservices.serializers.UpdateEquipmentController;
import securityservices.core.component.equipment.domain.model.Equipment;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.core.component.equipment.domain.services.EquipmentMapper;
import securityservices.shared.responses.ResultRequest;

@Path("Equipments")
public class EquipmentsResource {

    @Context
    private UriInfo context;
    private UseCaseEquipmentController useCaseController = new UseCaseEquipmentController();

    public EquipmentsResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEquipments() {
        //Utilitza 2 controladors de AppServices, un per obtenir els equipments i un altre per serialitzar-los
        GetAllEquipmentsController allEquipmentsController = new GetAllEquipmentsController();
        ResultRequest<List<EquipmentDTO>> request = allEquipmentsController.getAll();
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(EquipmentListSerializer.makeJsonResult(request.getValue()),
                MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllEquipmentsXML() {
        //Utilitza 2 controladors de AppServices, un per obtenir els equipments i un altre per serialitzar-los
        GetAllEquipmentsController allEquipmentsController = new GetAllEquipmentsController();
        ResultRequest<List<EquipmentDTO>> request = allEquipmentsController.getAll();
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(EquipmentListSerializer.makeXMLResult(request.getValue()),
                MediaType.APPLICATION_XML).build();
    }

    @GET
    @Path("/{equipmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipmentByID(@PathParam("equipmentId") String id) {
        GetEquipmentController equipmentController = new GetEquipmentController();
        ResultRequest<EquipmentDTO> request = equipmentController.getByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(EquipmentSerializer.makeJsonResult(request.getValue()),
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/{equipmentId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEquipmentByIDXML(@PathParam("equipmentId") String id) {
        GetEquipmentController equipmentController = new GetEquipmentController();
        ResultRequest<EquipmentDTO> request = equipmentController.getByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(EquipmentSerializer.makeXMLResult(request.getValue()),
                MediaType.APPLICATION_XML).build();
    }
    
    @DELETE
    @Path("/{equipmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEquipmentByID(@PathParam("equipmentId") String id) {
        DeleteEquipmentController deleteEquipmentController = new DeleteEquipmentController();
        ResultRequest<String> request = deleteEquipmentController.deleteByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(),
                MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEquipment(InputStream bodyParams) {
        String body = "", line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(bodyParams));
            while ((line = reader.readLine()) != null) {
                body += line.trim();
            }
        } catch (IOException ex) {
            return Response.ok("Error Reading...", MediaType.APPLICATION_JSON).build();
        }

        JsonEquipmentSerializer JESerializer = new JsonEquipmentSerializer();
        ResultRequest<EquipmentDTO> equipmentDTORR = JESerializer.unserialize(body);
        EquipmentDTO edto;
        AddEquipmentController addEquipmentController;

        if (equipmentDTORR.failed()) {
            return Response.status(400, equipmentDTORR.getError()).build();
        }
       
        edto = equipmentDTORR.getValue();
        ResultRequest<Equipment> equipmentRR = Equipment.getInstance(edto.getCode(), edto.getName(), edto.getType(), edto.getMaker(),
                edto.getDescription(), edto.getPrice(), edto.getHigh(), edto.getWide(), edto.getDeep(), edto.getWeight(),
                edto.isFragile(), edto.getFunction(), edto.getComponents(), edto.getPower());

        if (equipmentRR.failed()) {
            return Response.status(400, equipmentRR.getError()).build();
        }

        edto = EquipmentMapper.dtoFromComponent(equipmentRR.getValue());
        addEquipmentController = new AddEquipmentController();

        ResultRequest<String> request = addEquipmentController.add(edto);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(), MediaType.APPLICATION_JSON).build();
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEquipment(InputStream bodyParams) {
        String body = "", line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(bodyParams));
            while ((line = reader.readLine()) != null) {
                body += line.trim();
            }
        } catch (IOException ex) {
            return Response.ok("Error Reading...", MediaType.APPLICATION_JSON).build();
        }

        JsonEquipmentSerializer JESerializer = new JsonEquipmentSerializer();
        ResultRequest<EquipmentDTO> equipmentDTORR = JESerializer.unserialize(body);
        EquipmentDTO edto;
        UpdateEquipmentController updateEquipmentController;

        if (equipmentDTORR.failed()) {
            return Response.status(400, equipmentDTORR.getError()).build();
        }

        edto = equipmentDTORR.getValue();
        ResultRequest<Equipment> equipmentRR = Equipment.getInstance(edto.getCode(), edto.getName(), edto.getType(), edto.getMaker(),
                edto.getDescription(), edto.getPrice(), edto.getHigh(), edto.getWide(), edto.getDeep(), edto.getWeight(),
                edto.isFragile(), edto.getFunction(), edto.getComponents(), edto.getPower());

        if (equipmentRR.failed()) {
            return Response.status(400, equipmentRR.getError()).build();
        }

        edto = EquipmentMapper.dtoFromComponent(equipmentRR.getValue());
        updateEquipmentController = new UpdateEquipmentController();

        ResultRequest<String> request = updateEquipmentController.update(edto);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(), MediaType.APPLICATION_JSON).build();
    }
}
