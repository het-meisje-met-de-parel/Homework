package shipping.cargo.repo;

import java.util.ArrayList;
import java.util.List;

import shipping.cargo.domain.Cargo;
import shipping.exceptions.NotDeletedException;
import shipping.exceptions.NotExistingEntityExeption;

public class CargoRepo implements ICargoRepo {
    
    private final List <Cargo> storage = new ArrayList <> ();
    
    @Override
    public void add (Cargo cargo) {
        storage.add (cargo);
    }

    @Override
    public Cargo delete (Cargo cargo) throws NotDeletedException {
        if (storage.remove (cargo)) {
            return cargo;
        }
        
        throw new NotDeletedException ("Объект не существует");
    }
    
    @Override
    public Cargo get (Long id) throws NotExistingEntityExeption {
        for (Cargo cargo : storage) {
            if (cargo.getId ().equals (id)) {
                return cargo.clone ();
            }
        }
        
        throw new NotExistingEntityExeption("Объект не существует");
    }
    
    @Override
    public List <Cargo> getAll () {
        List <Cargo> result = new ArrayList <> ();
        for (Cargo cargo : storage) {
            result.add(cargo.clone ());
        }
        return result;
    }
    
}
