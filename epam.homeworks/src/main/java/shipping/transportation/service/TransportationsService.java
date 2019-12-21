package shipping.transportation.service;

import java.util.Collections;
import java.util.List;

import shipping.cargo.domain.Cargo;
import shipping.cargo.service.ICargosService;
import shipping.carrier.domain.Carrier;
import shipping.carrier.service.ICarriersService;
import shipping.exceptions.NotExistingEntityExeption;
import shipping.transportation.domain.Transportation;
import shipping.transportation.repo.ITransportationRepo;

public class TransportationsService implements ITransportationsService {
    
    private final ITransportationRepo repo;
    
    private ICarriersService carriersService;
    private ICargosService cargosService;
    
    public TransportationsService (ITransportationRepo repo) {
        this.repo = repo;
    }
    
    @Override
    public void setCarriersService (ICarriersService carriersService) {
        this.carriersService = carriersService;
    }
    
    @Override
    public void setCargosService (ICargosService cargosService) {
        this.cargosService = cargosService;
    }

    @Override
    public Transportation createAndAdd (Cargo cargo, Carrier carrier) throws NotExistingEntityExeption {
        if (cargo == null || carrier == null) {
            System.err.println ("Cargo and carrier must be not null");
            return null;
        }
        
        carrier = carriersService.get (carrier.getId ());
        cargo = cargosService.get (cargo.getId ());
        
        if (carrier == null) {
            System.err.println ("Carrier doesn't exist");
            return null;
        }
        
        if (cargo == null) {
            System.err.println ("Cargo doesn't exist");
            return null;
        }
        
        Transportation transportation = new Transportation (cargo, carrier);
        carrier.getTransportations ().add (transportation);
        cargo.getTransportations ().add (transportation);
        repo.add (transportation);
        return transportation;
    }

    @Override
    public Transportation delete (Transportation transportation) throws NotExistingEntityExeption {
        if (repo.delete (transportation) != null) {
            Carrier carrier = carriersService.get (transportation.getCarrier ().getId ());
            carrier.getTransportations ().remove (transportation);
            
            Cargo cargo = cargosService.get (transportation.getCargo ().getId ());
            cargo.getTransportations ().remove (transportation);
            
            return transportation;
        }
        
        return null;
    }

    @Override
    public Transportation get (Long id) throws NotExistingEntityExeption {
        return repo.get (id);
    }
    
    @Override
    public Transportation update (Transportation transportation) throws NotExistingEntityExeption {
        if (delete (transportation) != null) {
            repo.add (transportation);
        }
        
        return get (transportation.getId ());
    }

    @Override
    public List <Transportation> getByCarrier (Carrier carrier)  throws NotExistingEntityExeption{
        if (carrier == null) {
            System.err.println ("Carrier must be not null");
            return Collections.emptyList();
        }
        
        carrier = carriersService.get (carrier.getId ());
        
        return carrier.getTransportations ();
    }

    @Override
    public List <Transportation> getByCargo (Cargo cargo) throws NotExistingEntityExeption {
        if (cargo == null) {
            System.err.println ("Cargo must be not null");
            return Collections.emptyList();
        }
        
        cargo = cargosService.get (cargo.getId ());
        
        return cargo.getTransportations ();
    }

    @Override
    public List <Transportation> getAll () {
        return repo.getAll ();
    }
    
}
