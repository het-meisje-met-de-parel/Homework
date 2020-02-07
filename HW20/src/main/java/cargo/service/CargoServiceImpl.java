package cargo.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import application.db.DBConnectionProducer;
import cargo.domain.Cargo;
import cargo.domain.CargoType;
import cargo.domain.FoodCargo;
import cargo.exception.unckecked.CargoDeleteConstraintViolationException;
import cargo.repo.CargoRepo;
import cargo.search.CargoSearchCondition;
import transportation.domain.Transportation;
import transportation.service.TransportationService;

public class CargoServiceImpl implements CargoService {

  private TransportationService transportationService;
  private CargoRepo cargoRepo;

  public CargoServiceImpl(CargoRepo cargoRepo) {
    this.cargoRepo = cargoRepo;
  }
      
  @Override
  public void setTransportationService (TransportationService transportationService) {
    this.transportationService = transportationService;
  }

  @Override
  public void save(Cargo cargo) {
    cargoRepo.save(cargo);
  }
  
  @Override // transactional
  public void saveSeveral (List <Cargo> entities) {
      DBConnectionProducer.openTransaction ();
      CargoService.super.saveSeveral (entities);
      DBConnectionProducer.closeTransaction ();
  }

  @Override
  public Optional<Cargo> findById(Long id) {
    if (id != null) {
      return cargoRepo.findById(id).map (this::setTransportations);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Cargo> getByIdFetchingTransportations(Long id) {
    if (id != null) {
      return cargoRepo.getByIdFetchingTransportations(id).map (this::setTransportations);
    }
    return Optional.empty();
  }

  @Override
  public List<Cargo> getAll() {
    return cargoRepo.getAll().stream ().map (this::setTransportations).collect (Collectors.toList ());
  }

  @Override
  public int countAll() {
    return this.cargoRepo.countAll();
  }

  @Override
  public List<Cargo> findByName(String name) {
    Cargo[] found = cargoRepo.findByName(name);
    return (found == null || found.length == 0) ? Collections.emptyList() 
         : Arrays.asList(found).stream ().map (this::setTransportations)
             .collect (Collectors.toList ());
  }

  @Override
  public boolean deleteById(Long id) {
    Optional<Cargo> cargoOptional = getByIdFetchingTransportations(id);

    if (cargoOptional.isPresent()) {
      List<Transportation> transportations = cargoOptional.get().getTransportations();
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
    for (Cargo cargo : getAll ()) {
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
    return cargoRepo.search(cargoSearchCondition).stream ()
         . map (this::setTransportations)
         . collect (Collectors.toList ());
  }

  @Override
  public List <LocalDate> getUniqueExpirationDatesOfFoodCargosAfterDate (LocalDate date) {
    return getAll().stream().filter(a -> a.getCargoType().equals(CargoType.FOOD)).map(a -> (FoodCargo) a)
            .map(FoodCargo::getExpirationDate).distinct().filter(a -> a.isAfter(date)).collect(Collectors.toList());
  }
  
  private Cargo setTransportations (Cargo cargo) {
      final var transportations = transportationService.getAll ().stream ()
          . filter (tr -> tr.getCargo ().equals (cargo))
          . collect (Collectors.toList ());
      cargo.setTransportations (transportations);
      return cargo;
  }

}
