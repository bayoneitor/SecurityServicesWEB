package securityservices.managment.catalogs.presistence;

import securityservices.core.component.client.infraestructure.ClientRepository;
import securityservices.infrastructure.db.postgreadapters.ClientPostgreRepository;

public class ClientPersistenceCatalog {

    public static ClientRepository getInstance() {
        return new ClientPostgreRepository(PersistenceConnectionFactory.getDataConnection());
    }
}
