package cargo.service;

import cargo.domain.Cargo;
import cargo.exception.unckecked.CargoDeleteConstraintViolationException;
import cargo.repo.CargoRepo;
import cargo.search.CargoSearchCondition;
import transportation.domain.Transportation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CargoServiceImpl implements CargoService {

  private CargoRepo cargoRepo;

  public CargoServiceImpl(CargoRepo cargoRepo) {
    this.cargoRepo = cargoRepo;
  }

  @Override
  public void save(Cargo cargo) {
    cargoRepo.save(cargo);
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
}
