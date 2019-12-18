package shipping.carrier.service;

import java.util.List;

import shipping.carrier.domain.Carrier;
import shipping.carrier.domain.CarrierType;

public interface ICarriersService {

    void add(Carrier carrier);

    Carrier delete(Carrier carrier);

    Carrier get(Long id);

    List <Carrier> getByName (String name);
    
    List <Carrier> getByType (CarrierType type);

    List <Carrier> getAll();

}
