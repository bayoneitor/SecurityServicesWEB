package securityservices.core.component.service.appservices.serializers;

import java.util.List;
import securityservices.core.component.service.appservices.ServiceAPIController;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.shared.responses.ResultRequest;

public class GetAllServicesController extends ServiceAPIController {

    public ResultRequest<List<ServiceDTO>> getAll() {
        return this.serviceRepository.getAll();
    }
}
