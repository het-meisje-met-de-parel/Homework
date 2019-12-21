package shipping.carrier.repo;

import java.util.ArrayList;
import java.util.List;

import shipping.carrier.domain.Carrier;
import shipping.exceptions.NotDeletedException;
import shipping.exceptions.NotExistingEntityExeption;

public class CarrierRepo implements ICarrierRepo {
    
    private final List <Carrier> storage = new ArrayList <> ();
    
    @Override
    public void add (Carrier carrier) {
        storage.add (carrier);
    }
    
    @Override
    public Carrier delete (Carrier carrier) throws NotDeletedException {
        if (storage.remove (carrier)) {
            return carrier;
        }

        throw new NotDeletedException("Объект не существует");
    }
    
    @Override
    public Carrier get (Long id) throws NotExistingEntityExeption {
        for (Carrier carrier : storage) {
            if (carrier.getId ().equals (id)) {
                return carrier.clone();
            }
        }

        throw new NotExistingEntityExeption("Объект не существует");
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
