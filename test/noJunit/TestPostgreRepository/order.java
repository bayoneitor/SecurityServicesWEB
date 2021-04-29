package noJunit.TestPostgreRepository;

import java.util.List;
import securityservices.core.component.order.appservices.JsonOrderSerializer;
import securityservices.core.component.order.domain.model.Order;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.core.component.order.domain.services.OrderMapper;
import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;
import securityservices.infrastructure.db.postgreadapters.OrderPostgreRepository;
import securityservices.shared.responses.ResultRequest;

public class order {

    public static void main(String[] args) {
        Json JConn = JsonObjectFactory.getInstance();
        JConn.set("db_driver", "postgresql");
        JConn.set("db_address", "localhost");
        JConn.set("db_database", "SecurityServices");
        JConn.set("db_user", "SecurityServices");
        JConn.set("db_password", "linuxlinux");

        OrderPostgreRepository CPR = new OrderPostgreRepository(JConn);
        JsonOrderSerializer JOSerializer = new JsonOrderSerializer();
        ResultRequest<String> result;
        ResultRequest<OrderDTO> odtoRR;
        Order order;
        OrderDTO odto;
        String code = "O-05";

        //Comprobar método add   
        System.out.println("---- Comprobar método add");

        odto = new OrderDTO(code, 2, 50.0, 0.0, "type", "status", "additionalInfo", "15/03/2020-12:46:30", "17/03/2020-10:31:30", "credit", "17/03/2020-10:31:30",
                "Ref-1,5,3.2;Ref-8,1,99.6;Ref-2,3,9.99;", "asd54x5a-asx5-4fc9-9ea8-a4x7w5s5x6c2");
        result = CPR.add(odto);
        if (result.failed()) {
            System.out.println(result.getError());
        } else {
            System.out.println(result.getValue());
        }

        System.out.println();

        System.out.println("---- ---- Comprobar método exists");
        if (CPR.exists(code)) {
            System.out.println("El order con id " + code + " existe");
        }

        System.out.println("---- Comprobar método delete del order creado");
        result = CPR.deleteByID(code);
        if (result.failed()) {
            System.out.println(result.getError());
        } else {
            System.out.println(result.getValue());
            if (!CPR.exists(code)) {
                System.out.println("---- ---- Comprobar que no exista");
                System.out.println("El order con id " + code + " NO existe");
            }
        }

        System.out.println();

        //Comprobar método getByID
        System.out.println("---- Comprobar método getByID");
        odtoRR = CPR.getByID("O-01");
        if (odtoRR.failed()) {
            System.out.println(odtoRR.getError());
        } else {
            System.out.println(JOSerializer.serialize(odtoRR.getValue()).getValue());

            System.out.println();

            //Comprobar método update
            System.out.println("---- Comprobar método update del order O-01");
            ResultRequest<Order> serviceUnMapped = OrderMapper.componentFromDTO(odtoRR.getValue());
            if (serviceUnMapped.failed()) {
                System.out.println(serviceUnMapped.getError());
            } else {
                order = serviceUnMapped.getValue();
                //Cambiamos los valores del Order
                order.setAdditionalInfo("additional info Updated10");
                System.out.println("---- ---- Miramos un detalle para cambiarlo");
                System.out.println(order.getDetail(1));
                //Cambiamos la referencia del order
                order.updateDetail(1, "Ref-111,1,5.3");
                result = CPR.update(OrderMapper.dtoFromComponent(order));
                if (result.failed()) {
                    System.out.println(result.getError());
                } else {
                    System.out.println(result.getValue());
                }
            }
        }
        //Comprobar método getAll
        System.out.println();

        System.out.println("---- Comprobar método getAll");
        ResultRequest<List<OrderDTO>> getAllOrders = CPR.getAll();
        if (getAllOrders.failed()) {
            System.out.println(getAllOrders.getError());
        } else {
            //Mostrar todos los orders
            for (int i = 0; i < getAllOrders.getValue().size(); i++) {
                result = JOSerializer.serialize(getAllOrders.getValue().get(i));
                System.out.println("Order " + i + ": " + result.getValue());
            }
        }
    }
}
