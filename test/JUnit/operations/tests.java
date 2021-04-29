/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JUnit.operations;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import securityservices.core.component.order.domain.model.Order;
import securityservices.shared.responses.ResultRequest;

/**
 *
 * @author David
 */
public class tests {

    private Order ord;

    public tests() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        if (result.failed()) {
            ord = null;
        } else {
            ord = result.getValue();
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetInstanceSuccess() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");

        assertEquals("En principio deberia de estar todo bien",
                false,
                result.failed());
    }

    @Test
    public void testGetInstanceCodeNull() {
        ResultRequest<Order> result = Order.getInstance(null, 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceCodeEmpty() {
        ResultRequest<Order> result = Order.getInstance("  ", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceCreatorZero() {
        ResultRequest<Order> result = Order.getInstance("code", 0, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceCreatorNegative() {
        ResultRequest<Order> result = Order.getInstance("code", -1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor negativo",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceValueZero() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 0, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceValueNegative() {
        ResultRequest<Order> result = Order.getInstance("code", 1, -2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor negativo",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceTypeNull() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, null, "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceTypeEmpty() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, " ", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceStatusNull() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", null,
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceStatusEmpty() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", " ",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceAdditionalInfoNull() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                null,
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceAdditionalInfoEmpty() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "    ",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceInitDateNull() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                null, "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceInitDateEmpty() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                " ", "29/01/2021-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceFinishDateNull() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", null, "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceFinishDateEmpty() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "  ", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    @Test
    public void testGetInstancePaymentTypeNull() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", null, "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    @Test
    public void testGetInstancePaymentTypeEmpty() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "  ", "29/01/2021-18:46:30");
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    @Test
    public void testGetInstancePaymentDateNull() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", null);
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    @Test
    public void testGetInstancePaymentDateEmpty() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "   ");
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    @Test
    public void testGetInstanceFinishBeforeInitDate() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2020-18:46:30", "paymenttype", "29/01/2021-18:46:30");
        assertEquals("La fecha inicial no puede ser despues de la fecha final",
                true,
                result.failed());
    }

    @Test
    public void testGetInstancePaymentBeforeInitDate() {
        ResultRequest<Order> result = Order.getInstance("code", 1, 2.2, 0.3, "type", "status",
                "additional",
                "28/01/2021-18:46:30", "29/01/2021-18:46:30", "paymenttype", "29/01/2020-18:46:30");
        assertEquals("La fecha inicial no puede ser despues de la fecha final",
                true,
                result.failed());
    }

    @Test
    public void testSetCodeSuccess() {
        String valor = "code";

        ResultRequest result = ord.setCode(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, ord.getCode());
    }

    @Test
    public void testSetCodeNull() {
        String valor = null;

        ResultRequest result = ord.setCode(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetCodeEmpty() {
        String valor = " ";

        ResultRequest result = ord.setCode(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetPriceSuccess() {
        int valor = 1;

        ResultRequest result = ord.setCreator(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, ord.getCreator());
    }

    @Test
    public void testSetPriceZero() {
        int valor = 0;

        ResultRequest result = ord.setCreator(valor);
        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetPriceNegative() {
        int valor = -2;

        ResultRequest result = ord.setCreator(valor);
        assertEquals("No debe admitir valor negativo", true, result.failed());
    }

    @Test
    public void testSetValueSuccess() {
        double valor = 1.5;

        ResultRequest result = ord.setValue(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());
    }

    @Test
    public void testSetValueZero() {
        double valor = 0;

        ResultRequest result = ord.setValue(valor);
        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetValueNegative() {
        double valor = -2;

        ResultRequest result = ord.setValue(valor);
        assertEquals("No debe admitir valor negativo", true, result.failed());
    }

    @Test
    public void testSetTypeSuccess() {
        String valor = "type";

        ResultRequest result = ord.setType(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, ord.getType());
    }

    @Test
    public void testSetTypeNull() {
        String valor = null;

        ResultRequest result = ord.setType(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetTypeEmpty() {
        String valor = " ";

        ResultRequest result = ord.setType(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetStatusSuccess() {
        String valor = "status";

        ResultRequest result = ord.setStatus(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, ord.getStatus());
    }

    @Test
    public void testSetStatusNull() {
        String valor = null;

        ResultRequest result = ord.setStatus(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetStatusEmpty() {
        String valor = " ";

        ResultRequest result = ord.setCode(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetadditionalInfoSuccess() {
        String valor = "additionalInfo";

        ResultRequest result = ord.setAdditionalInfo(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, ord.getadditionalInfo());
    }

    @Test
    public void testSetadditionalInfoNull() {
        String valor = null;

        ResultRequest result = ord.setCode(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetadditionalInfoEmpty() {
        String valor = " ";

        ResultRequest result = ord.setAdditionalInfo(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetPaymentTypeSuccess() {
        String valor = "PaymentType";

        ResultRequest result = ord.setPaymentType(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, ord.getPaymentType());
    }

    @Test
    public void testSetPaymentTypeNull() {
        String valor = null;

        ResultRequest result = ord.setPaymentType(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetPaymentTypeEmpty() {
        String valor = " ";

        ResultRequest result = ord.setPaymentType(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    /*FECHAS*/
    @Test
    public void testSetInitDateSuccess() {
        String valor = "28/01/2021-18:46:30";
        ResultRequest result = ord.setInitDate(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());
    }

    @Test
    public void testSetInitDateNull() {
        String valor = null;
        ResultRequest result = ord.setInitDate(valor);

        assertEquals("No deberia de admitir null", true, result.failed());
    }

    @Test
    public void testSetInitDateEmpty() {
        String valor = "   ";
        ResultRequest result = ord.setInitDate(valor);

        assertEquals("No deberia de admitir null", true, result.failed());
    }

    @Test
    public void testSetFinishDateSuccess() {
        String valor = "28/04/2021-18:46:30";
        ResultRequest result = ord.setFinishDate(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());
    }

    @Test
    public void testSetFinishDateNull() {
        String valor = null;
        ResultRequest result = ord.setFinishDate(valor);

        assertEquals("No deberia de admitir null", true, result.failed());
    }

    @Test
    public void testSetFinishDateEmpty() {
        String valor = "   ";
        ResultRequest result = ord.setFinishDate(valor);

        assertEquals("No deberia de admitir null", true, result.failed());
    }

    @Test
    public void testSetPaymentDateSuccess() {
        String valor = "28/05/2021-18:46:30";
        ResultRequest result = ord.setPaymentDate(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());
    }

    @Test
    public void testSetPaymentDateNull() {
        String valor = null;
        ResultRequest result = ord.setPaymentDate(valor);

        assertEquals("No deberia de admitir null", true, result.failed());
    }

    @Test
    public void testSetPaymentDateEmpty() {
        String valor = "   ";
        ResultRequest result = ord.setPaymentDate(valor);

        assertEquals("No deberia de admitir null", true, result.failed());
    }

    /*otras*/
    @Test
    public void testSetInitDateAfterFinishDate() {
        String valor = "28/03/2021-18:46:30";
        ord.setFinishDate("28/02/2021-18:46:30");
        ResultRequest result = ord.setInitDate(valor);

        assertEquals("No deberia de admitir initDate>FinishDate", true, result.failed());
    }

    @Test
    public void testSetPaymentBeforeInitDate() {
        String valor = "28/02/2021-18:46:30";
        ord.setInitDate("28/04/2021-18:46:30");
        ResultRequest result = ord.setPaymentDate(valor);

        assertEquals("No deberia de admitir initDate>PaymentDate", true, result.failed());
    }

    @Test
    public void setDetailSuccess() {
        int result = ord.setDetail("Ref-1,1,5.3");

        assertEquals("Deberia de funcionar correctamente", 0, result);
    }

    @Test
    public void setDetailNull() {
        int result = ord.setDetail(null);

        assertEquals("No deberia de aceptar null", -1, result);
    }

    @Test
    public void setDetailEmpty() {
        int result = ord.setDetail("   ");

        assertEquals("No deberia de aceptar empty", -1, result);
    }

    @Test
    public void setDetailNotCompleted() {
        int result = ord.setDetail("Ref-1,1");

        assertEquals("No deberia de dejar introducir menos de dos datos", -1, result);
    }

    @Test
    public void setDetailEmptyValues() {
        int result = ord.setDetail("  ,   ,  ");

        assertEquals("No deberia de dejar introducir valores vacios separados por coma", -3, result);
    }

    @Test
    public void setDetailQuantityString() {
        int result = ord.setDetail("Ref,asd,5.2");

        assertEquals("No deberia de dejar introducir strings en cantidad", -4, result);
    }

    @Test
    public void setDetailQuantityDouble() {
        int result = ord.setDetail("Ref,1.1,5.2");

        assertEquals("No deberia de dejar introducir doubles en cantidad", -4, result);
    }

    @Test
    public void setDetailPriceString() {
        int result = ord.setDetail("Ref,5,asd");

        assertEquals("No deberia de dejar introducir strings en precio", -5, result);
    }

    @Test
    public void getDetailsValueAll() {
        ord.setSurcharges(0.2);
        ord.setDetail("Ref-1,1,5.3");
        ord.setDetail("Ref-2,5,2.3");
        ord.setDetail("Ref-3,2,10.3");
        double result = ord.getValue();
        assertEquals("Deberia de funcionar correctamente", 29.92, result, 0);
    }
}
