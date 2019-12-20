package shipping.cargo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import shipping.cargo.domain.Cargo;
import shipping.cargo.domain.CargoType;
import shipping.cargo.repo.ICargoRepo;

public class CargosService implements ICargosService {

    private final ICargoRepo repo;
    
    public CargosService (ICargoRepo repo) {
        this.repo = repo;
    }
    
    @Override
    public void add (Cargo cargo) {
        if (cargo.getId () == null) {
            System.err.println ("Cargo doesn't have id");
            return;
        }
        
        if (cargo.getName () == null || cargo.getCargoType () == null) {
            System.err.println ("Cargo doesn't have name or type");
            return;
        }
        
        repo.add (cargo);
    }

    @Override
    public Cargo delete (Cargo cargo) {
        return repo.delete (cargo);
    }

    @Override
    public Cargo get (Long id) {
        return repo.get (id);
    }
    
    @Override
    public Cargo update (Cargo cargo) {
        if (delete (cargo) != null) {
            add (cargo);
        }
        
        return get (cargo.getId ());
    }

    @Override
    public List <Cargo> getByName (String name) {
        List <Cargo> result = new ArrayList <> ();
        if (name == null) { return result; }
        
        for (Cargo cargo : getAll ()) {
            if (name.equals (cargo.getName ())) {
                result.add (cargo);
            }
        }
        
        return result;
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
    public List <Cargo> getAll () {
        return repo.getAll ();
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
