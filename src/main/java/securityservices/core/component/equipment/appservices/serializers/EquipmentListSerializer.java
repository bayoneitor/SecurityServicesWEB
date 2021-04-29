package securityservices.core.component.equipment.appservices.serializers;

import java.util.Iterator;
import java.util.List;
import securityservices.core.component.equipment.appservices.JsonEquipmentSerializer;
import securityservices.core.component.equipment.appservices.XmlEquipmentSerializer;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.core.shared.services.serializers.xmlapis.Dom;

public class EquipmentListSerializer {

    public static String makeJsonResult(List<EquipmentDTO> equipmentList) {
        String jsonEquipmentList = "{\"EquipmentList\":[";
        JsonEquipmentSerializer jsonEquipment = new JsonEquipmentSerializer();
        Iterator it = equipmentList.iterator();
        for (; it.hasNext();) {
            jsonEquipmentList += jsonEquipment.serialize(it.next()).getValue();
            if (it.hasNext()) {
                jsonEquipmentList += ",";
            }
        }
        jsonEquipmentList += "]}";
        return jsonEquipmentList;
    }

    public static String makeXMLResult(List<EquipmentDTO> equipmentList) {
        String xmlEquipmentList = "<equipments>";
        XmlEquipmentSerializer xmlEquipment = new XmlEquipmentSerializer(new Dom());
        Iterator it = equipmentList.iterator();
        for (; it.hasNext();) {
            xmlEquipmentList += xmlEquipment.serialize((EquipmentDTO) it.next()).getValue();
        }
        xmlEquipmentList += "</equipments>";
        return xmlEquipmentList;
    }
}
