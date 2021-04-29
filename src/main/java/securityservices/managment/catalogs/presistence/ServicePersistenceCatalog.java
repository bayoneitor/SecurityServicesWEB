package securityservices.managment.catalogs.presistence;

import securityservices.core.component.service.infraestructure.ServiceRepository;
import securityservices.infrastructure.db.postgreadapters.ServicePostgreRepository;

public class ServicePersistenceCatalog {

    public static ServiceRepository getInstance() {
        return new ServicePostgreRepository(PersistenceConnectionFactory.getDataConnection());
    }
}
