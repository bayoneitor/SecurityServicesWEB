package securityservices.core.component.equipment.appservices.serializers;

import securityservices.core.component.equipment.appservices.EquipmentAPIController;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.shared.responses.ResultRequest;

public class GetEquipmentController extends EquipmentAPIController {

    public ResultRequest<EquipmentDTO> getByID(String id) {
        return this.equipmentRepository.getByID(id);
    }
}
