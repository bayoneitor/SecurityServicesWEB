package securityservices.infrastructure.db.connectors;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;
import securityservices.shared.responses.ResultRequest;

public class JdbcConnector implements PersistenceConnector {

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private Json json = JsonObjectFactory.getInstance();

    public JdbcConnector(Json dataconex) {
        this.connect(dataconex);
    }

    public JdbcConnector(Connection dataconex) {
        this.connect = dataconex;
    }

    public JdbcConnector() {
    }

    @Override
    public ResultRequest<Json> connect(Json dataconnex) {
        try {
            String driver = dataconnex.get("db_driver");
            String address = dataconnex.get("db_address");
            String database = dataconnex.get("db_database");
            String user = dataconnex.get("db_user");
            String password = dataconnex.get("db_password");
            String port = dataconnex.get("db_port");

            if (driver == null || driver.trim().length() <= 2) {
                return ResultRequest.fails("\"Error\":\"No tiene Driver\"");
            }
            if (address == null || address.trim().length() <= 2) {
                return ResultRequest.fails("\"Error\":\"No tiene Address\"");
            }
            if (database == null || database.trim().length() <= 2) {
                return ResultRequest.fails("\"Error\":\"No tiene Database\"");
            }
            if (user == null || user.trim().length() <= 2) {
                return ResultRequest.fails("\"Error\":\"No tiene User\"");
            }
            if (password == null || password.trim().length() <= 2) {
                return ResultRequest.fails("\"Error\":\"No tiene Password\"");
            }
            if (port == null || port.trim().length() <= 2) {
                port = "5432";
            } else {
                try {
                    Integer.parseInt(port.trim());
                } catch (Exception ex) {
                    return ResultRequest.fails("\"Error\":\"El puerto tiene que ser un numero\"");
                }
            }
            if (driver.trim() == "postgresql") {
                Class.forName("org.postgresql.Driver");
            }

            this.connect = DriverManager.getConnection("jdbc:" + driver.trim() + "://" + address.trim() + ":" + port.trim() + "/" + database.trim() + "?"
                    + "user=" + user.trim() + "&password=" + password.trim());

            this.json.removeAll();
            this.json.set("Done", "Conexión Establecida");
            return ResultRequest.done(this.json);
        } catch (Exception e) {
            return ResultRequest.fails("\"Error\":\"" + e.toString() + "\"");
        }
    }

    @Override
    public Boolean isConnect() {
        try {
            if (this.connect.isClosed()) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public ResultRequest<Json> readQuery(String query) {
        if (!this.isConnect()) {
            return ResultRequest.fails("\"Error\":\"La conexion no esta iniciada\"");
        }
        if (query.trim().length() < 13) {
            return ResultRequest.fails("\"Error\":\"No has introducido una Query correcta\"");
        }
        try {
            this.statement = this.connect.createStatement();
            this.resultSet = this.statement.executeQuery(query);
            boolean empty = true;

            ResultSetMetaData mData = resultSet.getMetaData();
            int ncolumns = mData.getColumnCount();

            this.json.removeAll();
            Json jArray = JsonObjectFactory.getInstance();
            Json jObject = JsonObjectFactory.getInstance();

            while (resultSet.next()) {
                empty = false;
                jObject.removeAll();
                for (int i = 1; i <= ncolumns; i++) {
                    jObject.set(mData.getColumnName(i), resultSet.getString(i));
                }
                jArray.set("content", jObject);
                this.json.set(jArray.toString());
            }
            if (empty) {
                return ResultRequest.fails("\"Error\":\"No se ha encontrado ningun dato\"");
            }
            return ResultRequest.done(this.json);
        } catch (Exception ex) {
            return ResultRequest.fails("\"Error\":\"" + ex + "\"");
        }
    }

    @Override
    public ResultRequest<Json> unconnect() {
        try {
            if (this.resultSet != null) {
                this.resultSet.close();
            }

            if (this.statement != null) {
                this.statement.close();
            }

            if (this.connect != null) {
                this.connect.close();
            }

            this.json.removeAll();
            this.json.set("Done", "Desconectado Correctamente");
            return ResultRequest.done(this.json);
        } catch (Exception e) {
            return ResultRequest.fails("\"Error\":\"" + e.toString() + "\"");
        }
    }

    @Override
    public ResultRequest<Json> writeQuery(String query) {
        if (!this.isConnect()) {
            return ResultRequest.fails("\"Error\":\"La conexion no esta iniciada\"");
        }
        if (query.trim().length() < 13) {
            return ResultRequest.fails("\"Error\":\"No has introducido una Query correcta\"");
        }

        try {
            this.statement = this.connect.createStatement();
            this.statement.executeUpdate(query);

            this.json.removeAll();
            this.json.set("Done", "Realizado Correctamente");
            return ResultRequest.done(this.json);
        } catch (Exception e) {
            return ResultRequest.fails("\"Error\":\"" + e.toString() + "\"");
        }
    }

}
