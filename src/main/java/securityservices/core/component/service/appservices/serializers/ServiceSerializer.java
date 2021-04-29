package securityservices.core.component.service.appservices.serializers;

import securityservices.core.component.service.appservices.JsonServiceSerializer;
import securityservices.core.component.service.appservices.XmlServiceSerializer;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.core.shared.services.serializers.xmlapis.Dom;

public class ServiceSerializer {

    public static String makeJsonResult(ServiceDTO service) {
        String jsonServiceList = "{\"Service\":[";
        JsonServiceSerializer jsonService = new JsonServiceSerializer();
        jsonServiceList += jsonService.serialize(service).getValue();
        jsonServiceList += "]}";
        return jsonServiceList;
    }

    public static String makeXMLResult(ServiceDTO service) {
        XmlServiceSerializer xmlService = new XmlServiceSerializer(new Dom());
        String xmlServiceList = xmlService.serialize(service).getValue();
        return xmlServiceList;
    }
}
