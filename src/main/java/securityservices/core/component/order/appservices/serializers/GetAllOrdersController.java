package securityservices.core.component.order.appservices.serializers;

import java.util.List;
import securityservices.core.component.order.appservices.OrderAPIController;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.shared.responses.ResultRequest;

public class GetAllOrdersController extends OrderAPIController {

    public ResultRequest<List<OrderDTO>> getAll() {
        return this.orderRepository.getAll();
    }
}
