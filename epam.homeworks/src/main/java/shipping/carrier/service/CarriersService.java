package shipping.carrier.service;

import java.util.ArrayList;
import java.util.List;

import shipping.carrier.domain.Carrier;
import shipping.carrier.domain.CarrierType;
import shipping.carrier.repo.ICarrierRepo;
import shipping.exceptions.NotExistingEntityExeption;

public class CarriersService implements ICarriersService {
    
    private final ICarrierRepo repo;
    
    public CarriersService (ICarrierRepo repo) {
        this.repo = repo;
    }

    @Override
    public void add (Carrier carrier) {
        if (carrier.getId () == null) {
            System.err.println ("Carrier doesn't have id");
            return;
        }
        
        if (carrier.getName () == null || carrier.getCarrierType () == null) {
            System.err.println ("Carrier doesn't have name or type");
            return;
        }
        
        repo.add (carrier);
    }

    @Override
    public Carrier delete (Carrier carrier) {
        return repo.delete (carrier);
    }

    @Override
    public Carrier get (Long id) throws NotExistingEntityExeption {
        return repo.get (id);
    }
    
    @Override
    public Carrier update (Carrier carrier) throws NotExistingEntityExeption{
        if (delete (carrier) != null) {
            add (carrier);
        }
        
        return get (carrier.getId ());
    }

    @Override
    public List <Carrier> getByName (String name) {
        List <Carrier> result = new ArrayList <> ();
        if (name == null) { return result; }
        
        for (Carrier carrier : getAll ()) {
            if (name.equals (carrier.getName ())) {
                result.add (carrier);
            }
        }
        
        return result;
    }

    @Override
    public List <Carrier> getByType (CarrierType type) {
        List <Carrier> result = new ArrayList <> ();
        if (type == null) { return result; }
        
        for (Carrier carrier : getAll ()) {
            if (type.equals (carrier.getCarrierType ())) {
                result.add (carrier);
            }
        }
        
        return result;
    }

    @Override
    public List <Carrier> getAll () {
        return repo.getAll ();
    }
    
}
