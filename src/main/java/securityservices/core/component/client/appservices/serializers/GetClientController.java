package securityservices.core.component.client.appservices.serializers;

import securityservices.core.component.client.appservices.ClientAPIController;
import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.shared.responses.ResultRequest;

public class GetClientController extends ClientAPIController {

    public ResultRequest<ClientDTO> getByID(String id) {
        return this.clientRepository.getByID(id);
    }
}
