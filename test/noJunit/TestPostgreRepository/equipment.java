package noJunit.TestPostgreRepository;

import java.util.List;
import securityservices.core.component.equipment.appservices.JsonEquipmentSerializer;
import securityservices.core.component.equipment.domain.model.Equipment;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.core.component.equipment.domain.services.EquipmentMapper;
import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;
import securityservices.infrastructure.db.postgreadapters.EquipmentPostgreRepository;
import securityservices.shared.responses.ResultRequest;

public class equipment {

    public static void main(String[] args) {
        Json JConn = JsonObjectFactory.getInstance();
        JConn.set("db_driver", "postgresql");
        JConn.set("db_address", "localhost");
        JConn.set("db_database", "SecurityServices");
        JConn.set("db_user", "SecurityServices");
        JConn.set("db_password", "linuxlinux");

        EquipmentPostgreRepository EPR = new EquipmentPostgreRepository(JConn);
        JsonEquipmentSerializer JESerializer = new JsonEquipmentSerializer();
        ResultRequest<String> result;
        ResultRequest<EquipmentDTO> edtoRR;
        Equipment equipment;
        EquipmentDTO edto;
        String code = "E-05";
        
        //Comprobar método add   
        System.out.println("---- Comprobar método add");
        edto = new EquipmentDTO(code, "Servidor", "tipo", "ASUS", "Servidor de Linux", 499.99, 1.2, 1.5, 1.2, 5.8, true, "Dar servicios", "Servidor", 233, "123");
        result = EPR.add(edto);
        if (result.failed()) {
            System.out.println(result.getError());
        } else {
            System.out.println(result.getValue());
        }

        System.out.println();

        System.out.println("---- ---- Comprobar método exists");
        if (EPR.exists(code)) {
            System.out.println("El equipment con id " + code + " existe");
        }

        System.out.println("---- Comprobar método delete del equipment creado");
        result = EPR.deleteByID(code);
        if (result.failed()) {
            System.out.println(result.getError());
        } else {
            System.out.println(result.getValue());
            if (!EPR.exists(code)) {
                System.out.println("---- ---- Comprobar que no exista");
                System.out.println("El equipment con id " + code + " NO existe");
            }
        }

        System.out.println();

        //Comprobar método getByID
        System.out.println("---- Comprobar método getByID");
        edtoRR = EPR.getByID("E-01");
        if (edtoRR.failed()) {
            System.out.println(edtoRR.getError());
        } else {
            System.out.println(JESerializer.serialize(edtoRR.getValue()).getValue());

            System.out.println();

            //Comprobar método update
            System.out.println("---- Comprobar método update del equipment E-01");
            ResultRequest<Equipment> serviceUnMapped = EquipmentMapper.componentFromDTO(edtoRR.getValue());
            if (serviceUnMapped.failed()) {
                System.out.println(serviceUnMapped.getError());
            } else {
                equipment = serviceUnMapped.getValue();
                equipment.setName("Equipment Acutalizado");
                result = EPR.update(EquipmentMapper.dtoFromComponent(equipment));
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
        ResultRequest<List<EquipmentDTO>> getAllEquipments = EPR.getAll();
        if (getAllEquipments.failed()) {
            System.out.println(getAllEquipments.getError());
        } else {
            //Mostrar todos los equipments
            for (int i = 0; i < getAllEquipments.getValue().size(); i++) {
                result = JESerializer.serialize(getAllEquipments.getValue().get(i));
                System.out.println("Equipment " + i + ": " + result.getValue());
            }
        }
    }
}
