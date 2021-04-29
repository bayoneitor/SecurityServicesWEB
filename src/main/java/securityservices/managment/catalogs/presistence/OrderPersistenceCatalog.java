package securityservices.managment.catalogs.presistence;

import securityservices.core.component.order.infraestructure.OrderRepository;
import securityservices.infrastructure.db.postgreadapters.OrderPostgreRepository;

public class OrderPersistenceCatalog {

    public static OrderRepository getInstance() {
        return new OrderPostgreRepository(PersistenceConnectionFactory.getDataConnection());
    }
}
