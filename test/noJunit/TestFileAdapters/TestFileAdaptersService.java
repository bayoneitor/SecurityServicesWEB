package noJunit.TestFileAdapters;

import securityservices.infrastructure.filesystemadapters.FileManager;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.core.ports.infrastructure.FilePort;
import securityservices.core.shared.services.serializers.Serializer;
import securityservices.infrastructure.filesystemadapters.FileAdapter;
import securityservices.managment.catalogs.serializers.SerializerCatalog;
import securityservices.managment.catalogs.serializers.SerializerType;
import securityservices.shared.responses.ResultRequest;

public class TestFileAdaptersService {

    public static void main(String[] args) {

        //OBJETOS DTO PARA LA DEMO, MAPPERS Y METODO FACTORIA DE CLIENT YA ESTA PROBADO                       
        ServiceDTO sdto1 = new ServiceDTO("01", "nombre", "instalacion", "Artero SL", "Instalacion descripcion", 90.32, "periocidadasdasd", "condiciones", "21-01-2020", "21-01-2021", "id");
        ServiceDTO sdto2, sdto3;

        //OBJETO ENCARGADO DE LEER Y ESCRIBIR FICHEROS
        FilePort fileAdapter = new FileAdapter();
        //OBJETO QUE GESTIONA LOS NOMBRES Y RUTAS A UTILIZAR
        FileManager fileManager = new FileManager(fileAdapter);

        //SERIALITZACIO JSON OBTENIR EL OBJECTE A TRAVES DEL CATALOG/DICCIONARI QUE ENS PROPORCIONA L'OBJECTE ADIENT
        Serializer serviceSerializer = SerializerCatalog.getInstance(SerializerType.JsonService);

        ResultRequest<String> serviceSerialized = serviceSerializer.serialize(sdto1);
        String dataService = serviceSerialized.getValue();
        System.out.println(dataService);

        //ESCRITURA DEL FICHERO
        fileManager.write(dataService, "service", "json");

        //LECTURA DEL FICHERO ("UTILIZAD CADA UNO EL NOMBRE DE FICHERO NECESARIO")
        ResultRequest<String> dataServiceFile = fileManager.read("c:\\files\\service\\service_01_03_2021_20_05_54.json");
        if (dataServiceFile.failed()) {
            System.out.println(dataServiceFile.getError());
        } else {
            dataService = dataServiceFile.getValue();
            ResultRequest<ServiceDTO> serviceUnserialized = serviceSerializer.unserialize(dataService);

            if (serviceUnserialized.failed()) {
                System.out.println(serviceUnserialized.getError());
            } else {
                sdto2 = serviceUnserialized.getValue();
                //SERIALITZACIO XML AMB LA LLIBRERIA DOM SENSE QUE EL CLIENT TINGUI CONSTANCIA GRACIES AL CATALOG
                serviceSerializer = SerializerCatalog.getInstance(SerializerType.XmlService);

                serviceSerialized = serviceSerializer.serialize(sdto2);
                dataService = serviceSerialized.getValue();
                System.out.println(dataService);
                fileManager.write(dataService, "service", "xml");

                dataServiceFile = fileManager.read("c:\\files\\service\\service_01_03_2021_20_11_05.xml");
                if (dataServiceFile.failed()) {
                    System.out.println(dataServiceFile.getError());
                } else {
                    dataService = dataServiceFile.getValue();
                    serviceUnserialized = serviceSerializer.unserialize(dataService);
                    if (serviceUnserialized.failed()) {
                        System.out.println(serviceUnserialized.getError());
                    } else {
                        sdto3 = serviceUnserialized.getValue();
                        System.out.println(sdto3);
                    }
                }
            }
        }
    }
}
