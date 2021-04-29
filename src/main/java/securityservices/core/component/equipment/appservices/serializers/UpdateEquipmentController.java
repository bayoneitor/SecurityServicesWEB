package securityservices.core.component.equipment.appservices.serializers;

import securityservices.core.component.equipment.appservices.EquipmentAPIController;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.shared.responses.ResultRequest;

public class UpdateEquipmentController extends EquipmentAPIController {

    public ResultRequest<String> update(EquipmentDTO equipment) {
        return this.equipmentRepository.update(equipment);
    }
}
