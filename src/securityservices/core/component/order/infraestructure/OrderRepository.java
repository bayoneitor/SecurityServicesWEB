
package securityservices.core.component.order.infraestructure;

import java.util.List;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.shared.responses.ResultRequest;

public interface OrderRepository {
 public ResultRequest<List<OrderDTO>> getAll();
 public ResultRequest<OrderDTO> getByID (String id);
 public ResultRequest<String> add(OrderDTO order);
 public ResultRequest<String> update(OrderDTO order);
 public ResultRequest<String> deleteByID(String id);
 public Boolean exists(String id);
}
