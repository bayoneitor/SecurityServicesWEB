package securityservices.core.component.client.appservices.serializers;

import java.util.Iterator;
import java.util.List;
import securityservices.core.component.client.appservices.JsonClientSerializer;
import securityservices.core.component.client.appservices.XmlClientSerializer;
import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.core.shared.services.serializers.xmlapis.Dom;

public class ClientSerializer {

    public static String makeJsonResult(ClientDTO client) {
        String jsonClientList = "{\"Client\":[";
        JsonClientSerializer jsonClient = new JsonClientSerializer();
        jsonClientList += jsonClient.serialize(client).getValue();
        jsonClientList += "]}";
        return jsonClientList;
    }

    public static String makeXMLResult(ClientDTO client) {
        XmlClientSerializer xmlClient = new XmlClientSerializer(new Dom());
        String xmlClientList = xmlClient.serialize(client).getValue();
        return xmlClientList;
    }
}
