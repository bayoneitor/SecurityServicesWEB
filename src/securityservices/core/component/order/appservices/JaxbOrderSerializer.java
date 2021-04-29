package securityservices.core.component.order.appservices;

import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import securityservices.core.component.order.domain.model.OrderDetail;
import securityservices.core.component.order.domain.services.OrderDTO;
import securityservices.core.shared.services.serializers.Serializer;
import securityservices.core.shared.services.serializers.xmlapis.Jaxb;
import securityservices.shared.responses.ResultRequest;

public class JaxbOrderSerializer extends Jaxb implements Serializer {

    private JaxbOrderSerializer() {
    }

    //apliquem sistemÃ ticament el mateix concepte de tractament d'errors
    public static ResultRequest<JaxbOrderSerializer> getInstance() {
        try {
            JaxbOrderSerializer jaxbOrder = new JaxbOrderSerializer();
            //excepcio que genera la clase, i de la que volem fugir, mantenint el nostre tractament d'errors
            jaxbOrder.context = JAXBContext.newInstance(JaxbOrderDTO.class);
            return ResultRequest.done(jaxbOrder);
        } catch (JAXBException ex) {
            return ResultRequest.fails("{\"Error\":\"JAXBContext fails\",\"Details\":\"" + ex.toString() + "\"}");
        }
    }

    private String arrayToString(ArrayList<OrderDetail> allDetails) {
        String result = null;
        if (allDetails != null) {
            if (allDetails.size() > 0) {
                result = "";
                for (int i = 0; i < allDetails.size(); i++) {
                    OrderDetail item = allDetails.get(i);
                    result += item.getRef() + "," + item.getAmount() + "," + item.getPrice() + ";";
                }
            }
        }
        return result;
    }

    @Override
    public ResultRequest<OrderDTO> unserialize(String xresponse) {
        if (super.prepareUnmarshal(xresponse).failed()) {
            return ResultRequest.fails("{\"Error\":\"JAXB unmarshal fails\","
                    + "\"Details\":\"Can't unserialize xmldata to ClientDTO. \""
                    + super.prepareUnmarshal(xresponse).getError()
                    + "}");
        } else {
            JaxbOrderDTO jaxbodto = (JaxbOrderDTO) super.prepareUnmarshal(xresponse).getValue();

            OrderDTO odto = new OrderDTO(
                    jaxbodto.getCode(),
                    jaxbodto.getCreator(),
                    jaxbodto.getValue(),
                    jaxbodto.getSurcharges(),
                    jaxbodto.getType(),
                    jaxbodto.getStatus(),
                    jaxbodto.getAdditionalInfo(),
                    jaxbodto.getInitDate(),
                    jaxbodto.getFinishDate(),
                    jaxbodto.getPaymentType(),
                    jaxbodto.getPaymentDate(),
                    this.arrayToString(jaxbodto.getDetailsShop()),
                    jaxbodto.getOrderId()
            );
            return ResultRequest.done(odto);
        }
    }

    //Voy por aquí
    @Override
    public ResultRequest<String> serialize(Object orderDto) {
        JaxbOrderDTO jaxbodto = new JaxbOrderDTO(
                ((OrderDTO) orderDto).getCode(),
                ((OrderDTO) orderDto).getCreator(),
                ((OrderDTO) orderDto).getValue(),
                ((OrderDTO) orderDto).getSurcharges(),
                ((OrderDTO) orderDto).getType(),
                ((OrderDTO) orderDto).getStatus(),
                ((OrderDTO) orderDto).getAdditionalInfo(),
                ((OrderDTO) orderDto).getInitDate(),
                ((OrderDTO) orderDto).getFinishDate(),
                ((OrderDTO) orderDto).getPaymentType(),
                ((OrderDTO) orderDto).getPaymentDate(),
                ((OrderDTO) orderDto).getDetailsShop(),
                ((OrderDTO) orderDto).getOrderId());

        if (super.prepareMarshal(jaxbodto).failed()) {
            return ResultRequest.fails("{\"Error\":\"JAXB marshal fails\","
                    + "\"Details\":\"Can't serialize ClientDTO to xmldata. \""
                    + super.prepareMarshal(jaxbodto).getError()
                    + "}");
        } else {
            String xmlClient = super.prepareMarshal(jaxbodto).getValue();
            return ResultRequest.done(xmlClient);
        }
    }
}
