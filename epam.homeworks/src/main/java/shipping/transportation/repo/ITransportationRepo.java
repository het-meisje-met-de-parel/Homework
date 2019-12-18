package shipping.transportation.repo;

import java.util.List;

import shipping.transportation.domain.Transportation;

public interface ITransportationRepo {

    void add(Transportation transportation);

    Transportation delete(Transportation transportation);

    Transportation get(Long id);

    List <Transportation> getAll();

}
