package securityservices.userinterface.web.restadapters;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(securityservices.userinterface.web.restadapters.ClientsResource.class);
        resources.add(securityservices.userinterface.web.restadapters.ClientsfileResource.class);
        resources.add(securityservices.userinterface.web.restadapters.EquipmentsResource.class);
        resources.add(securityservices.userinterface.web.restadapters.EquipmentsfileResource.class);
        resources.add(securityservices.userinterface.web.restadapters.OrdersResource.class);
        resources.add(securityservices.userinterface.web.restadapters.OrdersfileResource.class);
        resources.add(securityservices.userinterface.web.restadapters.ServicesResource.class);
        resources.add(securityservices.userinterface.web.restadapters.ServicesfileResource.class);
    }
}
