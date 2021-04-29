package securityservices.core.component.service.appservices.serializers;

import java.util.Iterator;
import java.util.List;
import securityservices.core.component.service.appservices.JsonServiceSerializer;
import securityservices.core.component.service.appservices.XmlServiceSerializer;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.core.shared.services.serializers.xmlapis.Dom;

public class ServiceListSerializer {

    public static String makeJsonResult(List<ServiceDTO> serviceList) {
        String jsonServiceList = "{\"ServiceList\":[";
        JsonServiceSerializer jsonService = new JsonServiceSerializer();
        Iterator it = serviceList.iterator();
        for (; it.hasNext();) {
            jsonServiceList += jsonService.serialize(it.next()).getValue();
            if (it.hasNext()) {
                jsonServiceList += ",";
            }
        }
        jsonServiceList += "]}";
        return jsonServiceList;
    }

    public static String makeXMLResult(List<ServiceDTO> serviceList) {
        String xmlServiceList = "<services>";
        XmlServiceSerializer xmlService = new XmlServiceSerializer(new Dom());
        Iterator it = serviceList.iterator();
        for (; it.hasNext();) {
            xmlServiceList += xmlService.serialize((ServiceDTO) it.next()).getValue();
        }
        xmlServiceList += "</services>";
        return xmlServiceList;
    }
}
