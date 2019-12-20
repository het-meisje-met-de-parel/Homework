package shipping.carrier.repo;

import java.util.ArrayList;
import java.util.List;

import shipping.carrier.domain.Carrier;

public class CarrierRepo implements ICarrierRepo {
    
    private final List <Carrier> storage = new ArrayList <> ();
    
    @Override
    public void add (Carrier carrier) {
        storage.add (carrier);
    }
    
    @Override
    public Carrier delete (Carrier carrier) {
        if (storage.remove (carrier)) {
            return carrier;
        }
        
        return null;
    }
    
    @Override
    public Carrier get (Long id) {
        for (Carrier carrier : storage) {
            if (carrier.getId ().equals (id)) {
                return carrier.clone ();
            }
        }
        
        return null;
    }
    
    @Override
    public List <Carrier> getAll () {
        List <Carrier> result = new ArrayList<>();
        for (Carrier carrier : storage) {
            result.add(carrier.clone ());
        }
        return result;
    }
    
}
