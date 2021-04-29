package securityservices.core.component.equipment.domain.services;

import securityservices.core.component.equipment.domain.services.*;

/*
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
 */

 /*
DTO inmutable generado a traves del constructor para transferirlo a otra capa
sin permitir cambios en su contenido
 */
public class EquipmentDTO {

    private final String code, name, type, maker, description, function, components, equipmentId;
    private final Double price, high, wide, deep, weight;
    private final Integer power;
    private final Boolean fragile;

    public EquipmentDTO(String code, String name, String type, String maker, String description,
            Double price, Double high, Double wide, Double deep, Double weight, Boolean fragile,
            String function, String components, Integer power, String equipmentId) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.maker = maker;
        this.description = description;
        this.price = price;
        this.high = high;
        this.wide = wide;
        this.deep = deep;
        this.weight = weight;
        this.fragile = fragile;
        this.function = function;
        this.components = components;
        this.power = power;
        this.equipmentId = equipmentId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMaker() {
        return maker;
    }

    public String getDescription() {
        return description;
    }

    public String getFunction() {
        return function;
    }

    public String getComponents() {
        return components;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public Double getPrice() {
        return price;
    }

    public Double getHigh() {
        return high;
    }

    public Double getWide() {
        return wide;
    }

    public Double getDeep() {
        return deep;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getPower() {
        return power;
    }

    public Boolean isFragile() {
        return fragile;
    }

    
}
