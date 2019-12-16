package shipping.cargo.service;

import shipping.cargo.domain.Cargo;

public interface ICargosService {

    void add(Cargo cargo);

    Cargo delete(Cargo cargo);

    Cargo get(Long id);

    Cargo get(String name);

    Cargo[] getAll();

}
