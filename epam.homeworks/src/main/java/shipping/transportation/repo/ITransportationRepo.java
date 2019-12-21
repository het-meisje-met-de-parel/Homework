package shipping.transportation.repo;

import java.util.List;

import shipping.exceptions.NotDeletedException;
import shipping.exceptions.NotExistingEntityExeption;
import shipping.transportation.domain.Transportation;

public interface ITransportationRepo {

    void add(Transportation transportation);

    Transportation delete(Transportation transportation) throws NotDeletedException;

    Transportation get(Long id) throws NotExistingEntityExeption;

    List <Transportation> getAll();

}
