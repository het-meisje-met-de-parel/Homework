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
        return String.format("Carrier #%-4d %16s (%-8s), address '%s', in %2d transportations",
                id, name, carrierType.name(), address, transportations.size ());
    }
    
    @Override
    public Carrier clone () {
        Carrier carrier = new Carrier (getName());
        carrier.setCarrierType (getCarrierType());
        carrier.setAddress (getAddress());
        carrier.setId (getId ());
        return carrier;
    }

}
