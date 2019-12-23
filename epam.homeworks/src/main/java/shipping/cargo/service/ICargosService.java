package shipping.cargo.service;

import java.util.List;

import shipping.cargo.domain.Cargo;
import shipping.cargo.domain.CargoType;
import shipping.common.service.ITransportableEntityService;

public interface ICargosService extends ITransportableEntityService <Cargo> {
    
    List <Cargo> getByType (CargoType type);
    
    List <Cargo> getSortedByName ();
    
    List <Cargo> getSortedByWeight ();
    
    List <Cargo> getSortedByNameAndWeight ();

}
