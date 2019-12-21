package shipping.carrier.service;

import java.util.List;

import shipping.carrier.domain.Carrier;
import shipping.carrier.domain.CarrierType;
import shipping.exceptions.NotExistingEntityExeption;

public interface ICarriersService {

    void add(Carrier carrier);

    Carrier delete(Carrier carrier);

    Carrier get(Long id) throws NotExistingEntityExeption;
    
    Carrier update (Carrier carrier) throws NotExistingEntityExeption;

    List <Carrier> getByName (String name);
    
    List <Carrier> getByType (CarrierType type);

    List <Carrier> getAll();

}
