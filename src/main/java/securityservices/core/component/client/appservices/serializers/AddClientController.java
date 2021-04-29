package securityservices.core.component.client.appservices.serializers;

import securityservices.core.component.client.appservices.ClientAPIController;
import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.shared.responses.ResultRequest;

public class AddClientController extends ClientAPIController {

    public ResultRequest<String> add(ClientDTO client) {
        return this.clientRepository.add(client);
    }
}
