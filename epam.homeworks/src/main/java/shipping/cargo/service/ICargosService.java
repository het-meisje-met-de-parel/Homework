package shipping.cargo.service;

import java.util.List;

import shipping.cargo.domain.Cargo;
import shipping.cargo.domain.CargoType;

public interface ICargosService {

    void add(Cargo cargo);

    Cargo delete(Cargo cargo);

    Cargo get(Long id);
    
    Cargo update(Cargo cargo);

    List <Cargo> getByName (String name);
    
    List <Cargo> getByType (CargoType type);

    List <Cargo> getAll();
    
    List <Cargo> getSortedByName ();
    
    List <Cargo> getSortedByWeight ();
    
    List <Cargo> getSortedByNameAndWeight ();

}