package noJunit.TestPostgreRepository;

import java.util.List;
import securityservices.core.component.service.appservices.JsonServiceSerializer;
import securityservices.core.component.service.domain.model.Service;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.core.component.service.domain.services.ServiceMapper;
import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;
import securityservices.infrastructure.db.postgreadapters.ServicePostgreRepository;
import securityservices.shared.responses.ResultRequest;

public class service {

    public static void main(String[] args) {
        Json JConn = JsonObjectFactory.getInstance();
        JConn.set("db_driver", "postgresql");
        JConn.set("db_address", "localhost");
        JConn.set("db_database", "SecurityServices");
        JConn.set("db_user", "SecurityServices");
        JConn.set("db_password", "linuxlinux");

        ServicePostgreRepository SPR = new ServicePostgreRepository(JConn);
        JsonServiceSerializer JSSerializer = new JsonServiceSerializer();
        ResultRequest<String> result;
        ResultRequest<ServiceDTO> sdtoRR;
        Service service;
        ServiceDTO sdto;
        String code = "S-05";

        //Comprobar método add   
        System.out.println("---- Comprobar método add");
        sdto = new ServiceDTO(code, "Sistemas Informaticos", "Sistemas", "Bayona SL", "Instalacion S.O", 25.99,
                "anual", "Debes tener un equipo", "21-01-2020", null, "123");
        result = SPR.add(sdto);
        if (result.failed()) {
            System.out.println(result.getError());
        } else {
            System.out.println(result.getValue());
        }

        System.out.println();

        System.out.println("---- ---- Comprobar que exista");
        if (SPR.exists(code)) {
            System.out.println("El service con id " + code + " existe");
        }

        System.out.println("---- Comprobar método delete del service creado");
        result = SPR.deleteByID(code);
        if (result.failed()) {
            System.out.println(result.getError());
        } else {
            System.out.println(result.getValue());
            if (!SPR.exists(code)) {
                System.out.println("---- ---- Comprobar que no exista");
                System.out.println("El service con id " + code + " NO existe");
            }
        }

        System.out.println();

        //Comprobar método getByID
        System.out.println("---- Comprobar método getByID");
        sdtoRR = SPR.getByID("S-01");
        if (sdtoRR.failed()) {
            System.out.println(sdtoRR.getError());
        } else {
            System.out.println(JSSerializer.serialize(sdtoRR.getValue()).getValue());

            System.out.println();

            //Comprobar método update
            System.out.println("---- Comprobar método update del service S-01");
            ResultRequest<Service> serviceUnMapped = ServiceMapper.componentFromDTO(sdtoRR.getValue());
            if (serviceUnMapped.failed()) {
                System.out.println(serviceUnMapped.getError());
            } else {
                service = serviceUnMapped.getValue();
                service.setName("Limpieza Ordenadores Updated");
                result = SPR.update(ServiceMapper.dtoFromComponent(service));
                if (result.failed()) {
                    System.out.println(result.getError());
                } else {
                    System.out.println(result.getValue());
                }
            }
        }
        //Comprobar método getAll
        System.out.println();

        System.out.println("---- Comprobar método getAll");
        ResultRequest<List<ServiceDTO>> getAllServices = SPR.getAll();
        if (getAllServices.failed()) {
            System.out.println(getAllServices.getError());
        } else {
            //Mostrar todos los services
            for (int i = 0; i < getAllServices.getValue().size(); i++) {
                result = JSSerializer.serialize(getAllServices.getValue().get(i));
                System.out.println("Service " + i + ": " + result.getValue());
            }
        }
    }
}
