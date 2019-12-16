package shipping.transportation.repo;

import shipping.carrier.domain.Carrier;
import shipping.storage.Storage;
import shipping.transportation.domain.Transportation;

public class TransportationRepo implements ITransportationRepo {

    private final Storage storage;
    
    public TransportationRepo (Storage storage) {
        this.storage = storage;
    }
    
    @Override
    public void add (Transportation transportation) {
        System.err.println ("Not supported");
    }

    @Override
    public Carrier delete (Transportation transportation) {
        System.err.println ("Not supported");
        return null;
    }

    @Override
    public Transportation get (Long id) {
        return storage.getTransportationById (id);
    }

    @Override
    public Transportation [] getAll () {
        System.err.println ("Not supported");
        return null;
    }
    
}
