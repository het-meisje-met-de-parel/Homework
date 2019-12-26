package carrier.repo;

import carrier.domain.Carrier;
import common.business.repo.CommonRepo;

import java.util.List;

public interface CarrierRepo extends CommonRepo<Carrier, Long> {

  Carrier getByIdFetchingTransportations(long id);

  Carrier[] findByName(String name);

}
