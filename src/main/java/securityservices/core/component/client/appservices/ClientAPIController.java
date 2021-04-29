package securityservices.core.component.client.appservices;

//Controlador genèric que adquireix la classe que ens connecta amb persistència
import securityservices.core.component.client.infraestructure.ClientRepository;
import securityservices.managment.catalogs.presistence.ClientPersistenceCatalog;

public abstract class ClientAPIController {

    protected ClientRepository clientRepository;
    //Constructor que rep l’objecte per injecció de dependències que ens connecta a persistència

    public ClientAPIController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //Constructor que demana a la capa de managment l’objecte que ens connecta a persistència
    public ClientAPIController() {
        this.clientRepository = ClientPersistenceCatalog.getInstance();
    }
}
