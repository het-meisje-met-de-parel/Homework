package shipping.cargo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import shipping.cargo.domain.Cargo;
import shipping.cargo.domain.CargoType;
import shipping.cargo.repo.ICargoRepo;
import shipping.common.service.AbstractTransportableEntityService;
import shipping.exceptions.IdentifierMissedException;

public class CargosService extends AbstractTransportableEntityService <Cargo> implements ICargosService {
    
    public CargosService (ICargoRepo repo) {
        super (repo);
    }
    
    @Override
    public void add (Cargo cargo) throws IdentifierMissedException {
        Objects.requireNonNull (cargo);
        if (cargo.getCargoType () == null) {
            throw new IdentifierMissedException ("Entity type is null");
        }
        
        super.add (cargo);
    }

    @Override
    public List <Cargo> getByType (CargoType type) {
        List <Cargo> result = new ArrayList <> ();
        if (type == null) { return result; }
        
        for (Cargo cargo : getAll ()) {
            if (type.equals (cargo.getCargoType ())) {
                result.add (cargo);
            }
        }
        
        return result;
    }
    
    @Override
    public List <Cargo> getSortedByName () {
        List <Cargo> result = getAll ();
        result.sort (new Comparator <Cargo> () {
            @Override
            public int compare (Cargo o1, Cargo o2) {
                return o1.getName ().compareToIgnoreCase (o2.getName ());
            }
        });
        
        return result;
    }
    
    @Override
    public List <Cargo> getSortedByWeight () {
        List <Cargo> result = getAll ();
        result.sort (new Comparator <Cargo> () {
            @Override
            public int compare (Cargo o1, Cargo o2) {
                return Integer.compare (o1.getWeight (), o2.getWeight ());
            }
        });
        
        return result;
    }
    
    @Override
    public List <Cargo> getSortedByNameAndWeight () {
        List <Cargo> result = getAll ();
        result.sort (new Comparator <Cargo> () {
            @Override
            public int compare (Cargo o1, Cargo o2) {
                int compare = o1.getName ().compareToIgnoreCase (o2.getName ());
                if (compare != 0) { return compare; }
                
                return Integer.compare (o1.getWeight (), o2.getWeight ());
            }
        });
        
        return result;
    }
    
}
