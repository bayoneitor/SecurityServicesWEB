package securityservices.core.component.equipment.appservices.serializers;

import securityservices.core.component.equipment.appservices.EquipmentAPIController;
import securityservices.shared.responses.ResultRequest;

public class DeleteEquipmentController extends EquipmentAPIController {

    public ResultRequest<String> deleteByID(String id) {
        return this.equipmentRepository.deleteByID(id);
    }
}
