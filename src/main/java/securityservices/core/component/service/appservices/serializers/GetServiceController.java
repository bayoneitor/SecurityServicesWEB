package securityservices.core.component.service.appservices.serializers;

import securityservices.core.component.service.appservices.ServiceAPIController;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.shared.responses.ResultRequest;

public class GetServiceController extends ServiceAPIController {

    public ResultRequest<ServiceDTO> getByID(String id) {
        return this.serviceRepository.getByID(id);
    }
}
