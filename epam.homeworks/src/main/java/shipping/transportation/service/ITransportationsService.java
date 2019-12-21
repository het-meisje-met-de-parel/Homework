package shipping.transportation.service;

import java.util.List;

import shipping.cargo.domain.Cargo;
import shipping.cargo.service.ICargosService;
import shipping.carrier.domain.Carrier;
import shipping.carrier.service.ICarriersService;
import shipping.exceptions.NotExistingEntityExeption;
import shipping.transportation.domain.Transportation;

public interface ITransportationsService {

    public void setCarriersService (ICarriersService carriersService);
    
    public void setCargosService (ICargosService cargosService);
    
    Transportation createAndAdd(Cargo cargo, Carrier carrier) throws NotExistingEntityExeption;

    Transportation delete(Transportation transportation) throws NotExistingEntityExeption;

    Transportation get(Long id) throws NotExistingEntityExeption;
    
    Transportation update (Transportation transportation) throws NotExistingEntityExeption;
    
    List <Transportation> getByCarrier (Carrier carrier) throws NotExistingEntityExeption;
    
    List <Transportation> getByCargo (Cargo cargo) throws NotExistingEntityExeption;

    List <Transportation> getAll();

}
