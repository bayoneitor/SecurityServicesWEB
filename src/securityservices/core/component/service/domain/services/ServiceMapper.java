package securityservices.core.component.service.domain.services;

import securityservices.core.component.service.domain.services.*;
import securityservices.core.component.service.domain.model.Service;
import securityservices.shared.responses.ResultRequest;

public class ServiceMapper {

    public static ResultRequest<Service> componentFromDTO(ServiceDTO sdto) {
        return Service.getInstance(
                sdto.getCode(),
                sdto.getName(),
                sdto.getType(),
                sdto.getMaker(),
                sdto.getDescription(),
                sdto.getPrice(),
                sdto.getPeriodicity(),
                sdto.getConditions(),
                sdto.getStartDate(),
                sdto.getFinishDate()
        );
    }

    public static ServiceDTO dtoFromComponent(Service s) {
        return new ServiceDTO(
                s.getCode(),
                s.getName(),
                s.getType(),
                s.getMaker(),
                s.getDescription(),
                s.getPrice(),
                s.getPeriodicity(),
                s.getConditions(),
                s.getStartDate(),
                s.getFinishDate(),
                s.getServiceId()
        );
    }
}
