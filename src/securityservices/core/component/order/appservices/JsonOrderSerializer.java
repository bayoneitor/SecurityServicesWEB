package securityservices.core.component.order.appservices;

import java.util.ArrayList;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;
import securityservices.core.shared.services.serializers.Serializer;
import securityservices.shared.responses.ResultRequest;

public class JsonOrderSerializer implements Serializer {

    private Json jOrder = JsonObjectFactory.getInstance();
    private Json jDetail = JsonObjectFactory.getInstance();
    private Json jArray = JsonObjectFactory.getInstance();

    public JsonOrderSerializer() {
    }

    public Json detailsShop_JSONFromString(String detailsShop, Json json) {
        if (detailsShop != null) {
            String StringDetail[] = detailsShop.split(";");
            if (StringDetail.length > 0) {
                for (int i = 0; i < StringDetail.length; i++) {
                    String campos[] = StringDetail[i].split(",");
                    String amount = campos[1];
                    String ref = campos[0];
                    String price = campos[2];

                    jDetail.set("ref", ref);
                    jDetail.set("amount", amount);
                    jDetail.set("price", price);
                    json.set("detailsShop", jDetail);

                    jDetail.removeAll();
                }
            }
        } else {
            json.set("detailsShop", "null");
        }
        return json;
    }

    public String detailsShop_StringFromJSON(Json json) {
        String detailsShop = null;
        if (json.get("detailsShop") != null) {
            if (json.getArraySize("detailsShop") > 0) {
                detailsShop = "";
                for (int i = 0; i < json.getArraySize("detailsShop"); i++) {
                    jArray = json.getArrayObj("detailsShop", i);
                    detailsShop += jArray.get("ref") + "," + jArray.get("amount") + ","
                            + jArray.get("price") + ";";
                }
            }
        }
        return detailsShop;
    }

    @Override
    public ResultRequest<OrderDTO> unserialize(String data) {
        jOrder.set(data);
        try {
            String detailsShop = this.detailsShop_StringFromJSON(jOrder);
            OrderDTO order = new OrderDTO(
                    jOrder.get("code"),
                    Integer.valueOf(jOrder.get("creator")),
                    Double.valueOf(jOrder.get("value")),
                    Double.valueOf(jOrder.get("surcharges")),
                    jOrder.get("type"),
                    jOrder.get("status"),
                    jOrder.get("additionalInfo"),
                    jOrder.get("initDate"),
                    jOrder.get("finishDate"),
                    jOrder.get("paymentType"),
                    jOrder.get("paymentDate"),
                    detailsShop,
                    jOrder.get("orderId")
            );

            return ResultRequest.done(order);
        } catch (Exception e) {
            return ResultRequest.fails("{\"Error\":\"OrderDTO unserialized Exception\","
                    + "\"Details\":\"" + e.toString() + "\"}");
        }
    }

    @Override
    public ResultRequest<String> serialize(Object order) {
        jOrder.removeAll();
        jOrder = this.detailsShop_JSONFromString(((OrderDTO) order).getDetailsShop(), jOrder);
        if (jOrder.get("detailsShop").equals("null")) {
            jOrder.set("value", "0.1");
        } else {
            jOrder.set("value", String.valueOf(((OrderDTO) order).getValue()));
        }

        jOrder.set("code", ((OrderDTO) order).getCode());
        jOrder.set("creator", String.valueOf(((OrderDTO) order).getCreator()));
        jOrder.set("surcharges", String.valueOf(((OrderDTO) order).getSurcharges()));
        jOrder.set("type", ((OrderDTO) order).getType());
        jOrder.set("status", ((OrderDTO) order).getStatus());
        jOrder.set("additionalInfo", ((OrderDTO) order).getAdditionalInfo());
        jOrder.set("initDate", ((OrderDTO) order).getInitDate());
        jOrder.set("finishDate", ((OrderDTO) order).getFinishDate());
        jOrder.set("paymentType", ((OrderDTO) order).getPaymentType());
        jOrder.set("paymentDate", ((OrderDTO) order).getPaymentDate());
        jOrder.set("orderId", ((OrderDTO) order).getOrderId());
        return ResultRequest.done(jOrder.toString());
    }

}
