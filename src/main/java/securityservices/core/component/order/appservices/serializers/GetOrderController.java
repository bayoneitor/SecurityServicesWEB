package securityservices.core.component.order.appservices.serializers;

import securityservices.core.component.order.appservices.OrderAPIController;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.shared.responses.ResultRequest;

public class GetOrderController extends OrderAPIController {

    public ResultRequest<OrderDTO> getByID(String id) {
        return this.orderRepository.getByID(id);
    }
}
