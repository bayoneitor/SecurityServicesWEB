package securityservices.core.component.service.appservices.serializers;

import securityservices.core.component.service.appservices.ServiceAPIController;
import securityservices.shared.responses.ResultRequest;

public class DeleteServiceController extends ServiceAPIController {

    public ResultRequest<String> deleteByID(String id) {
        return this.serviceRepository.deleteByID(id);
    }
}
