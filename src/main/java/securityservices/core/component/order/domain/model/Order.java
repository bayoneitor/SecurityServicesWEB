package securityservices.core.component.order.domain.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import securityservices.core.shared.operations.Billable;
import securityservices.core.shared.operations.Operation;
import securityservices.shared.responses.ResultRequest;
import securityservices.shared.responses.ResultResponses;

public class Order extends Operation implements Billable {

    protected UUID orderId;
    protected String paymentType;
    protected LocalDateTime paymentDate;
    protected ArrayList<OrderDetail> details = new ArrayList();

    public Order() {
        this.setOrderId();
    }

    public static ResultRequest<Order> getInstance(String code, int creator, double value, double surcharges, String type,
            String status, String additionalInfo, String initDate, String finishDate,
            String paymentType, String paymentDate) {

        Order order = new Order();

        ResultRequest result = order.setCode(code);
        if (result.failed()) {
            return result;
        }

        result = order.setCreator(creator);
        if (result.failed()) {
            return result;
        }
        result = order.setValue(value);
        if (result.failed()) {
            return result;
        }
        result = order.setSurcharges(surcharges);
        if (result.failed()) {
            return result;
        }

        result = order.setType(type);
        if (result.failed()) {
            return result;
        }
        result = order.setStatus(status);
        if (result.failed()) {
            return result;
        }
        result = order.setAdditionalInfo(additionalInfo);
        if (result.failed()) {
            return result;
        }

        result = order.setFinishDate(finishDate);
        if (result.failed()) {
            return result;
        }

        result = order.setInitDate(initDate);
        if (result.failed()) {
            return result;
        }

        result = order.setPaymentDate(paymentDate);
        if (result.failed()) {
            return result;
        }

        result = order.setPaymentType(paymentType);
        if (result.failed()) {
            return result;
        }
        return ResultRequest.done(order);

    }

    protected void setOrderId() {
        this.orderId = UUID.randomUUID();
    }

    public ResultRequest setPaymentType(String paymentType) {
        if (paymentType == null || paymentType.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid paymentType\"");
        }
        this.paymentType = paymentType;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setPaymentDate(String paymentDate) {
        try {
            this.paymentDate = LocalDateTime.parse(paymentDate, dateTimeFormatter);

            if (this.initDate != null && !checkDates(this.paymentDate)) {
                return ResultRequest.fails("\"Error\":\"invalid dates (startDate > paymentDate)\"");
            }

            return ResultRequest.done(ResultResponses.SUCCESS);
        } catch (Exception e) {
            return ResultRequest.fails("\"Error\":\"invalid paymentDate: " + e.getMessage() + "\"");
        }
    }

    public String getOrderId() {
        return orderId.toString();
    }

    public String getPaymentDate() {
        if (paymentDate == null) {
            return "";
        }
        return paymentDate.format(dateTimeFormatter);
    }

    public String getPaymentType() {
        return paymentType;
    }

    public int getNumDetails() {
        return details.size();
    }

    public int checkDetail(String detail) {
        int count = detail.length() - detail.replace(",", "").length();
        if (count >= 2) {
            String campos[] = detail.split(",");
            if (campos.length == 3) {
                if (campos[0].trim().length() > 0 && campos[0] != null) {
                    if (campos[1].trim().length() > 0) {
                        try {
                            Integer.parseInt(campos[1]);
                        } catch (NumberFormatException nfe) {
                            return -4;
                        }
                        if (campos[2].trim().length() > 0) {
                            try {
                                Double.parseDouble(campos[2]);
                            } catch (NumberFormatException nfe) {
                                return -5;
                            }
                            return 0;
                        }
                    }
                }
                return -3;
            }
            return -2;
        }
        return -1;
    }

    public int setDetail(String detail) {
        if (detail == null) {
            return -1;
        }
        int statusDet = this.checkDetail(detail);
        if (statusDet == 0) {
            String campos[] = detail.split(",");
            int amount = Integer.valueOf(campos[1]);
            String ref = campos[0];
            Double price = Double.valueOf(campos[2]);

            if (ref.trim().length() > 0 && price > 0 && amount > 0) {
                //Comprobar primero si esta en la lista, y despues añado
                details.add(new OrderDetail(ref, amount, price));
                return 0;
            }
        }
        return statusDet;
    }

    //obtener la posicion
    public String getDetail(int n) {
        int max = Integer.valueOf(this.getNumDetails() + 1);

        if (n > 0 && n < max) {
            OrderDetail item = details.get(n - 1);
            String det = item.getRef() + "," + item.getAmount() + "," + item.getPrice();
            return det;
        }
        return "ERROR can't find Product";
    }

    public int getDetailIndex(String n) {
        if (n.trim().length() > 0 && n != null) {
            for (int i = 0; i < this.getNumDetails(); i++) {
                OrderDetail item = details.get(i);
                if (item.getRef().equals(n)) {
                    return i;
                }
            }
        }
        return -1;
    }

    //obtener la referencia
    public String getDetail(String n) {
        int index = this.getDetailIndex(n);
        if (index != -1) {
            OrderDetail item = details.get(index);
            return item.getRef() + "," + item.getAmount() + "," + item.getPrice() + ";";
        }
        return "ERROR can't find Product";
    }

    //Actualización de datos
    public int updateDetail(int n, String newdetail) {
        if (this.checkDetail(newdetail) == 0) {
            if (!this.getDetail(n).equals("-1")) {
                String campos[] = newdetail.split(",");
                int amount = Integer.valueOf(campos[1]);
                String ref = campos[0];
                Double price = Double.valueOf(campos[2]);

                OrderDetail item = details.get(n - 1);
                item.setRef(ref);
                item.setAmount(amount);
                item.setPrice(price);
                return 0;
            } else {
                return -2;
            }
        }
        return -1;
    }

    public int updateDetail(String n, String newdetail) {
        int index = this.getDetailIndex(n);
        if (index != -1) {
            return this.updateDetail(index + 1, newdetail);
        }
        return -1;
    }

    public int deleteDetail(int n) {
        if (this.getDetail(n) != "-1") {
            details.remove(n - 1);
            return 0;
        }
        return -1;
    }

    public int deleteDetail(String n) {
        int index = this.getDetailIndex(n);
        if (index != -1) {
            return this.deleteDetail(index + 1);
        }
        return -1;
    }

    @Override
    public double getValue() {
        int size = this.getNumDetails();
        if (size > 0) {
            Double price = 0.0;
            for (int i = 0; i < this.getNumDetails(); i++) {
                OrderDetail item = details.get(i);
                price += item.getPrice() * item.getAmount();
            }
            if (this.surcharges > 0 && this.surcharges < 1) {
                return Math.round((price * (1.0 - this.surcharges)) * 100.0) / 100.0;
            }
            return price;
        }
        return 0;
    }

    @Override
    public int getClient() {
        return super.creator;
    }

    @Override
    public String getInitDate() {
        return super.getInitDate();
    }
}
