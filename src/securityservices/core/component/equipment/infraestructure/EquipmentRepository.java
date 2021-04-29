
package securityservices.core.component.equipment.infraestructure;

import java.util.List;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.shared.responses.ResultRequest;

public interface EquipmentRepository {
 public ResultRequest<List<EquipmentDTO>> getAll();
 public ResultRequest<EquipmentDTO> getByID (String id);
 public ResultRequest<String> add(EquipmentDTO equipment);
 public ResultRequest<String> update(EquipmentDTO equipment);
 public ResultRequest<String> deleteByID(String id);
 public Boolean exists(String id);
}
