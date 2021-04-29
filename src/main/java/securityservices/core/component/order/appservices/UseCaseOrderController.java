package securityservices.core.component.order.appservices;

import securityservices.core.component.order.domain.model.Order;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.core.component.order.domain.services.OrderMapper;
import securityservices.core.shared.services.serializers.Serializer;
import securityservices.infrastructure.filesystemadapters.FileManager;
import securityservices.shared.responses.ResultRequest;

public class UseCaseOrderController {

    private FileManager fileManager = new FileManager();

    public ResultRequest<String> getJsonResource() {
        //ResultRequest<String> dataOrderFile = fileManager.read("c:\\files\\order\\order_03_03_2021_15_17_37.json");
        Order order1 = null;

        ResultRequest<Order> orderRequest = Order.getInstance("json", 1, 2.2, 0.2, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");

        if (orderRequest.failed()) {
            System.out.println(orderRequest.getError());
        } else {
            order1 = orderRequest.getValue();
            order1.setDetail("Ref-1,1,5.3");
            order1.setDetail("Ref-2,5,2.3");
            order1.setDetail("Ref-3,2,10.3");
        }

        OrderDTO odto1 = OrderMapper.dtoFromComponent(order1);

        JsonOrderSerializer jsSerializer = new JsonOrderSerializer();

        ResultRequest<String> dataOrderFile = jsSerializer.serialize(odto1);

        if (dataOrderFile.failed() == false) {
            return dataOrderFile;
        }
        return ResultRequest.fails("Not Found");
    }

    public ResultRequest<String> getXmlResource() {
        //ResultRequest<String> dataOrderFile = fileManager.read("c:\\files\\order\\order_03_03_2021_15_18_36.xml");
        Order order1 = null;
        
        ResultRequest<Order> orderRequest = Order.getInstance("jaxb", 1, 2.2, 0.2, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");

        if (orderRequest.failed()) {
            System.out.println(orderRequest.getError());
        } else {
            order1 = orderRequest.getValue();
            order1.setDetail("Ref-1,1,5.3");
            order1.setDetail("Ref-2,5,2.3");
            order1.setDetail("Ref-3,2,10.3");
        }

        OrderDTO odto1 = OrderMapper.dtoFromComponent(order1);
        //Igual
        ResultRequest<JaxbOrderSerializer> jaxbOrderSerializer = JaxbOrderSerializer.getInstance();

        Serializer xmlOrderSerializer = jaxbOrderSerializer.getValue();
        ResultRequest<String> dataOrderFile = xmlOrderSerializer.serialize(odto1);
        
        if (dataOrderFile.failed() == false) {
            return dataOrderFile;
        }
        return ResultRequest.fails("Not Found");
    }
}
