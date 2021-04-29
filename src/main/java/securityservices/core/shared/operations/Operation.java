package securityservices.core.shared.operations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import securityservices.shared.responses.ResultRequest;
import securityservices.shared.responses.ResultResponses;

public abstract class Operation {

    protected int creator;
    protected double value, surcharges;
    protected String code, type, status, additionalInfo;
    protected LocalDateTime initDate, finishDate;
    protected DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy'-'HH:mm:ss");

    public Operation() {
    }

    public boolean checkDates(LocalDateTime date) {
        Duration diff = Duration.between(this.initDate, date);
        if (diff.isNegative()) {
            return false;
        }
        return true;
    }

    public ResultRequest setCreator(Integer creator) {
        if (creator == null || creator <= 0) {
            return ResultRequest.fails("\"Error\":\"invalid creator\"");
        }
        this.creator = creator;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setValue(Double value) {
        if (value == null || value <= 0) {
            return ResultRequest.fails("\"Error\":\"invalid value\"");
        }
        this.value = value;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setSurcharges(Double surcharges) {
        if (surcharges == null || surcharges <= 0) {
            return ResultRequest.fails("\"Error\":\"invalid surcharges\"");
        }
        this.surcharges = surcharges;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setCode(String code) {
        if (code == null || code.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid code\"");
        }
        this.code = code;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setType(String type) {
        if (type == null || type.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid type\"");
        }
        this.type = type;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setStatus(String status) {
        if (status == null || status.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid status\"");
        }
        this.status = status;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setAdditionalInfo(String additionalInfo) {
        if (additionalInfo == null || additionalInfo.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid additionalInfo\"");
        }
        this.additionalInfo = additionalInfo;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setFinishDate(String finishDate) {
        try {
            this.finishDate = LocalDateTime.parse(finishDate, dateTimeFormatter);
            if (this.initDate != null && !checkDates(this.finishDate)) {
                return ResultRequest.fails("\"Error\":\"invalid dates (startDate > finishDate)\"");
            }
            return ResultRequest.done(ResultResponses.SUCCESS);
        } catch (Exception e) {
            return ResultRequest.fails("\"Error\":\"invalid finishDate: " + e.getMessage() + "\"");
        }
    }

    public ResultRequest setInitDate(String initDate) {
        try {
            this.initDate = LocalDateTime.parse(initDate, dateTimeFormatter);
            if (this.finishDate != null && !checkDates(this.finishDate)) {
                return ResultRequest.fails("\"Error\":\"invalid dates (startDate > finishDate)\"");
            }
            
            return ResultRequest.done(ResultResponses.SUCCESS);
        } catch (Exception e) {
            return ResultRequest.fails("\"Error\":\"invalid initDate: " + e.getMessage() + "\"");
        }
    }

    public String getInitDate() {
        if (initDate == null) {
            return "";
        }
        return initDate.format(dateTimeFormatter);
    }

    public String getFinishDate() {
        if (finishDate == null) {
            return "";
        }
        return finishDate.format(dateTimeFormatter);
    }

    public String getCode() {
        return code;
    }

    public int getCreator() {
        return creator;
    }

    public double getValue() {
        return value;
    }

    public double getSurcharges() {
        return surcharges;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getadditionalInfo() {
        return additionalInfo;
    }
}
