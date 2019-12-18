package shipping.carrier.domain;

import shipping.common.AbstractTransportableEntity;

public class Carrier extends AbstractTransportableEntity {

    private String address;
    private CarrierType carrierType;

    public Carrier(String name) {
        super(name);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CarrierType getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(CarrierType carrierType) {
        this.carrierType = carrierType;
    }

    @Override
    public String toString () {
        return String.format("Carrier #%d %s (%s), address '%s', in %d transportations",
                id, name, carrierType.name(), address, transportations.size ());
    }

}
