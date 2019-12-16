package shipping.cargo.domain;

import shipping.common.AbstractTransportableEntity;
import shipping.transportation.domain.Transportation;

public abstract class Cargo extends AbstractTransportableEntity {

    protected final CargoType cargoType;
    protected final int weight;

    public Cargo (CargoType cargoType, String name, int weight) {
        super (name);
        this.cargoType = cargoType;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    @Override
    public String toString () {
        return String.format("Cargo #%d %s (%s), weight %d, in %d transportations",
                id, name, cargoType.name(), weight, transportations.length);
    }

}
