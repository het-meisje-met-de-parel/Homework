package shipping.cargo.domain;

import shipping.common.domain.AbstractTransportableEntity;

public abstract class Cargo extends AbstractTransportableEntity {

    protected final CargoType cargoType;
    protected final int weight;

    public Cargo (CargoType cargoType, String name, int weight) {
        super (name);
        this.cargoType = cargoType;
        this.weight = weight;
    }
    
    @Override
    protected void setId (Long id) {
        super.setId (id);
    }

    public int getWeight() {
        return weight;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    @Override
    public String toString () {
        return String.format("Cargo #%-4d %16s (%-8s), weight %5d, in %2d transportations",
                id, name, cargoType.name(), weight, transportations.size ());
    }
    
    @Override
    public abstract Cargo clone ();

}
