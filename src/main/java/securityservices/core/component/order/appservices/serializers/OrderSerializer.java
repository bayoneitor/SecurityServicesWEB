package securityservices.core.component.order.appservices.serializers;

import securityservices.core.component.order.appservices.JaxbOrderSerializer;
import securityservices.core.component.order.appservices.JsonOrderSerializer;
import securityservices.core.component.order.domain.services.OrderDTO;

public class OrderSerializer {

    public static String makeJsonResult(OrderDTO order) {
        String jsonOrderList = "{\"Order\":[";
        JsonOrderSerializer jsonOrder = new JsonOrderSerializer();
        jsonOrderList += jsonOrder.serialize(order).getValue();
        jsonOrderList += "]}";
        return jsonOrderList;
    }

    public static String makeXMLResult(OrderDTO order) {
        JaxbOrderSerializer xmlOrder = JaxbOrderSerializer.getInstance().getValue();
        String xmlOrderList = xmlOrder.serialize(order).getValue();
        return xmlOrderList;
    }
}
