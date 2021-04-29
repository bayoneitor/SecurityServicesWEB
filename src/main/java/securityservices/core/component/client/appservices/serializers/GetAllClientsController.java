package securityservices.core.component.client.appservices.serializers;

import java.util.List;
import securityservices.core.component.client.appservices.ClientAPIController;
import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.shared.responses.ResultRequest;

public class GetAllClientsController extends ClientAPIController {

    public ResultRequest<List<ClientDTO>> getAll() {
        return this.clientRepository.getAll();
    }
}
