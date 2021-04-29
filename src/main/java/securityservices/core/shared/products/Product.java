package securityservices.core.shared.products;

import securityservices.shared.responses.ResultRequest;
import securityservices.shared.responses.ResultResponses;

public abstract class Product implements Marketable {

    protected String code, name, type, maker, description;
    protected Double price;
    protected Boolean available;

    public Product() {
        this.available = true;
    }
    public ResultRequest setPrice(Double price) {
        if (price == null || price <= 0) {
            return ResultRequest.fails("\"Error\":\"invalid price\"");
        }
        this.price = price;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }
    public ResultRequest setDescription(String description) {
        if (description == null || description.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid description\"");
        }
        this.description = description;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }
    public ResultRequest setMaker(String maker) {
        if (maker == null || maker.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid maker\"");
        }
        this.maker = maker;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }
    
    public ResultRequest setType(String type) {
        if (type == null || type.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid type\"");
        }
        this.type = type;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }
    
    public ResultRequest setCode(String code) {
        if (code == null || code.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid code\"");
        }
        this.code = code;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setName(String name) {
        if (name == null || name.trim().equals("")) {
            return ResultRequest.fails("\"Error\":\"invalid name\"");
        }
        this.name = name;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean isAvailable() {
        return available;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
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

    @Override
    public Double getPrice() {
        return price;
    }

}
