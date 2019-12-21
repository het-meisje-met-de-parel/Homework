package shipping.transportation.repo;

import java.util.ArrayList;
import java.util.List;

import shipping.exceptions.NotDeletedException;
import shipping.exceptions.NotExistingEntityExeption;
import shipping.transportation.domain.Transportation;

public class TransportationRepo implements ITransportationRepo {
    
    private final List <Transportation> storage = new ArrayList <> ();
    
    @Override
    public void add (Transportation transportation) {
        storage.add (transportation);
    }

    @Override
    public Transportation delete (Transportation transportation) throws NotDeletedException {
        if (storage.remove (transportation)) {
            return transportation;
        }

        throw new NotDeletedException("Объект не существует");
    }

    @Override
    public Transportation get (Long id) throws NotExistingEntityExeption {
        for (Transportation transportation : storage) {
            if (transportation.getId ().equals (id)) {
                return transportation.clone ();
            }
        }
        
        throw new NotExistingEntityExeption("Объект не сущетвует");
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
