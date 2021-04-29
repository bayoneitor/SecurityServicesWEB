package securityservices;

import securityservices.core.component.order.domain.model.Order;
import securityservices.core.shared.services.Stock;

public class SecurityServices {

    public static void main(String[] args) {
        /*EXPRESIONES REGULARES*/
 /*
        int error = Check.checkExample("4500-2570=1930");
        switch (error) {
            case 0:
                System.out.println("Operación Correcta");
                break;
            case -1:
                System.out.println("Cadena Mal Formada");
                break;
            case -2:
                System.out.println("Operación Incorrecta");
                break;
        }

        error = Check.checkExample("45/5=9");
        switch (error) {
            case 0:
                System.out.println("Operación Correcta");
                break;
            case -1:
                System.out.println("Cadena Mal Formada");
                break;
            case -2:
                System.out.println("Operación Incorrecta");
                break;
        }

        int statusDNI = Check.checkDNI("53319469-H");
        switch (statusDNI) {
            case 0:
                System.out.println("DNI correcte");
                break;
            case -1:
                System.out.println("DNI incorrecte");
                break;
        }
        //Comprobar va bien con año bisiesto
        int statusDate1 = Check.checkDate("29/2/2020");
        switch (statusDate1) {
            case 0:
                System.out.println("Data correcte");
                break;
            case -1:
                System.out.println("Data incorrecte");
                break;
        }
        //Comprobar que salga error si ponemos un año que no es bisiesto
        int statusDate2 = Check.checkDate("29/2/2021");
        switch (statusDate2) {
            case 0:
                System.out.println("Data correcte");
                break;
            case -1:
                System.out.println("Data incorrecte");
                break;
        }
        //Diferencia de dias
        System.out.println("Diferencia Dias:" + Check.diffDates("19/2/2020", "20/8/2025"));
        //Check Email
        int statusEmail = Check.checkEmail("david@gmail.com");
        switch (statusEmail) {
            case 0:
                System.out.println("Email correcte");
                break;
            case -1:
                System.out.println("Email incorrecte");
                break;
        }*/
/*
        //SetOrder
        System.out.println("---------- Array Order ----------");
        Order ord = new Order();
        //ord 1
        String det1 = ord.setDetail("Ref-1;50;0.2");
        System.out.println("Set ord1: " + det1);
        //Obtener order
        //-- Int
        String getDetN = ord.getDetail(1);
        System.out.println("Obtenido por num: " + getDetN);
        //-- String
        String getDetS = ord.getDetail("Ref-1");
        System.out.println("Obtenido por String: " + getDetS);
        //Actualizar order
        //-- Int
        System.out.println("Update ord1 status(int): " + ord.updateDetail(1, "Ref-1;12;0.9"));
        String getDetNUp = ord.getDetail(1);
        System.out.println("Update por num: " + getDetNUp);
        //-- String
        System.out.println("Update ord1 status(string): " + ord.updateDetail("Ref-1", "Ref-1;12;0.10"));
        String getDetSUp = ord.getDetail("Ref-1");
        System.out.println("Update por string: " + getDetSUp);
        //Eliminacion order
        //-- Int
        System.out.println("Delete Status (int)" + ord.deleteDetail(1));
        System.out.println("Delete por INT: " + ord.getDetail(1));
        //-- String
        System.out.println("Delete Status (string)" + ord.deleteDetail("Ref-1"));
        System.out.println("Delete por STRING: " + ord.getDetail(1));
        //Precio total(Añadimos productos para probar)
        ord.setDetail("Ref-1;15;5.50");
        ord.setDetail("Ref-2;5;92.9");
        ord.setDetail("Ref-3;20;19.99");
        ord.setDetail("Ref-4;4;4.30");
        //se le puede dar un surcharges comprueba si es mayor que 0, si no, no lo aplica
        System.out.println("Precio Total: " + ord.getValue());

        //---------------------------------- STOCK 
        System.out.println("---------- Stock ----------");
        Stock st = new Stock();
        System.out.println("Añadir producto status: " + st.updateStock("asd", 5));
        System.out.println("Mirar producto: " + st.getAmount("asd"));
        //Añadimos 5 mas
        System.out.println("Añadimos 5 de stock status: " + st.updateStock("asd", 5));
        System.out.println("Mirmaos que se hayan añadido: " + st.getAmount("asd"));
        //restamos de mas
        System.out.println("Quitamos productos de más (status): " + st.updateStock("asd", -11));
        System.out.println("Miramos como se queda despues de `eliminar`: " + st.getAmount("asd"));
        //restamos 10 justos y vemos que se habrá eliminado
        System.out.println("Quitamos productos exactos (status): " + st.updateStock("asd", -10));
        //Saldrá -1 por que no existe
        System.out.println("Miramos como se queda despues de eliminar: " + st.getAmount("asd"));
        //Añado varios productos mas
        st.updateStock("Ordenadores", 5);
        st.updateStock("Licencia Windows", 8);
        st.updateStock("NAS", 2);
        st.updateStock("Antivirus", 9);
        //Retorna el size del array
        System.out.println("Miramos cuantos productos existen: " + st.getNumLines());
        //Printa el stock
        System.out.println("------Lista de stock");
        for (int i = 0; i < st.getNumLines(); i++) {
            System.out.println(st.getLines()[i]);
        }*/
    }
}
