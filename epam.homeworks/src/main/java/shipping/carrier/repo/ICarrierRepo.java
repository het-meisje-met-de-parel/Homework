package shipping.carrier.repo;

import java.util.List;

import shipping.carrier.domain.Carrier;

public interface ICarrierRepo {

    void add(Carrier carrier);

    Carrier delete(Carrier carrier);

    Carrier get(Long id);

    List <Carrier> getAll();

}
