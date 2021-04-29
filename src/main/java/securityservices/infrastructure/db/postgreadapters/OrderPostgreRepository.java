package securityservices.infrastructure.db.postgreadapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import securityservices.core.component.order.appservices.JsonOrderSerializer;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.core.component.order.infraestructure.OrderRepository;
import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;
import securityservices.infrastructure.db.connectors.JdbcConnector;
import securityservices.infrastructure.db.connectors.PersistenceConnector;
import securityservices.shared.responses.ResultRequest;

public class OrderPostgreRepository implements OrderRepository {

    private PersistenceConnector connection;
    JsonOrderSerializer JOSerializer = new JsonOrderSerializer();

    public OrderPostgreRepository(Json dataconnex) {
        this.connection = new JdbcConnector(dataconnex);
    }

    public OrderPostgreRepository(PersistenceConnector connection) {
        this.connection = connection;
    }

    private Json detailsShop_getJSON(Json jOrder) {
        String detailsShopString = jOrder.get("details").toString();
        detailsShopString = detailsShopString.replace("[", "").replace("]", "")
                .replace("{", "")
                .replace("\\", "")
                .replace("\"", "");

        String detailsShopArr[] = detailsShopString.split("},");
        String line, item[], value[];
        String result = "", ref = "", amount = "", price = "";
        for (int i = 0; i < detailsShopArr.length; i++) {
            line = detailsShopArr[i].trim().replace("}", "");
            item = line.split(",");
            for (int j = 0; j < item.length; j++) {
                value = item[j].trim().split(":");
                if (value[0].trim().equals("ref")) {
                    ref = value[1].trim();
                } else if (value[0].trim().equals("amount")) {
                    amount = value[1].trim();
                } else if (value[0].trim().equals("price")) {
                    price = value[1].trim();
                }
            }
            result += ref + "," + amount + "," + price + ";";
        }
        return this.JOSerializer.detailsShop_JSONFromString(result, jOrder);
    }

