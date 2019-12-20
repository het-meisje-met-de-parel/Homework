package shipping.transportation.repo;

import java.util.ArrayList;
import java.util.List;

import shipping.transportation.domain.Transportation;

public class TransportationRepo implements ITransportationRepo {
    
    private final List <Transportation> storage = new ArrayList <> ();
    
    @Override
    public void add (Transportation transportation) {
        storage.add (transportation);
    }

    @Override
    public Transportation delete (Transportation transportation) {
        if (storage.remove (transportation)) {
            return transportation;
        }
        
        return null;
    }

    @Override
    public Transportation get (Long id) {
        for (Transportation transportation : storage) {
            if (transportation.getId ().equals (id)) {
                return transportation.clone ();
            }
        }
        
        return null;
    }

    @Override
    public List <Transportation> getAll () {
        List <Transportation> result = new ArrayList<>();
        for (Transportation transportation : storage) {
            result.add(transportation.clone ());
        }
        return result;
    }
    
}
