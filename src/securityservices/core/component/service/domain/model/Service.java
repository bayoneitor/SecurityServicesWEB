package securityservices.core.component.service.domain.model;

import securityservices.core.shared.products.Product;
import java.util.UUID;
import securityservices.core.shared.services.Check;
import securityservices.shared.responses.ResultRequest;
import securityservices.shared.responses.ResultResponses;

public class Service extends Product {

    protected UUID serviceId;
    protected String periodicity, conditions, startDate, finishDate;

    protected Service() {
        this.setServiceId();
    }

    public static ResultRequest<Service> getInstance(String code, String name, String type, String maker, String description, Double price,
            String periodicity, String conditions, String startDate, String finishDate) {

        Service service = new Service();

        ResultRequest result = service.setCode(code);
        if (result.failed()) {
            return result;
        }

        result = service.setName(name);
        if (result.failed()) {
            return result;
        }

        result = service.setType(type);
        if (result.failed()) {
            return result;
        }

        result = service.setMaker(maker);
        if (result.failed()) {
            return result;
        }

        result = service.setDescription(description);
        if (result.failed()) {
            return result;
        }

        result = service.setPrice(price);
        if (result.failed()) {
            return result;
        }

        result = service.setPeriodicity(periodicity);
        if (result.failed()) {
            return result;
        }

        result = service.setConditions(conditions);
        if (result.failed()) {
            return result;
        }

        result = service.setStartDate(startDate);
        if (result.failed()) {
            return result;
        }

        result = service.setFinishDate(finishDate);
        if (result.failed()) {
            return result;
        }

        if (!Check.EasyDiffDates(startDate, finishDate)) {
            return ResultRequest.fails("\"Error\":\"invalid dates (startDate > finishDate)\"");
        }

        //Comprobar que la startDate no es mas grande que finishDate
        return ResultRequest.done(service);
    }

    //Comprobaciones
    public ResultRequest setConditions(String conditions) {
        if (conditions == null || conditions.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid condition\"");
        }
        this.conditions = conditions;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setPeriodicity(String periodicity) {
        if (periodicity == null || periodicity.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid periodicity\"");
        }
        this.periodicity = periodicity;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setStartDate(String startDate) {
        if (Check.checkDate(startDate) == 0) {
            this.startDate = startDate;
            return ResultRequest.done(ResultResponses.SUCCESS);
        }
        return ResultRequest.fails("\"Error\":\"invalid start Date\"");
    }

    public ResultRequest setFinishDate(String finishDate) {
        if (Check.checkDate(finishDate) == 0) {
            this.finishDate = finishDate;
            return ResultRequest.done(ResultResponses.SUCCESS);
        }
        return ResultRequest.fails("\"Error\":\"invalid finish Date\"");
    }

    protected void setServiceId() {
        this.serviceId = UUID.randomUUID();
    }

    public String getServiceId() {
        return serviceId.toString();
    }

    public String getStartDate() {
        if (this.startDate != null) {
            return this.startDate;
        }
        return "";
    }

    public String getFinishDate() {
        if (this.finishDate != null) {
            return this.finishDate;
        }
        return "";
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public String getConditions() {
        return conditions;
    }

    @Override
    public String getDetails() {
        return "Periodicity:" + this.periodicity
                + ";Conditions:" + this.conditions;
    }
}
