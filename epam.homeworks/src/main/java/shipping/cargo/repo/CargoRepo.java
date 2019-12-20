package shipping.cargo.repo;

import java.util.ArrayList;
import java.util.List;

import shipping.cargo.domain.Cargo;

public class CargoRepo implements ICargoRepo {
    
    private final List <Cargo> storage = new ArrayList <> ();
    
    @Override
    public void add (Cargo cargo) {
        storage.add (cargo);
    }
    
    @Override
    public Cargo delete (Cargo cargo) {
        if (storage.remove (cargo)) {
            return cargo;
        }
        
        return null;
    }
    
    @Override
    public Cargo get (Long id) {
        for (Cargo cargo : storage) {
            if (cargo.getId ().equals (id)) {
                return cargo.clone ();
            }
        }
        
        return null;
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
