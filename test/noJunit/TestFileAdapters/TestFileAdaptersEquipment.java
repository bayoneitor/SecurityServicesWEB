package noJunit.TestFileAdapters;

import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.infrastructure.filesystemadapters.FileManager;
import securityservices.core.ports.infrastructure.FilePort;
import securityservices.core.shared.services.serializers.Serializer;
import securityservices.infrastructure.filesystemadapters.FileAdapter;
import securityservices.managment.catalogs.serializers.SerializerCatalog;
import securityservices.managment.catalogs.serializers.SerializerType;
import securityservices.shared.responses.ResultRequest;

public class TestFileAdaptersEquipment {

    public static void main(String[] args) {

        //OBJETOS DTO PARA LA DEMO, MAPPERS Y METODO FACTORIA DE CLIENT YA ESTA PROBADO                       
        EquipmentDTO edto1 = new EquipmentDTO("code", "name", "type", "maker", "description",
                    5.55, 0.3, 0.2, 0.45, 1.3, true,
                    "function", "components", 1, "id");
        EquipmentDTO edto2, edto3;

        //OBJETO ENCARGADO DE LEER Y ESCRIBIR FICHEROS
        FilePort fileAdapter = new FileAdapter();
        //OBJETO QUE GESTIONA LOS NOMBRES Y RUTAS A UTILIZAR
        FileManager fileManager = new FileManager(fileAdapter);

        //SERIALITZACIO JSON OBTENIR EL OBJECTE A TRAVES DEL CATALOG/DICCIONARI QUE ENS PROPORCIONA L'OBJECTE ADIENT
        Serializer equipmentSerializer = SerializerCatalog.getInstance(SerializerType.JsonEquipment);

        ResultRequest<String> equipmentSerialized = equipmentSerializer.serialize(edto1);
        String dataEquipment = equipmentSerialized.getValue();
        System.out.println(dataEquipment);

        //ESCRITURA DEL FICHERO
        fileManager.write(dataEquipment, "equipment", "json");

        //LECTURA DEL FICHERO ("UTILIZAD CADA UNO EL NOMBRE DE FICHERO NECESARIO")
        ResultRequest<String> dataEquipmentFile = fileManager.read("c:\\files\\equipment\\equipment_01_03_2021_20_26_16.json");
        if (dataEquipmentFile.failed()) {
            System.out.println(dataEquipmentFile.getError());
        } else {
            dataEquipment = dataEquipmentFile.getValue();
            ResultRequest<EquipmentDTO> equipmentUnserialized = equipmentSerializer.unserialize(dataEquipment);

            if (equipmentUnserialized.failed()) {
                System.out.println(equipmentUnserialized.getError());
            } else {
                edto2 = equipmentUnserialized.getValue();
                //SERIALITZACIO XML AMB LA LLIBRERIA DOM SENSE QUE EL CLIENT TINGUI CONSTANCIA GRACIES AL CATALOG
                equipmentSerializer = SerializerCatalog.getInstance(SerializerType.XmlEquipment);

                equipmentSerialized = equipmentSerializer.serialize(edto2);
                dataEquipment = equipmentSerialized.getValue();
                System.out.println(dataEquipment);
                fileManager.write(dataEquipment, "equipment", "xml");

                dataEquipmentFile = fileManager.read("c:\\files\\equipment\\equipment_01_03_2021_20_27_01.xml");
                if (dataEquipmentFile.failed()) {
                    System.out.println(dataEquipmentFile.getError());
                } else {
                    dataEquipment = dataEquipmentFile.getValue();
                    equipmentUnserialized = equipmentSerializer.unserialize(dataEquipment);
                    if (equipmentUnserialized.failed()) {
                        System.out.println(equipmentUnserialized.getError());
                    } else {
                        edto3 = equipmentUnserialized.getValue();
                        System.out.println(edto3);
                    }
                }
            }
        }
    }
}
