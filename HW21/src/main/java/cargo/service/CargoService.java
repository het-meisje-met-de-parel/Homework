package cargo.service;

import cargo.domain.Cargo;
import cargo.search.CargoSearchCondition;
import carrier.domain.Carrier;
import common.business.service.CommonService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CargoService extends CommonService<Cargo, Long> {

    Optional<Cargo> getByIdFetchingTransportations(Long id);

    List<Cargo> findByName(String name);

    List<Cargo> search(CargoSearchCondition cargoSearchCondition);

    List <LocalDate> getUniqueExpirationDatesOfFoodCargosAfterDate (LocalDate date);
    
    void saveCargoAndCarrier (Cargo cargo, Carrier carrier);

}
