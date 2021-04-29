package noJunit;

import securityservices.core.component.client.appservices.JaxbClientSerializer;
import securityservices.core.component.client.appservices.JsonClientSerializer;
import securityservices.core.component.client.appservices.XmlClientSerializer;
import securityservices.core.component.client.domain.model.Client;
import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.core.component.client.domain.services.ClientMapper;
import securityservices.core.component.equipment.appservices.JsonEquipmentSerializer;
import securityservices.core.component.equipment.appservices.XmlEquipmentSerializer;
import securityservices.core.component.equipment.domain.model.Equipment;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.core.component.equipment.domain.services.EquipmentMapper;
import securityservices.core.component.order.appservices.JaxbOrderSerializer;
import securityservices.core.component.order.appservices.JsonOrderSerializer;
import securityservices.core.component.order.domain.model.Order;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.core.component.order.domain.services.OrderMapper;
import securityservices.core.component.service.appservices.JsonServiceSerializer;
import securityservices.core.component.service.appservices.XmlServiceSerializer;
import securityservices.core.component.service.domain.model.Service;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.core.component.service.domain.services.ServiceMapper;
import securityservices.core.shared.services.serializers.Serializer;
import securityservices.core.shared.services.serializers.Xml;
import securityservices.core.shared.services.serializers.xmlapis.Dom;
import securityservices.core.shared.services.serializers.xmlapis.JDom;
import securityservices.shared.responses.ResultRequest;

public class TestSerializers {

