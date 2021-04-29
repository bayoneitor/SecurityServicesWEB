package securityservices.core.component.service.appservices;

import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.shared.responses.ResultRequest;
import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;
import securityservices.core.shared.services.serializers.Serializer;

public class JsonServiceSerializer implements Serializer {

    private Json jService = JsonObjectFactory.getInstance();

    public JsonServiceSerializer() {
    }

    @Override
    public ResultRequest<ServiceDTO> unserialize(String data) {
        jService.set(data);
        try {
            ServiceDTO service = new ServiceDTO(
                    jService.get("code"),
                    jService.get("name"),
                    jService.get("type"),
                    jService.get("maker"),
                    jService.get("description"),
                    Double.valueOf(jService.get("price")),
                    jService.get("periodicity"),
                    jService.get("conditions"),
                    jService.get("startDate"),
                    jService.get("finishDate"),
                    jService.get("serviceId")
            );
            return ResultRequest.done(service);

        } catch (Exception e) {
            return ResultRequest.fails("{\"Error\":\"ServiceDTO unserialized Exception\","
                    + "\"Details\":\"" + e.toString() + "\"}");
        }
    }

    @Override
    public ResultRequest<String> serialize(Object service) {
        jService.removeAll();
        jService.set("code", ((ServiceDTO) service).getCode());
        jService.set("name", ((ServiceDTO) service).getName());
        jService.set("type", ((ServiceDTO) service).getType());
        jService.set("maker", ((ServiceDTO) service).getMaker());
        jService.set("description", ((ServiceDTO) service).getDescription());
        jService.set("price", String.valueOf(((ServiceDTO) service).getPrice()));
        jService.set("periodicity", ((ServiceDTO) service).getPeriodicity());
        jService.set("conditions", ((ServiceDTO) service).getConditions());
        jService.set("startDate", ((ServiceDTO) service).getStartDate());
        jService.set("finishDate", ((ServiceDTO) service).getFinishDate());
        jService.set("serviceId", ((ServiceDTO) service).getServiceId());

        return ResultRequest.done(jService.toString());
    }

}
