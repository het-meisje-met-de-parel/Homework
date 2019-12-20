package shipping.transportation.service;

import java.util.List;

import shipping.cargo.domain.Cargo;
import shipping.cargo.service.ICargosService;
import shipping.carrier.domain.Carrier;
import shipping.carrier.service.ICarriersService;
import shipping.transportation.domain.Transportation;

public interface ITransportationsService {

    public void setCarriersService (ICarriersService carriersService);
    
    public void setCargosService (ICargosService cargosService);
    
    Transportation createAndAdd(Cargo cargo, Carrier carrier);

    Transportation delete(Transportation transportation);

    Transportation get(Long id);
    
    Transportation update (Transportation transportation);
    
    List <Transportation> getByCarrier (Carrier carrier);
    
    List <Transportation> getByCargo (Cargo cargo);

    List <Transportation> getAll();

}
