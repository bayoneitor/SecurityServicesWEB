package securityservices.core.component.equipment.appservices.serializers;

import java.util.List;
import securityservices.core.component.equipment.appservices.EquipmentAPIController;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.shared.responses.ResultRequest;

public class GetAllEquipmentsController extends EquipmentAPIController {

    public ResultRequest<List<EquipmentDTO>> getAll() {
        return this.equipmentRepository.getAll();
    }
}
