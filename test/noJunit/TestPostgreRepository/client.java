package noJunit.TestPostgreRepository;

import java.util.List;
import securityservices.core.component.client.appservices.JsonClientSerializer;
import securityservices.core.component.client.domain.model.Client;
import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.core.component.client.domain.services.ClientMapper;
import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;
import securityservices.infrastructure.db.postgreadapters.ClientPostgreRepository;
import securityservices.shared.responses.ResultRequest;

public class client {

    public static void main(String[] args) {
        Json JConn = JsonObjectFactory.getInstance();
        JConn.set("db_driver", "postgresql");
        JConn.set("db_address", "localhost");
        JConn.set("db_database", "SecurityServices");
        JConn.set("db_user", "SecurityServices");
        JConn.set("db_password", "linuxlinux");

        ClientPostgreRepository CPR = new ClientPostgreRepository(JConn);
        JsonClientSerializer JCSerializer = new JsonClientSerializer(); 
        ResultRequest<String> result;
        ResultRequest<ClientDTO> cdtoRR;
        Client client;
        ClientDTO cdto;
        String code = "C-05";
        
        //Comprobar método add   
        System.out.println("---- Comprobar método add");
        cdto = new ClientDTO(code, "Pablo", "Perico los palotes", "123456789", "pablo@gmail.com", true,
                "31-octubre-2000", "a1s4dc5x-1aa8-461b-a0f1-a54s1xxc14a8", 8, "linuxlinux");
        result = CPR.add(cdto);
        if (result.failed()) {
            System.out.println(result.getError());
        } else {
            System.out.println(result.getValue());
        }

        System.out.println();

        System.out.println("---- ---- Comprobar método exists");
        if (CPR.exists(code)) {
            System.out.println("El client con id " + code + " existe");
        }

        System.out.println("---- Comprobar método delete del client creado");
        result = CPR.deleteByID(code);
        if (result.failed()) {
            System.out.println(result.getError());
        } else {
            System.out.println(result.getValue());
            if (!CPR.exists(code)) {
                System.out.println("---- ---- Comprobar que no exista");
                System.out.println("El client con id " + code + " NO existe");
            }
        }

        System.out.println();

        //Comprobar método getByID
        System.out.println("---- Comprobar método getByID");
        cdtoRR = CPR.getByID("C-01");
        if (cdtoRR.failed()) {
            System.out.println(cdtoRR.getError());
        } else {
            System.out.println(JCSerializer.serialize(cdtoRR.getValue()).getValue());

            System.out.println();

            //Comprobar método update
            System.out.println("---- Comprobar método update del client C-01");
            ResultRequest<Client> serviceUnMapped = ClientMapper.componentFromDTO(cdtoRR.getValue());
            if (serviceUnMapped.failed()) {
                System.out.println(serviceUnMapped.getError());
            } else {
                client = serviceUnMapped.getValue();
                client.setName("DavidUpdated");
                result = CPR.update(ClientMapper.dtoFromComponent(client));
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
        ResultRequest<List<ClientDTO>> getAllClients = CPR.getAll();
        if (getAllClients.failed()) {
            System.out.println(getAllClients.getError());
        } else {
            //Mostrar todos los clients
            for (int i = 0; i < getAllClients.getValue().size(); i++) {
                result = JCSerializer.serialize(getAllClients.getValue().get(i));
                System.out.println("Client " + i + ": " + result.getValue());
            }
        }
    }
}
