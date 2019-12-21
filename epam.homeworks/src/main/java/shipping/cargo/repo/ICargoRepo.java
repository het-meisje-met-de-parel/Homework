package shipping.cargo.repo;

import java.util.List;

import shipping.cargo.domain.Cargo;
import shipping.exceptions.NotDeletedException;
import shipping.exceptions.NotExistingEntityExeption;

public interface ICargoRepo {

    void add(Cargo cargo);

    Cargo delete(Cargo cargo) throws NotDeletedException;

    Cargo get(Long id) throws NotExistingEntityExeption;

    List <Cargo> getAll();

}
