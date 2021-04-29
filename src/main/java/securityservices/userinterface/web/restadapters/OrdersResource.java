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
import securityservices.core.component.order.appservices.JsonOrderSerializer;
import securityservices.core.component.order.appservices.UseCaseOrderController;
import securityservices.core.component.order.appservices.serializers.AddOrderController;
import securityservices.core.component.order.appservices.serializers.DeleteOrderController;
import securityservices.core.component.order.appservices.serializers.GetAllOrdersController;
import securityservices.core.component.order.appservices.serializers.GetOrderController;
import securityservices.core.component.order.appservices.serializers.OrderListSerializer;
import securityservices.core.component.order.appservices.serializers.OrderSerializer;
import securityservices.core.component.order.appservices.serializers.UpdateOrderController;
import securityservices.core.component.order.domain.model.Order;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.core.component.order.domain.services.OrderMapper;
import securityservices.shared.responses.ResultRequest;

@Path("Orders")
public class OrdersResource {

    @Context
    private UriInfo context;
    private UseCaseOrderController useCaseController = new UseCaseOrderController();

    public OrdersResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() {
        //Utilitza 2 controladors de AppServices, un per obtenir els orders i un altre per serialitzar-los
        GetAllOrdersController allOrdersController = new GetAllOrdersController();
        ResultRequest<List<OrderDTO>> request = allOrdersController.getAll();
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(OrderListSerializer.makeJsonResult(request.getValue()),
                MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllOrdersXML() {
        //Utilitza 2 controladors de AppServices, un per obtenir els orders i un altre per serialitzar-los
        GetAllOrdersController allOrdersController = new GetAllOrdersController();
        ResultRequest<List<OrderDTO>> request = allOrdersController.getAll();
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(OrderListSerializer.makeXMLResult(request.getValue()),
                MediaType.APPLICATION_XML).build();
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderByID(@PathParam("orderId") String id) {
        GetOrderController orderController = new GetOrderController();
        ResultRequest<OrderDTO> request = orderController.getByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(OrderSerializer.makeJsonResult(request.getValue()),
                MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getOrderByIDXML(@PathParam("orderId") String id) {
        GetOrderController orderController = new GetOrderController();
        ResultRequest<OrderDTO> request = orderController.getByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(OrderSerializer.makeXMLResult(request.getValue()),
                MediaType.APPLICATION_XML).build();
    }

    @DELETE
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrderByID(@PathParam("orderId") String id) {
        DeleteOrderController deleteOrderController = new DeleteOrderController();
        ResultRequest<String> request = deleteOrderController.deleteByID(id);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(),
                MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOrder(InputStream bodyParams) {
        String body = "", line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(bodyParams));
            while ((line = reader.readLine()) != null) {
                body += line.trim();
            }
        } catch (IOException ex) {
            return Response.ok("Error Reading...", MediaType.APPLICATION_JSON).build();
        }

        JsonOrderSerializer JOSerializer = new JsonOrderSerializer();
        ResultRequest<OrderDTO> orderDTORR = JOSerializer.unserialize(body);
        OrderDTO ordto;
        Order order;
        AddOrderController addOrderController;

        if (orderDTORR.failed()) {
            return Response.status(400, orderDTORR.getError()).build();
        }
        ordto = orderDTORR.getValue();
        ResultRequest<Order> orderRR = Order.getInstance(ordto.getCode(), ordto.getCreator(), ordto.getValue(), ordto.getSurcharges(),
                ordto.getType(), ordto.getStatus(), ordto.getAdditionalInfo(), ordto.getInitDate(), ordto.getFinishDate(),
                ordto.getPaymentType(), ordto.getPaymentDate());

        if (orderRR.failed()) {
            return Response.status(400, orderRR.getError()).build();
        }
        //Si no falla le seteamos los detalles
        order = orderRR.getValue();
        String details[] = ordto.getDetailsShop().split(";");
        int detailsResult;
        for (int i = 0; i < details.length; i++) {
            detailsResult = order.setDetail(details[i]);
            if (detailsResult < 0) {
                return Response.status(400, "Error al añadir el producto numero " + i + " de la lista, error " + detailsResult).build();
            }
        }

        ordto = OrderMapper.dtoFromComponent(order);
        addOrderController = new AddOrderController();

        ResultRequest<String> request = addOrderController.add(ordto);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrder(InputStream bodyParams) {
        String body = "", line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(bodyParams));
            while ((line = reader.readLine()) != null) {
                body += line.trim();
            }
        } catch (IOException ex) {
            return Response.ok("Error Reading...", MediaType.APPLICATION_JSON).build();
        }

        JsonOrderSerializer JOSerializer = new JsonOrderSerializer();
        ResultRequest<OrderDTO> orderDTORR = JOSerializer.unserialize(body);
        OrderDTO ordto;
        Order order;
        UpdateOrderController updateOrderController;

        if (orderDTORR.failed()) {
            return Response.status(400, orderDTORR.getError()).build();
        }

        ordto = orderDTORR.getValue();
        ResultRequest<Order> orderRR = Order.getInstance(ordto.getCode(), ordto.getCreator(), ordto.getValue(), ordto.getSurcharges(),
                ordto.getType(), ordto.getStatus(), ordto.getAdditionalInfo(), ordto.getInitDate(), ordto.getFinishDate(),
                ordto.getPaymentType(), ordto.getPaymentDate());

        if (orderRR.failed()) {
            return Response.status(400, orderRR.getError()).build();
        }
        //Si no falla le seteamos los detalles
        order = orderRR.getValue();
        String details[] = ordto.getDetailsShop().split(";");
        int detailsResult;
        for (int i = 0; i < details.length; i++) {
            detailsResult = order.setDetail(details[i]);
            if (detailsResult < 0) {
                return Response.status(400, "Error al actualizar el producto numero " + (i + 1) + " de la lista, error " + detailsResult).build();
            }
        }

        ordto = OrderMapper.dtoFromComponent(order);
        updateOrderController = new UpdateOrderController();

        ResultRequest<String> request = updateOrderController.update(ordto);
        if (request.failed()) {
            return Response.status(400, request.getError()).build();
        }
        return Response.ok(request.getValue(), MediaType.APPLICATION_JSON).build();
    }
}
