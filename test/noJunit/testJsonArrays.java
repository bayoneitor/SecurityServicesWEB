package noJunit;

import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;

public class testJsonArrays {
    public static void main (String [] args) {        
        System.out.println ("Prueba generando un json que incluye una matriz de objetos json:");

        Json jdata = JsonObjectFactory.getInstance();
        Json jobject = JsonObjectFactory.getInstance();
        Json jarray = JsonObjectFactory.getInstance();
        Json jdetail = JsonObjectFactory.getInstance();
        
        jdetail.set ("ref", "22");
        jdetail.set ("amount", "2");
        jdetail.set ("price", "9.99");
        jarray.set ("detalles", jdetail);
        
        jdetail.removeAll ();
        
        jdetail.set ("ref", "23");
        jdetail.set ("amount", "3");
        jdetail.set ("price", "6.99");
        jarray.set ("detalles", jdetail);
        
        jdetail.removeAll ();
        
        jdetail.set ("ref", "24");
        jdetail.set ("amount", "4");
        jdetail.set ("price", "3.99");
        jarray.set ("detalles", jdetail);
        
        jobject.set (jarray.toString ());
        
        jobject.set ("Codigo", "1");
        
        jdata.set ("orden", jobject);
        System.out.println (jdata.toString ());
    }
}