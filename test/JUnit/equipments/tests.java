/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JUnit.equipments;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import securityservices.core.component.equipment.domain.model.Equipment;
import securityservices.shared.responses.ResultRequest;

/**
 *
 * @author David
 */
public class tests {

    private Equipment e;

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
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.5, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        if (result.failed()) {
            e = null;
        } else {
            e = result.getValue();
        }
    }

    @After
    public void tearDown() {
    }

    /*
        Miramos que no nos de ningun error, pasando todo bien
     */
    @Test
    public void testGetInstanceSuccess() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.5, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("En principio deberia de estar todo bien",
                false,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un null en code
     */
    @Test
    public void testGetInstanceCodeNull() {
        ResultRequest<Equipment> result = Equipment.getInstance(null, "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un string vacio
     */
    @Test
    public void testGetInstanceCodeEmpty() {
        ResultRequest<Equipment> result = Equipment.getInstance("  ", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un null en name
     */
    @Test
    public void testGetInstanceNameNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", null, "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un string vacio
     */
    @Test
    public void testGetInstanceNameEmpty() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "  ", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un null en type
     */
    @Test
    public void testGetInstanceTypeNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", null, "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un string vacio
     */
    @Test
    public void testGetInstanceTypeEmpty() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "  ", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("Debe de indicar que el type esta mal, ya que le hemos dado un valor vacio",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un null en maker
     */
    @Test
    public void testGetInstanceMakerNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", null,
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un string vacio en maker
     */
    @Test
    public void testGetInstanceMakerEmpty() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "  ",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un null en description
     */
    @Test
    public void testGetInstanceDescriptionNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                null, 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un string vacio en description
     */
    @Test
    public void testGetInstanceDescriptionEmpty() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                " ", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un null
     */
    @Test
    public void testGetInstancePriceNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", null, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un zero
     */
    @Test
    public void testGetInstancePriceZero() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 0.0, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un numero negativo
     */
    @Test
    public void testGetInstancePriceNegative() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", -5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor negativo",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un null
     */
    @Test
    public void testGetInstanceHighNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, null, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un zero
     */
    @Test
    public void testGetInstanceHighZero() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.0, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un numero negativo
     */
    @Test
    public void testGetInstanceHighNegative() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, -0.3, 0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor negativo",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un null
     */
    @Test
    public void testGetInstanceWideNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, null, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un zero
     */
    @Test
    public void testGetInstanceWideZero() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.0, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un numero negativo
     */
    @Test
    public void testGetInstanceWideNegative() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, -0.2, 0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor negativo",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un null
     */
    @Test
    public void testGetInstanceDeepNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, null, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un zero
     */
    @Test
    public void testGetInstanceDeepZero() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.0, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un numero negativo
     */
    @Test
    public void testGetInstanceDeepNegative() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, -0.45, 1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor negativo",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un null
     */
    @Test
    public void testGetInstanceWeightNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, null, true, "function", "components", 1);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un zero
     */
    @Test
    public void testGetInstanceWeightZero() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 0.0, true, "function", "components", 1);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un numero negativo
     */
    @Test
    public void testGetInstanceWeightNegative() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, -1.3, true, "function", "components", 1);
        assertEquals("No debe admitir valor negativo",
                true,
                result.failed());
    }

    /*
        Miramos que no de un valor null
     */
    @Test
    public void testGetInstanceFragileNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, null, "function", "components", 1);
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un null en function
     */
    @Test
    public void testGetInstanceFunctionNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, null, "components", 1);
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un string vacio
     */
    @Test
    public void testGetInstanceFunctionEmpty() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "  ", "components", 1);
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un null en components
     */
    @Test
    public void testGetInstanceComponentsNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", null, 1);
        assertEquals("No debe admitir valor null",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un string vacio
     */
    @Test
    public void testGetInstanceComponentsEmpty() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", " ", 1);
        assertEquals("No debe admitir valor vacio",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un null
     */
    @Test
    public void testGetInstancePowerNull() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", null);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al pasarle un zero
     */
    @Test
    public void testGetInstancePowerZero() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", 0);
        assertEquals("No debe admitir valor 0",
                true,
                result.failed());
    }

    /*
        Miramos que nos de error al meter un numero negativo
     */
    @Test
    public void testGetInstancePowerNegative() {
        ResultRequest<Equipment> result = Equipment.getInstance("code", "name", "type", "maker",
                "description", 5.55, 0.3, 0.2, 0.45, 1.3, true, "function", "components", -1);
        assertEquals("No debe admitir valor negativo",
                true,
                result.failed());
    }

    @Test
    public void testSetCodeSuccess() {
        String valor = "code";

        ResultRequest result = e.setCode(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getCode());
    }

    @Test
    public void testSetCodeNull() {
        String valor = null;

        ResultRequest result = e.setCode(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetCodeEmpty() {
        String valor = " ";

        ResultRequest result = e.setCode(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetNameSuccess() {
        String valor = "name";

        ResultRequest result = e.setName(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getName());
    }

    @Test
    public void testSetNameNull() {
        String valor = null;

        ResultRequest result = e.setName(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetNameEmpty() {
        String valor = " ";

        ResultRequest result = e.setName(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetTypeSuccess() {
        String valor = "type";

        ResultRequest result = e.setType(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getType());
    }

    @Test
    public void testSetTypeNull() {
        String valor = null;

        ResultRequest result = e.setType(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetTypeEmpty() {
        String valor = " ";

        ResultRequest result = e.setType(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetMakerSuccess() {
        String valor = "maker";

        ResultRequest result = e.setMaker(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getMaker());
    }

    @Test
    public void testSetMakerNull() {
        String valor = null;

        ResultRequest result = e.setMaker(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetMakerEmpty() {
        String valor = " ";

        ResultRequest result = e.setMaker(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetDescriptionSuccess() {
        String valor = "description";

        ResultRequest result = e.setDescription(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getDescription());
    }

    @Test
    public void testSetDescriptionNull() {
        String valor = null;

        ResultRequest result = e.setDescription(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetDescriptionEmpty() {
        String valor = " ";

        ResultRequest result = e.setDescription(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetPriceSuccess() {
        Double valor = 1.2;

        ResultRequest result = e.setPrice(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getPrice());
    }

    @Test
    public void testSetPriceNull() {
        Double valor = null;

        ResultRequest result = e.setPrice(valor);

        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetPriceZero() {
        Double valor = 0.0;

        ResultRequest result = e.setPrice(valor);
        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetPriceNegative() {
        Double valor = -1.2;

        ResultRequest result = e.setPrice(valor);
        assertEquals("No debe admitir valor negativo", true, result.failed());
    }

    @Test
    public void testSetHighSuccess() {
        Double valor = 1.2;

        ResultRequest result = e.setHigh(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getHigh());
    }

    @Test
    public void testSetHighNull() {
        Double valor = null;

        ResultRequest result = e.setHigh(valor);

        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetHighZero() {
        Double valor = 0.0;

        ResultRequest result = e.setHigh(valor);
        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetHighNegative() {
        Double valor = -1.2;

        ResultRequest result = e.setHigh(valor);
        assertEquals("No debe admitir valor negativo", true, result.failed());
    }

    @Test
    public void testSetWideSuccess() {
        Double valor = 1.2;

        ResultRequest result = e.setWide(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getWide());
    }

    @Test
    public void testSetWideNull() {
        Double valor = null;

        ResultRequest result = e.setWide(valor);

        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetWideZero() {
        Double valor = 0.0;

        ResultRequest result = e.setWide(valor);
        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetWideNegative() {
        Double valor = -1.2;

        ResultRequest result = e.setWide(valor);
        assertEquals("No debe admitir valor negativo", true, result.failed());
    }

    @Test
    public void testSetDeepSuccess() {
        Double valor = 1.2;

        ResultRequest result = e.setDeep(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getDeep());
    }

    @Test
    public void testSetDeepNull() {
        Double valor = null;

        ResultRequest result = e.setDeep(valor);

        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetDeepZero() {
        Double valor = 0.0;

        ResultRequest result = e.setDeep(valor);
        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetDeepNegative() {
        Double valor = -1.2;

        ResultRequest result = e.setDeep(valor);
        assertEquals("No debe admitir valor negativo", true, result.failed());
    }

    @Test
    public void testSetWeightSuccess() {
        Double valor = 1.2;

        ResultRequest result = e.setWeight(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getWeight());
    }

    @Test
    public void testSetWeightNull() {
        Double valor = null;

        ResultRequest result = e.setWeight(valor);

        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetWeightZero() {
        Double valor = 0.0;

        ResultRequest result = e.setWeight(valor);
        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetWeightNegative() {
        Double valor = -1.2;

        ResultRequest result = e.setWeight(valor);
        assertEquals("No debe admitir valor negativo", true, result.failed());
    }

    @Test
    public void testSetFragileSuccess() {
        Boolean valor = true;

        ResultRequest result = e.setFragile(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.isFragile());
    }

    @Test
    public void testSetFragileNull() {
        Boolean valor = null;

        ResultRequest result = e.setFragile(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetFunctionSuccess() {
        String valor = "function";

        ResultRequest result = e.setFunction(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getFunction());
    }

    @Test
    public void testSetFunctionNull() {
        String valor = null;

        ResultRequest result = e.setFunction(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetFunctionEmpty() {
        String valor = " ";

        ResultRequest result = e.setFunction(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetComponentsSuccess() {
        String valor = "components";

        ResultRequest result = e.setComponents(valor);

        assertEquals("Deberia de funcionar bien", false, result.failed());

        assertEquals("El GET debe de funcionar bien", valor, e.getComponents());
    }

    @Test
    public void testSetComponentsNull() {
        String valor = null;

        ResultRequest result = e.setComponents(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetComponentsEmpty() {
        String valor = " ";

        ResultRequest result = e.setComponents(valor);
        assertEquals("No deberia de funcionar bien", true, result.failed());
    }

    @Test
    public void testSetPowerSuccess() {
        Integer valor = 1;

        ResultRequest result = e.setPower(valor);
        assertEquals("No debe admitir valor 0", false, result.failed());
        assertEquals("El GET debe de funcionar bien", valor, e.getPower());
    }

    @Test
    public void testSetPowerNull() {
        Integer valor = null;

        ResultRequest result = e.setPower(valor);
        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetPowerZero() {
        Integer valor = 0;

        ResultRequest result = e.setPower(valor);
        assertEquals("No debe admitir valor 0", true, result.failed());
    }

    @Test
    public void testSetPowerNegative() {
        Integer valor = -1;

        ResultRequest result = e.setPower(valor);
        assertEquals("No debe admitir valor negativo", true, result.failed());
    }
    /*-------*/
}
