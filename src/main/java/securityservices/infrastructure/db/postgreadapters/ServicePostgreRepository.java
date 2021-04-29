package securityservices.infrastructure.db.postgreadapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import securityservices.core.component.service.appservices.JsonServiceSerializer;
import securityservices.core.component.service.domain.services.ServiceDTO;
import securityservices.core.component.service.infraestructure.ServiceRepository;
import securityservices.core.shared.services.serializers.Json;
import securityservices.infrastructure.db.connectors.JdbcConnector;
import securityservices.infrastructure.db.connectors.PersistenceConnector;
import securityservices.shared.responses.ResultRequest;

public class ServicePostgreRepository implements ServiceRepository {

    private PersistenceConnector connection;
    JsonServiceSerializer JSSerializer = new JsonServiceSerializer();

    public ServicePostgreRepository(Json dataconnex) {
        this.connection = new JdbcConnector(dataconnex);
    }

    public ServicePostgreRepository(PersistenceConnector connection) {
        this.connection = connection;
    }

    private String date_DTOFromBD(String date) {
        if (date == null) {
            return null;
        }
        final String OLD_FORMAT = "yyyy'-'MM'-'dd HH:mm:ssX";
        final String NEW_FORMAT = "dd'-'MM'-'yyyy";
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
        final String OLD_FORMAT = "dd'-'MM'-'yyyy";
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
    public ResultRequest<List<ServiceDTO>> getAll() {
        ResultRequest<Json> query = this.connection.readQuery("SELECT * FROM services");
        if (query.failed()) {
            return ResultRequest.fails(query.getError());
        }
        Json jServices = query.getValue();
        List<ServiceDTO> serviceArr = new ArrayList<ServiceDTO>();
        for (int i = 0; i < jServices.getArraySize("content"); i++) {
            Json jService = jServices.getArrayObj("content", i);
            String startDate = jService.get("startdate");
            String finishDate = jService.get("finishdate");

            jService.remove("startdate");
            jService.remove("finishdate");

            jService.set("startDate", this.date_DTOFromBD(startDate));
            jService.set("finishDate", this.date_DTOFromBD(finishDate));

            jService.set("serviceId", "123");
            serviceArr.add(this.JSSerializer.unserialize(jService.toString()).getValue());
        }
        return ResultRequest.done(serviceArr);

    }

    @Override
    public ResultRequest<ServiceDTO> getByID(String id) {
        ResultRequest<Json> query = this.connection.readQuery("SELECT * FROM services WHERE code='" + id + "'");
        if (query.failed()) {
            return ResultRequest.fails(query.getError());
        }
        Json jService = query.getValue().getJResult("content");
        String startDate = jService.get("startdate");
        String finishDate = jService.get("finishdate");

        jService.remove("startdate");
        jService.remove("finishdate");

        jService.set("startDate", this.date_DTOFromBD(startDate));
        jService.set("finishDate", this.date_DTOFromBD(finishDate));

        jService.set("serviceId", "123");
        return this.JSSerializer.unserialize(jService.toString());
    }

    @Override
    public ResultRequest<String> add(ServiceDTO service) {
        ResultRequest<Json> query = this.connection.writeQuery("INSERT INTO services VALUES('" + service.getCode() + "','" + service.getName()
                + "','" + service.getType() + "','" + service.getMaker() + "','" + service.getDescription()
                + "','" + service.getPrice() + "','" + service.getPeriodicity()
                + "','" + service.getConditions() + "','" + this.date_BDFromDTO(service.getStartDate()) + "','" + this.date_BDFromDTO(service.getFinishDate()) + "');");
        if (query.failed()) {
            return ResultRequest.fails("Error al insertar en base de datos el service con id " + service.getCode() + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha insertado correctamente el service con id " + service.getCode());
        }
    }

    @Override
    public ResultRequest<String> update(ServiceDTO service) {
        ResultRequest<Json> query = this.connection.writeQuery("UPDATE services SET name='" + service.getName()
                + "', type='" + service.getType() + "', maker='" + service.getMaker() + "', " + "description='" + service.getDescription()
                + "', price=" + service.getPrice() + ", periodicity='" + service.getPeriodicity()
                + "', conditions='" + service.getConditions() + "', startdate='" + this.date_BDFromDTO(service.getStartDate())
                + "', finishdate='" + this.date_BDFromDTO(service.getFinishDate()) 
                + "' WHERE code='" + service.getCode() + "';");
        if (query.failed()) {
            return ResultRequest.fails("Error al actualizar en base de datos el service con id " + service.getCode() + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha actualizado correctamente el service con id " + service.getCode());
        }
    }

    @Override
    public ResultRequest<String> deleteByID(String id) {
        ResultRequest<Json> query = this.connection.writeQuery("DELETE FROM services WHERE code='" + id + "'");
        if (query.failed()) {
            return ResultRequest.fails("Error al borrar en base de datos el service con id " + id + ". Error: " + query.getError());
        } else {
            return ResultRequest.done("Se ha borrado correctamente el service con id " + id);
        }
    }

    @Override
    public Boolean exists(String id) {
        if (this.connection.readQuery("SELECT code FROM services WHERE code='" + id + "'").failed()) {
            return false;
        } else {
            return true;
        }
    }
}
