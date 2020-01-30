package carrier.service;

import carrier.domain.Carrier;
import common.business.service.CommonService;

import java.util.List;
import java.util.Optional;

public interface CarrierService extends CommonService<Carrier, Long> {

  Optional<Carrier> getByIdFetchingTransportations(Long id);

  List<Carrier> findByName(String name);

}
