package securityservices.core.shared.products;

import securityservices.shared.PhysicalData;
import securityservices.shared.responses.ResultRequest;

public abstract class PhysicalProduct extends Product implements Storable {

    protected Double high, wide, deep, weight;
    protected Boolean fragile;
    protected PhysicalData physics;

    public PhysicalProduct() {
    }

    public ResultRequest setHigh(Double high) {
        return this.physics.setHigh(high);
    }

    public ResultRequest setWide(Double wide) {
        return this.physics.setWide(wide);
    }

    public ResultRequest setDeep(Double deep) {
        return this.physics.setDeep(deep);
    }

    public ResultRequest setWeight(Double weight) {
        return this.physics.setWeight(weight);
    }

    public ResultRequest setFragile(Boolean fragile) {
        return this.physics.setFragile(fragile);
    }

    public void setPhysics(PhysicalData physics) {
        this.physics = physics;
    }

    public Double getHigh() {
        return this.physics.getHigh();
    }

    public Double getWide() {
        return this.physics.getWide();
    }

    public Double getDeep() {
        return this.physics.getDeep();
    }

    @Override
    public Double getWeight() {
        return this.physics.getWeight();
    }

    @Override
    public Boolean isFragile() {
        return this.physics.isFragile();
    }
}
