package securityservices.core.component.order.domain.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"ref", "price", "amount"})
public class OrderDetail {

    protected int amount;
    protected double price;
    protected String ref;
    
    public OrderDetail(){
    }
    
    public OrderDetail(String ref, int amount, double price) {
        this.ref = ref;
        this.amount = amount;
        this.price = price;
    }

    @XmlElement(name = "amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @XmlElement(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @XmlElement(name = "ref")
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
