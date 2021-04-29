
package securityservices.core.component.service.infraestructure;

import java.util.List;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.shared.responses.ResultRequest;

public interface ServiceRepository {
 public ResultRequest<List<ServiceDTO>> getAll();
 public ResultRequest<ServiceDTO> getByID (String id);
 public ResultRequest<String> add(ServiceDTO service);
 public ResultRequest<String> update(ServiceDTO service);
 public ResultRequest<String> deleteByID(String id);
 public Boolean exists(String id);
}
