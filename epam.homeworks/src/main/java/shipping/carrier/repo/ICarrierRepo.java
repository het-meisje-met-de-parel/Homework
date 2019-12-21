package shipping.carrier.repo;

import java.util.List;

import shipping.carrier.domain.Carrier;
import shipping.exceptions.NotDeletedException;
import shipping.exceptions.NotExistingEntityExeption;

public interface ICarrierRepo {

    void add(Carrier carrier);

    Carrier delete(Carrier carrier) throws NotDeletedException;

    Carrier get(Long id) throws NotExistingEntityExeption;

    List <Carrier> getAll();

}
