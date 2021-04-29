package securityservices.core.component.order.appservices;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import securityservices.core.component.order.domain.model.OrderDetail;

@XmlRootElement(name = "order")
@XmlType(propOrder = {"code", "creator", "value", "surcharges", "type",
                       "status", "additionalInfo", "initDate","finishDate", 
                       "paymentType", "paymentDate", "detailsShop", "orderId"})
public class JaxbOrderDTO {

    private int creator;
    private double value, surcharges;
    private String code, type, status, additionalInfo, initDate, finishDate, orderId, paymentDate, paymentType;
    private ArrayList<OrderDetail> detailsShop = new ArrayList<>();

    public JaxbOrderDTO() {
    }
    public JaxbOrderDTO(String code, int creator, double value, double surcharges, String type,
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
        this.orderId = orderId;
        this.setDetailsShop(detailsShop);
    }

    @XmlElement(name = "creator")
    public int getCreator() {
        return creator;
    }

    @XmlElement(name = "value")
    public double getValue() {
        return value;
    }

    @XmlElement(name = "surcharges")
    public double getSurcharges() {
        return surcharges;
    }

    @XmlElement(name = "code")
    public String getCode() {
        return code;
    }

    @XmlElement(name = "type")
    public String getType() {
        return type;
    }

    @XmlElement(name = "status")
    public String getStatus() {
        return status;
    }

    @XmlElement(name = "additionalInfo")
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    @XmlElement(name = "initDate")
    public String getInitDate() {
        return initDate;
    }

    @XmlElement(name = "finishDate")
    public String getFinishDate() {
        return finishDate;
    }

    @XmlElement(name = "orderId")
    public String getOrderId() {
        return orderId;
    }

    @XmlElement(name = "paymentDate")
    public String getPaymentDate() {
        return paymentDate;
    }

    @XmlElement(name = "paymentType")
    public String getPaymentType() {
        return paymentType;
    }

    @XmlElementWrapper(name = "detailsShop")
    @XmlElement(name = "detail")

    public ArrayList<OrderDetail> getDetailsShop() {
        return detailsShop;
    }

    //@XmlTransient
    public void setCreator(int creator) {
        this.creator = creator;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setSurcharges(double surcharges) {
        this.surcharges = surcharges;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setadditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setDetailsShop(String detailsShop) {
        if (detailsShop != null) {
            String details[] = detailsShop.split(";");
            for (Integer i = 0; i < details.length; i++) {
                String campos[] = details[i].split(",");
                int amount = Integer.valueOf(campos[1]);
                String ref = campos[0];
                Double price = Double.valueOf(campos[2]);

                if (ref.trim().length() > 0 && price > 0 && amount > 0) {
                    this.detailsShop.add(new OrderDetail(ref, amount, price));
                }
            }
        } else {
            this.detailsShop = null;
        }
    }
}
