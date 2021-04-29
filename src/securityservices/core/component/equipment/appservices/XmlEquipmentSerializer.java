package securityservices.core.component.equipment.appservices;

import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.core.shared.services.serializers.Serializer;
import securityservices.core.shared.services.serializers.Xml;
import securityservices.shared.responses.ResultRequest;

public class XmlEquipmentSerializer implements Serializer {

    private Xml xmlConverter;

    public XmlEquipmentSerializer(Xml xmlConvert) {
        this.xmlConverter = xmlConvert;
    }

    @Override
    public ResultRequest<EquipmentDTO> unserialize(String data) {
        xmlConverter.set(data);
        try {
            EquipmentDTO equipment = new EquipmentDTO(
                    xmlConverter.getValueNode("code"),
                    xmlConverter.getValueNode("name"),
                    xmlConverter.getValueNode("type"),
                    xmlConverter.getValueNode("maker"),
                    xmlConverter.getValueNode("description"),
                    Double.valueOf(xmlConverter.getValueNode("price")),
                    Double.valueOf(xmlConverter.getValueNode("high")),
                    Double.valueOf(xmlConverter.getValueNode("wide")),
                    Double.valueOf(xmlConverter.getValueNode("deep")),
                    Double.valueOf(xmlConverter.getValueNode("weight")),
                    Boolean.valueOf(xmlConverter.getValueNode("fragile")),
                    xmlConverter.getValueNode("function"),
                    xmlConverter.getValueNode("components"),
                    Integer.valueOf(xmlConverter.getValueNode("power")),
                    xmlConverter.getValueNode("equipmentId")
            );
            return ResultRequest.done(equipment);
        } catch (Exception e) {
            return ResultRequest.fails("{\"Error\":\"ClientDTO unserialized Exception\","
                    + "\"Details\":\"" + e.toString() + "\"}");
        }
    }

    @Override
    public ResultRequest<String> serialize(Object equipment) {
        xmlConverter.createDocument();
        xmlConverter.setRootNode("equipment");
        xmlConverter.setNode("code", ((EquipmentDTO) equipment).getCode());
        xmlConverter.setNode("name", ((EquipmentDTO) equipment).getName());
        xmlConverter.setNode("type", ((EquipmentDTO) equipment).getType());
        xmlConverter.setNode("maker", ((EquipmentDTO) equipment).getMaker());
        xmlConverter.setNode("description", ((EquipmentDTO) equipment).getDescription());
        xmlConverter.setNode("price", String.valueOf(((EquipmentDTO) equipment).getPrice()));
        xmlConverter.setNode("high", String.valueOf(((EquipmentDTO) equipment).getHigh()));
        xmlConverter.setNode("wide", String.valueOf(((EquipmentDTO) equipment).getWide()));
        xmlConverter.setNode("deep", String.valueOf(((EquipmentDTO) equipment).getDeep()));
        xmlConverter.setNode("weight", String.valueOf(((EquipmentDTO) equipment).getWeight()));
        xmlConverter.setNode("fragile", String.valueOf(((EquipmentDTO) equipment).isFragile()));
        xmlConverter.setNode("function", ((EquipmentDTO) equipment).getFunction());
        xmlConverter.setNode("components", ((EquipmentDTO) equipment).getComponents());
        xmlConverter.setNode("power", String.valueOf(((EquipmentDTO) equipment).getPower()));
        xmlConverter.setNode("equipmentId", ((EquipmentDTO) equipment).getEquipmentId());

        return ResultRequest.done(xmlConverter.toString());
    }
}
