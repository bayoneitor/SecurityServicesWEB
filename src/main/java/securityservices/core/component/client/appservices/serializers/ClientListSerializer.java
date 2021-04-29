package securityservices.core.component.client.appservices.serializers;

import java.util.Iterator;
import java.util.List;
import securityservices.core.component.client.appservices.JsonClientSerializer;
import securityservices.core.component.client.appservices.XmlClientSerializer;
import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.core.shared.services.serializers.xmlapis.Dom;

public class ClientListSerializer {

    public static String makeJsonResult(List<ClientDTO> clientList) {
        String jsonClientList = "{\"ClientList\":[";
        JsonClientSerializer jsonClient = new JsonClientSerializer();
        Iterator it = clientList.iterator();
        for (; it.hasNext();) {
            jsonClientList += jsonClient.serialize(it.next()).getValue();
            if (it.hasNext()) {
                jsonClientList += ",";
            }
        }
        jsonClientList += "]}";
        return jsonClientList;
    }

    public static String makeXMLResult(List<ClientDTO> clientList) {
        String xmlClientList = "<clients>";
        XmlClientSerializer xmlClient = new XmlClientSerializer(new Dom());
        Iterator it = clientList.iterator();
        for (; it.hasNext();) {
            xmlClientList += xmlClient.serialize((ClientDTO) it.next()).getValue();
        }
        xmlClientList += "</clients>";
        return xmlClientList;
    }
}
