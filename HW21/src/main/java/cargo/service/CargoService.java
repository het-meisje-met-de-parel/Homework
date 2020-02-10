package cargo.service;

import cargo.domain.Cargo;
import cargo.search.CargoSearchCondition;
import common.business.service.CommonService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CargoService extends CommonService<Cargo, Long> {

    Optional<Cargo> getByIdFetchingTransportations(Long id);

    List<Cargo> findByName(String name);

    List<Cargo> search(CargoSearchCondition cargoSearchCondition);

    public List <LocalDate> getUniqueExpirationDatesOfFoodCargosAfterDate (LocalDate date);

}
