package securityservices.core.component.equipment.appservices.serializers;

import securityservices.core.component.equipment.appservices.EquipmentAPIController;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.shared.responses.ResultRequest;

public class AddEquipmentController extends EquipmentAPIController {

    public ResultRequest<String> add(EquipmentDTO equipment) {
        return this.equipmentRepository.add(equipment);
    }
}
