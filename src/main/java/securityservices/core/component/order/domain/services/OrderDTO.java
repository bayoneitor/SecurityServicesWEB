package securityservices.core.component.order.domain.services;

import securityservices.core.component.order.domain.services.*;
import securityservices.core.component.order.domain.services.*;

/*
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
 */

 /*
DTO inmutable generado a traves del constructor para transferirlo a otra capa
sin permitir cambios en su contenido
 */
public class OrderDTO {

    private final int creator;
    private final double value, surcharges;
    private final String code, type, status, additionalInfo, initDate, finishDate, orderId, paymentDate, paymentType, detailsShop;

    public OrderDTO(String code, int creator, double value, double surcharges, String type,
            String status, String additionalInfo, String initDate, String finishDate,
            String paymentType, String paymentDate, String detailsShop, String orderId) {
        this.code = code;
        this.creator = creator;
        this.value = value;
        this.surcharges = surcharges;
        this.type = type;
        this.status = status;
        this.additionalInfo = additionalInfo;
        this.initDate = initDate;
        this.finishDate = finishDate;
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
        this.detailsShop = detailsShop;
        this.orderId = orderId;
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

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getInitDate() {
        return initDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getDetailsShop() {
        return detailsShop;
    }

}
