package securityservices.core.component.order.appservices.serializers;

import securityservices.core.component.order.appservices.OrderAPIController;
import securityservices.shared.responses.ResultRequest;

public class DeleteOrderController extends OrderAPIController {

    public ResultRequest<String> deleteByID(String id) {
        return this.orderRepository.deleteByID(id);
    }
}
