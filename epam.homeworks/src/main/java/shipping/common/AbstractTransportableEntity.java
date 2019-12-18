package shipping.common;

import java.util.ArrayList;
import java.util.List;

import shipping.transportation.domain.Transportation;

public abstract class AbstractTransportableEntity extends AbstractEntity {

    protected List <Transportation> transportations = new ArrayList <> ();

    protected final String name;

    public AbstractTransportableEntity (String name) {
        this.name = name;
    }

    public List <Transportation> getTransportations () {
        return transportations;
    }

    public String getName() {
        return name;
    }

}
