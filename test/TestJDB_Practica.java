
import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;
import securityservices.infrastructure.db.connectors.JdbcConnector;
import securityservices.shared.responses.ResultRequest;

public class TestJDB_Practica {

    public static void main(String[] args) {
        Json jsonConn = JsonObjectFactory.getInstance();
        JdbcConnector jdbc = new JdbcConnector();
        
        jsonConn.set("db_driver", "postgresql");
        jsonConn.set("db_address", "localhost");
        //json.set("db_port", "1234");
        jsonConn.set("db_database", "SecurityServices");
        jsonConn.set("db_user", "SecurityServices");
        jsonConn.set("db_password", "linuxlinux");
        //Conexion
        System.out.println("---- Conexión a BD");
        ResultRequest connect = jdbc.connect(jsonConn);
        if (connect.failed()) {
            System.out.println(connect.getError());
        } else {
            System.out.println(connect.getValue());
        }
        //Miramos que este conectado
        System.out.println("---- Miramos que este conectado");
        System.out.println(jdbc.isConnect());

        //Seleccionamos todos los clientes
        System.out.println("---- Seleccionamos todos los clientes");
        ResultRequest select = jdbc.readQuery("SELECT * FROM clients");
        if (select.failed()) {
            System.out.println(select.getError());
        } else {
            System.out.println(select.getValue());
        }
        //Update de 1 cliente
        System.out.println("---- Update del cliente C-01");
        ResultRequest writeQuery = jdbc.writeQuery("UPDATE clients SET name = 'Paco' WHERE code = 'C-01'");
        if (writeQuery.failed()) {
            System.out.println(writeQuery.getError());
        } else {
            System.out.println(writeQuery.getValue());
        }
        //Ver cambios del cliente modificado
        System.out.println("---- Ver cambios del cliente modificado");
        select = jdbc.readQuery("SELECT * FROM clients WHERE code = 'C-01'");
        if (select.failed()) {
            System.out.println(select.getError());
        } else {
            System.out.println(select.getValue());
        }

        //Eliminamos todo lo de Order
        System.out.println("---- Eliminar datos de Orders");
        /*writeQuery = jdbc.writeQuery("DELETE FROM orders");
        if (writeQuery.failed()) {
            System.out.println(writeQuery.getError());
        } else {
            System.out.println(writeQuery.getValue());
        }*/
        //Seleccionamos lo que hay en order, nos debería de dar un error de vacio
        System.out.println("---- Seleccionamos Orders para ver que no hay nada");
        select = jdbc.readQuery("SELECT * FROM orders");
        if (select.failed()) {
            System.out.println(select.getError());
        } else {
            System.out.println(select.getValue());
        }
        //Insertamos un order
        System.out.println("---- Insertamos un order");
        writeQuery = jdbc.writeQuery("INSERT INTO orders VALUES ('O-08', 1, 29.92,0.2, 'type', 'status', 'additional info', '27/01/2021 18:46:30', '29/01/2021 18:46:30', '53d8cb13-d66c-48f4-a77e-cbbc806948f0', 'credit', '29/01/2021 18:46:30',"
                + "'[{\"ref\": \"Ref-1\", \"price\": \"5.3\", \"amount\": \"1\"}, {\"ref\": \"Ref-2\", \"price\": \"2.3\", \"amount\": \"5\"}, {\"ref\": \"Ref-3\", \"price\": \"10.3\", \"amount\": \"2\"}]')");
        if (writeQuery.failed()) {
            System.out.println(writeQuery.getError());
        } else {
            System.out.println(writeQuery.getValue());
        }
        //Seleccionamos order, para ver el insertado
        System.out.println("---- Seleccionamos order, para ver el insertado");
        select = jdbc.readQuery("SELECT * FROM orders");
        if (select.failed()) {
            System.out.println(select.getError());
        } else {
            System.out.println(select.getValue());
        }

        //Desconectar
        System.out.println("---- Nos desconectamos de la BD");
        ResultRequest unconnect = jdbc.unconnect();
        if (unconnect.failed()) {
            System.out.println(unconnect.getError());
        } else {
            System.out.println(unconnect.getValue());
        }
        //Miramos el estado de la conexion
        System.out.println("---- Miramos el estado de la conexion");
        System.out.println(jdbc.isConnect());

    }
}
