package shipping.carrier.service;

import shipping.carrier.domain.Carrier;

public interface ICarriersService {

    void add(Carrier carrier);

    Carrier delete(Carrier carrier);

    Carrier get(Long id);

    Carrier get(String name);

    Carrier[] getAll();

}
