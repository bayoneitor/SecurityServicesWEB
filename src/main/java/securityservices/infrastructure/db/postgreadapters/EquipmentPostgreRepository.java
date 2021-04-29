package securityservices.infrastructure.db.postgreadapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import securityservices.core.component.equipment.appservices.JsonEquipmentSerializer;
import securityservices.core.component.equipment.domain.services.EquipmentDTO;
import securityservices.core.component.equipment.infraestructure.EquipmentRepository;
import securityservices.core.shared.services.serializers.Json;
import securityservices.core.shared.services.serializers.JsonObjectFactory;
import securityservices.infrastructure.db.connectors.JdbcConnector;
import securityservices.infrastructure.db.connectors.PersistenceConnector;
import securityservices.shared.responses.ResultRequest;

public class EquipmentPostgreRepository implements EquipmentRepository {

    private PersistenceConnector connection;
    JsonEquipmentSerializer JESerializer = new JsonEquipmentSerializer();

    public EquipmentPostgreRepository(Json dataconnex) {
        this.connection = new JdbcConnector(dataconnex);
    }

    public EquipmentPostgreRepository(PersistenceConnector connection) {
        this.connection = connection;
    }

    private Json physicData_DTOFromBD(Json json) {
        Json physicData = JsonObjectFactory.getInstance();
        physicData.set(json.get("physicdata").toString());
        
        json.set("deep", physicData.get("deep"));
        json.set("high", physicData.get("high"));
        json.set("wide", physicData.get("wide"));
        json.set("weight", physicData.get("weight"));
        json.set("fragile", physicData.get("fragile"));
        return json;
    }

    private String physicData_BDFromDTO(EquipmentDTO equipment) {
        return "{\"deep\":" + equipment.getDeep()
                + ",\"high\":" + equipment.getHigh()
                + ",\"wide\":" + equipment.getWide()
                + ",\"weight\":" + equipment.getWeight()
                + ",\"fragile\": " + equipment.isFragile()
                + "}";
    }

    @Override
    public ResultRequest<List<EquipmentDTO>> getAll() {
        ResultRequest<Json> query = this.connection.readQuery("SELECT * FROM equipments");
        if (query.failed()) {
            return ResultRequest.fails(query.getError());
        }
        Json jEquipments = query.getValue();
        List<EquipmentDTO> equipmentArr = new ArrayList<EquipmentDTO>();
        for (int i = 0; i < jEquipments.getArraySize("content"); i++) {
            Json jEquipment =this.physicData_DTOFromBD(jEquipments.getArrayObj("content", i));

            jEquipment.set("equipmentId", "123");
            equipmentArr.add(this.JESerializer.unserialize(jEquipment.toString()).getValue());
        }
        return ResultRequest.done(equipmentArr);

    }

    @Override
    public ResultRequest<EquipmentDTO> getByID(String id) {
        ResultRequest<Json> query = this.connection.readQuery("SELECT * FROM equipments WHERE code='" + id + "'");
        if (query.failed()) {
            return ResultRequest.fails(query.getError());
        }
        Json jEquipment = this.physicData_DTOFromBD(query.getValue().getJResult("content"));

        jEquipment.set("equipmentId", "123");
        return this.JESerializer.unserialize(jEquipment.toString());
    }

    @Override
    public ResultRequest<String> add(EquipmentDTO equipment) {
        ResultRequest<Json> query = this.connection.writeQuery("INSERT INTO equipments VALUES('" + equipment.getCode() + "','" + equipment.getName()
                + "','" + equipment.getType() + "','" + equipment.getMaker() + "','" + equipment.getDescription()
                + "','" + equipment.getPrice() + "','" + equipment.getFunction() + "','" + equipment.getComponents()
                + "','" + equipment.getPower() + "','" + this.physicData_BDFromDTO(equipment) + "');");
        if (query.failed()) {
            return ResultRequest.fails("Error al insertar en base de datos el equipment con id " + equipment.getCode() + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha insertado correctamente el equipment con id " + equipment.getCode());
        }
    }

    @Override
    public ResultRequest<String> update(EquipmentDTO equipment) {
        ResultRequest<Json> query = this.connection.writeQuery("UPDATE equipments SET name='" + equipment.getName()
                + "', type='" + equipment.getType() + "', maker='" + equipment.getMaker() + "', " + "description='" + equipment.getDescription()
                + "', price=" + equipment.getPrice() + ", function='" + equipment.getFunction()
                + "', components='" + equipment.getComponents() + "', power='" + equipment.getPower()
                + "', physicdata='" + this.physicData_BDFromDTO(equipment) 
                + "' WHERE code='" + equipment.getCode() + "';");
        if (query.failed()) {
            return ResultRequest.fails("Error al actualizar en base de datos el equipment con id " + equipment.getCode() + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha actualizado correctamente el equipment con id " + equipment.getCode());
        }
    }

    @Override
    public ResultRequest<String> deleteByID(String id) {
        ResultRequest<Json> query = this.connection.writeQuery("DELETE FROM equipments WHERE code='" + id + "'");
        if (query.failed()) {
            return ResultRequest.fails("Error al borrar en base de datos el equipment con id " + id + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha borrado correctamente el equipment con id " + id);
        }
    }

    @Override
    public Boolean exists(String id) {
        if (this.connection.readQuery("SELECT code FROM equipments WHERE code='" + id + "'").failed()) {
            return false;
        } else {
            return true;
        }
    }
}
