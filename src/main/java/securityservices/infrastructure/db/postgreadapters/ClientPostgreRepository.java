package securityservices.infrastructure.db.postgreadapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import securityservices.core.component.client.appservices.JsonClientSerializer;
import securityservices.core.component.client.domain.services.ClientDTO;
import securityservices.core.component.client.infraestructure.ClientRepository;
import securityservices.core.shared.services.serializers.Json;
import securityservices.infrastructure.db.connectors.JdbcConnector;
import securityservices.infrastructure.db.connectors.PersistenceConnector;
import securityservices.shared.responses.ResultRequest;

public class ClientPostgreRepository implements ClientRepository {

    private PersistenceConnector connection;
    JsonClientSerializer JCSerializer = new JsonClientSerializer();

    public ClientPostgreRepository(Json dataconnex) {
        this.connection = new JdbcConnector(dataconnex);
    }

    public ClientPostgreRepository(PersistenceConnector connection) {
        this.connection = connection;
    }

    private String date_DTOFromBD(String date) {
        if (date == null) {
            return null;
        }
        final String OLD_FORMAT = "yyyy'-'MM'-'dd";
        final String NEW_FORMAT = "dd'-'MMMM'-'yyyy";
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
        final String OLD_FORMAT = "dd'-'MMMM'-'yyyy";
        final String NEW_FORMAT = "yyyy'-'MM'-'dd";
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

    @Override
    public ResultRequest<List<ClientDTO>> getAll() {
        ResultRequest<Json> query = this.connection.readQuery("SELECT * FROM clients");
        if (query.failed()) {
            return ResultRequest.fails(query.getError());
        }
        Json jClients = query.getValue();
        List<ClientDTO> clientArr = new ArrayList<ClientDTO>();
        for (int i = 0; i < jClients.getArraySize("content"); i++) {
            Json jClient = jClients.getArrayObj("content", i);
            String birthday = jClient.get("birthday");
            jClient.remove("birthday");
            jClient.set("birthday", this.date_DTOFromBD(birthday));

            clientArr.add(this.JCSerializer.unserialize(jClient.toString()).getValue());
        }
        return ResultRequest.done(clientArr);

    }

    @Override
    public ResultRequest<ClientDTO> getByID(String id) {
        ResultRequest<Json> query = this.connection.readQuery("SELECT * FROM clients WHERE code='" + id + "'");
        if (query.failed()) {
            return ResultRequest.fails(query.getError());
        }
        Json jClient = query.getValue().getJResult("content");
        String birthday = jClient.get("birthday");
        jClient.remove("birthday");
        jClient.set("birthday", this.date_DTOFromBD(birthday));
        return this.JCSerializer.unserialize(jClient.toString());
    }

    @Override
    public ResultRequest<String> add(ClientDTO client) {
        ResultRequest<Json> query = this.connection.writeQuery("INSERT INTO clients VALUES('" + client.getCode() + "','" + client.getName()
                + "','" + client.getAddress() + "','" + client.getPhone() + "','" + client.getEmail()
                + "','" + client.isCompany() + "','" + this.date_BDFromDTO(client.getBirthday())
                + "','" + client.getClientId() + "','" + client.getPassword() + "','" + client.getNumEquipments() + "');");
        if (query.failed()) {
            return ResultRequest.fails("Error al insertar en base de datos el client con id " + client.getCode() + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha insertado correctamente el client con id " + client.getCode());
        }
    }

    @Override
    public ResultRequest<String> update(ClientDTO client) {
        ResultRequest<Json> query = this.connection.writeQuery("UPDATE clients SET name='" + client.getName()
                + "', address='" + client.getAddress() + "', phone='" + client.getPhone() + "', " + "email='" + client.getEmail()
                + "', company=" + client.isCompany() + ", birthday='" + this.date_BDFromDTO(client.getBirthday())
                + "', clientid='" + client.getClientId() + "', password='" + client.getPassword()
                + "', numequipments='" + client.getNumEquipments() 
                + "' WHERE code='" + client.getCode() + "';");
        if (query.failed()) {
            return ResultRequest.fails("Error al actualizar en base de datos el client con id " + client.getCode() + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha actualizado correctamente el client con id " + client.getCode());
        }
    }

    @Override
    public ResultRequest<String> deleteByID(String id) {
        ResultRequest<Json> query = this.connection.writeQuery("DELETE FROM clients WHERE code='" + id + "'");
        if (query.failed()) {
            return ResultRequest.fails("Error al borrar en base de datos el client con id " + id + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha borrado correctamente el client con id " + id);
        }
    }

    @Override
    public Boolean exists(String id) {
        if (this.connection.readQuery("SELECT code FROM clients WHERE code='" + id + "'").failed()) {
            return false;
        } else {
            return true;
        }
    }
}
