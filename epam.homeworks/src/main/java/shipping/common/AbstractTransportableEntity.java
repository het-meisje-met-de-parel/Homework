package shipping.common;

import shipping.transportation.Transportation;

public abstract class AbstractTransportableEntity extends AbstractEntity {

    protected Transportation [] transportations;

    protected final String name;

    public AbstractTransportableEntity (String name) {
        this.name = name;
    }

    public Transportation[] getTransportations() {
        return transportations;
    }

    public void setTransportations(Transportation[] transportations) {
        this.transportations = transportations;
    }

    public String getName() {
        return name;
    }

}