    public static void main(String[] args) {

        //CLIENT
        System.out.println("CLIENT ------------------");
        System.out.println("------ JSON -----------------------");

        //OBJETOS CLIENT PARA LA DEMO     
        Client client1 = null, auxClient = null;

        ResultRequest<Client> clientRequest = Client.getInstance("001", "jose", "carrer kalea 1", "666555444", "josem@gmail.cat",
                false, "20-febrero-1997", 3, "***");
        if (clientRequest.failed()) {
            System.out.println(clientRequest.getError());
        } else {
            client1 = clientRequest.getValue();
        }

        if (client1 != null) {

            ClientDTO cdto1 = ClientMapper.dtoFromComponent(client1);
            ClientDTO auxCdto;

            //SERIALITZACIO JSON AMB UNA LLIBRERIA ESTABLERTA PER DEFECTE
            JsonClientSerializer jcSerializer = new JsonClientSerializer();

            ResultRequest<String> clientSerialized = jcSerializer.serialize(cdto1);

            String jsonClient = clientSerialized.getValue();
            System.out.println(jsonClient);

            String newJsonClient = "{"
                    + "  \"birthday\": \"22-febrero-1987\","
                    + "  \"code\": \"023\","
                    + "  \"clientid\": \"1d335c53-f5c4-48da-9a6a-e6f766c67a76\","
                    + "  \"address\": \"carrer kalea 13\","
                    + "  \"phone\": \"666555888\","
                    + "  \"name\": \"alex\","
                    + "  \"numequipments\": \"4\","
                    + "  \"password\": \"xela\","
                    + "  \"iscompany\": \"false\","
                    + "  \"email\": \"alexm@gmail.cat\""
                    + "}";

            ResultRequest<ClientDTO> clientUnserialized = jcSerializer.unserialize(newJsonClient);

            if (clientUnserialized.failed()) {
                System.out.println(clientUnserialized.getError());

            } else {
                auxCdto = clientUnserialized.getValue();
                ResultRequest<Client> clientUnMapped = ClientMapper.componentFromDTO(auxCdto);

                if (clientUnMapped.failed()) {
                    System.out.println(clientUnMapped.getError());
                } else {
                    auxClient = clientUnMapped.getValue();
                }
            }
            System.out.println("------ XML -----------------------");
            //SERIALITZACIO XML AMB LA LLIBRERIA DOM (revisseu que aprofitem els components adients de la serialitzacio anterior)
            Xml xmlConverter = new Dom();
            Serializer xmlClientSerializer = new XmlClientSerializer(xmlConverter);

            clientSerialized = xmlClientSerializer.serialize(cdto1);

            String xmlClient = clientSerialized.getValue();
            System.out.println(xmlClient);

            //APROFITEM EL MATEIX STRING AMB EL XML PER SIMULAR LA ENTRADA D'UN DOCUMENT XML AL SISTEMA
            clientUnserialized = xmlClientSerializer.unserialize(xmlClient);

            if (clientUnserialized.failed()) {
                System.out.println(clientUnserialized.getError());
            } else {
                auxCdto = clientUnserialized.getValue();
                ResultRequest<Client> clientUnMapped = ClientMapper.componentFromDTO(auxCdto);
                if (clientUnMapped.failed()) {
                    System.out.println(clientUnMapped.getError());
                } else {
                    auxClient = clientUnMapped.getValue();
                }
            }
            System.out.println("------ JAXB -----------------------");
            //SERIALITZACIO XML AMB LA LLIBRERIA JAXB (revisseu que aprofitem els components adients de la serialitzacio anterior)
            ResultRequest<JaxbClientSerializer> jaxbClientSerializer = JaxbClientSerializer.getInstance();

            if (jaxbClientSerializer.failed()) {
                System.out.println(jaxbClientSerializer.getError());
            } else {
                xmlClientSerializer = jaxbClientSerializer.getValue();
                clientSerialized = xmlClientSerializer.serialize(cdto1);

                xmlClient = clientSerialized.getValue();
                System.out.println(xmlClient);

                clientUnserialized = xmlClientSerializer.unserialize(xmlClient);

                if (clientUnserialized.failed()) {
                    System.out.println(clientUnserialized.getError());

                } else {
                    auxCdto = clientUnserialized.getValue();
                    ResultRequest<Client> clientUnMapped = ClientMapper.componentFromDTO(auxCdto);

                    if (clientUnMapped.failed()) {
                        System.out.println(clientUnMapped.getError());
                    } else {
                        auxClient = clientUnMapped.getValue();
                    }
                }
            }
            //SERVICE
            System.out.println("SERVICE ------------------");
            System.out.println("------ JSON -----------------------");

            Service service1 = null, auxService = null;
            ServiceDTO auxSdto = null;

            ResultRequest<Service> serviceRequest = Service.getInstance("01", "nombre", "instalacion", "Artero SL", "Instalacion descripcion", 90.32, "periocidadasdasd", "condiciones", "21-01-2020", "21-01-2021");
            if (serviceRequest.failed()) {
                System.out.println(serviceRequest.getError());
            } else {
                service1 = serviceRequest.getValue();
            }

            if (service1 != null) {
                ServiceDTO sdto1 = ServiceMapper.dtoFromComponent(service1);

                JsonServiceSerializer jsSerializer = new JsonServiceSerializer();

                ResultRequest<String> serviceSerialized = jsSerializer.serialize(sdto1);

                String jsonService = serviceSerialized.getValue();
                System.out.println(jsonService);

                String newJsonService = "{"
                        + "\"code\": \"01\","
                        + "\"price\": \"90.32\","
                        + "\"name\": \"nombre\","
                        + "\"periodicity\": \"periocidadasdasd\","
                        + "\"maker\": \"Artero SL\","
                        + "\"description\": \"Instalacion descripcion\","
                        + "\"finishDate\": \"21-01-2021\","
                        + "\"type\": \"instalacion\","
                        + "\"conditions\": \"condiciones\","
                        + "\"startDate\": \"21-01-2021\""
                        + "}";
                ResultRequest<ServiceDTO> serviceUnserialized = jsSerializer.unserialize(newJsonService);
                if (serviceUnserialized.failed()) {
                    System.out.println(serviceUnserialized.getError());
                } else {
                    auxSdto = serviceUnserialized.getValue();
                    ResultRequest<Service> serviceUnMapped = ServiceMapper.componentFromDTO(auxSdto);

                    if (serviceUnMapped.failed()) {
                        System.out.println(serviceUnMapped.getError());
                    } else {
                        auxService = serviceUnMapped.getValue();
                    }
                }
                System.out.println("------ XML -----------------------");
                //SERIALITZACIO XML AMB LA LLIBRERIA DOM (revisseu que aprofitem els components adients de la serialitzacio anterior)
                xmlConverter = new Dom();
                Serializer xmlServiceSerializer = new XmlServiceSerializer(xmlConverter);

                serviceSerialized = xmlServiceSerializer.serialize(sdto1);

                String xmlService = serviceSerialized.getValue();
                System.out.println(xmlService);

                //APROFITEM EL MATEIX STRING AMB EL XML PER SIMULAR LA ENTRADA D'UN DOCUMENT XML AL SISTEMA
                serviceUnserialized = xmlServiceSerializer.unserialize(xmlService);

                if (serviceUnserialized.failed()) {
                    System.out.println(serviceUnserialized.getError());
                } else {
                    auxSdto = serviceUnserialized.getValue();
                    ResultRequest<Service> serviceUnMapped = ServiceMapper.componentFromDTO(auxSdto);
                    if (serviceUnMapped.failed()) {
                        System.out.println(serviceUnMapped.getError());
                    } else {
                        auxService = serviceUnMapped.getValue();
                    }
                }
            }

            //EQUIPMENT
            System.out.println("EQUIPMENT ------------------");
            System.out.println("------ JSON -----------------------");
            Equipment equipment1 = null, auxEquipment = null;
            EquipmentDTO auxEdto = null;

            ResultRequest<Equipment> equipmentRequest = Equipment.getInstance("code", "name", "type", "maker", "description",
                    5.55, 0.3, 0.2, 0.45, 1.3, true,
                    "function", "components", 1);
            if (equipmentRequest.failed()) {
                System.out.println(equipmentRequest.getError());
            } else {
                equipment1 = equipmentRequest.getValue();
            }

            if (equipment1 != null) {
                EquipmentDTO edto1 = EquipmentMapper.dtoFromComponent(equipment1);

                JsonEquipmentSerializer jsSerializer = new JsonEquipmentSerializer();

                ResultRequest<String> equipmentSerialized = jsSerializer.serialize(edto1);

                String jsonEquipment = equipmentSerialized.getValue();
                System.out.println(jsonEquipment);

                String newJsonEquipment = "{"
                        + "\"deep\": \"0.45\","
                        + "\"components\": \"components\","
                        + "\"code\": \"code\","
                        + "\"wide\": \"0.2\","
                        + "\"maker\": \"maker\","
                        + "\"description\": \"description\","
                        + "\"weight\": \"1.3\","
                        + "\"type\": \"type\","
                        + "\"high\": \"0.3\","
                        + "\"price\": \"5.55\","
                        + "\"function\": \"function\","
                        + "\"name\": \"name\","
                        + "\"fragile\": \"caca\","
                        + "\"power\": \"1\""
                        + "}";
                ResultRequest<EquipmentDTO> equipmentUnserialized = jsSerializer.unserialize(newJsonEquipment);
                if (equipmentUnserialized.failed()) {
                    System.out.println(equipmentUnserialized.getError());
                } else {
                    auxEdto = equipmentUnserialized.getValue();
                    ResultRequest<Equipment> equipmentUnMapped = EquipmentMapper.componentFromDTO(auxEdto);

                    if (equipmentUnMapped.failed()) {
                        System.out.println(equipmentUnMapped.getError());
                    } else {
                        auxEquipment = equipmentUnMapped.getValue();
                    }
                }
                System.out.println("------ XML (JDOM) -----------------------");
                //SERIALITZACIO XML AMB LA LLIBRERIA DOM (revisseu que aprofitem els components adients de la serialitzacio anterior)
                xmlConverter = new JDom();
                Serializer xmlEquipmentSerializer = new XmlEquipmentSerializer(xmlConverter);

                equipmentSerialized = xmlEquipmentSerializer.serialize(edto1);

                String xmlEquipment = equipmentSerialized.getValue();
                System.out.println(xmlEquipment);

                //APROFITEM EL MATEIX STRING AMB EL XML PER SIMULAR LA ENTRADA D'UN DOCUMENT XML AL SISTEMA
                equipmentUnserialized = xmlEquipmentSerializer.unserialize(xmlEquipment);

                if (equipmentUnserialized.failed()) {
                    System.out.println(equipmentUnserialized.getError());
                } else {
                    auxEdto = equipmentUnserialized.getValue();
                    ResultRequest<Equipment> equipmentUnMapped = EquipmentMapper.componentFromDTO(auxEdto);
                    if (equipmentUnMapped.failed()) {
                        System.out.println(equipmentUnMapped.getError());
                    } else {
                        auxEquipment = equipmentUnMapped.getValue();
                    }
                }

            }

            //ORDER
            System.out.println("ORDER ------------------");
            System.out.println("------ JSON -----------------------");
            Order order1 = null, auxOrder = null;
            OrderDTO auxOdto = null;
            //Como se setea entonces el detalle
            ResultRequest<Order> orderRequest = Order.getInstance("code", 1, 2.2, 0.2, "type", "status",
                    "additional",
                    "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");

            if (orderRequest.failed()) {
                System.out.println(orderRequest.getError());
            } else {
                order1 = orderRequest.getValue();
                order1.setDetail("Ref-1,1,5.3");
                order1.setDetail("Ref-2,5,2.3");
                order1.setDetail("Ref-3,2,10.3");
            }
            
            OrderDTO odto1 = OrderMapper.dtoFromComponent(order1);

            JsonOrderSerializer jsSerializer = new JsonOrderSerializer();

            ResultRequest<String> orderSerialized = jsSerializer.serialize(odto1);

            String jsonOrder = orderSerialized.getValue();
            System.out.println(jsonOrder);
            //Si pones un detalle mal te dice el error de que producto y el error que tiene
            String newJsonOrder = "{\n"
                    + "  \"detailsShop\": [\n"
                    + "    {\n"
                    + "      \"ref\": \"Ref-1\",\n"
                    + "      \"amount\": \"1\",\n"
                    + "      \"price\": \"5.3\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "      \"ref\": \"Ref-2\",\n"
                    + "      \"amount\": \"5\",\n"
                    + "      \"price\": \"2.3\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "      \"ref\": \"Ref-3\",\n"
                    + "      \"amount\": \"2\",\n"
                    + "      \"price\": \"10.3\"\n"
                    + "    }\n"
                    + "  ],\n"
                    + "  \"creator\": \"1\",\n"
                    + "  \"code\": \"code\",\n"
                    + "  \"orderId\": \"2f236379-a07e-4c8b-b8b9-64723bf8b803\",\n"
                    + "  \"initDate\": \"28/01/2021-18:46:30\",\n"
                    + "  \"additionalInfo\": \"additional\",\n"
                    + "  \"type\": \"type\",\n"
                    + "  \"paymentType\": \"paymenttype\",\n"
                    + "  \"surcharges\": \"0.3\",\n"
                    + "  \"finishDate\": \"29/01/2021-18:46:30\",\n"
                    + "  \"paymentDate\": \"29/01/2021-18:46:30\",\n"
                    + "  \"value\": \"37.5122\",\n"
                    + "  \"status\": \"status\"\n"
                    + "}";
            ResultRequest<OrderDTO> orderUnserialized = jsSerializer.unserialize(newJsonOrder);
            if (orderUnserialized.failed()) {
                System.out.println(orderUnserialized.getError());
            } else {
                auxOdto = orderUnserialized.getValue();
                ResultRequest<Order> orderUnMapped = OrderMapper.componentFromDTO(auxOdto);

                if (orderUnMapped.failed()) {
                    System.out.println(orderUnMapped.getError());
                } else {
                    auxOrder = orderUnMapped.getValue();
                }
            }
            System.out.println("------ JAXB -----------------------");
            //SERIALITZACIO XML AMB LA LLIBRERIA JAXB (revisseu que aprofitem els components adients de la serialitzacio anterior)
            ResultRequest<JaxbOrderSerializer> jaxbOrderSerializer = JaxbOrderSerializer.getInstance();

            if (jaxbOrderSerializer.failed()) {
                System.out.println(jaxbOrderSerializer.getError());
            } else {
                //AQUI
                Serializer xmlOrderSerializer = jaxbOrderSerializer.getValue();
                orderSerialized = xmlOrderSerializer.serialize(odto1);

                String xmlOrder = orderSerialized.getValue();
                System.out.println(xmlOrder);

                orderUnserialized = xmlOrderSerializer.unserialize(xmlOrder);

                if (orderUnserialized.failed()) {
                    System.out.println(orderUnserialized.getError());

                } else {
                    auxOdto = orderUnserialized.getValue();
                    ResultRequest<Order> orderUnMapped = OrderMapper.componentFromDTO(auxOdto);

                    if (orderUnMapped.failed()) {
                        System.out.println(orderUnMapped.getError());
                    } else {
                        auxOrder = orderUnMapped.getValue();
                    }
                }
            }
        }
    }
}
