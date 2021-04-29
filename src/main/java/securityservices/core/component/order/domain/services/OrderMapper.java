package securityservices.core.component.order.domain.services;

import securityservices.core.component.order.domain.services.*;
import securityservices.core.component.order.domain.model.Order;
import securityservices.shared.responses.ResultRequest;

public class OrderMapper {

    public static ResultRequest<Order> componentFromDTO(OrderDTO odto) {
        ResultRequest<Order> RRO = Order.getInstance(
                odto.getCode(),
                odto.getCreator(),
                odto.getValue(),
                odto.getSurcharges(),
                odto.getType(),
                odto.getStatus(),
                odto.getAdditionalInfo(),
                odto.getInitDate(),
                odto.getFinishDate(),
                odto.getPaymentType(),
                odto.getPaymentDate()
        );
        if (RRO.failed()) {
            return RRO;
        }

        Order ord = RRO.getValue();
        
        String detailsShop = odto.getDetailsShop();

        if (detailsShop != null) {
            String StringDetail[] = detailsShop.split(";");
            if (StringDetail.length > 0) {
                for (int i = 0; i < StringDetail.length; i++) {
                    int statusDetail = ord.setDetail(StringDetail[i]);
                    if (statusDetail < 0) {
                        ord = null;
                        return ResultRequest.fails("\"Error\":\"invalid product number " + i + ", error " + statusDetail + "\"");
                    }
                }
            }
        }

        return RRO;
    }

    public static OrderDTO dtoFromComponent(Order o) {
        String detailsShop = null;
        if (o.getNumDetails() != 0) {
            detailsShop = "";
            for (int i = 0; i < o.getNumDetails(); i++) {
                detailsShop += o.getDetail(i + 1) + ";";
            }
        }
        OrderDTO odto = new OrderDTO(
                o.getCode(),
                o.getCreator(),
                o.getValue(),
                o.getSurcharges(),
                o.getType(),
                o.getStatus(),
                o.getadditionalInfo(),
                o.getInitDate(),
                o.getFinishDate(),
                o.getPaymentType(),
                o.getPaymentDate(),
                detailsShop,
                o.getOrderId()
        );

        return odto;
    }
}
