package noJunit.TestFileAdapters;

import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.infrastructure.filesystemadapters.FileManager;
import securityservices.core.ports.infrastructure.FilePort;
import securityservices.core.shared.services.serializers.Serializer;
import securityservices.infrastructure.filesystemadapters.FileAdapter;
import securityservices.managment.catalogs.serializers.SerializerCatalog;
import securityservices.managment.catalogs.serializers.SerializerType;
import securityservices.shared.responses.ResultRequest;

public class TestFileAdaptersOrder {

    public static void main(String[] args) {

        //OBJETOS DTO PARA LA DEMO, MAPPERS Y METODO FACTORIA DE CLIENT YA ESTA PROBADO                       
        OrderDTO odto1 = new OrderDTO("code", 1, 2.2, 0.3, "type", "status",
                    "additional",
                    "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30","Ref-1,1,5.3;Ref-2,5,2.3;Ref-3,2,10.3;", "id");
        OrderDTO odto2, odto3;

        //OBJETO ENCARGADO DE LEER Y ESCRIBIR FICHEROS
        FilePort fileAdapter = new FileAdapter();
        //OBJETO QUE GESTIONA LOS NOMBRES Y RUTAS A UTILIZAR
        FileManager fileManager = new FileManager(fileAdapter);

        //SERIALITZACIO JSON OBTENIR EL OBJECTE A TRAVES DEL CATALOG/DICCIONARI QUE ENS PROPORCIONA L'OBJECTE ADIENT
        Serializer orderSerializer = SerializerCatalog.getInstance(SerializerType.JsonOrder);

        ResultRequest<String> orderSerialized = orderSerializer.serialize(odto1);
        String dataOrder = orderSerialized.getValue();
        System.out.println(dataOrder);

        //ESCRITURA DEL FICHERO
        fileManager.write(dataOrder, "order", "json");

        //LECTURA DEL FICHERO ("UTILIZAD CADA UNO EL NOMBRE DE FICHERO NECESARIO")
        ResultRequest<String> dataOrderFile = fileManager.read("c:\\files\\order\\order_03_03_2021_15_17_37.json");
        if (dataOrderFile.failed()) {
            System.out.println(dataOrderFile.getError());
        } else {
            dataOrder = dataOrderFile.getValue();
            ResultRequest<OrderDTO> orderUnserialized = orderSerializer.unserialize(dataOrder);

            if (orderUnserialized.failed()) {
                System.out.println(orderUnserialized.getError());
            } else {
                odto2 = orderUnserialized.getValue();
                //SERIALITZACIO XML AMB LA LLIBRERIA DOM SENSE QUE EL CLIENT TINGUI CONSTANCIA GRACIES AL CATALOG
                orderSerializer = SerializerCatalog.getInstance(SerializerType.XmlOrder);

                orderSerialized = orderSerializer.serialize(odto2);
                dataOrder = orderSerialized.getValue();
                System.out.println(dataOrder);
                fileManager.write(dataOrder, "order", "xml");

                dataOrderFile = fileManager.read("c:\\files\\order\\order_03_03_2021_15_18_36.xml");
                if (dataOrderFile.failed()) {
                    System.out.println(dataOrderFile.getError());
                } else {
                    dataOrder = dataOrderFile.getValue();
                    orderUnserialized = orderSerializer.unserialize(dataOrder);
                    if (orderUnserialized.failed()) {
                        System.out.println(orderUnserialized.getError());
                    } else {
                        odto3 = orderUnserialized.getValue();
                        System.out.println(odto3);
                    }
                }
            }
        }
    }
}
