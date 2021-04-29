package securityservices.core.component.service.appservices.serializers;

import securityservices.core.component.service.appservices.ServiceAPIController;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.shared.responses.ResultRequest;

public class AddServiceController extends ServiceAPIController {

    public ResultRequest<String> add(ServiceDTO service) {
        return this.serviceRepository.add(service);
    }
}
