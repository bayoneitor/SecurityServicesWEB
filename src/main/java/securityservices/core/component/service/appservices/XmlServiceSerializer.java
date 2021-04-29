package securityservices.core.component.service.appservices;

import securityservices.core.component.equipment.appservices.*;
import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.core.shared.services.serializers.Serializer;
import securityservices.core.shared.services.serializers.Xml;
import securityservices.shared.responses.ResultRequest;

public class XmlServiceSerializer implements Serializer {

    private Xml xmlConverter;

    public XmlServiceSerializer(Xml xmlConvert) {
        this.xmlConverter = xmlConvert;
    }

    @Override
    public ResultRequest<ServiceDTO> unserialize(String data) {
        xmlConverter.set(data);
        try {
            ServiceDTO service = new ServiceDTO(
                    xmlConverter.getValueNode("code"),
                    xmlConverter.getValueNode("name"),
                    xmlConverter.getValueNode("type"),
                    xmlConverter.getValueNode("maker"),
                    xmlConverter.getValueNode("description"),
                    Double.valueOf(xmlConverter.getValueNode("price")),
                    xmlConverter.getValueNode("periodicity"),
                    xmlConverter.getValueNode("conditions"),
                    xmlConverter.getValueNode("startDate"),
                    xmlConverter.getValueNode("finishDate"),
                    xmlConverter.getValueNode("serviceId")
            );
            return ResultRequest.done(service);
        } catch (Exception e) {
            return ResultRequest.fails("{\"Error\":\"ClientDTO unserialized Exception\","
                    + "\"Details\":\"" + e.toString() + "\"}");
        }
    }

    @Override
    public ResultRequest<String> serialize(Object service) {
        xmlConverter.createDocument();
        xmlConverter.setRootNode("service");
        xmlConverter.setNode("code", ((ServiceDTO) service).getCode());
        xmlConverter.setNode("name", ((ServiceDTO) service).getName());
        xmlConverter.setNode("type", ((ServiceDTO) service).getType());
        xmlConverter.setNode("maker", ((ServiceDTO) service).getMaker());
        xmlConverter.setNode("description", ((ServiceDTO) service).getDescription());
        xmlConverter.setNode("price", String.valueOf(((ServiceDTO) service).getPrice()));
        xmlConverter.setNode("periodicity", ((ServiceDTO) service).getPeriodicity());
        xmlConverter.setNode("conditions", ((ServiceDTO) service).getConditions());
        xmlConverter.setNode("startDate", ((ServiceDTO) service).getStartDate());
        xmlConverter.setNode("finishDate", ((ServiceDTO) service).getFinishDate());
        xmlConverter.setNode("serviceId", ((ServiceDTO) service).getServiceId());

        return ResultRequest.done(xmlConverter.toString());
    }
}
