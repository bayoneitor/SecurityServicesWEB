package securityservices.core.component.service.domain.services;

/*
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
 */

 /*
DTO inmutable generado a traves del constructor para transferirlo a otra capa
sin permitir cambios en su contenido
 */
public class ServiceDTO {

    private final String periodicity, conditions, startDate, finishDate, code, name, type, maker, description, serviceId;
    private final Double price;
    private final Boolean available;

    public ServiceDTO(String code, String name, String type, String maker, String description, Double price,
            String periodicity, String conditions, String startDate, String finishDate, String serviceId) {
        this.name = name;
        this.code = code;
        this.periodicity = periodicity;
        this.conditions = conditions;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.type = type;
        this.maker = maker;
        this.description = description;
        this.price = price;
        this.available = true;
        this.serviceId = serviceId;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public String getConditions() {
        return conditions;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMaker() {
        return maker;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Boolean isAvailable() {
        return available;
    }

    public String getServiceId() {
        return serviceId;
    }
}
