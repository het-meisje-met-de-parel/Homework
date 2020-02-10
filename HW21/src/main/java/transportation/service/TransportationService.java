package transportation.service;

import java.util.List;

import cargo.domain.Cargo;
import carrier.domain.Carrier;
import common.business.service.CommonService;
import transportation.domain.Transportation;

public interface TransportationService extends CommonService<Transportation, Long> {
    
    List <Transportation> findByCargo (Cargo cargo);
    
    List <Transportation> findByCarrier (Carrier carrier);

}
