package securityservices.core.component.client.appservices.serializers;

import securityservices.core.component.client.appservices.ClientAPIController;
import securityservices.shared.responses.ResultRequest;

public class DeleteClientController extends ClientAPIController {

    public ResultRequest<String> deleteByID(String id) {
        return this.clientRepository.deleteByID(id);
    }
}
