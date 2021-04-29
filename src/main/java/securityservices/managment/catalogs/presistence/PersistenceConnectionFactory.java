package securityservices.managment.catalogs.presistence;

import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;

public class PersistenceConnectionFactory {

    private static Json postgreConnection() {
        Json jcon = JsonObjectFactory.getInstance();
        jcon.set("db_driver", "postgresql");
        jcon.set("db_address", "localhost");
        jcon.set("db_database", "SecurityServices");
        jcon.set("db_user", "SecurityServices");
        jcon.set("db_password", "linuxlinux");
        return jcon;
    }

    public static Json getDataConnection() {
        return postgreConnection();

    }
}
