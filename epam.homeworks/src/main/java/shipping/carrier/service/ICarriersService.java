package shipping.carrier.service;

import java.util.List;

import shipping.carrier.domain.Carrier;
import shipping.carrier.domain.CarrierType;
import shipping.common.service.ITransportableEntityService;

public interface ICarriersService extends ITransportableEntityService <Carrier> {
    
    List <Carrier> getByType (CarrierType type);

}
