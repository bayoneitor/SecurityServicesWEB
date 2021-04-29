package securityservices.core.component.order.appservices;

//Controlador genèric que adquireix la classe que ens connecta amb persistència
import securityservices.core.component.order.infraestructure.OrderRepository;
import securityservices.managment.catalogs.presistence.OrderPersistenceCatalog;

public abstract class OrderAPIController {

    protected OrderRepository orderRepository;
    //Constructor que rep l’objecte per injecció de dependències que ens connecta a persistència

    public OrderAPIController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //Constructor que demana a la capa de managment l’objecte que ens connecta a persistència
    public OrderAPIController() {
        this.orderRepository = OrderPersistenceCatalog.getInstance();
    }
}
