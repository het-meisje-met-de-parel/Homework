package shipping.cargo.repo;

import shipping.cargo.domain.Cargo;

public interface ICargoRepo {

    void add(Cargo cargo);

    Cargo delete(Cargo cargo);

    Cargo get(Long id);

    Cargo get(String name);

    Cargo[] getAll();

}
