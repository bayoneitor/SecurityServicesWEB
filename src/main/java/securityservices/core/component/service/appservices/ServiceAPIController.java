package securityservices.core.component.service.appservices;

//Controlador genèric que adquireix la classe que ens connecta amb persistència
import securityservices.core.component.service.appservices.*;
import securityservices.core.component.service.infraestructure.ServiceRepository;
import securityservices.managment.catalogs.presistence.ServicePersistenceCatalog;

public abstract class ServiceAPIController {

    protected ServiceRepository serviceRepository;
    //Constructor que rep l’objecte per injecció de dependències que ens connecta a persistència

    public ServiceAPIController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    //Constructor que demana a la capa de managment l’objecte que ens connecta a persistència
    public ServiceAPIController() {
        this.serviceRepository = ServicePersistenceCatalog.getInstance();
    }
}
