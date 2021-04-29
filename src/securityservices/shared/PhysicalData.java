package securityservices.shared;

import securityservices.shared.responses.ResultRequest;
import securityservices.shared.responses.ResultResponses;

public class PhysicalData {

    protected Double high, wide, deep, weight;
    protected Boolean fragile;

    public PhysicalData() {
    }

    public static ResultRequest<PhysicalData> getInstance(Double high, Double wide, Double deep, Double weight, Boolean fragile) {
        PhysicalData physics = new PhysicalData();
       
        ResultRequest result = physics.setHigh(high);
        if (result.failed()) {
            return result;
        }

        result = physics.setWide(wide);
        if (result.failed()) {
            return result;
        }
        
        result = physics.setDeep(deep);
        if (result.failed()) {
            return result;
        }

        result = physics.setWeight(weight);
        if (result.failed()) {
            return result;
        }

        result = physics.setFragile(fragile);
        if (result.failed()) {
            return result;
        }

        return ResultRequest.done(physics);
    }

    public ResultRequest setFragile(Boolean fragile) {
        if (fragile == null || fragile != true && fragile != false) {
            return ResultRequest.fails("\"Error\":\"invalid fragile\"");
        }
        this.fragile = fragile;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setWeight(Double weight) {
        if (weight == null || weight <= 0) {
            return ResultRequest.fails("\"Error\":\"invalid weight\"");
        }
        this.weight = weight;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setDeep(Double deep) {
        if (deep == null || deep <= 0) {
            return ResultRequest.fails("\"Error\":\"invalid deep\"");
        }
        this.deep = deep;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setWide(Double wide) {
        if (wide == null || wide <= 0) {
            return ResultRequest.fails("\"Error\":\"invalid wide\"");
        }
        this.wide = wide;
        return ResultRequest.done(ResultResponses.SUCCESS);
    }

    public ResultRequest setHigh(Double high) {
        if (high == null || high <= 0) {
            return ResultRequest.fails("\"Error\":\"invalid high\"");
        }
        this.high = high;
        return ResultRequest.done(ResultResponses.SUCCESS);
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

    public Boolean isFragile() {
        return fragile;
    }
}