    private String date_DTOFromBD(String date) {
        if (date == null) {
            return null;
        }
        final String OLD_FORMAT = "yyyy'-'MM'-'dd HH:mm:ssX";
        final String NEW_FORMAT = "dd'/'MM'/'yyyy'-'HH:mm:ss";
        String oldDateString = date;
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        try {
            Date dateOld = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            return sdf.format(dateOld);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String date_BDFromDTO(String date) {
        if (date == null) {
            return null;
        }
        final String OLD_FORMAT = "dd'/'MM'/'yyyy'-'HH:mm:ss";
        final String NEW_FORMAT = "yyyy'-'MM'-'dd HH:mm:ss";
        String oldDateString = date;
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        try {
            Date dateOld = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            return sdf.format(dateOld);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private Json modifyJSON(Json jOrder) {
        jOrder = this.detailsShop_getJSON(jOrder);
        String initDate = jOrder.get("initdate");
        String finishDate = jOrder.get("finishdate");
        String paymentDate = jOrder.get("paymentdate");

        jOrder.remove("initdate");
        jOrder.remove("finishdate");
        jOrder.remove("paymentdate");

        jOrder.set("initDate", this.date_DTOFromBD(initDate));
        jOrder.set("finishDate", this.date_DTOFromBD(finishDate));
        jOrder.set("paymentDate", this.date_DTOFromBD(paymentDate));
        
        String additionalInfo = jOrder.get("additionalinfo");
        String orderId = jOrder.get("orderid");
        String paymentType = jOrder.get("paymenttype");
        
        jOrder.remove("additionalinfo");
        jOrder.remove("orderid");
        jOrder.remove("paymenttype");
        
        jOrder.set("additionalInfo", additionalInfo);
        jOrder.set("orderId", orderId);
        jOrder.set("paymentType", paymentType);

        return jOrder;
    }

    @Override
    public ResultRequest<List<OrderDTO>> getAll() {
        ResultRequest<Json> query = this.connection.readQuery("SELECT * FROM orders");
        if (query.failed()) {
            return ResultRequest.fails(query.getError());
        }
        Json jOrders = query.getValue();
        List<OrderDTO> orderArr = new ArrayList<OrderDTO>();
        for (int i = 0; i < jOrders.getArraySize("content"); i++) {
            Json jOrder = this.modifyJSON(jOrders.getArrayObj("content", i));
            orderArr.add(this.JOSerializer.unserialize(jOrder.toString()).getValue());
        }
        return ResultRequest.done(orderArr);

    }

    @Override
    public ResultRequest<OrderDTO> getByID(String id) {
        ResultRequest<Json> query = this.connection.readQuery("SELECT * FROM orders WHERE code='" + id + "'");
        if (query.failed()) {
            return ResultRequest.fails(query.getError());
        }
        Json jOrder = this.modifyJSON(query.getValue().getJResult("content"));

        return this.JOSerializer.unserialize(jOrder.toString());
    }

    @Override
    public ResultRequest<String> add(OrderDTO order) {
        Json jOrder = JsonObjectFactory.getInstance();
        jOrder = this.JOSerializer.detailsShop_JSONFromString(order.getDetailsShop(), jOrder);
        String detailsShop = jOrder.get("detailsShop").toString();

        ResultRequest<Json> query = this.connection.writeQuery("INSERT INTO orders VALUES('" + order.getCode() + "','" + order.getCreator()
                + "','" + order.getValue() + "','" + order.getSurcharges() + "','" + order.getType()
                + "','" + order.getStatus() + "','" + order.getAdditionalInfo() + "','" + this.date_BDFromDTO(order.getInitDate())
                + "','" + this.date_BDFromDTO(order.getFinishDate()) + "','" + order.getOrderId() + "','" + order.getPaymentType()
                + "','" + this.date_BDFromDTO(order.getPaymentDate()) + "','" + detailsShop + "');");
        if (query.failed()) {
            return ResultRequest.fails("Error al insertar en base de datos el order con id " + order.getCode() + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha insertado correctamente el order con id " + order.getCode());
        }
    }

    @Override
    public ResultRequest<String> update(OrderDTO order) {
        Json jOrder = JsonObjectFactory.getInstance();
        jOrder = this.JOSerializer.detailsShop_JSONFromString(order.getDetailsShop(), jOrder);
        String detailsShop = jOrder.get("detailsShop").toString();

        ResultRequest<Json> query = this.connection.writeQuery("UPDATE orders SET creator='" + order.getCreator()
                + "', value='" + order.getValue() + "', surcharges='" + order.getSurcharges() + "', " + "type='" + order.getType()
                + "', status='" + order.getStatus() + "', additionalinfo='" + order.getAdditionalInfo()
                + "', initdate='" + this.date_BDFromDTO(order.getInitDate()) + "', finishdate='" + this.date_BDFromDTO(order.getFinishDate())
                + "', orderid='" + order.getOrderId() + "', paymenttype='" + order.getPaymentType()
                + "', paymentdate='" + this.date_BDFromDTO(order.getPaymentDate()) + "', details='" + detailsShop
                + "' WHERE code='" + order.getCode() + "';");
        if (query.failed()) {
            return ResultRequest.fails("Error al actualizar en base de datos el order con id " + order.getCode() + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha actualizado correctamente el order con id " + order.getCode());
        }
    }

    @Override
    public ResultRequest<String> deleteByID(String id) {
        ResultRequest<Json> query = this.connection.writeQuery("DELETE FROM orders WHERE code='" + id + "'");
        if (query.failed()) {
            return ResultRequest.fails("Error al borrar en base de datos el order con id " + id + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha borrado correctamente el order con id " + id);
        }
    }

    @Override
    public Boolean exists(String id) {
        if (this.connection.readQuery("SELECT code FROM orders WHERE code='" + id + "'").failed()) {
            return false;
        } else {
            return true;
        }
    }
}
