package shipping.transportation.repo;

import shipping.carrier.domain.Carrier;
import shipping.transportation.domain.Transportation;

public interface ITransportationRepo {

    void add(Transportation transportation);

    Carrier delete(Transportation transportation);

    Transportation get(Long id);

    Transportation[] getAll();

}
