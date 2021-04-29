package securityservices.managment.catalogs.presistence;

import securityservices.core.component.equipment.infraestructure.EquipmentRepository;
import securityservices.infrastructure.db.postgreadapters.EquipmentPostgreRepository;

public class EquipmentPersistenceCatalog {

    public static EquipmentRepository getInstance() {
        return new EquipmentPostgreRepository(PersistenceConnectionFactory.getDataConnection());
    }
}
