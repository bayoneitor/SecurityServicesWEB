package securityservices.core.component.equipment.domain.model;

import java.util.UUID;
import securityservices.core.shared.products.PhysicalProduct;
import securityservices.core.shared.products.Storable;
import securityservices.shared.PhysicalData;
import securityservices.shared.responses.ResultRequest;
import securityservices.shared.responses.ResultResponses;

public class Equipment extends PhysicalProduct implements Storable {

    protected UUID equipmentId;
    protected String function, components;
    protected Integer power;
    
    private Equipment() {
        this.setEquipmentId();
    }

    public static ResultRequest<Equipment> getInstance(String code, String name, String type, String maker, String description,
            Double price, Double high, Double wide, Double deep, Double weight, Boolean fragile,
            String function, String components, Integer power) {
        Equipment equipment = new Equipment();

        ResultRequest<PhysicalData> physicRequest = PhysicalData.getInstance(high, wide, deep, weight, fragile);
        if (physicRequest.failed()) {
            return ResultRequest.fails(physicRequest.getError());
        }
        
        equipment.setPhysics(physicRequest.getValue());

        ResultRequest result = equipment.setCode(code);
        if (result.failed()) {
            return result;
        }

        result = equipment.setName(name);
        if (result.failed()) {
            return result;
        }

        result = equipment.setType(type);
        if (result.failed()) {
            return result;
        }

        result = equipment.setMaker(maker);
        if (result.failed()) {
            return result;
        }

        result = equipment.setDescription(description);
        if (result.failed()) {
            return result;
        }

        result = equipment.setPrice(price);
        if (result.failed()) {
            return result;
        }

        result = equipment.setFunction(function);
        if (result.failed()) {
            return result;
        }
        result = equipment.setComponents(components);
        if (result.failed()) {
            return result;
        }
        result = equipment.setPower(power);
        if (result.failed()) {
            return result;
        }
        return ResultRequest.done(equipment);
    }

    public ResultRequest setComponents(String components) {
        if (components == null || components.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid components\"");
        }
        this.components = components;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setFunction(String function) {
        if (function == null || function.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid function\"");
        }
        this.function = function;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setPower(Integer power) {
        if (power == null || power <= 0) {
            return ResultRequest.fails("\"Error\":\"invalid power\"");
        }
        this.power = power;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    protected void setEquipmentId() {
        this.equipmentId = UUID.randomUUID();
    }

    public String getEquipmentId() {
        return equipmentId.toString();
    }

    public String getFunction() {
        return function;
    }

    public Integer getPower() {
        return power;
    }

    public String getComponents() {
        return components;
    }

    @Override
    public String getDetails() {
        return "Function:" + function + ";Components:" + this.components;
    }

    @Override
    public String getDimensions() {
        return "W:" + this.physics.getWide() + ";D:" + this.physics.getDeep() + ";H:" + this.physics.getHigh();
    }

    @Override
    public Double getVolum() {
        return this.physics.getHigh() * this.physics.getWide() * this.physics.getDeep();
    }
}
