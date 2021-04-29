package securityservices.core.component.order.appservices.serializers;

import java.util.Iterator;
import java.util.List;
import securityservices.core.component.order.appservices.JaxbOrderSerializer;
import securityservices.core.component.order.appservices.JsonOrderSerializer;
import securityservices.core.component.order.domain.services.OrderDTO;

public class OrderListSerializer {

    public static String makeJsonResult(List<OrderDTO> orderList) {
        String jsonOrderList = "{\"OrderList\":[";
        JsonOrderSerializer jsonOrder = new JsonOrderSerializer();
        Iterator it = orderList.iterator();
        for (; it.hasNext();) {
            jsonOrderList += jsonOrder.serialize(it.next()).getValue();
            if (it.hasNext()) {
                jsonOrderList += ",";
            }
        }
        jsonOrderList += "]}";
        return jsonOrderList;
    }

    public static String makeXMLResult(List<OrderDTO> orderList) {
        String xmlOrderList = "<orders>";
        JaxbOrderSerializer xmlOrder = JaxbOrderSerializer.getInstance().getValue();
        Iterator it = orderList.iterator();
        for (; it.hasNext();) {
            xmlOrderList += xmlOrder.serialize((OrderDTO) it.next()).getValue();
        }
        xmlOrderList += "</orders>";
        return xmlOrderList.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
    }
}
