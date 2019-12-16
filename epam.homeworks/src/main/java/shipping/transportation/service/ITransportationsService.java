package shipping.transportation.service;

import shipping.carrier.domain.Carrier;
import shipping.transportation.domain.Transportation;

public interface ITransportationsService {

    void add(Transportation transportation);

    Carrier delete(Transportation transportation);

    Transportation get(Long id);

    Transportation[] getAll();

}
