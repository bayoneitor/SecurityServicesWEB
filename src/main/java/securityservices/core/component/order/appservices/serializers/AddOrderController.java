package securityservices.core.component.order.appservices.serializers;

import securityservices.core.component.order.appservices.OrderAPIController;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.shared.responses.ResultRequest;

public class AddOrderController extends OrderAPIController {

    public ResultRequest<String> add(OrderDTO order) {
        return this.orderRepository.add(order);
    }
}
