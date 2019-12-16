package shipping.carrier.repo;

import shipping.carrier.domain.Carrier;

public interface ICarrierRepo {

    void add(Carrier carrier);

    Carrier delete(Carrier carrier);

    Carrier get(Long id);

    Carrier get(String name);

    Carrier[] getAll();

}
