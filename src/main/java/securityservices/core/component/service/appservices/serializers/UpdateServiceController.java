package securityservices.core.component.service.appservices.serializers;

import securityservices.core.component.service.appservices.ServiceAPIController;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.shared.responses.ResultRequest;

public class UpdateServiceController extends ServiceAPIController {

    public ResultRequest<String> update(ServiceDTO service) {
        return this.serviceRepository.update(service);
    }
}
