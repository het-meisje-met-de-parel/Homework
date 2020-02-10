package cargo.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cargo.domain.Cargo;
import cargo.domain.CargoType;
import cargo.domain.FoodCargo;
import cargo.exception.unckecked.CargoDeleteConstraintViolationException;
import cargo.repo.CargoRepo;
import cargo.search.CargoSearchCondition;
import carrier.domain.Carrier;
import carrier.service.CarrierService;
import lombok.Setter;
import storage.IdGenerator;
import transportation.domain.Transportation;
import transportation.service.TransportationService;

public class CargoServiceImpl implements CargoService {

  @Setter
  private TransportationService transportationService;
  
  @Setter
  private CarrierService carrierService;
    
  private CargoRepo cargoRepo;

  public CargoServiceImpl(CargoRepo cargoRepo) {
    this.cargoRepo = cargoRepo;
  }

  @Override
  public void save(Cargo cargo) {
    if (cargo.getId () == null) {
        cargo.setId (IdGenerator.generateId ());
    }
    
    if (cargoRepo.findById (cargo.getId ()).isPresent ()) {
        update (cargo);
    } else {
        cargoRepo.save(cargo);
    }
  }

  @Override
  public Optional<Cargo> findById(Long id) {
    if (id != null) {
      return cargoRepo.findById(id);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Cargo> getByIdFetchingTransportations(Long id) {
    if (id != null) {
      return cargoRepo.getByIdFetchingTransportations(id);
    }
    return Optional.empty();
  }

  @Override
  public List<Cargo> getAll() {
    return cargoRepo.getAll();
  }

  @Override
  public int countAll() {
    return this.cargoRepo.countAll();
  }

  @Override
  public List<Cargo> findByName(String name) {
    Cargo[] found = cargoRepo.findByName(name);
    return (found == null || found.length == 0) ? Collections.emptyList() : Arrays.asList(found);
  }

  @Override
  public boolean deleteById(Long id) {
    Optional<Cargo> cargoOptional = this.getByIdFetchingTransportations(id);

    if (cargoOptional.isPresent()) {
      Cargo cargo = cargoOptional.get ();
      List<Transportation> transportations = transportationService.findByCargo (cargo);
      boolean hasTransportations = transportations != null && transportations.size() > 0;
      if (hasTransportations) {
        throw new CargoDeleteConstraintViolationException(id);
      }

      return cargoRepo.deleteById(id);
    } else {
      return false;
    }
  }

  @Override
  public void printAll() {
    List<Cargo> allCargos = cargoRepo.getAll();

    for (Cargo cargo : allCargos) {
      System.out.println(cargo);
    }
  }

  @Override
  public boolean update(Cargo cargo) {
    if (cargo != null) {
      return cargoRepo.update(cargo);
    }

    return false;
  }

  @Override
  public List<Cargo> search(CargoSearchCondition cargoSearchCondition) {
    return cargoRepo.search(cargoSearchCondition);
  }

  @Override
  public List <LocalDate> getUniqueExpirationDatesOfFoodCargosAfterDate (LocalDate date) {
    return getAll().stream().filter(a -> a.getCargoType().equals(CargoType.FOOD)).map(a -> (FoodCargo) a)
            .map(FoodCargo::getExpirationDate).distinct().filter(a -> a.isAfter(date)).collect(Collectors.toList());
  }

  @Override
  public void saveCargoAndCarrier (Cargo cargo, Carrier carrier) {
      carrierService.deleteById (carrier.getId ());
      cargoRepo.deleteById (cargo.getId ());
      
      cargoRepo.saveCargoAndCarrier (cargo, carrier);
  }

}