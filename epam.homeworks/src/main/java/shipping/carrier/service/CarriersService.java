package shipping.carrier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import shipping.carrier.domain.Carrier;
import shipping.carrier.domain.CarrierType;
import shipping.carrier.repo.ICarrierRepo;
import shipping.common.service.AbstractTransportableEntityService;
import shipping.exceptions.IdentifierMissedException;

public class CarriersService extends AbstractTransportableEntityService <Carrier> implements ICarriersService {
    
    public CarriersService (ICarrierRepo repo) {
        super (repo);
    }

    @Override
    public void add (Carrier carrier) throws IdentifierMissedException {
        Objects.requireNonNull (carrier);
        if (carrier.getCarrierType () == null) {
            throw new IdentifierMissedException ("Entity type is null");
        }
        
        super.add (carrier);
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
    
}
