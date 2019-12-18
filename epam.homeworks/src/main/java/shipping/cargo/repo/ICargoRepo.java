package shipping.cargo.repo;

import java.util.List;

import shipping.cargo.domain.Cargo;

public interface ICargoRepo {

    void add(Cargo cargo);

    Cargo delete(Cargo cargo);

    Cargo get(Long id);

    List <Cargo> getAll();

}
