package shipping.transportation.service;

import java.util.List;

import shipping.cargo.domain.Cargo;
import shipping.cargo.service.ICargosService;
import shipping.carrier.domain.Carrier;
import shipping.carrier.service.ICarriersService;
import shipping.common.service.IEntityService;
import shipping.exceptions.NotExistingEntityExeption;
import shipping.transportation.domain.Transportation;

public interface ITransportationsService extends IEntityService <Transportation> {

    void setCarriersService (ICarriersService carriersService);
    
    void setCargosService (ICargosService cargosService);
    
    Transportation createAndAdd(Cargo cargo, Carrier carrier) throws NotExistingEntityExeption;
    
    List <Transportation> getByCarrier (Carrier carrier) throws NotExistingEntityExeption;
    
    List <Transportation> getByCargo (Cargo cargo) throws NotExistingEntityExeption;

}
