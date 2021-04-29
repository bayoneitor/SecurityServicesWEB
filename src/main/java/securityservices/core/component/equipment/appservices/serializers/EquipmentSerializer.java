package securityservices.core.component.equipment.appservices.serializers;

import java.util.Iterator;
import java.util.List;
import securityservices.core.component.equipment.appservices.JsonEquipmentSerializer;
import securityservices.core.component.equipment.appservices.XmlEquipmentSerializer;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.core.shared.services.serializers.xmlapis.Dom;

public class EquipmentSerializer {

    public static String makeJsonResult(EquipmentDTO equipment) {
        String jsonEquipmentList = "{\"Equipment\":[";
        JsonEquipmentSerializer jsonEquipment = new JsonEquipmentSerializer();
        jsonEquipmentList += jsonEquipment.serialize(equipment).getValue();
        jsonEquipmentList += "]}";
        return jsonEquipmentList;
    }

    public static String makeXMLResult(EquipmentDTO equipment) {
        XmlEquipmentSerializer xmlEquipment = new XmlEquipmentSerializer(new Dom());
        String xmlEquipmentList = xmlEquipment.serialize(equipment).getValue();
        return xmlEquipmentList;
    }
}
