package transportation.repo;

import java.util.List;

import cargo.domain.Cargo;
import carrier.domain.Carrier;
import common.business.repo.CommonRepo;
import transportation.domain.Transportation;

public interface TransportationRepo extends CommonRepo<Transportation, Long> {
    
    List <Transportation> findByCargo (Cargo cargo);
    
    List <Transportation> findByCarrier (Carrier carrier);
    
}
